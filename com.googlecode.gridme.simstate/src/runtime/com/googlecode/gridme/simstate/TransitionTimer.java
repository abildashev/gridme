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
package com.googlecode.gridme.simstate;

/**
 * This class represents a transition timer event.  
 * The timer has an id and a time value.
 */
public class TransitionTimer
{
  private long time;
  private final String id;

  /**
   * Creates a new time instance with the given id and
   * time values.
   * @param id time id
   * @param time time left to the next event
   */
  public TransitionTimer(String id, long time)
  {
    this.time = time;
    this.id = id;
  }

  /**
   * Decreases the time value.
   * @param amount time amount to subtract.
   */
  public void decreaseTimer(long amount)
  {
    time = time - amount;
  }

  /**
   * @return Current timer value.
   */
  public long getTime()
  {
    return time;
  }

  /**
   * @return true if timer has elapsed, false otherwise. 
   */
  public boolean ready()
  {
    return time <= 0;
  }

  /**
   * @return timer id.
   */
  public String getId()
  {
    return id;
  }
}
