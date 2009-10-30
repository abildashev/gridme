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

import java.util.Random;

import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.simstate.ModelProgressMonitor;

public class SimpleGenerator implements WorkloadGenerator
{
  protected Integer length;
  protected Float serial;
  protected Integer pMinWidth;
  protected Integer pMaxWidth;
  protected Integer minLength;
  protected Integer maxLength;
  protected Integer rectLen;
  protected Integer rectWidth;

  protected Random rand1;
  protected Random rand2;
  protected Random rand3;
  protected Random rand4;

  @Override
  public SWFWorkload generate(ModelProgressMonitor monitor)
      throws GRuntimeException
  {
    monitor.begin(rectLen * rectWidth);

    SWFWorkload result = new SWFWorkload();
    
    result.getComment().setValue(WorkloadComment.NOTE, "Generator: " +
        getName() + ", " + "Generation parameters: " +
        "Length = " + RuntimeUtils.formatTime(length) + ", " +
        "Fraction of serial tasks = " + serial + ", " +
    		((serial!=1)?("Min width of parallel tasks = " + pMinWidth + ", " +
    				"Maximum width of parallel tasks = " + pMaxWidth + ", "):"") +
    		"Minimum task length = " + RuntimeUtils.formatTime(minLength) + ", " +
    		"Maximum task length = " + RuntimeUtils.formatTime(maxLength) + ", " +
    		"Total square rectangle length = " + RuntimeUtils.formatTime(rectLen) + ", " +
    		"Total square rectangle width = " + rectWidth);
    
    rand1 = new Random();
    rand2 = new Random();
    rand3 = new Random();
    rand4 = new Random();
    int totalSqr = 0;

    while(totalSqr < rectLen * rectWidth)
    {
      int startTime = rand1.nextInt(length + 1);
      float fser = rand2.nextFloat();
      int width = 1;
      if(fser > serial)
      {
        width = genWidth();
      }
      int height = genHeight();
      TaskInfoSWF task = new TaskInfoSWF(null, startTime, -1, width, -1,
          height, -1, -1, "-1", 0, -1);
      result.addTask(task);

      totalSqr += width * height;
      monitor.progress(width * height);
    }

    return result;
  }

  private int genHeight()
  {
    return minLength + rand3.nextInt(maxLength - minLength + 1);
  }

  private int genWidth()
  {
    return pMinWidth + rand4.nextInt(pMaxWidth - pMinWidth + 1);
  }

  @Override
  public String getName()
  {
    return "Simple generator";
  }

  @Override
  public String getDescription()
  {
    return "This is a simple workload generator which "
        + "uses uniform distribution to create a task flow in the "
        + "specified time segment.";
  }
}
