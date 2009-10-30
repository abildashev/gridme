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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.googlecode.gridme.runtime.GElement;
import com.googlecode.gridme.runtime.ImplementationDescription;
import com.googlecode.gridme.runtime.ModelElementImplementationDescription;
import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.log.metrics.SubCountMetric;
import com.googlecode.gridme.runtime.log.metrics.queue.QueueLengthMetric;
import com.googlecode.gridme.runtime.log.metrics.queue.QueueSqrExpMetric;
import com.googlecode.gridme.runtime.log.metrics.queue.QueueSqrRealMetric;
import com.googlecode.gridme.runtime.log.metrics.time.WaitTimeMetric;
import com.googlecode.gridme.runtime.schedule.GBroker;
import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.MetaScheduler;
import com.googlecode.gridme.runtime.schedule.MetaSchedulingDecision;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignalWl;
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.GSignal;
import com.googlecode.gridme.simstate.RuntimeModel;

/**
 * Simple broker implementation. During start
 * the broker discovers all clusters that can
 * be reached from it and adds them to the list of
 * assigned clusters.
 * 
 * The task is sent to a cluster without any checks.
 * If the cluster is not operational the task may be
 * lost. You can extend this class and override the
 * startTask() method to handle this. 
 * 
 * After receiving the first task in a sequence the
 * broker will wait some amount of time (a constant value 
 * by default, but can be changed by overriding getDelay() 
 * method) before scheduling. 
 */
@ModelElementImplementationDescription("Broker implementation. "
    + "At the beginning the broker tries to discover all clusters that can "
    + "be reached from it and adds them to the list of assigned clusters.")
public class SimpleBroker extends GElement implements GBroker
{
  // Scheduler delay default value
  private static final long DELAY = 30L;

  /**
   * The list of attached clusters
   */
  protected ArrayList<GCluster> clusters;

  /**
   * Scheduler reference
   */
  protected MetaScheduler scheduler;

  // Number of tasks submitted
  private int csub;

  /**
   * Create new instance of broker.
   * 
   * @param id broker id
   * @param params parameters
   * 
   * @throws GRuntimeException in the case of initialization error.
   */
  public SimpleBroker(String id) throws GRuntimeException
  {
    super(id);
    clusters = new ArrayList<GCluster>();
    setStatemachine(new SimpleBrokerSTM(this));
    // Allow signals
    allowSignals(GTaskSignal.class, GTaskSignalWl.class, GHourSignal.class);
    getStatemachine().addParam(SimpleBrokerSTM.PARAM_hourSignal,
        GHourSignal.class);
  }

  @Parameter(description = "The full name of a java class that "
      + "implements MetaScheduler interface.", required = true, hasParams = true, category = ImplementationDescription.SCHED_GLOBAL)
  public void setScheduler(Object impl)
  {
    scheduler = (MetaScheduler) impl;
    scheduler.assignBroker(this);
  }

  @Override
  public void setModel(RuntimeModel model)
  {
    super.setModel(model);

    addAlarm(new GHourAlarm(3600L, getModel().getModelTime(), this));
  }

  @Override
  public Collection<GCluster> getClusters()
  {
    return clusters;
  }

  @Override
  public Object action(int id) throws Exception
  {
    switch(id)
    {
      case SimpleBrokerSTM.ACTION_discover:
        discoverClusters();
        break;
      case SimpleBrokerSTM.ACTION_getDelay:
        return getDelay();
      case SimpleBrokerSTM.ACTION_schedule:
        schedule();
        break;
      case SimpleBrokerSTM.ACTION_doHourly:
        // Remove hourly alarm signals
        getAllSignals(GHourSignal.class);
        reportHourlyStats();
        break;
    }
    return null;
  }

  private void reportHourlyStats() throws GRuntimeException
  {
    getMetricsLogger().logMetric(
        new SubCountMetric(getId(), getModel().getModelTime(), csub));
  }

  /**
   * Returns delay value. This delay value is used
   * to wait some time after receiving the first task
   * in a sequence.
   */
  protected Long getDelay()
  {
    return DELAY;
  }

  /**
   * Do actual schedule.
   * @throws GRuntimeException 
   */
  protected void schedule() throws GRuntimeException
  {
    long ctime = getModel().getModelTime();

    // Put all signals into queue.
    Collection<GSignal> newSignals = getAllSignals(GTaskSignal.class);
    for(GSignal sig : newSignals)
    {
      scheduler.getQueue().addTask((GTask) sig);
      ((GTask) sig).setSubmitTime(ctime);
    }

    csub += newSignals.size();

    // Do scheduling and send tasks to clusters. Each sent task is removed from the queue.
    Collection<MetaSchedulingDecision> result = scheduler.schedule(ctime);
    for(MetaSchedulingDecision dec : result)
    {
      startTask(dec.getTask(), dec.getCluster());
    }

    // log stats
    if(getMetricsLogger() != null)
    {
      getMetricsLogger()
          .logMetric(
              new QueueLengthMetric(getId(), ctime, scheduler.getQueue()
                  .getSize()));

      getMetricsLogger().logMetric(
          new QueueSqrExpMetric(getId(), ctime, scheduler.getQueue()
              .getExpectedSquare()));

      getMetricsLogger().logMetric(
          new QueueSqrRealMetric(getId(), ctime, scheduler.getQueue()
              .getRealSquare()));
    }
  }

  /**
   * Sends a task to cluster. A cluster may be down.
   * This method does not handle this.
   * @throws GRuntimeException 
   */
  protected void startTask(GTask task, GCluster cluster)
      throws GRuntimeException
  {
    GTaskSignal tsig = (GTaskSignal) task;
    tsig.setRecipient((ActiveElement) cluster);
    tsig.setDelivered(false);
    sendAll(tsig); // send through all connections
    scheduler.getQueue().removeTask(task.getId());
    // Log wait time metric
    if(getMetricsLogger() != null)
    {
      getMetricsLogger().logMetric(
          new WaitTimeMetric(getId(), getModel().getModelTime(), getModel()
              .getModelTime()
              - task.getSubmitTime()));
      // TODO: add here diff metric for wait time in broker queue
    }
  }

  /**
   * Finds all connected clusters
   */
  protected void discoverClusters()
  {
    Collection<GElement> all = getAvailableElements();
    for(GElement element : all)
    {
      if(element instanceof GCluster)
      {
        GCluster cluster = (GCluster) element;
        clusters.add(cluster);
      }
    }
  }

  @Override
  public void experimentFinished() throws Exception
  {
    reportHourlyStats();
  }
}
