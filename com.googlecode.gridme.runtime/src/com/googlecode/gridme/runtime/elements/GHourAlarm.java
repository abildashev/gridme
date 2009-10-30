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
package com.googlecode.gridme.runtime.elements;

import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.GSignal;
import com.googlecode.gridme.simstate.RecurringAlarm;

/**
 * This alarm is used to report cluster and broker hourly
 * statistics. 
 */
public class GHourAlarm extends RecurringAlarm
{
  private ActiveElement parent;
  
  public GHourAlarm(long period, long currentTime, ActiveElement elem)
  {
    super(period, currentTime);
    parent = elem;
  }
  
  @Override
  public GSignal getSignal()
  {
    return new GHourSignal(parent);
  }
}
