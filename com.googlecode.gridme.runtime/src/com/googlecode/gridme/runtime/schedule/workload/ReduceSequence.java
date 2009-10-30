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
package com.googlecode.gridme.runtime.schedule.workload;

/**
 * Accumulates a sequence of values and
 * returns an arithmetic mean after the
 * limit is reached. This class is used
 * during chart building to fold very
 * long value arrays.  
 */
abstract class ReduceSequence
{
  private long point;
  private long accumulate;
  private int activeCount;
  private final int period;

  public ReduceSequence(int period)
  {
    this.period = period;
  }

  public void add(long point, long value)
  {
    if(period == 0)
    {
      handleValue(point, value);
    }
    else
    {
      accumulate += value;
      activeCount++;
  
      if(activeCount >= period)
      {
        handleValue(this.point, (float) accumulate / activeCount);
        this.point = point;
        accumulate = 0;
        activeCount = 0;
      }
    }
  }

  public void finish()
  {
    if(activeCount > 0)
    {
      handleValue(point, (float) accumulate / activeCount);
    }
  }

  public abstract void handleValue(long point, float value);
}
