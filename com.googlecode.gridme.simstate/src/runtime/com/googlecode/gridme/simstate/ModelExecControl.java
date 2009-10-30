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
 * This class is used to implement user - model 
 * interaction. It uses synchronized methods to
 * access user commands and flags from the runtime model.
 */
public class ModelExecControl
{
  private boolean stopCMD = false;
  private boolean finished = false;

  /**
   * A command to stop simulation.  
   */
  public void setStopCMD()
  {
    stopCMD = true;
  }

  /**
   * @return true if stop command had been set.
   */
  public boolean getStopCMD()
  {
    return stopCMD;
  }

  /**
   * Sets finished flag. 
   */
  public void setFinished()
  {
    finished = true;
  }

  /**
   * @return true if model has finished.
   */
  public boolean finished()
  {
    return finished;
  }
}
