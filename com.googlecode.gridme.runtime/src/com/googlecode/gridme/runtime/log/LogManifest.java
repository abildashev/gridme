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

import java.util.Calendar;

/**
 * Represents log meta information. 
 */
public class LogManifest
{
  private String description;
  private Calendar startTime;

  public LogManifest()
  {
  }

  public LogManifest(String description)
  {
    this.description = description;
    startTime = Calendar.getInstance();
  }

  public LogManifest(String description, Calendar start)
  {
    this.description = description;
    startTime = start;
  }
  
  public String getDescription()
  {
    return description;
  }

  public Calendar getStartTime()
  {
    return startTime;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public void setStartTime(Calendar startTime)
  {
    this.startTime = startTime;
  }
}
