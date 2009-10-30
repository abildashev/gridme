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
package com.googlecode.gridme.ui;

import com.googlecode.gridme.simstate.ModelErrorLogger;

/**
 * Provides logger for the runtime model errors. 
 */
public class UIErrorLogger implements ModelErrorLogger
{
  private Throwable lastError;

  public UIErrorLogger()
  {
  }

  @Override
  public synchronized void logError(String message)
  {
    GridmeUIPlugin.logErrorMessage(message, null);
  }

  @Override
  public synchronized void logError(String message, Throwable e)
  {
    lastError = e;
    GridmeUIPlugin.logErrorMessage(message, e);
  }

  @Override
  public void startLog()
  {
  }

  @Override
  public void stopLog()
  {
  }

  @Override
  public Throwable getLastError()
  {
    return lastError;
  }

  @Override
  public void logInfoMessage(String message)
  {
    GridmeUIPlugin.logInfoMessage(message);
  }

  @Override
  public void logWarningMessage(String message)
  {
    GridmeUIPlugin.logWarningMessage(message);
  }
}
