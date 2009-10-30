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

import java.util.Calendar;

import com.googlecode.gridme.runtime.ModelElement;
import com.googlecode.gridme.runtime.PowerSupply;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.log.GanttLogger;
import com.googlecode.gridme.runtime.log.NodeStatePowersave;
import com.googlecode.gridme.runtime.schedule.impl.GNodeSleepSignal;
import com.googlecode.gridme.runtime.schedule.impl.GNodeWakeupSignal;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignalWl;

/**
 * Power aware node tracks the amount of energy it consumes.
 */
public class PowerAwareNode extends DedicatedNode
{
  // Energy consumption in sleep state
  protected final int wattsSleep;

  // Energy consumption in idle state
  protected final int wattsIdle;

  // Energy consumption in busy state
  protected final int wattsBusy;

  // Energy consumption during wakeup
  protected final int wattsWake;

  // total amount of energy consumed
  private int totalWattsConsumed;

  // total cost of energy consumed
  private int totalWattsConsumedCost;

  // Time limit since last wake up
  private long wakeTimeout;

  // time since last check
  private long timeWattsCounterStarted;

  // current consumption value 
  private int currentWattsConsumption;

  private int powerOnCount;

  private PowerSupply powerS;

  private long lastWakeUpTime;

  private float nightPowerCost;
  
  public PowerAwareNode(String id, int wattsSleep, int wattsIdle,
      int wattsBusy, int wattsWake, long wakeTimeout, float nightPowerCost)
  {
    super(id);
    allowSignals(GTaskSignal.class, GTaskSignalWl.class,
        GNodeSleepSignal.class, GNodeWakeupSignal.class);
    setStatemachine(new PowerAwareNodeSTM(this));
    this.wattsSleep = wattsSleep;
    this.wattsIdle = wattsIdle;
    this.wattsBusy = wattsBusy;
    this.wattsWake = wattsWake;
    this.wakeTimeout = wakeTimeout;
    this.nightPowerCost = nightPowerCost;
    powerS = new PowerSupply(PowerAwareCluster.DAY_START,
        PowerAwareCluster.DAY_END);
  }

  @Override
  public boolean isFree()
  {
    return PowerAwareNodeSTM.STATE_Idle.equals(getStatemachine()
        .getCurrentStateName());
  }

  public boolean isSleeping()
  {
    return PowerAwareNodeSTM.STATE_Sleep.equals(getStatemachine()
        .getCurrentStateName());
  }

  @Override
  public Object action(int id) throws Exception
  {
    switch(id)
    {
      case PowerAwareNodeSTM.ACTION_startTask:
        // Get signals
        lastTask = task = (GTaskSignal) getSignal(GTaskSignal.class);
        setnResetPower(wattsBusy);
        break;

      case PowerAwareNodeSTM.ACTION_stopTask:
        getParent().sendSignal(
            new GTaskFinishedSignal(task, this, getParent()), this);
        task = null;
        break;

      case PowerAwareNodeSTM.ACTION_TaskFinished:
        // Return timer value
        return task.getRealExecutionTime()
            / ((BaseCluster) getParent()).getSpeedup();

      case PowerAwareNodeSTM.ACTION_sleep:
        getAllSignals(GNodeSleepSignal.class);
        setnResetPower(wattsSleep);
        break;

      case PowerAwareNodeSTM.ACTION_wakeup:
        getAllSignals(GNodeWakeupSignal.class);
        powerOnCount++;
        lastWakeUpTime = getModel().getModelTime();
        totalWattsConsumed += wattsWake;
        totalWattsConsumedCost += wattsWake
            * (powerS.isDayNow(getModel().getRealTime()) ? 1
                : nightPowerCost);
        break;

      case PowerAwareNodeSTM.ACTION_enterIdle:
        setnResetPower(wattsIdle);
        // Notify cluster that the node is free
        getParent().sendSignal(new GNodeFreeSignal(this, getParent()), this);
        break;
    }
    return null;
  }

  /** 
   * Computes watts from the last state change, sets
   * the new value for consumption and resets timer. 
   */
  private void setnResetPower(int watts)
  {
    int result = currentWattsConsumption
        * (int) (getModel().getModelTime() - timeWattsCounterStarted);

    totalWattsConsumed += result;
    totalWattsConsumedCost += getLastContinuousWattsUntilNowCost();

    timeWattsCounterStarted = getModel().getModelTime();
    currentWattsConsumption = watts;
  }

  private int getLastContinuousWattsUntilNow()
  {
    return currentWattsConsumption
        * (int) (getModel().getModelTime() - timeWattsCounterStarted);
  }

  private int getLastContinuousWattsUntilNowCost()
  {
    int result = getLastContinuousWattsUntilNow();

    Calendar start = getModel().getRealTime();
    start.add(Calendar.SECOND,
        (int) (-1 * (getModel().getModelTime() - timeWattsCounterStarted)));
    Calendar end = getModel().getRealTime();

    float dayTimePart = powerS.dayTimeAmount(start, end);

    return (int) (result * dayTimePart + result * (1 - dayTimePart)
        * nightPowerCost);
  }

  /**
   * @return total watts*sec = joules since last call to startWattsCounter()
   */
  public int getWattsConsumed()
  {
    return totalWattsConsumed + getLastContinuousWattsUntilNow();
  }

  public int getWattsConsumedCost()
  {
    return totalWattsConsumedCost + getLastContinuousWattsUntilNowCost();
  }

  /**
   * Resets watts counter
   */
  public void startWattsCounter()
  {
    totalWattsConsumed = 0;
    totalWattsConsumedCost = 0;
    timeWattsCounterStarted = getModel().getModelTime();
    //powerOnCount = 0;
  }

  private void reportNodeStatePowersave() throws GRuntimeException
  {
    GanttLogger glog = ((ModelElement) getParent()).getGanttLogger();
    if(glog != null)
    {
      glog
          .logNodeState(new NodeStatePowersave(getParent().getId(), getId(),
              lastStateChangeTime, getModel().getModelTime()
                  - lastStateChangeTime));
    }
  }

  @Override
  protected void stateChangeHandler(String oldState, String newState)
      throws GRuntimeException
  {
    if(PowerAwareNodeSTM.STATE_Idle.equals(oldState))
    {
      reportNodeStateIdle();
    }
    else if(PowerAwareNodeSTM.STATE_Busy.equals(oldState))
    {
      reportNodeStateBusy();
    }
    else if(PowerAwareNodeSTM.STATE_Sleep.equals(oldState))
    {
      reportNodeStatePowersave();
    }
    lastStateChangeTime = getModel().getModelTime();
  }

  @Override
  public void experimentFinished() throws Exception
  {
    // State does not change.
    stateChangeHandler(getStatemachine().getCurrentStateName(), null);

    //super.experimentFinished();
  }

  public int getPowerOnCount()
  {
    return powerOnCount;
  }

  /**
   * @return true if this node can be powered on since last power on even.
   */
  public boolean canPowerOn()
  {
    long timeout = getModel().getModelTime() - lastWakeUpTime;
    return timeout > wakeTimeout;
  }
}
