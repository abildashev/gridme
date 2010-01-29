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
package com.googlecode.gridme.runtime.visual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.log.LogAnalyser;
import com.googlecode.gridme.runtime.log.LogManifest;

/**
 * LogEntry represents an execution profile instance.
 * The entry contains name, description and a path to the profile. 
 */
public class LogEntry
{
  private final String name;
  private final String description;
  private List<LogAnalyser> logs;

  public LogEntry(String name, LogAnalyser log) throws GRuntimeException
  {
    this(name, Arrays.asList(new LogAnalyser[]{log}));
  }
  
  public LogEntry(String name, List<LogAnalyser> logs) throws GRuntimeException
  {
    this.logs = logs;
    this.name = name;
    
    LogManifest man = null;
    if(!logs.isEmpty())
    {
      man = logs.get(0).getManifest();
    }
    
    if(man != null && !man.getDescription().isEmpty())
    {
      this.description = man.getDescription();
    }
    else
    {
      this.description = name;      
    }
  }

  public String getName()
  {
    return name;
  }

  public List<LogAnalyser> getLogs()
  {
    return logs;
  }

  public LogAnalyser getLog()
  {
    return logs.get(0);
  }
  
  public String getDescription()
  {
    return description;
  }
}
