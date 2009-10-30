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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents an active model element. The
 * element has a statemachine and can have a list of childs.
 */
public abstract class ActiveElement implements StatemachineHandler
{
  protected ArrayList<GSignal> signals; // the list of available signals

  protected ArrayList<ActiveElement> childs; // the list of child elements

  private final String id; // element id

  private RuntimeModel model; // the reference to the model

  private ActiveElement parent; // elements parent

  private Statemachine stateMachine; // element statemachine

  private ArrayList<GSignal> privateBuffer; /// hidden signal buffer

  private HashMap<String, TransitionTimer> timers; /// timer events

  private HashSet<Class<? extends GSignal>> allowedSignals; /// allowed signals list

  private boolean allowAllSignals;

  private long smallestTimer; // Smallest timer value cache.

  private final ArrayList<Alarm> alarms; /// the list of alarms

  private long smallestAlarm;

  /**
   * Creates new active element. Don't forget to set the runtime model
   * instance before execution.
   * 
   * @param id element id
   * @param parent parent element
   */
  public ActiveElement(String id)
  {
    this.id = id;
    privateBuffer = new ArrayList<GSignal>();
    signals = new ArrayList<GSignal>();
    timers = new HashMap<String, TransitionTimer>(5);
    childs = new ArrayList<ActiveElement>();
    allowedSignals = new HashSet<Class<? extends GSignal>>();
    allowAllSignals = false;
    smallestTimer = Long.MAX_VALUE;
    alarms = new ArrayList<Alarm>();
  }

  /**
   * Helper constructor which sets a model reference.
   */
  public ActiveElement(String id, RuntimeModel model)
  {
    this(id);
    setModel(model);
  }

  /**
   * Adds a set of signals to the list of allowed
   * signals. If an element receives a signal which
   * is not allowed an {@link IllegalArgumentException}
   * will be thrown.
   * 
   * @param signals signals
   */
  protected void allowSignals(Class<? extends GSignal>... signals)
  {
    allowAllSignals = false;
    allowedSignals.addAll(Arrays.asList(signals));
  }

  /**
   * Allows this element to receive any signal.
   */
  protected void allowAllSignals()
  {
    allowAllSignals = true;
  }

  /**
   * @return parent element
   */
  public ActiveElement getParent()
  {
    return parent;
  }

  /**
   * Sets element parent instance. 
   * @param parent parent
   */
  public void setParent(ActiveElement parent)
  {
    this.parent = parent;
  }

  /**
   * Sets reference to the runtime model.
   * @param model
   */
  public void setModel(RuntimeModel model)
  {
    this.model = model;

    for(ActiveElement child : childs)
    {
      child.setModel(model);
    }
  }

  /**
   * @return element id
   */
  public String getId()
  {
    return id;
  }

  /**
   * @return control object
   */
  public final ModelExecControl getExecControl()
  {
    return model.getControl();
  }

  /**
   * @return runtime model instance
   */
  public final RuntimeModel getModel()
  {
    return model;
  }

  /**
   * @param stm statemachine instance
   */
  protected final void setStatemachine(Statemachine stm)
  {
    this.stateMachine = stm;
  }

  /**
   * @return elements statemachine instance. May be null.
   */
  protected final Statemachine getStatemachine()
  {
    return stateMachine;
  }

  /**
   * Returns a signal with the given name
   * from the input queue.
   * Does not remove the signal.
   * All signals that extend the given class will be accepted.
   *
   * @param name signal class
   * @return signal instance if found or null otherwise
   */
  public final GSignal checkSignal(Class<? extends GSignal> signal)
  {
    GSignal result = null;
    for(GSignal sig : signals)
    {
      if(isChildOf(sig, signal))
      {
        result = sig;
        break;
      }
    }
    return result;
  }

  /**
   * @return true if the signal is a child of the given class.
   */
  protected boolean isChildOf(GSignal sig, Class<? extends GSignal> sigClass)
  {
    boolean castOk = false;

    try
    {
      sigClass.cast(sig);
      castOk = true;
    }
    catch(ClassCastException e)
    {
    }

    return castOk;
  }

  /**
   * Returns a list of signals with the given name.
   * All signals are removed from the queue.
   *
   * @param name signal name
   * @return Collection of signals. If nothing is found -
   * returns an empty Collection.
   */
  public Collection<GSignal> getAllSignals(Class<? extends GSignal> sigClass)
  {
    ArrayList<GSignal> result = new ArrayList<GSignal>();

    for(Iterator<GSignal> it = signals.iterator(); it.hasNext();)
    {
      GSignal sig = it.next();

      if(isChildOf(sig, sigClass))
      {
        result.add(sig);
        it.remove();
      }
    }
    return result;
  }

  /**
   * Removes all signals from the queue.
   * @return the list of remove signals
   */
  public Collection<GSignal> getAllSignals()
  {
    ArrayList<GSignal> result = new ArrayList<GSignal>();
    result.addAll(signals);
    signals.clear();
    return result;
  }

  /**
   * Removes all signals from the queue.
   */
  public void removeAllSignals()
  {
    signals.clear();
  }

  /**
   * Returns the signal if exists.
   * The signal is removed from the queue.
   * 
   * @param signal signal class
   * @return Signal or null if the signal does not exist.
   * 
   * TODO: get children ?
   */
  public final GSignal getSignal(Class<? extends GSignal> sigClass)
  {
    GSignal result = null;

    for(Iterator<GSignal> it = signals.iterator(); it.hasNext();)
    {
      GSignal sig = it.next();
      if(isChildOf(sig, sigClass))
      {
        result = sig;
        it.remove();
        break;
      }
    }
    return result;
  }

  /**
   * If a timer with 0 time value exists this method will
   * return it. The timer stays in the list of active timers.  
   *
   * @return first available zero timer.
   */
  public final TransitionTimer getReadyTimer(String name)
  {
    TransitionTimer result = timers.get(name);

    if(result != null && result.ready())
    {
      return result;
    }

    return null;
  }

  /**
   * Adds new timer value to the list of active timers.
   */
  public final void addTimer(TransitionTimer timer)
  {
    timers.put(timer.getId(), timer);
    // Update smallest value
    updateSmallestTimerValue(timer.getTime());
  }

  /**
   * Puts a signal into the input buffer of the element.
   * but it will be available only after current transition.
   * @param signal signal
   * @param sender current sender
   */
  public final void sendSignal(GSignal signal, ActiveElement sender)
  {
    if(!allowAllSignals && !allowedSignals.contains(signal.getClass()))
    {
      throw new IllegalArgumentException("Element: " + getId() + ", Sender: "
          + sender.getId() + ". The signal is not allowed: "
          + signal.getClass().getCanonicalName());
    }

    synchronized(privateBuffer)
    {
      privateBuffer.add(signal);
    }

    // Log signal send event if we have a logger
    if(getModel().getProfileLogger() != null)
    {
      getModel().getProfileLogger().sendSignal(sender.getId(),
          signal.getClass().getName(), getId(), getModel().getModelTime());
    }
  }

  /**
   * Puts the signal into the buffer. 
   * @param signal Signal
   * @param sender Current sender
   * @param notAlreadyDelivered flag that controls the sending. If set to true,
   * the signal is skipped if it has been already delivered. 
   */
  public final void sendSignal(GSignal signal, ActiveElement sender,
      boolean notAlreadyDelivered)
  {
    if(!notAlreadyDelivered || !signal.isDelivered())
    {
      sendSignal(signal, sender);
    }
  }

  /**
   * Copies all signals from the private to the
   * signal buffer of the element.
   */
  public final void deliverSignals()
  {
    for(ActiveElement element : childs)
    {
      element.deliverSignals();
    }

    signals.addAll(privateBuffer);
    privateBuffer.clear();
  }

  /**
   * Adds child elements. This method will set 
   * model child reference
   * if current element has a not 
   * null model reference. 
   * 
   * @param elements childs
   */
  public final void addChild(ActiveElement... elements)
  {
    for(ActiveElement elem : elements)
    {
      childs.add(elem);
      elem.setParent(this);
      // Set model here ?
      if(getModel() != null)
      {
        elem.setModel(getModel());
      }
    }
  }

  /**
   * Makes an iteration.
   *
   * @return true if at least one transition has been made.
   */
  public final boolean run() throws Exception
  {
    boolean transition = false;

    if(getStatemachine() != null && !getStatemachine().finished())
    {
      smallestAlarm = Long.MAX_VALUE;
      
      // Decrease timers if any
      if(getModel().getTimerDecrease() != Long.MAX_VALUE)
      {
        decreaseTimers(getModel().getTimerDecrease());
        decreaseAlarms(getModel().getTimerDecrease());
      }

      String oldState = getStatemachine().getCurrentStateName();
      transition = getStatemachine().run() || transition;

      // Log state change
      if(transition) 
      {
        if(!oldState.equals(getStatemachine().getCurrentStateName()))
        {
          // Call handler only if state really changes 
          stateChangeHandler(oldState, getStatemachine().getCurrentStateName());
        }
        if(getModel().getProfileLogger() != null)
        {
          getModel().getProfileLogger().stateChange(getId(), oldState,
              getStatemachine().getCurrentStateName(), getModel().getModelTime());
        }
      }

      // Update smallest timer from alarms
      updateTimerFromAlarms();
      
      // Now report the timer value.
      getModel().setShorterTimer(smallestTimer);

      // Report finished status
      if(!getStatemachine().finished())
      {
        getModel().reportNotFinished();
      }
    }

    return transition;
  }

  /**
   * Removes all timers.
   */
  public final void clearTimers()
  {
    timers.clear();
    smallestTimer = Long.MAX_VALUE;
  }

  /**
   * Decreases all timers by a specified amount.
   *
   * @param time time to subtract
   */
  private void decreaseTimers(long time)
  {
    for(TransitionTimer timer : timers.values())
    {
      timer.decreaseTimer(time);
    }
    if(smallestTimer != Long.MAX_VALUE)
    {
      smallestTimer -= time;
    }
  }

  /**
   * This method is called when experiment is started.
   * Subclasses may override it to provide special 
   * behavior, such as logging and initialization.
   */
  public void experimentStarted() throws Exception
  {
    for(ActiveElement element : childs)
    {
      element.experimentStarted();
    }
  }

  /**
   * This method is called when experiment is finished.
   * Subclasses may override it to provide special 
   * behavior, such as logging end point information.
   */
  public void experimentFinished() throws Exception
  {
    for(ActiveElement element : childs)
    {
      element.experimentFinished();
    }
  }

  /**
   * Logs error through the default error logger.
   * @param message error message
   * @param e cause
   */
  protected void logError(String message, Throwable e)
  {
    if(getModel().getErrorLogger() != null)
    {
      getModel().getErrorLogger().logError(message, e);
    }
  }

  /**
   * This method is used to build a list of element instances.
   */
  public void buildList(List<ActiveElement> list)
  {
    list.add(this);

    for(ActiveElement element : childs)
    {
      element.buildList(list);
    }
  }

  /**
   * Adds an alarm.
   */
  public void addAlarm(Alarm alarm)
  {
    alarms.add(alarm);
    updateSmallestTimerValue(alarm.getNotifyTime());
  }

  private void updateSmallestTimerValue(long value)
  {
    if(value < smallestTimer)
    {
      smallestTimer = value;
    }
  }

  /**
   * Decreases alarms and checks if there are ready alarms. 
   */
  private void decreaseAlarms(long amount)
  {
    for(Alarm alarm : alarms)
    {
      alarm.tick(amount);
      if(alarm.ready())
      {
        GSignal asig = alarm.getSignal();
        
        signals.add(asig);
        alarm.reset(getModel().getModelTime());
        if(getModel().getProfileLogger() != null)
        {
          getModel().getProfileLogger().sendSignal(getId(),
              asig.getClass().getName(), getId(), getModel().getModelTime());
        }
      }
      if(alarm.getNotifyTime() < smallestAlarm)
      {
        smallestAlarm = alarm.getNotifyTime();
      }
    }
  }
  
  private void updateTimerFromAlarms()
  {
    if(smallestAlarm == Long.MAX_VALUE)
    {
      for(Alarm alarm : alarms)
      {
        if(alarm.getNotifyTime() < smallestAlarm)
        {
          smallestAlarm = alarm.getNotifyTime();
        }
      }
    }
    updateSmallestTimerValue(smallestAlarm);
  }

  @Override
  public String toString()
  {
    return getId();
  }

  /**
   * Provides possibility for the model element to
   * make some actions on state change.
   */
  protected void stateChangeHandler(String oldState, String newState) throws Exception 
  {
    // Do nothing here
  }
}
