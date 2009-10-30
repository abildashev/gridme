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
 * Base alarm class. Extend it to implement a custom alarm.
 */
public interface Alarm
{
  /**
   * @return new signal instance to send.
   */
  public GSignal getSignal();

  /**
   * @return true if alarm is ready.
   */
  public boolean ready();
  
  /**
   * @return a time period left to notify this alarm.
   */
  public long getNotifyTime();
  
  /**
   * Signals this alarm to update internal structures.
   * 
   * @param amount - time increase value
   */
  public void tick(long amount);
  
  /**
   * Resets the alarm. 
   * 
   * @param currentTime current model time.
   */
  public void reset(long currentTime);
}
