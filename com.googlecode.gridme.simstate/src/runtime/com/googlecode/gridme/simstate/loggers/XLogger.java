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
package com.googlecode.gridme.simstate.loggers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.googlecode.gridme.simstate.ModelLogger;

/**
 * A superclass which provides basic XML based
 * logging functionality. 
 */
public class XLogger implements ModelLogger
{
  protected static final int BUFFER_SIZE = 1048576;
  protected PrintStream log;

  /**
   * Creates new xml-based profile logger. 
   * @param logFile log file
   * @throws IOException if the file can not be created.
   */
  protected XLogger(File logFile) throws IOException
  {
    log = new PrintStream(new BufferedOutputStream(
        new FileOutputStream(logFile), BUFFER_SIZE));
  }

  public void startLog()
  {
    log.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
  }

  public void stopLog()
  {
    log.close();
  }
}
