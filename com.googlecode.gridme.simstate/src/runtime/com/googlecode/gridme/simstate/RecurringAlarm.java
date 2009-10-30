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
 * Generates a signal at regular time intervals.
 */
public abstract class RecurringAlarm implements Alarm
{
  private long remainder;
  private final long period;
  
  /**
   * @param period alarm period
   * @param currentTime current model time
   */
  public RecurringAlarm(long period, long currentTime)
  {
    this.period = period;
    this.remainder = period;
  }

  @Override
  public boolean ready()
  {
    return remainder <= 0;
  }

  @Override
  public long getNotifyTime()
  {
    return remainder < 0 ? 0 : remainder;
  }

  @Override
  public void reset(long currentTime)
  {
    remainder = period;
  }

  @Override
  public void tick(long amount)
  {
    remainder -= amount;
  }
}
