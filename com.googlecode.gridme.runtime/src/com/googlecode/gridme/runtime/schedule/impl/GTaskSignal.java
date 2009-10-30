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
import java.util.HashMap;

import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.GSignal;

/**
 * Model entity that represents a task.
 */
public class GTaskSignal extends GSignal implements GTask
{
  private long submitTime;
  private long releaseTime = -1;
  private final String id;
  private int maxNodes;
  private int minNodes;
  private long realTime;
  private long reqTime;
  private int priority;
  // Custom properties
  private HashMap<String, String> tags;

  /**
   * Creates new task instance
   * 
   * @param name task id
   * @param minNodes minimum number of nodes
   * @param maxNodes maximum number of nodes
   * @param reqTime requested time
   * @param realTime real time
   * @param priority initial priority
   * @param sender signal sender
   * @param recipient signal recipient
   */
  public GTaskSignal(String id, int minNodes, int maxNodes, long reqTime,
                     long realTime, int priority, ActiveElement sender,
                     ActiveElement recipient)
  {
    super(sender, recipient);

    if(id == null || !id.matches("\\w+"))
    {
      throw new IllegalArgumentException("Invalid task id");
    }
    if(minNodes <= 0)
    {
      throw new IllegalArgumentException(
          "Minimum number of nodes is a natural number");
    }
    if(maxNodes < 0)
    {
      throw new IllegalArgumentException(
          "Maximum number of nodes must be nonnegative");
    }
    if(reqTime < 0)
    {
      throw new IllegalArgumentException(
          "Requested time must be nonnegative");
    }
    if(realTime < 0)
    {
      throw new IllegalArgumentException(
          "Real time must be nonnegative");
    }

    this.id = id;
    this.minNodes = minNodes;
    this.maxNodes = maxNodes;
    this.reqTime = reqTime;
    this.realTime = realTime;
    this.priority = priority;
    tags = new HashMap<String, String>();
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
  public int getPriority()
  {
    return priority;
  }

  @Override
  public synchronized String getTag(String name)
  {
    return tags.get(name);
  }

  @Override
  public void setTag(String name, String value)
  {
    synchronized(tags)
    {
      tags.put(name, value);
    }
  }

  @Override
  public synchronized void decreasePriority(int amount)
  {
    priority -= amount;
  }

  @Override
  public synchronized void increasePriority(int amount)
  {
    priority += amount;
  }

  @Override
  public synchronized Collection<String> tags()
  {
    return tags.keySet();
  }

  @Override
  public long getSubmitTime()
  {
    return submitTime;
  }

  @Override
  public void setSubmitTime(long time)
  {
    submitTime = time;
  }

  @Override
  public long getSquare()
  {
    return realTime * minNodes;
  }

  @Override
  public long getReleaseTime()
  {
    if(releaseTime > 0)
    {
      return releaseTime;
    }
    else
    {
      return submitTime;
    }
  }

  public void setReleaseTime(long releaseTime)
  {
    this.releaseTime = releaseTime;
  }
}
