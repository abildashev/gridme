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
package com.googlecode.gridme.runtime;

import java.io.File;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;

/**
 * Various utilities.
 */
public class RuntimeUtils
{
  /**
   * Removes directory and all its contents.
   */
  public static boolean deleteDir(File dir) throws GRuntimeException
  {
    boolean deleted = false;
    try
    {
      if(dir.isDirectory())
      {
        String[] children = dir.list();
        for(int i = 0; i < children.length; i++)
        {
          boolean success = deleteDir(new File(dir, children[i]));
          if(!success)
          {
            deleted = false;
          }
          else
          {
            deleted = true;
          }
        }
      }
      // The directory is now empty so delete it
      deleted = dir.delete();
    }
    catch(Exception e)
    {
      throw new GRuntimeException(e);
    }
    return deleted;
  }

  /**
   *
   */
  public static int parseTimeText(String text) throws NumberFormatException
  {
    String[] tokens = text.split(" ");
    int result = 0;

    if(tokens.length > 4 || tokens.length < 1)
    {
      throw new NumberFormatException();
    }

    for(String tok : tokens)
    {
      if(tok.length() < 2)
      {
        throw new NumberFormatException();
      }

      int tki = Integer.parseInt(tok.substring(0, tok.length() - 1));

      if(tok.endsWith("d") || tok.endsWith("D"))
      {
        result += tki * 3600 * 24;
      }
      else if(tok.endsWith("h") || tok.endsWith("H"))
      {
        result += tki * 3600;
      }
      else if(tok.endsWith("m") || tok.endsWith("M"))
      {
        result += tki * 60;
      }
      else if(tok.endsWith("s") || tok.endsWith("S"))
      {
        result += tki;
      }
      else
      {
        throw new NumberFormatException();
      }
    }

    return result;
  }

  public static String formatTime(long tsec)
  {
    long d = tsec / (3600 * 24);
    long h = (tsec - d * 3600 * 24) / 3600;
    long m = (tsec - d * 3600 * 24 - h * 3600) / 60;
    long s = tsec - d * 3600 * 24 - h * 3600 - m * 60;

    return (d > 0 ? ("" + d + "d") : "")
        + (h > 0 ? ((d > 0 ? " " : "") + h + "h") : "")
        + (m > 0 ? ((d > 0 | h > 0 ? " " : "") + m + "m") : "")
        + (s > 0 | tsec == 0 ? ((d > 0 | h > 0 | m > 0 ? " " : "") + s + "s")
            : "");
  }

  public static String removeExtension(String fileName)
  {
    String[] segments = fileName.split("\\.");
    StringBuffer result = new StringBuffer();

    for(int i = 0; i < segments.length - 1; i++)
    {
      if(i > 0)
      {
        result.append(".");
      }
      result.append(segments[i]);
    }

    return result.toString();
  }

  /**
   * Converts a 'setName' into 'name'
   */
  public static String getParameterName(String methodName)
      throws GRuntimeException
  {
    try
    {
      String nameUpper = methodName.substring(3, methodName.length());
      return nameUpper.substring(0, 1).toLowerCase()
          + nameUpper.substring(1, nameUpper.length());
    }
    catch(IndexOutOfBoundsException e)
    {
      throw new GRuntimeException("Invalid parameter setter method name "
          + methodName);
    }
  }

  public static String getParameterMethodName(String paramName)
  {
    return "set" + paramName.substring(0, 1).toUpperCase()
        + paramName.substring(1, paramName.length());
  }
  
  public static String getResultFileName(String experiment, String run, String series)
  {
    return experiment + "." + series + "." + run + ".gstats";
  }

  public static String getRunNameFromFileName(String experiment, String runFileName)
  {
    Pattern p = Pattern.compile(getRunFilePattern(experiment));
    Matcher matcher = p.matcher(runFileName);
    if(!matcher.matches())
    {
      throw new IllegalStateException("Incorrect run scenario name: " + runFileName);
    }
    
    return matcher.group(1); 
  }
  
  /**
   * Return seconds to wait from now to the given hour.
   */
  public static long waitUntil(Calendar now, int hour)
  {
    if(now.get(Calendar.HOUR_OF_DAY) > hour)
    {
      throw new IllegalStateException("Invalid dates");
    }

    Calendar s = (Calendar) now.clone();
    s.set(Calendar.HOUR_OF_DAY, hour);
    return (s.getTimeInMillis() - now.getTimeInMillis()) / 1000;
  }

  /**
   * Truncates the string to the specified length.
   */
  public static String cutString(String str, int len)
  {
    if(str != null && !str.isEmpty())
    {
      int i = Math.min(str.length(), len);
      String result = str.substring(0, i);
      if(i == len)
      {
        result += "...";
      }
      return result;
    }
    else
    {
      return str;
    }
  }
  
  public static String stringJoin(List<String> values, String delimeter)
  {
    Iterator<String> iter = values.iterator();
    
    if(!iter.hasNext()) return "";
    StringBuilder buffer = new StringBuilder(iter.next());
    while ( iter.hasNext() )
    {
      buffer.append(delimeter).append(iter.next());
    }
    return buffer.toString();
  }

  public static String getRunFilePattern(String experimentName)
  {
    return experimentName + "\\.(.+)\\.gstats";
  }

  public static String getRunFilePattern(String experimentName, String runName, List<String> seriePath)
  {
    StringBuffer result = new StringBuffer();
    result.append(experimentName);
    for(String p: seriePath)
    {
      result.append("\\." + p);
    }
    result.append("\\." + runName + "\\.gstats");
    return result.toString();
  }
}
