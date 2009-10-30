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

import com.googlecode.gridme.runtime.GElement;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignalWl;
import com.googlecode.gridme.runtime.schedule.workload.WorkloadTaskInfo;
import com.googlecode.gridme.simstate.ActiveElement;

/**
 * Task flow generates a number of tasks at regular time 
 * intervals. The period and the number of tasks are obtained
 * by calling internal methods.
 * 
 * See the state machine for the detailed behavior of the flow.
 * Methods are called in the following order:
 * <ol>
 *  <li> init() - is called only once.
 *  <li> prepare()
 *  <li> getPeriod() - to get the delay for the next generation
 *  <li> getTask()
 *  <li> getRecipient()
 * </ol>
 */
public abstract class SimpleTaskFlow extends GElement
{
  public SimpleTaskFlow(String id)
  {
    super(id);
    setStatemachine(new SimpleTaskFlowSTM(this));
  }

  @Override
  public Object action(int id) throws GRuntimeException
  {
    switch(id)
    {
      case SimpleTaskFlowSTM.ACTION_prepareTask:
        prepare();
        break;

      case SimpleTaskFlowSTM.ACTION_isFinished:
        return isFinished();
        
      case SimpleTaskFlowSTM.ACTION_getDelay:
        return getPeriod();

      case SimpleTaskFlowSTM.ACTION_generateTasks:
        generateTask();
        break;

      case SimpleTaskFlowSTM.ACTION_init:
        init();
        break;
        
      case SimpleTaskFlowSTM.ACTION_getStartDelay:
        return getStartDelay();
    }
    return null;
  }

  /**
   * Sends number of tasks. 
   */
  private void generateTask()
  {
    GTask task = getTask();
    GTaskSignal tsignal = null;
    
    // If we have additional information from the workload create 
    // corresponding signal object.
    if(task instanceof WorkloadTaskInfo)
    {
      WorkloadTaskInfo twl = (WorkloadTaskInfo) task;
      tsignal = new GTaskSignalWl(task.getId(), task.getNodesMin(), task
          .getNodesMax(), task.getRequestedExecutionTime(), task
          .getRealExecutionTime(), task.getPriority(), this, getRecipient(),
          twl.getWaitTimeInClusterQueue(), twl.getCompletionTime());
    }
    else
    {
      tsignal = new GTaskSignal(task.getId(), task.getNodesMin(), task
          .getNodesMax(), task.getRequestedExecutionTime(), task
          .getRealExecutionTime(), task.getPriority(), this, getRecipient());
    }

    // Send through all connections
    sendAll(tsignal);
  }

  /**
   * Performs initialization
   */
  protected abstract void init();

  /**
   * Returns time period for the next transmission. 
   */
  protected abstract long getPeriod();

  /**
   * Returns recipient of the task.
   */
  protected abstract ActiveElement getRecipient();

  /**
   * Returns a task that must be sent.
   */
  protected abstract GTask getTask();

  /**
   * Prepares to the task generation.
   */
  protected abstract void prepare() throws GRuntimeException;
  
  /**
   * Returns true if submission is finished
   */
  protected abstract boolean isFinished();
  
  /**
   * Start delay defines a pause before this flow 
   * will start.
   */
  protected abstract long getStartDelay();
}
