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
package com.googlecode.gridme.runtime.opt;

import com.googlecode.gridme.simstate.ModelProgressMonitor;

/**
 * Prints total progress to console in
 * percent of completed job.
 */
public class ConsoleProgressMonitor implements ModelProgressMonitor
{
  private static final int TOTAL_PARTS = 10;

  private float increment;
  private int untilIncrement;
  private int partsReported = 1;
  
  /**
   * Start time
   */
  private long startTime;

  private int total;

  @Override
  public void begin(int totalWork)
  {
    total = totalWork;
    increment = (float)totalWork / TOTAL_PARTS;
    startTime = System.currentTimeMillis();
  }

  @Override
  public void done()
  {
    int left = 10 - partsReported;
    
    for(int i = 0; i <= left; i++)
    {
      reportPart();
    }
    
    System.out.println("done in "
        + (System.currentTimeMillis() - startTime) / 1000 + " Sec.");
  }

  @Override
  public void progress(int work)
  {
    untilIncrement += work;
    total -= work;
    
    if(total < 0)
    {
      throw new IllegalStateException("Total work amount is less than current.");
    }

    int parts = (int) (untilIncrement / increment);
    
    for(int i = 0; i < parts; i++)
    {
      reportPart();
    }
    
    if(parts > 0)
    {
      untilIncrement = 0;
    }
  }

  private void reportPart()
  {
    System.out.print("" + partsReported*10 + "% ");
    partsReported++;
  }
}
