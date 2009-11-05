/*******************************************************************************
 * Copyright (c)  2009 Dmitry Grushin <dgrushin@gmail.com>
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
package com.googlecode.gridme.runtime.schedule.workload;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkloadComment
{
  public static final String VERSION = "Version";
  public static final String MAXJOBS = "MaxJobs";
  public static final String UNIXSTARTTIME = "UnixStartTime";
  public static final String TIMEZONESTRING = "TimeZoneString";
  public static final String STARTTIME = "StartTime";
  public static final String ENDTIME = "EndTime";
  public static final String MAXNODES = "MaxNodes";
  public static final String MAXPROCS = "MaxProcs";
  public static final String NOTE = "Note";
  
  private HashMap<String, String> values = new HashMap<String, String>();
  private String[] ALL_KEYS = new String[]{VERSION, MAXJOBS, UNIXSTARTTIME, TIMEZONESTRING, STARTTIME,
      ENDTIME, MAXNODES, MAXPROCS, NOTE};

  public WorkloadComment()
  {
    setValue(VERSION, "2.2");
  }
  
  public void setValue(String key, String value)
  {
    values.put(key, value);
  }

  public String getValue(String key)
  {
    return values.get(key);
  }
  
  public String toString()
  {
    StringBuilder str = new StringBuilder();
    
    for(String key : ALL_KEYS)
    {
      String val = values.get(key);
      if(val != null)
      {
        str.append("; " + key + ": " + val + "\n");
      }
    }
    
    return str.toString();
  }

  public void setAndParse(String line)
  {
    String regex = ";(.+):\\s(.+)";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(line);
    
    if(m.matches() && Arrays.asList(ALL_KEYS).contains(m.group(1).trim()))
    {
      values.put(m.group(1).trim(), m.group(2).trim());
    }
  }
}
