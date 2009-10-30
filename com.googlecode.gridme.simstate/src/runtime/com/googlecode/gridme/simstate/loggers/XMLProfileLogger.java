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

import java.io.File;
import java.io.IOException;

import com.googlecode.gridme.simstate.ModelProfileLogger;

public class XMLProfileLogger extends XLogger implements ModelProfileLogger
{
  /**
   * Creates new xml-based profile logger. 
   */
  public XMLProfileLogger(File logFile) throws IOException
  {
    super(logFile);
  }

  @Override
  public void sendSignal(String id, String signal, String target, long time)
  {
    log.println("<send-signal name=\"" + signal + "\" time=\"" + time
        + "\" sender=\"" + id + "\" receiver=\"" + target
        + "\"/>");
  }

  @Override
  public void stateChange(String id, String oldState, String newState, long time)
  {
    log.println("<state-change elem=\"" + id + "\" time=\"" + time
        + "\" old=\"" + oldState + "\" new=\"" + newState + "\"/>");
  }

  @Override
  public void startLog()
  {
    super.startLog();
    log.println("<profile>");
  }

  @Override
  public void stopLog()
  {
    log.println("</profile>");
    super.stopLog();
  }

  @Override
  public synchronized void logMessage(String id, String message, long time)
  {
    log.println("<message elem=\"" + id + "\" time=\"" + time
        + "\">");
    log.println("<![CDATA[" + message + "]]>");
    log.println("</message>");
  }
}
