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
package com.googlecode.gridme.runtime;

import java.util.Calendar;

/**
 * Contains different methods to operate with 
 * day and night time. 
 */
public class PowerSupply
{
  private final int dayStartHr;
  private final int dayEndHr;

  public PowerSupply(int dayStartHr, int dayEndHr)
  {
    this.dayStartHr = dayStartHr;
    this.dayEndHr = dayEndHr;
  }

  /**
   *
   */
  public boolean isDayNow(Calendar now)
  {
    int hour = now.get(Calendar.HOUR_OF_DAY);
    return hour >= dayStartHr && hour < dayEndHr;
  }

  public long getNightLength()
  {
    return (24 - dayEndHr + dayStartHr) * 3600;
  }

  public float dayTimeAmount(Calendar start, Calendar end)
  {
    long dayTime;
    long nightTime;

    Calendar s0 = (Calendar) start.clone();
    s0.set(Calendar.HOUR_OF_DAY, 0);
    s0.set(Calendar.MINUTE, 0);
    s0.set(Calendar.SECOND, 0);
    s0.set(Calendar.MILLISECOND, 0);

    Calendar sDay = (Calendar) s0.clone();
    sDay.set(Calendar.HOUR_OF_DAY, dayStartHr);

    Calendar sNight = (Calendar) s0.clone();
    sNight.set(Calendar.HOUR_OF_DAY, dayEndHr);

    Calendar e0 = (Calendar) end.clone();
    e0.set(Calendar.HOUR_OF_DAY, 0);
    e0.set(Calendar.MINUTE, 0);
    e0.set(Calendar.SECOND, 0);
    e0.set(Calendar.MILLISECOND, 0);

    Calendar eDay = (Calendar) e0.clone();
    eDay.set(Calendar.HOUR_OF_DAY, dayStartHr);

    Calendar eNight = (Calendar) e0.clone();
    eNight.set(Calendar.HOUR_OF_DAY, dayEndHr);

    dayTime = (long) ((e0.getTimeInMillis() - s0.getTimeInMillis()) * ((float) (dayEndHr - dayStartHr) / 24));
    nightTime = (long) ((e0.getTimeInMillis() - s0.getTimeInMillis()) * ((float) (24 - (dayEndHr - dayStartHr)) / 24));

    if(start.get(Calendar.HOUR_OF_DAY) < dayStartHr)
    {
      nightTime -= start.getTimeInMillis() - s0.getTimeInMillis();
    }

    if(start.get(Calendar.HOUR_OF_DAY) >= dayStartHr
        && start.get(Calendar.HOUR_OF_DAY) < dayEndHr)
    {
      nightTime -= dayStartHr * 3600 * 1000;
      dayTime -= start.getTimeInMillis() - sDay.getTimeInMillis();
    }

    if(start.get(Calendar.HOUR_OF_DAY) >= dayEndHr)
    {
      nightTime -= dayStartHr * 3600 * 1000
          - (start.getTimeInMillis() - sNight.getTimeInMillis());
      dayTime -= (dayEndHr - dayStartHr) * 3600 * 1000;
    }

    if(end.get(Calendar.HOUR_OF_DAY) < dayStartHr)
    {
      nightTime += end.getTimeInMillis() - e0.getTimeInMillis();
    }

    if(end.get(Calendar.HOUR_OF_DAY) >= dayStartHr
        && end.get(Calendar.HOUR_OF_DAY) < dayEndHr)
    {
      nightTime += dayStartHr * 3600 * 1000;
      dayTime += end.getTimeInMillis() - eDay.getTimeInMillis();
    }

    if(end.get(Calendar.HOUR_OF_DAY) >= dayEndHr)
    {
      nightTime += dayStartHr * 3600 * 1000
          + (end.getTimeInMillis() - eNight.getTimeInMillis());
      dayTime += (dayEndHr - dayStartHr) * 3600 * 1000;
    }

    long total = dayTime + nightTime;

    return total > 0 ? (float) dayTime / total : 0;
  }

  public int getDayStartHr()
  {
    return dayStartHr;
  }

  public int getDayEndHr()
  {
    return dayEndHr;
  }
}
