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

public abstract class NodeState
{
  private final String clusterId;
  private final String nodeId;
  private final long startTime;
  private final long duration;
  
  public NodeState(String clusterId, String nodeId,
      long startTime, long duration)
  {
    this.clusterId = clusterId;
    this.nodeId = nodeId;
    this.startTime = startTime;
    this.duration = duration;
  }

  public String getClusterId()
  {
    return clusterId;
  }

  public String getNodeId()
  {
    return nodeId;
  }

  public long getStartTime()
  {
    return startTime;
  }

  public long getDuration()
  {
    return duration;
  }

  @Override
  public boolean equals(Object obj)
  {
    NodeState state = (NodeState)obj;
    
    return state.getClusterId().equals(clusterId) && 
    state.getNodeId().equals(nodeId) && state.getStartTime() == startTime &&
    state.getDuration() == duration;
  }
  
  public abstract String getStateName();
  
  public abstract String getStateProperties();
}
