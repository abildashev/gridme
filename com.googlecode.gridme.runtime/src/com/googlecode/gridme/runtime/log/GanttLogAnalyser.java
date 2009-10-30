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

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;

public interface GanttLogAnalyser
{
  public Calendar getCalendarStartTime() throws GRuntimeException;
  
  public long getMinTimeSegment(String clusterId) throws GRuntimeException;

  public long getMaxTimeSegment(String clusterId) throws GRuntimeException;

  public int getNodesCount(String clusterId) throws GRuntimeException;

  public List<String> getNodes(String clusterId) throws GRuntimeException;

  public List<NodeState> getNodeStateChange(String clusterId, String nodeId)
      throws GRuntimeException;
  
  public List<String> getClusters() throws GRuntimeException;
  
  public Collection<GanttMessage> getMessages() throws GRuntimeException;
}
