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
package com.googlecode.gridme.runtime.log;

import java.util.ArrayList;

public class GanttMessage
{
  final long time;
  final ArrayList<String> messages;

  public GanttMessage(long time, String message)
  {
    this.time = time;
    messages = new ArrayList<String>();
    messages.add(message);
  }

  public long getTime()
  {
    return time;
  }

  public String getFirstMessage()
  {
    return messages.get(0);
  }

  public ArrayList<String> getMessages()
  {
    return messages;
  }
  
  public void addMessage(String message)
  {
    messages.add(message);
  }
}
