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

import com.googlecode.gridme.runtime.ModelElementImplementationDescription;
import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignalWl;
import com.googlecode.gridme.simstate.RuntimeModel;

@ModelElementImplementationDescription("Cluster implementation. "
    + "The cluster has a set of nodes that can execute tasks. "
    + "The cluster also has a speedup factor that defines how fast "
    + "it will execute the given task.")
public class SimpleCluster extends BaseCluster
{
  /**
   * Creates a new instance of simple cluster.
   * 
   * @param id cluster id
   * 
   * @throws GRuntimeException if an initialization error occurs.
   */
  public SimpleCluster(String id) throws GRuntimeException
  {
    super(id);

    // Init signals and statemachine
    allowSignals(GTaskSignal.class, GTaskSignalWl.class,
        GTaskFinishedSignal.class, GHourSignal.class);
    setStatemachine(new SimpleClusterSTM(this));

    getStatemachine().addParam(SimpleClusterSTM.PARAM_hourSignal,
        GHourSignal.class);
  }

  @Parameter(description = "Total number of cluster nodes", required = true, hasParams = false, category = 0)
  public void setNodes(String value)
  {
    // Create nodes
    int freeNodes = Integer.parseInt(value);
    for(int i = 0; i < freeNodes; i++)
    {
      addChild(new DedicatedNode(getId() + "@" + i));
    }
  }
  
  @Override
  public void setModel(RuntimeModel model)
  {
    super.setModel(model);
    
    addAlarm(new GHourAlarm(HOUR, getModel()
        .getModelTime(), this));
  }
  
  @Override
  public Object action(int id) throws GRuntimeException
  {
    switch(id)
    {
      case SimpleClusterSTM.ACTION_getScheduleDelay:
        return 0;
      case SimpleClusterSTM.ACTION_doSchedule:
        schedule();
        break;
      case SimpleClusterSTM.ACTION_doHourly:
        // Remove hourly alarm signals
        getAllSignals(GHourSignal.class);
        reportHourlyStats();
        break;
    }
    return null;
  }
}
