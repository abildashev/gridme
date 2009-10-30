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
 * provide information about errors. 
 */
public interface ModelErrorLogger extends ModelLogger
{
  /**
   * Logs error.
   * @param message error message text. 
   */
  public void logError(String message);
  
  /**
   * Logs message and the source of error.
   * @param message error message text
   * @param e exception that had caused the error. May be null.
   */
  public void logError(String message, Throwable e);
  
  public void logInfoMessage(String message);
  
  public void logWarningMessage(String message);
  
  /**
   * Returns the last error if exists.
   */
  public Throwable getLastError();
}
