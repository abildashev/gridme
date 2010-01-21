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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.googlecode.gridme.runtime.ImplementationDescription;
import com.googlecode.gridme.runtime.ModelElementImplementationDescription;
import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.exceptions.GIllegalStateException;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.exceptions.IllegalParameterException;
import com.googlecode.gridme.runtime.log.metrics.power.EcoPowerMetric;
import com.googlecode.gridme.runtime.log.metrics.power.HoldTasksCountMetric;
import com.googlecode.gridme.runtime.log.metrics.power.NodeMaxPowerOnCountMetric;
import com.googlecode.gridme.runtime.log.metrics.power.NodePowerOnCountMetric;
import com.googlecode.gridme.runtime.log.metrics.power.NodesOnCountMetric;
import com.googlecode.gridme.runtime.log.metrics.power.PowerConsumeCostMetric;
import com.googlecode.gridme.runtime.log.metrics.power.PowerConsumeMetric;
import com.googlecode.gridme.runtime.schedule.GNode;
import com.googlecode.gridme.runtime.schedule.impl.GNodeSleepSignal;
import com.googlecode.gridme.runtime.schedule.impl.GNodeWakeupSignal;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignalWl;
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.RuntimeModel;

@ModelElementImplementationDescription("Cluster implementation that can "
    + "calculate the amount of energy it consumes. " + "For this type of cluster you have to set the "
    + "energy consumption parameters and the power " + "manager implementation.")
public class PowerAwareCluster extends BaseCluster
{
  public static final int DAY_START = 7;
  public static final int DAY_END = 23;
  public static final long POWER_MANAGE_PERIOD = 0;

  protected List<PowerAwareNode> sleepingNodes;
  protected long powerManagePeriod;
  protected ClusterPowerManager pmanager;

  private Integer wattsSleep;
  private Integer wattsIdle;
  private Integer wattsBusy;
  private Integer wattsWake;
  private Integer nodesCount;
  private Float nightPowerCost;
  private boolean changed = false;
  private Integer powerOnDelay = 0;

  // TODO: refactor stats report mechanism: create transparent framework for
  // registering and changing a metric value. Report metrics automatically.
  private int holdTasks;

  // Finishes initialization when all required parameters are present.
  private void initialize()
  {
    if(wattsSleep != null && wattsIdle != null && wattsBusy != null && nodesCount != null && wattsWake != null
        && nightPowerCost != null)
    {
      childs.clear();

      // if(!(wattsBusy >= wattsIdle && wattsIdle >= wattsSleep))
      // {
      // throw new
      // IllegalParameterException("wattsBusy, wattsIdle, or wattsSleep");
      // }

      // Create nodes
      for(int i = 0; i < nodesCount; i++)
      {
        addChild(new PowerAwareNode(getId() + "@" + i, wattsSleep / getPPN(), wattsIdle / getPPN(), wattsBusy
            / getPPN(), wattsWake / getPPN(), nightPowerCost, powerOnDelay));
      }
    }
  }

  @Parameter(description = "The full name of a java class that " + "implements ClusterPowerManager interface.", required = false, hasParams = true, category = ImplementationDescription.POWER)
  public void setPowerManager(Object impl)
  {
    pmanager = (ClusterPowerManager) impl;
    pmanager.assignCluster(this);
  }

  @Parameter(description = "Time period at which the cluster will manage node "
      + "power consumption. If set to 0, power manager will be switched off.", required = false, hasParams = false, category = 0)
  public void setPowerPeriod(String value)
  {
    powerManagePeriod = Integer.parseInt(value);
  }

  @Parameter(description = "Total number of cluster nodes", required = true, hasParams = false, category = 0)
  public void setNodes(String value)
  {
    nodesCount = Integer.parseInt(value);
    initialize();
  }

  @Parameter(description = "Energy consumption of one cluster node in sleep state.", required = true, hasParams = false, category = 0)
  public void setWattsSleep(String value)
  {
    wattsSleep = Integer.parseInt(value);
    initialize();
  }

  @Parameter(description = "Energy consumption of one cluster node in idle state.", required = true, hasParams = false, category = 0)
  public void setWattsIdle(String value)
  {
    wattsIdle = Integer.parseInt(value);
    initialize();
  }

  @Parameter(description = "Energy consumption of one cluster node in busy state.", required = true, hasParams = false, category = 0)
  public void setWattsBusy(String value)
  {
    wattsBusy = Integer.parseInt(value);
    initialize();
  }

  @Parameter(description = "Energy consumption of one cluster node during wake up.", required = true, hasParams = false, category = 0)
  public void setWattsWake(String value)
  {
    wattsWake = Integer.parseInt(value);
    initialize();
  }

  @Parameter(description = "Night time power cost factor.", required = true, hasParams = false, category = 0)
  public void setNightPowerCost(String value)
  {
    nightPowerCost = Float.parseFloat(value);
    initialize();
  }



  @Parameter(description = "Number of cpu cores per node.", required = false, hasParams = false, category = 0)
  public void setPpn(String value) throws IllegalParameterException
  {
    int ppn = Integer.parseInt(value);

    if(getSize() % ppn != 0)
    {
      throw new IllegalParameterException("ppn", value);
    }

    setSMPCores(ppn);

    initialize();
  }

  @Parameter(description = "Time to get from off to idle state", required = false, hasParams = false, category = 0)
  public void setPowerOnDelay(String value)
  {
    powerOnDelay = Integer.parseInt(value);
    initialize();
  }
  
  public PowerAwareCluster(String id) throws GRuntimeException
  {
    super(id);

    // Init signals and statemachine
    allowSignals(GTaskSignal.class, GTaskSignalWl.class, GTaskFinishedSignal.class, GHourSignal.class,
        GNodeFreeSignal.class, GPowerManageSignal.class);

    setStatemachine(new PowerAwareClusterSTM(this));

    getStatemachine().addParam(PowerAwareClusterSTM.PARAM_hourSignal, GHourSignal.class);

    sleepingNodes = new LinkedList<PowerAwareNode>();
    powerManagePeriod = POWER_MANAGE_PERIOD;
  }

  @Override
  protected void reportHourlyStats() throws GRuntimeException
  {
    // Report hourly energy consumption.
    long totalPower = 0;
    long totalPowerCost = 0;
    int maxPowerOnCount = 0;
    int totalPowerOnCount = 0;

    for(ActiveElement elem : childs)
    {
      PowerAwareNode node = (PowerAwareNode) elem;
      totalPower += node.getWattsConsumed();
      totalPowerCost += node.getWattsConsumedCost();
      totalPowerOnCount += node.getPowerOnCount();
      if(node.getPowerOnCount() > maxPowerOnCount)
      {
        maxPowerOnCount = node.getPowerOnCount();
      }
      node.startWattsCounter();
    }

    if(getMetricsLogger() != null)
    {
      // Log in joules
      getMetricsLogger().logMetric(new PowerConsumeMetric(getId(), getModel().getModelTime(), totalPower));

      getMetricsLogger().logMetric(new PowerConsumeCostMetric(getId(), getModel().getModelTime(), totalPowerCost));

      getMetricsLogger().logMetric(
          new NodePowerOnCountMetric(getId(), getModel().getModelTime(), totalPowerOnCount / getPPN()));

      getMetricsLogger().logMetric(
          new NodesOnCountMetric(getId(), getModel().getModelTime(), getSize() - getSleepNodes()));

      getMetricsLogger().logMetric(new NodeMaxPowerOnCountMetric(getId(), getModel().getModelTime(), maxPowerOnCount));

      getMetricsLogger().logMetric(new HoldTasksCountMetric(getId(), getModel().getModelTime(), holdTasks));
    }

    holdTasks = 0;

    // Get total busy time of all nodes
    long totalBusyTime = getTotalLoadTime();

    // Report minimum required power = total busy time * wattsBusy + total not
    // busy time * wattsSleep
    if(getMetricsLogger() != null)
    {
      getMetricsLogger().logMetric(
          new EcoPowerMetric(getId(), getModel().getModelTime(),
              (totalBusyTime * wattsBusy + (HOUR * getSize() - totalBusyTime) * wattsSleep) / getPPN()));
    }

    // Load stats will be cleared here
    super.reportHourlyStats();
  }

  public int getSleepNodes()
  {
    int sleepNodes = 0;
    for(Iterator<? extends GNode> nodeIt = getNodesIterator(); nodeIt.hasNext();)
    {
      PowerAwareNode node = (PowerAwareNode) nodeIt.next();
      if(node.isSleeping())
      {
        sleepNodes++;
      }
    }
    return sleepNodes;
  }

  @Override
  public void setModel(RuntimeModel model)
  {
    super.setModel(model);

    addAlarm(new GHourAlarm(HOUR, getModel().getModelTime(), this));

    if(powerManagePeriod > 0)
    {
      addAlarm(new GPowerManageAlarm(powerManagePeriod, getModel().getModelTime(), this));
    }
  }

  @Override
  public Object action(int id) throws GRuntimeException
  {
    switch(id)
    {
    case PowerAwareClusterSTM.ACTION_getScheduleDelay:
      return 0;
    case PowerAwareClusterSTM.ACTION_doSchedule:
      getAllSignals(GNodeFreeSignal.class);
      schedule();
      break;
    case PowerAwareClusterSTM.ACTION_doHourly:
      // Remove hourly alarm signals
      getAllSignals(GHourSignal.class);
      reportHourlyStats();
      break;
    case PowerAwareClusterSTM.ACTION_powerManage:
      getAllSignals(GPowerManageSignal.class);
      managePower();
    }
    return null;
  }

  /**
   * Calls power manager and sends signals to nodes.
   */
  protected void managePower() throws GIllegalStateException
  {
    Collection<LocalPowerManagementDecision> declist = pmanager.manage();
    for(LocalPowerManagementDecision dec : declist)
    {
      if(dec.getNodes().size() % getPPN() != 0)
      {
        throw new GIllegalStateException("Trying to switch a number of nodes not divisible by " + getPPN());
      }

      if(dec instanceof PowerManagementDecisionSleep)
      {
        for(GNode node : dec.getNodes())
        {
          ((ActiveElement) node).sendSignal(new GNodeSleepSignal(this, (ActiveElement) node), this);

          sleepingNodes.add((PowerAwareNode) node);
          changed = true;
        }
      }
      else if(dec instanceof PowerManagementDecisionWakeup)
      {
        for(GNode node : dec.getNodes())
        {
          ((ActiveElement) node).sendSignal(new GNodeWakeupSignal(this, (ActiveElement) node), this);

          sleepingNodes.remove(node);
          changed = true;
        }
      }
    }
  }

  public List<PowerAwareNode> getSleepingNodes()
  {
    return sleepingNodes;
  }

  public boolean hasSleepingNodes()
  {
    return !sleepingNodes.isEmpty();
  }

  @Override
  protected void schedule() throws GRuntimeException
  {
    getAllSignals(GNodeFreeSignal.class);
    super.schedule();
  }

  @Override
  public boolean isChanged()
  {
    boolean result = changed;
    changed = false;
    return result;
  }

  public void addHolds(int holds)
  {
    holdTasks += holds;
  }
}
