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
package com.googlecode.gridme.runtime.tests;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import com.googlecode.gridme.runtime.PowerSupply;

/**
 * Tests for visualizer.
 */
public class TestEnergyUtils
{
  public static float pdayTimeAmount(Calendar start, Calendar end)
  {
    Calendar startClone = (Calendar) start.clone();
    long dayTimeSeconds = 0;
    long nightTimeSeconds = 0;
    PowerSupply ps = new PowerSupply(7, 23);

    while(startClone.before(end))
    {
      if(ps.isDayNow(startClone))
      {
        dayTimeSeconds++;
      }
      else
      {
        nightTimeSeconds++;
      }
      startClone.add(Calendar.SECOND, 1);
    }

    long total = dayTimeSeconds + nightTimeSeconds;
    
    return total > 0 ? (float) dayTimeSeconds / total : 0;
  }
  
  @Test
  public void test1() throws Exception
  {
    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    
    PowerSupply ps = new PowerSupply(7, 23);
    
    start.set(2009, 1, 2, 8, 10, 10);
    end.set(2009, 1, 10, 4, 11, 12);
    
    float a = pdayTimeAmount(start, end);
    float b = ps.dayTimeAmount(start, end);
    
    System.out.println("a=" + a + " b=" + b);
    assertTrue((int)(a * 10000) == (int)(b * 10000));
  }
}
