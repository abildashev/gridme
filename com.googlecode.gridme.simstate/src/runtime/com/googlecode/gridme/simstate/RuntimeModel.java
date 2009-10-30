/*******************************************************************************
 * Copyright (c) 2009 Dmitry Grushin <dgrushin@gmail.com>.
 * 
 * This file is part of GridMe.
 * 
 * GridMe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GridMe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with GridMe.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Dmitry Grushin <dgrushin@gmail.com> - initial API and implementation
 ******************************************************************************/
package com.googlecode.gridme.simstate;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The main class that drives simulation. Simulation can be spread
 * to a set of threads according to a supervisor worker pattern.
 */
public class RuntimeModel implements Runnable
{
  public static final long TIME_WAIT_POOL = 10000;

  private final Calendar startRealTime; // Real start time of experiment
  private long modelTime; // current model time
  private long stopTime; // time to stop
  private final long startTime; // start time of the experiment
  private ActiveElement model; // model root object
  private ModelProgressMonitor progress;
  private ModelErrorLogger errors;
  private ModelProfileLogger profile;
  private long shortestTimer; // minimal timer
  private long timerDecrease; // timer decrease value (saved shortest timer)
  private boolean transition; // if at least one transition has been made
  private boolean finished; // if all state machines have finished

  private Thread modelExec; // main execution thread
  private ModelExecControl control; // control object
  private ElementQueue elementQueue; // queue of elements to execute by pool executors  
  private ThreadPoolExecutor threadPool; // the pool of threads
  private ElementsExecControl elemControl; // control object to communicate with pool executors.
  private int threadsCount; // threads count
  private int currentActiveExecutors; // active executors count
  private Object startIterationLock; // lock to communicate with pool executors
  private Object endIterationLock; // lock to wait for executors
  private int finishedCount; // number of finished workers in the pool
  private final boolean useThreads; // thread usage flag
  private ArrayList<File> workingPathList;

  /**
   * Creates a new model. This call is equivalent to
   * RuntimeModel(start, stop, null, null, null)
   */
  public RuntimeModel(long startTime, long stopTime)
  {
    this(startTime, stopTime, Calendar.getInstance(), true, null, null, null);
  }

  private void resetTransition()
  {
    transition = false;
    finished = true;
    shortestTimer = Long.MAX_VALUE;
  }

  public synchronized void reportTransition(boolean transition)
  {
    this.transition |= transition;
  }

  /**
   * Returns timer decrease value
   */
  public long getTimerDecrease()
  {
    return timerDecrease;
  }

  /**
   * Tries to set a timer value that is less than existing one.
   * Active elements will call this method to declare their timer
   * values. This variable will hold the shortest timer after
   * each run iteration.   
   */
  public synchronized void setShorterTimer(long shortestTimer)
  {
    if(shortestTimer > 0 && shortestTimer < this.shortestTimer)
    {
      this.shortestTimer = shortestTimer;
    }
  }

  /**
   * Creates a new model.
   * @param startTime start time value
   * @param stopTime stop time value
   * @param startRealTime real time of experiment start
   * @param useThreads use thread pool for parallel 
   * execution of state machine iterations.
   * @param progress Progress monitor (may be null)
   * @param errors Error logger (may be null)
   * @param profile Profile logger (may be null)
   */
  public RuntimeModel(long startTime, long stopTime, Calendar startRealTime,
      boolean useThreads, ModelProgressMonitor progress,
      ModelErrorLogger errors, ModelProfileLogger profile)
  {
    this.stopTime = stopTime;
    this.startTime = startTime;
    this.startRealTime = startRealTime;
    modelTime = startTime;
    modelExec = new Thread(this, "Simstate model");
    control = new ModelExecControl();
    this.progress = progress;
    this.errors = errors;
    this.profile = profile;
    shortestTimer = timerDecrease = Long.MAX_VALUE;
    finished = false;
    threadsCount = Runtime.getRuntime().availableProcessors() > 1 ? Runtime
        .getRuntime().availableProcessors() + 1 : 0;
    this.useThreads = useThreads && threadsCount > 0;

    // Initialize pool
    if(this.useThreads)
    {
      threadPool = new ThreadPoolExecutor(threadsCount, threadsCount,
          Long.MAX_VALUE, TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<Runnable>());
      // control
      elemControl = new ElementsExecControl();
      startIterationLock = new Object();
      endIterationLock = new Object();

      for(int i = 0; i < threadsCount; i++)
      {
        threadPool.execute(new ActivePoolExecutor(this));
      }

      System.out.println("Using pool with " + threadsCount + " threads.");
    }
    
    workingPathList = new ArrayList<File>();
  }

  public ElementsExecControl getElemExecControl()
  {
    return elemControl;
  }

  public ElementQueue getElementQueue()
  {
    return elementQueue;
  }

  /**
   * @return control object
   */
  public ModelExecControl getControl()
  {
    return control;
  }

  /**
   * Sets model root element.
   * @param model root model element
   */
  public void setModel(ActiveElement model)
  {
    this.model = model;
    model.setModel(this);
  }

  /**
   * Main method to run a model.
   */
  public void runModel()
  {
    modelExec.start();
  }

  /**
   * Stops the simulation.
   */
  public void stopModel()
  {
    control.setStopCMD();
  }

  /**
   * Returns true if the model has finished execution.
   * @return
   */
  public boolean finished()
  {
    return control.finished();
  }

  /**
   * @return current model time
   */
  public long getModelTime()
  {
    return modelTime;
  }

  /**
   * Sets current model time value. 
   * @param modelTime time value
   */
  protected void setModelTime(long modelTime)
  {
    this.modelTime = modelTime;
  }

  /**
   * Starts model execution
   */
  public void run()
  {
    long timeUnit = (stopTime - startTime) / 100; // progress report period

    if(timeUnit == 0)
    {
      timeUnit = 1;
    }

    long timeToReport = startTime + timeUnit;

    // Initialize progress monitor work units
    if(progress != null)
    {
      progress.begin(100);
    }
    if(profile != null)
    {
      profile.startLog();
    }
    if(errors != null)
    {
      errors.startLog();
    }

    // Initialize queue
    ArrayList<ActiveElement> elist = new ArrayList<ActiveElement>();
    model.buildList(elist);
    elementQueue = new ElementQueue();
    elementQueue.setElemList(elist);

    try
    {
      // Do startup actions
      model.experimentStarted();

      while(true)
      {
        if(control.getStopCMD())
        {
          break;
        }

        do
        {
          model.deliverSignals();
          // Reset iteration counters
          elementQueue.reset();
          resetTransition();
          // Do iteration
          makeIteration();
          // Unset timer decrease for the next possible transitions
          timerDecrease = Long.MAX_VALUE;
        }
        while(transition);

        // Exit if all elements are already in stop state.
        if(finished)
        {
          break;
        }

        // If no transitions has been made we increase time and
        // subtract timer values.
        // The value of shortestTimer will be equal to Long.MAX_VALUE 
        // if there is no timer found. In this case simulation will finish.
        //
        // Shortest timer is set to the max value for the 
        // next iteration.
        if(shortestTimer != Long.MAX_VALUE)
        {
          setModelTime(modelTime + shortestTimer);
          timerDecrease = shortestTimer;
        }
        else
        {
          setModelTime(Long.MAX_VALUE);
        }

        // Exit if we know that next iteration will 
        // break the time limit
        if(timeFinished())
        {
          setModelTime(stopTime);
          break;
        }

        // Otherwise continue to the next iteration with the
        // new time value.
        // Print execution progress
        if(modelTime > timeToReport && progress != null)
        {
          timeToReport += timeUnit;
          progress.progress(1);
        }
      }

      if(useThreads)
      {
        // Finish pool threads
        elemControl.setStopCMD();
        workersNotifyWait();
        threadPool.shutdown();

        // Wait for pool termination.
        if(!threadPool.awaitTermination(TIME_WAIT_POOL, TimeUnit.MILLISECONDS))
        {
          throw new IllegalStateException("Pool execution error");
        }
      }

      // Do finish actions
      model.experimentFinished();
    }
    catch(Throwable e)
    {
      if(errors != null)
      {
        errors.logError("Execution stopped at time " + modelTime, e);
      }
      else
      {
        e.printStackTrace();
      }
    }
    finally
    {
      // finish execution
      control.setFinished();

      if(progress != null)
      {
        progress.done();
      }
      if(profile != null)
      {
        profile.stopLog();
      }
      if(errors != null)
      {
        errors.stopLog();
      }
    }
  }

  /**
   * Makes one iteration of all active elements state machines.
   * If more than 2 elements exceed default time limit on execution the 
   * next iteration will be run by the thread pool if it is available.   
   */
  private void makeIteration() throws Exception
  {
    if(useThreads)
    {
      workersNotifyWait();
    }
    else
    {
      // Execute tasks if any
      for(ActiveElement elem : elementQueue.getElemList())
      {
        transition |= elem.run();
      }
    }
  }

  /**
     * Notifies all active workers and wait for the
     * end of iteration.
     */
  private void workersNotifyWait() throws InterruptedException
  {
    // Reset notifications count
    currentActiveExecutors = threadsCount;

    synchronized(startIterationLock)
    {
      startIterationLock.notifyAll();
    }

    synchronized(endIterationLock)
    {
      while(currentActiveExecutors > finishedCount)
      {
        endIterationLock.wait();
      }
    }
  }

  public Object getStartIterationLock()
  {
    return startIterationLock;
  }

  /**
   * Reports that one of the pool executors 
   * has finished processing tasks.
   */
  public void reportExecutorReady()
  {
    synchronized(endIterationLock)
    {
      currentActiveExecutors--;
      endIterationLock.notify();
    }
  }

  public void reportWorkerFinished()
  {
    synchronized(endIterationLock)
    {
      finishedCount++;
    }
    reportExecutorReady();
  }

  /**
   * @return true if it is time to stop simulation.
   */
  private boolean timeFinished()
  {
    if(modelTime < 0)
    {
      throw new IllegalStateException("Model time is negative");
    }

    return (stopTime > 0) && (modelTime > stopTime);
  }

  /**
   * @return Experiment duration
   */
  public long getDuration()
  {
    return modelTime - startTime;
  }

  /**
   * @return reference to error logger
   */
  public ModelErrorLogger getErrorLogger()
  {
    return errors;
  }

  /**
   * @return reference to the profile logger
   */
  public ModelProfileLogger getProfileLogger()
  {
    return profile;
  }

  /**
   * Waits for model completion.
   * 
   * @return true if model has finished execution, false if timeout
   * has exceeded.
   * 
   * @throws InterruptedException 
   */
  public boolean waitForCompletion(long timeoutms) throws InterruptedException
  {
    modelExec.join(timeoutms);
    return !modelExec.isAlive();
  }

  /**
   * @return Top level active element
   */
  public ActiveElement getModel()
  {
    return model;
  }

  /**
   * Reports that at least one active element is still running. 
   */
  public void reportNotFinished()
  {
    finished = false;
  }

  /**
   * @return an instance of calendar representing model real time 
   */
  public Calendar getRealTime()
  {
    Calendar result = (Calendar) startRealTime.clone();
    result.setTimeInMillis(startRealTime.getTimeInMillis() + getModelTime()
        * 1000);
    return result;
  }

  /**
   * Converts model time in seconds to real time in millis.
   * 
   * @param modelTime model time in seconds
   * @return real time in millis
   */
  public long modelToRealTime(long modelTime)
  {
    return startRealTime.getTimeInMillis() + modelTime * 1000;
  }
  
  public void setProgressMonitor(ModelProgressMonitor progress)
  {
    this.progress = progress;
  }
  
  public void addWorkingPath(File path)
  {
    workingPathList.add(path);
  }
  
  public File getFileByName(String name)
  {
    File result = null;
    
    for(File file : workingPathList)
    {
      result = new File(file, name);
      if(result.exists())
      {
        break;
      }
      else
      {
        result = null;
      }
    }
    
    return result;
  }
}
