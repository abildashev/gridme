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
package com.googlecode.gridme.runtime.log;

public class NodeStateBusy extends NodeState
{
  public static final String STATE_NAME = "b";
  private final String taskId;
  
  public NodeStateBusy(String clusterId, String nodeId, String taskId, long startTime,
      long duration)
  {
    super(clusterId, nodeId, startTime, duration);
    this.taskId = taskId;
  }

  public String getTaskId()
  {
    return taskId;
  }

  @Override
  public String getStateName()
  {
    return STATE_NAME;
  }

  @Override
  public String getStateProperties()
  {
    return taskId;
  }

  @Override
  public boolean equals(Object obj)
  {
    return super.equals(obj) && ((NodeStateBusy)obj).taskId.equals(taskId);
  }
}
