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

import java.util.Collection;

import com.googlecode.gridme.runtime.schedule.GTask;

public class TaskInfo implements GTask
{
  private String id;
  private int maxNodes;
  private int minNodes;
  private int prio;
  private long realTime;
  private long reqTime;
  private String part;

  public TaskInfo(String id, int minNodes, int maxNodes, long realTime,
                  long reqTime, int prio, String part)
  {
    this.id = id;
    this.maxNodes = maxNodes;
    this.minNodes = minNodes;
    this.prio = prio;
    this.realTime = realTime;
    this.reqTime = reqTime;
    this.part = part;
  }

  public String getPart()
  {
    return part;
  }

  @Override
  public void decreasePriority(int amount)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getId()
  {
    return id;
  }

  @Override
  public int getNodesMax()
  {
    return maxNodes;
  }

  @Override
  public int getNodesMin()
  {
    return minNodes;
  }

  @Override
  public int getPriority()
  {
    return prio;
  }

  @Override
  public long getRealExecutionTime()
  {
    return realTime;
  }

  @Override
  public long getRequestedExecutionTime()
  {
    return reqTime;
  }

  @Override
  public void increasePriority(int amount)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getTag(String name)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setTag(String name, String value)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<String> tags()
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public long getSubmitTime()
  {
    return 0;
  }

  @Override
  public void setSubmitTime(long time)
  {
  }

  @Override
  public long getSquare()
  {
    return realTime * minNodes;
  }

  @Override
  public long getReleaseTime()
  {
    return 0;
  }

  @Override
  public void setReleaseTime(long time)
  {
  }
}
