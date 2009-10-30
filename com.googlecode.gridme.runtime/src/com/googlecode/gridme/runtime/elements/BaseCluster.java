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
package com.googlecode.gridme.runtime.elements;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.googlecode.gridme.runtime.GElement;
import com.googlecode.gridme.runtime.ImplementationDescription;
import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.exceptions.GIllegalStateException;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.log.metrics.*;
import com.googlecode.gridme.runtime.log.metrics.queue.QueueLengthMetric;
import com.googlecode.gridme.runtime.log.metrics.queue.QueueSqrExpMetric;
import com.googlecode.gridme.runtime.log.metrics.queue.QueueSqrRealMetric;
import com.googlecode.gridme.runtime.log.metrics.time.WaitTimeHoldMetric;
import com.googlecode.gridme.runtime.log.metrics.time.WaitTimeMetric;
import com.googlecode.gridme.runtime.log.metrics.time.WaitTimeWorkloadMetric;
import com.googlecode.gridme.runtime.log.properties.ClusterSizeProperty;
import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GNode;
import com.googlecode.gridme.runtime.schedule.GNodeGroup;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.LocalScheduler;
import com.googlecode.gridme.runtime.schedule.LocalSchedulingDecision;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.workload.WorkloadTaskInfo;
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.GSignal;
import com.googlecode.gridme.simstate.RuntimeModel;

public abstract class BaseCluster extends GElement implements GCluster
{
  public static final long HOUR = 3600L;

  public static class NodeIterator implements Iterator<GNode>
  {
    private Iterator<ActiveElement> iter;

    private GNode nextNode;

    public NodeIterator(BaseCluster cluster)
    {
      iter = cluster.childs.iterator();
    }

    @Override
    public boolean hasNext()
    {
      if(nextNode == null)
      {
        // Find next node and hold it until next() call
        while(iter.hasNext())
        {
          nextNode = (GNode) iter.next();
          if(nextNode.isFree())
          {
            return true;
          }
        }
        return false;
      }
      else
      {
        return true;
      }
    }

    @Override
    public GNode next()
    {
      if(!hasNext())
      {
        throw new IndexOutOfBoundsException("No more items left");
      }
      GNode result = nextNode;
      nextNode = null; // unset node
      return result;
    }

    @Override
    public void remove()
    {
      throw new UnsupportedOperationException("Can not remove nodes from cluster");
    }
  }

  public static class SMPNodeIterator implements Iterator<GNodeGroup>
  {
    GNodeGroup groups;
    Iterator<? extends GNode> nit;
    int ppn;
    GNodeGroup nextGroup;
    
    public SMPNodeIterator(Iterator<? extends GNode> it, int ppn)
    {
      nit = it;
      groups = new GNodeGroup();
      this.ppn = ppn;
    }
    
    @Override
    public boolean hasNext()
    {
      nextGroup = new GNodeGroup();
      
      for(int i = 0; i < ppn && nit.hasNext(); i++)
      {
        nextGroup.addNode(nit.next());
      }
      
      if(nextGroup.getNodes().size() > 0 && nextGroup.getNodes().size() != ppn)
      {
        throw new IllegalStateException("Cluster has number of nodes not divisible by ppn=" + ppn);
      }

      return nextGroup.getNodes().size() > 0;
    }

    @Override
    public GNodeGroup next()
    {
      return nextGroup;
    }

    @Override
    public void remove()
    {
      throw new UnsupportedOperationException("Can not remove nodes from cluster");
    }
  }
  
  private float speedup;

  private HashMap<String, String> tags;

  protected LocalScheduler scheduler;

  /**
   * Total number of submitted tasks since cluster startup
   */
  private int csubCount;

  /**
   * Total square of submitted tasks using real execution time
   */
  private long csubSquareReal;

  /**
   * Total square of submitted tasks using user specified time
   */
  private long csubSquareExpected;

  /**
   * Total number of started tasks
   */
  private int cstartCount;

  /**
   * Total number of finished tasks
   */
  private int cfinCount;

  /**
   * Total real square of finished tasks
   */
  private long cfinSquareReal;

  /**
   * Total expected square of finished tasks
   */
  private long cfinSquareExpected;

  private int ppn = 1;
  
  /**
   * Makes scheduling. Scheduling can occur in two cases: new task arrival
   * (GTaskSignal) or finished task (GTaskFinishedSignal).
   * 
   * @throws GRuntimeException
   */
  protected void schedule() throws GRuntimeException
  {
    long ctime = getModel().getModelTime();
    long sqr = 0, sqrE = 0;

    // System.out.println("SCHEDULE, time: " + getModel().getModelTime());

    // Get new tasks from the signal queue and put them into schedulers queue.
    Collection<GSignal> slist = getAllSignals(GTaskSignal.class);
    for(GSignal sig : slist)
    {
      GTask task = (GTask) sig;
      scheduler.getQueue().addTask(task);

      sqr = task.getNodesMin() * task.getRealExecutionTime();
      sqrE = task.getNodesMin() * task.getRequestedExecutionTime();
      csubSquareReal += sqr;
      csubSquareExpected += sqrE;

      ((GTask) sig).setSubmitTime(ctime);
    }

    // Update number of submitted tasks
    csubCount += slist.size();

    // Get all finished signals
    slist = getAllSignals(GTaskFinishedSignal.class);

    // Collapse the signals to the list of tasks
    HashSet<GTask> finTasks = new HashSet<GTask>();
    for(GSignal sig : slist)
    {
      GTask ts = ((GTaskFinishedSignal) sig).getTask();
      if(!finTasks.contains(ts))
      {
        finTasks.add(ts);

        cfinCount++;

        long tsqre = ts.getRequestedExecutionTime() * ts.getNodesMin();
        long tsqr = ts.getRealExecutionTime() * ts.getNodesMin();

        cfinSquareExpected += tsqre;
        cfinSquareReal += tsqr;
      }
    }

    // Do scheduling and send tasks to nodes. Each sent task is removed from the
    // queue.
    Collection<LocalSchedulingDecision> result = scheduler.schedule(ctime);
    for(LocalSchedulingDecision dec : result)
    {
      startTask(dec.getTask(), dec.getNodes());
    }

    // System.out.println("END SCHEDULE, time: " + getModel().getModelTime());
  }

  /**
   * Sends a task to each node.
   * 
   * @param task
   *          task
   * @param nodes
   *          the list of assigned nodes.
   */
  private void startTask(GTask task, Collection<GNode> nodes) throws GRuntimeException
  {
    for(GNode node : nodes)
    {
      ((ActiveElement) node).sendSignal((GSignal) task, this);
    }
    scheduler.getQueue().removeTask(task.getId());

    logTaskWaitTime(task);

    cstartCount++;
  }

  /**
   * Logs task wait time - difference between now and task submit time.
   */
  private void logTaskWaitTime(GTask task) throws GRuntimeException
  {
    if(getMetricsLogger() != null)
    {
      long wtimeCur = getModel().getModelTime() - task.getSubmitTime();

      getMetricsLogger().logMetric(new WaitTimeMetric(getId(), getModel().getModelTime(), wtimeCur));

      getMetricsLogger()
          .logMetric(
              new WaitTimeHoldMetric(getId(), getModel().getModelTime(), getModel().getModelTime()
                  - task.getReleaseTime()));

      // Report wait time value from workload.
      if(task instanceof WorkloadTaskInfo)
      {
        WorkloadTaskInfo wtask = (WorkloadTaskInfo) task;
        getMetricsLogger().logMetric(
            new WaitTimeWorkloadMetric(getId(), getModel().getModelTime(), wtask.getWaitTimeInClusterQueue()));
      }
    }
  }

  /**
   * Creates a new instance of cluster.
   * 
   * @param id
   *          cluster id
   * 
   * @throws GRuntimeException
   *           if an initialization error occurs.
   */
  public BaseCluster(String id) throws GRuntimeException
  {
    super(id);

    // Set default speedup
    speedup = 1;
    // Initialize tags
    tags = new HashMap<String, String>();
  }

  @Override
  public void setModel(RuntimeModel model)
  {
    super.setModel(model);
  }

  /**
   * Returns cluster speedup.
   */
  public float getSpeedup()
  {
    return speedup;
  }

  @Override
  public int getFreeNodes()
  {
    int freeNodes = 0;
    for(Iterator<? extends GNode> iterator = getFreeNodesIterator(); iterator.hasNext();)
    {
      freeNodes++;
      iterator.next();
    }
    return freeNodes;
  }

  @Override
  public int getBusyNodes()
  {
    int bnodes = 0;
    for(Iterator<? extends GNode> iterator = getNodesIterator(); iterator.hasNext();)
    {
      GNode node = iterator.next();
      if(node.isBusy())
      {
        bnodes++;
      }
    }
    return bnodes;
  }

  @Override
  public Iterator<? extends GNode> getFreeNodesIterator()
  {
    return new NodeIterator(this);
  }

  @Override
  public int getSize()
  {
    return childs.size();
  }

  @Override
  public String getTag(String name)
  {
    return tags.get(name);
  }

  @Override
  public void setTag(String name, String value)
  {
    tags.put(name, value);
  }

  /**
   * This method resets node load statistics.
   */
  protected void reportHourlyStats() throws GRuntimeException
  {
    long ctime = getModel().getModelTime();

    long loadSum = getTotalLoadTime();

    if(getMetricsLogger() != null)
    {
      getMetricsLogger().logMetric(new SubCountMetric(getId(), ctime, csubCount));
      getMetricsLogger().logMetric(new StartCountMetric(getId(), ctime, cstartCount));
      getMetricsLogger().logMetric(new FinCountMetric(getId(), ctime, cfinCount));
      getMetricsLogger().logMetric(
          new CLoadMetric(getId(), ctime, (long) (((float) loadSum / (HOUR * getSize())) * 100)));
      getMetricsLogger().logMetric(new QueueLengthMetric(getId(), ctime, scheduler.getQueue().getSize()));
      getMetricsLogger().logMetric(new QueueSqrExpMetric(getId(), ctime, scheduler.getQueue().getExpectedSquare()));
      getMetricsLogger().logMetric(new QueueSqrRealMetric(getId(), ctime, scheduler.getQueue().getRealSquare()));
    }

    resetLoadStats();
  }

  protected long getTotalLoadTime()
  {
    long loadSum = 0;
    for(ActiveElement elem : childs)
    {
      DedicatedNode node = (DedicatedNode) elem;
      loadSum += node.getLoad();
    }
    return loadSum;
  }

  protected void resetLoadStats()
  {
    for(ActiveElement elem : childs)
    {
      DedicatedNode node = (DedicatedNode) elem;
      node.resetLoad();
    }
  }

  @Override
  public Collection<String> tags()
  {
    return tags.keySet();
  }

  @Override
  public void experimentStarted() throws Exception
  {
    // Log sizes. No need to call log on childs.
    if(getMetricsLogger() != null)
    {
      getMetricsLogger().logStaticProperty(new ClusterSizeProperty(getId(), "" + getSize()));
    }
  }

  @Override
  public void experimentFinished() throws Exception
  {
    // Log nodes stats. No need to call log on childs.
    reportHourlyStats();

    StringBuilder qids = new StringBuilder();
    // Log wait time for all tasks that are left in the queue.
    for(Iterator<GTask> tit = getScheduler().getQueue().getIterator(); tit.hasNext();)
    {
      GTask task = tit.next();
      logTaskWaitTime(task);
      qids.append(task.getId());
      if(tit.hasNext())
      {
        qids.append(" ");
      }
    }

    StringBuilder hids = new StringBuilder();
    for(Iterator<GTask> tit = getScheduler().getQueue().getHoldTasks().iterator(); tit.hasNext();)
    {
      GTask task = tit.next();
      hids.append(task.getId());
      if(tit.hasNext())
      {
        hids.append(","); 
      }
    }
    
    if(getGanttLogger() != null)
    {
      getGanttLogger().logInfoMessage(getModel().getModelTime(), "Queue tasks: " + qids.toString());
      getGanttLogger().logInfoMessage(getModel().getModelTime(), "Hold tasks: " + hids.toString());
    }
    
    super.experimentFinished();
  }

  @Override
  public LocalScheduler getScheduler()
  {
    return scheduler;
  }

  @Override
  public Iterator<? extends GNode> getNodesIterator()
  {
    return (Iterator<? extends GNode>) childs.iterator();
  }

  @Override
  public boolean isChanged()
  {
    return false;
  }

  @Override
  public Iterator<GNodeGroup> getSMPIterator()
  {
    return new SMPNodeIterator(getNodesIterator(), getPPN());
  }

  public void setSMPCores(int n)
  {
    ppn = n;
  }

  @Override
  public int getPPN()
  {
    return ppn;
  }

  @Parameter(description = "Speedup defines how " + "fast the cluster will execute a given task. "
      + "The value must be a float not greater then 1.", required = false, hasParams = false, category = 0)
  public void setSpeedup(String value)
  {
    speedup = Float.parseFloat(value);
  }

  @Parameter(description = "The list of custom tags in the form 'tag1=value1,tag2=value2'", required = false, hasParams = false, category = 0)
  public void setTags(String value)
  {
    parseTags(tags, value);
  }

  @Parameter(description = "The full name of a java class that " + "implements LocalScheduler interface.", required = true, hasParams = true, category = ImplementationDescription.SCHED_LOCAL)
  public void setScheduler(Object inst)
  {
    scheduler = (LocalScheduler) inst;
    scheduler.assignCluster(this);
  }

  @Parameter(description = "Total number of cluster nodes", required = true, hasParams = false, category = 0)
  public abstract void setNodes(String value);
}
