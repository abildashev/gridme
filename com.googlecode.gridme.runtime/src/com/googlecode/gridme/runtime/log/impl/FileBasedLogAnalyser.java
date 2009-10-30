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
package com.googlecode.gridme.runtime.log.impl;

import java.io.BufferedReader;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.exceptions.LoggerException;
import com.googlecode.gridme.runtime.log.AnalyserResult;
import com.googlecode.gridme.runtime.log.InTimeValue;
import com.googlecode.gridme.runtime.log.LogAnalyser;
import com.googlecode.gridme.runtime.log.LogManifest;
import com.googlecode.gridme.runtime.log.StaticProperty;

/**
 * File based analyzer implementation.
 * @see FastLogger 
 */
public class FileBasedLogAnalyser extends ZipLogAnalyser implements LogAnalyser
{
  // Metrics descriptions - we keep the map and 
  // fill it during index parsing.
  private HashMap<String,String> metricsDesc;
  private LogManifest manifest;

  /**
   * Opens new analyzer.
   * 
   * @param path database path
   * @throws LoggerException if the database does not exist.
   */
  public FileBasedLogAnalyser(File path) throws LoggerException
  {
    super(path);
    metricsDesc = new HashMap<String, String>();
  }

  /**
   * 
   * @param average - if true collect average value, otherwise aggregate.
   */
  private AnalyserResult getUniValueChange(final String metricsName,
                                           List<String> elements,
                                           int maxLength,
                                           long start,
                                           long stop,
                                           boolean average) throws GRuntimeException
  {
    if(maxLength < 0)
    {
      throw new IllegalArgumentException("maxLength can not be negative");
    }

    AnalyserResult result = new AnalyserResult(maxLength);

    if(!elements.isEmpty())
    {
      // Make a hash to store the last metrics value and keep the set of elements.
      HashMap<String, Long> elems = new HashMap<String, Long>(elements.size());

      // Get maximum number of records from index.
      int totalRec = 0;
      for(String elem : elements)
      {
        totalRec += getRequestLength(elem, metricsName);
        elems.put(elem, 0L);
      }

      try
      {
        // Skip if the given metrics does not exist for these elements
        if(totalRec > 0)
        {
          BufferedReader input = getDataReader(1);

          // Initialize interval length
          int intervalSize;
          if(maxLength == 0)
          {
            intervalSize = 1;
          }
          else
          {
            intervalSize = totalRec / maxLength;

            if(intervalSize == 0)
            {
              intervalSize = 1;
            }
          }

          int intervalCount = 1;
          int recordCount = 0;
          int currentCount = 0; // avg counter
          long currentAverage = 0; // get avg value
          long time = 0; // get avg time
          long cvalue;
          long ctime;
          long totalAvg = 0;
          long totalCount = 0;
          long aggregateAvg = 0;
          long peakValue = 0;
          long peakTime = 0;

          while(input.ready())
          {
            String line = input.readLine();
            if(line == null)
            {
              break;
            }
            String[] vt = line.split(",");

            if(metricsName.equals(vt[1]))
            {
              if(elems.get(vt[0]) != null)
              {
                // get time
                ctime = Long.parseLong(vt[3]);

                if((start < 0 || ctime >= start) && (stop < 0 || ctime <= stop))
                {
                  // get value
                  cvalue = Long.parseLong(vt[2]);
                  // Put value into cache for this element if required
                  elems.put(vt[0], cvalue);
                  // Update total average values
                  totalCount++;
                  totalAvg += cvalue;

                  // Now make the aggregate value for all elements
                  cvalue = 0;
                  for(Long val : elems.values())
                  {
                    cvalue += val;
                  }

                  aggregateAvg += cvalue;

                  // Cache peak value
                  if(cvalue > peakValue)
                  {
                    peakValue = cvalue;
                    peakTime = ctime;
                  }
                  // Make average value
                  if(average)
                  {
                    cvalue = cvalue / elems.values().size();
                  }

                  // update average counters 
                  currentAverage += cvalue;
                  time += ctime;
                  // Increase number of read records
                  recordCount++;
                  currentCount++;
                  // Check if we have to start a new interval
                  if((intervalCount != maxLength && recordCount == intervalCount
                      * intervalSize)
                      || (intervalCount == maxLength && recordCount == totalRec))
                  {
                    result.addValue(new InTimeValue(time / currentCount,
                        currentAverage / currentCount));
                    currentCount = 0;
                    time = currentAverage = 0;
                    intervalCount++;
                  }
                }
              }
            }
          }

          if(!average)
          {
            totalAvg = aggregateAvg;
          }
          result.setArithmeticMean((float) totalAvg
              / totalCount);
          result.setAggregate(totalAvg);          
          result.setPeak(new InTimeValue(peakTime, peakValue));
          input.close();
        }
      }
      catch(Exception e)
      {
        throw new LoggerException("Database read error "
            + path.getAbsolutePath(), e);
      }
    }
    return result;
  }

  /**
   * Extracts request length from index.
   */
  private int getRequestLength(String elementId, final String metricsName) throws LoggerException
  {
    try
    {
      BufferedReader input = getDataReader(2);
      // Search for the entry
      int result = 0;
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        String[] vt = line.split(",");
        if(elementId.equals(vt[0]))
        {
          if(metricsName.equals(vt[1]))
          {
            // Get value
            result = Integer.parseInt(vt[2]);
            break;
          }
        }
      }
      input.close();
      return result;
    }
    catch(Exception e)
    {
      throw new LoggerException("Index read error " + path.getAbsolutePath(), e);
    }
  }

  @Override
  public List<String> getElements() throws GRuntimeException
  {
    try
    {
      BufferedReader input = getDataReader(2);
      HashSet<String> result = new HashSet<String>(100);
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        result.add(line.split(",")[0]);
      }
      input.close();
      
      return new ArrayList<String>(result);
    }
    catch(Exception e)
    {
      throw new LoggerException("Index read error " + path.getAbsolutePath(), e);
    }
  }

  @Override
  public List<String> getMetrics(String elementId) throws GRuntimeException
  {
    try
    {
      BufferedReader input = getDataReader(2);
      ArrayList<String> result = new ArrayList<String>(10);
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        String[] tk = line.split(",");
        if(elementId.equals(tk[0]))
        {
          result.add(tk[1]);
          metricsDesc.put(tk[1], URLDecoder.decode(tk[3], FastLogger.ENCODING));
        }
      }
      input.close();
      return result;
    }
    catch(Exception e)
    {
      throw new LoggerException("Index read error " + path.getAbsolutePath(), e);
    }
  }

  @Override
  public List<StaticProperty> getProperties(String elementId) throws GRuntimeException
  {
    try
    {
      BufferedReader input = getDataReader(3);
      ArrayList<StaticProperty> result = new ArrayList<StaticProperty>();
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        String[] tk = line.split(",");
        if(elementId.equals(tk[0]))
        {
          result.add(new StaticProperty(elementId, 
              URLDecoder.decode(tk[1], FastLogger.ENCODING), 
              URLDecoder.decode(tk[2], FastLogger.ENCODING)));
        }
      }
      input.close();
      return result;
    }
    catch(Exception e)
    {
      throw new LoggerException(
          "Property read error " + path.getAbsolutePath(), e);
    }
  }

  @Override
  public AnalyserResult getAggregateValueChange(String metricsName,
                                                List<String> elements,
                                                int maxLength,
                                                long start,
                                                long stop) throws GRuntimeException
  {
    return getUniValueChange(metricsName, elements, maxLength, start, stop,
        false);
  }

  @Override
  public AnalyserResult getValueChange(String metricsName,
                                       List<String> elements,
                                       int maxLength,
                                       long start,
                                       long stop) throws GRuntimeException
  {
    return getUniValueChange(metricsName, elements, maxLength, start, stop,
        true);
  }

  @Override
  public LogManifest getManifest() throws GRuntimeException
  {
    if(manifest == null)
    {
      manifest = new LogManifest();
      try
      {
        BufferedReader input = getDataReader(4);
        while(input.ready())
        {
          String line = input.readLine();
          if(line == null)
          {
            break;
          }
          String[] tk = line.split(",");
          manifest.setDescription(URLDecoder.decode(tk[0], FastLogger.ENCODING));
          
          Calendar startTime = Calendar.getInstance();
          startTime.setTimeZone(TimeZone.getTimeZone(URLDecoder.decode(tk[1],
              FastLogger.ENCODING)));
          startTime.setTimeInMillis(Long.parseLong(tk[2]));
          manifest.setStartTime(startTime);
          break;
        }
        input.close();
      }
      catch(Exception e)
      {
        throw new LoggerException("Manifest read error " + path.getAbsolutePath(), e);
      }
    }
    return manifest;
  }

  @Override
  public String getMetricsDescription(String metricsName)
      throws GRuntimeException
  {
    if(metricsDesc.isEmpty())
    {
      try
      {
        BufferedReader input = getDataReader(2);
        while(input.ready())
        {
          String line = input.readLine();
          if(line == null)
          {
            break;
          }
          String[] tk = line.split(",");
          metricsDesc.put(tk[1], URLDecoder.decode(tk[3], FastLogger.ENCODING));
        }
        input.close();
      }
      catch(Exception e)
      {
        throw new LoggerException("Index read error " + path.getAbsolutePath(), e);
      }
    }
    
    return metricsDesc.get(metricsName);
  }

  @Override
  public List<String> getMetrics() throws GRuntimeException
  {
    try
    {
      BufferedReader input = getDataReader(2);
      HashSet<String> result = new HashSet<String>(100);
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        String[] tk = line.split(",");
        result.add(tk[1]);
        metricsDesc.put(tk[1], URLDecoder.decode(tk[3], FastLogger.ENCODING));
      }
      input.close();
      
      return new ArrayList<String>(result);
    }
    catch(Exception e)
    {
      throw new LoggerException("Index read error " + path.getAbsolutePath(), e);
    }
  }

  @Override
  public Calendar getCalendarStartTime() throws GRuntimeException
  {
    return getManifest().getStartTime();
  }
}
