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

import com.googlecode.gridme.runtime.ModelElement;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.log.GanttLogger;
import com.googlecode.gridme.runtime.log.NodeStateBusy;
import com.googlecode.gridme.runtime.log.NodeStateIdle;
import com.googlecode.gridme.runtime.schedule.GNode;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignalWl;
import com.googlecode.gridme.simstate.ActiveElement;

/**
 * Dedicated node can execute only one task at 
 * a given time moment. When the task is finished
 * the node can execute a new task. 
 * 
 * The node accepts only {@link GTaskSignal} signals.
 * @see DedicatedNodeSTM state machine class and corresponding model.
 */
public class DedicatedNode extends ActiveElement implements GNode
{
  protected GTaskSignal task;
  protected GTaskSignal lastTask;
  protected long lastStateChangeTime;
  protected long load;
  protected long loadStartTime;

  public DedicatedNode(String id)
  {
    super(id);
    allowSignals(GTaskSignal.class, GTaskSignalWl.class);
    setStatemachine(new DedicatedNodeSTM(this));
  }

  @Override
  public boolean isFree()
  {
    return task == null;
  }

  public boolean isBusy()
  {
    return task != null;
  }
  
  @Override
  public Object action(int id) throws Exception
  {
    switch(id)
    {
      case DedicatedNodeSTM.ACTION_startTask:
        // Get signals
        lastTask = task = (GTaskSignal) getSignal(GTaskSignal.class);
        break;
      case DedicatedNodeSTM.ACTION_stopTask:
        getParent().sendSignal(
            new GTaskFinishedSignal(task, this, getParent()), this);
        task = null;
        break;
      case DedicatedNodeSTM.ACTION_TaskFinished:
        // Return timer value
        return task.getRealExecutionTime()
            / ((SimpleCluster) getParent()).getSpeedup();
    }
    return null;
  }

  protected void reportNodeStateBusy() throws GRuntimeException
  {
    GanttLogger glog = ((ModelElement) getParent()).getGanttLogger();
    if(glog != null)
    {
      glog.logNodeState(new NodeStateBusy(getParent().getId(), getId(),
          lastTask.getId(), lastStateChangeTime, getModel().getModelTime()
              - lastStateChangeTime));
    }

    load += getModel().getModelTime()
        - Math.max(lastStateChangeTime, loadStartTime);
  }

  protected void reportNodeStateIdle() throws GRuntimeException
  {
    GanttLogger glog = ((ModelElement) getParent()).getGanttLogger();
    if(glog != null)
    {
      glog
          .logNodeState(new NodeStateIdle(getParent().getId(), getId(),
              lastStateChangeTime, getModel().getModelTime()
                  - lastStateChangeTime));
    }
  }

  @Override
  protected void stateChangeHandler(String oldState, String newState)
      throws GRuntimeException
  {
    if(DedicatedNodeSTM.STATE_Idle.equals(oldState))
    {
      reportNodeStateIdle();
    }
    else if(DedicatedNodeSTM.STATE_Busy.equals(oldState))
    {
      reportNodeStateBusy();
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

  public void resetLoad()
  {
    load = 0;
    loadStartTime = getModel().getModelTime();
  }

  /**
   * Total time in busy state
   */
  public long getLoad()
  {
    long add = 0;

    if(DedicatedNodeSTM.STATE_Busy.equals(getStatemachine()
        .getCurrentStateName()))
    {
      add = getModel().getModelTime() - Math.max(lastStateChangeTime, loadStartTime);
    }

    return load + add;
  }
}
