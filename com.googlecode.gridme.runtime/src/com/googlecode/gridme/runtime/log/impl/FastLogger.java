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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.exceptions.LoggerException;
import com.googlecode.gridme.runtime.log.LogManifest;
import com.googlecode.gridme.runtime.log.Metric;
import com.googlecode.gridme.runtime.log.MetricsLogger;
import com.googlecode.gridme.runtime.log.StaticProperty;

/**
 * Provides fast text file based implementation.
 * File format: 
 *  <ol>
 *    <li> Zip entry with data DATA_NAME
 *    <li> Zip entry with index data INDEX_NAME
 *    <li> Zip entry with properties PROPERTIES_NAME
 *    <li> Zip entry with manifest MANIFEST_NAME
 *  </ol>
 *  
 *  Data format is the following: {ElementId,MetricsName,Value,Time}
 *  Index format: {ElementId,MetricsName,Record Count,Metrics description}
 *  Properties format: {ElementId,PropertyName,Value}
 *  Manifest format: {Experiment description,Timezone,Time in millis} 
 */
public class FastLogger extends TextBasedLogger implements MetricsLogger
{
  public static final String DATA_NAME = "data";
  public static final String INDEX_NAME = "index";
  public static final String PROPERTIES_NAME = "properties";
  public static final String MANIFEST_NAME = "manifest";

  /**
   * Index record
   */
  private static class IndexEntry
  {
    private final String elementId;
    private final String metricName;
    private final String metricDescr;
    private int value;

    public IndexEntry(String elementId, String metricName, String descr)
    {
      this.elementId = elementId;
      this.metricName = metricName;
      String md = metricName;
      if(descr != null)
      {
        try
        {
          md = URLEncoder.encode(descr, ENCODING);
        }
        catch(UnsupportedEncodingException e)
        {
        }
      }
      metricDescr = md;
    }

    /**
     * Increment number of records
     */
    public void incValue()
    {
      value++;
    }

    public String toString()
    {
      return elementId + "," + metricName + "," + value + "," + metricDescr;
    }
  }

  private HashMap<String, IndexEntry> indexTab;
  private ArrayList<StaticProperty> properties;
  private LogManifest manifest;

  /**
   * Creates a new instance of the logger.
   * @param path database path.
   * @param force if true, existing file will be overwritten.
   * @throws LoggerException 
   */
  public FastLogger(File path, boolean force) throws LoggerException
  {
    super(DATA_NAME, path, force);

    properties = new ArrayList<StaticProperty>();
    indexTab = new HashMap<String, IndexEntry>(200);
  }

  @Override
  public void logMetric(Metric metric)
  {
    String key = metric.getElementId() + metric.getMetricName();

    out.println("" + metric.getElementId() + "," + metric.getMetricName()
        + "," + metric.getValue() + "," + metric.getTime());

    // Update index
    synchronized(indexTab)
    {
      IndexEntry e = indexTab.get(key);
      if(e == null)
      {
        e = new IndexEntry(metric.getElementId(), metric.getMetricName(),
            metric.getComment());
        indexTab.put(key, e);
      }
      e.incValue();
    }
  }

  @Override
  public void logStaticProperty(StaticProperty prop) throws GRuntimeException
  {
    synchronized(properties)
    {
      properties.add(prop);
    }
  }

  /**
   * Releases resources occupied by the logger.
   * @throws LoggerException 
   */
  public void close() throws LoggerException
  {
    try
    {
      // Write index
      zippo.putNextEntry(new ZipEntry(INDEX_NAME));
      for(IndexEntry ie : indexTab.values())
      {
        out.println(ie.toString());
      }
      // Write properties
      zippo.putNextEntry(new ZipEntry(PROPERTIES_NAME));
      for(StaticProperty p : properties)
      {
        out.println(p.getElementId() + ","
            + URLEncoder.encode(p.getName(), ENCODING) + ","
            + URLEncoder.encode(p.getValue(), ENCODING));
      }
      // Write manifest
      zippo.putNextEntry(new ZipEntry(MANIFEST_NAME));
      if(manifest != null)
      {
        out.println(URLEncoder.encode(manifest.getDescription(), ENCODING)
            + ","
            + URLEncoder.encode(manifest.getStartTime().getTimeZone().getID(),
                ENCODING) + "," + manifest.getStartTime().getTimeInMillis());
      }
    }
    catch(IOException e)
    {
      throw new LoggerException("Error while making index", e);
    }
    // Finish
    out.close();
  }

  @Override
  public void setManifest(LogManifest man) throws GRuntimeException
  {
    this.manifest = man;
  }
}
