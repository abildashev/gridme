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

import com.googlecode.gridme.simstate.ModelErrorLogger;
import com.googlecode.gridme.simstate.ModelProfileLogger;
import com.googlecode.gridme.simstate.ModelProgressMonitor;
import com.googlecode.gridme.simstate.RuntimeModel;

/**
 * Runtime model implementation for grid 
 * modeling experiments.
 */
public class GridRuntimeModel extends RuntimeModel
{
  private int runNumber;

  public GridRuntimeModel(long startTime, long stopTime,
      Calendar startRealTime, ModelProgressMonitor progress,
      ModelErrorLogger errors, ModelProfileLogger profile, int runNumber,
      boolean useThreads)
  {
    super(startTime, stopTime, startRealTime, useThreads, progress, errors,
        profile);
    this.runNumber = runNumber;
  }

  /**
   * Returns the value of run counter. Elements
   * may change behavior according to this.
   */
  public int getRunNumber()
  {
    return runNumber;
  }
}
