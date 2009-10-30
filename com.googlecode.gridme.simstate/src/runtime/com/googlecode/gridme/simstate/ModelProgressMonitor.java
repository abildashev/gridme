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
 * This interface provides methods for the running model to
 * provide information about model execution progress. 
 */
public interface ModelProgressMonitor
{
  /**
   * Notifies that a simulation has started.
   * @param totalWork number of work units
   */
  public void begin(int totalWork);
  
  /**
   * Notifies that a given number of work 
   * (not an absolute value) has been completed.
   */
  public void progress(int work);
  
  /**
   * Notifies that the work is done.
   */
  public void done();
}
