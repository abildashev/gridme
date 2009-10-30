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
package com.googlecode.gridme.runtime.schedule.workload;

import com.googlecode.gridme.runtime.ModelElementImplementationDescription;
import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.simstate.ActiveElement;

/**
 * Special workload flow that will send tasks to broker only if 
 * they have the size less than the specified value.
 */
@ModelElementImplementationDescription("The Workload implementation that "
    + "may have a size constraints on the generated tasks.")
public class WorkloadTaskFlowBySize extends WorkloadTaskFlow
{
  protected int size;

  public WorkloadTaskFlowBySize(String id)
      throws GRuntimeException
  {
    super(id);
  }

  @Parameter(description = "Only tasks with size not greater than the specified "
      + "value will be generated.", required = true, hasParams = false, category = 0)
  public void setSize(String value)
  {
    size = Integer.parseInt(value);
  }

  @Override
  protected ActiveElement getRecipient()
  {
    if(broker != null && task.getNodesMin() <= size)
    {
      return broker;
    }

    ActiveElement cluster = clusters.get(task.getPart());
    if(cluster != null)
    {
      return cluster;
    }
    else
    {
      return broker;
    }
  }
}
