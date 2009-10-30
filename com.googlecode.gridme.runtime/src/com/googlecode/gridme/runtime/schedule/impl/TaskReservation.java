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
package com.googlecode.gridme.runtime.schedule.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.googlecode.gridme.runtime.schedule.GNode;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.Reservation;

/**
 * Reservation for tasks.
 */
public class TaskReservation implements Reservation,
    Comparable<TaskReservation>
{
  protected GTask task;
  protected ArrayList<GNode> nodes;
  protected long startTime;
  protected long duration;
  protected boolean allocated;

  public TaskReservation(GTask task, long startTime, long duration)
  {
    this.task = task;
    this.nodes = new ArrayList<GNode>();
    this.startTime = startTime;
    this.duration = duration;
    allocated = false;
  }

  public void addNode(GNode node)
  {
    nodes.add(node);
  }

  @Override
  public long getDuration()
  {
    return duration;
  }

  public long getStopTime()
  {
    return startTime + duration;
  }
  
  @Override
  public List<GNode> getNodes()
  {
    return nodes;
  }

  @Override
  public int getSize()
  {
    return nodes.size();
  }

  @Override
  public long getStartTime()
  {
    return startTime;
  }

  public GTask getTask()
  {
    return task;
  }

  public boolean isReady(long now)
  {
    boolean result = !allocated && startTime <= now;

    for(Iterator<GNode> it = nodes.iterator(); it.hasNext() && result; result = result
        && it.next().isFree())
    {
    }

    return result;
  }

  @Override
  public int compareTo(TaskReservation res)
  {
    return (int) (startTime - res.startTime);
  }

  public void setAllocated()
  {
    this.allocated = true;
  }
}
