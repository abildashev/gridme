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
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.ZipEntry;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.exceptions.LoggerException;
import com.googlecode.gridme.runtime.log.GanttLogger;
import com.googlecode.gridme.runtime.log.GanttMessage;
import com.googlecode.gridme.runtime.log.NodeState;

/**
 * File format: 
 *  <ol>
 *    <li> Zip entry with time TIME_NAME
 *    <li> Zip entry with data DATA_NAME
 *    <li> Zip entry with cluster sizes CSIZE_NAME
 *    <li> Zip entry with messages MESSAGES_NAME
 *  </ol>
 *  
 *  Time format: {timezone,milliseconds start}
 *  Data format is the following: {ClusterId,NodeId,StartTime,Duration,State,StateProperties}
 *  Cluster sizes format: {ClusterId,minTime,maxTime,nodeCount}
 *  Messages format: {modeltimesecond,message}
 */
public class FastGanttLogger extends TextBasedLogger implements GanttLogger
{
  public static final int E_TIME = 1;
  public static final int E_DATA = 2;
  public static final int E_CLUSTERS = 3;
  public static final int E_MESSAGES = 4;
  
  public static final String DATA_NAME = "data";
  public static final String CLUSTERS_NAME = "clusters";
  public static final String MESSAGES_NAME = "messages";
  public static final String TIME_NAME = "time";

  private HashMap<String, Long> clusterMinTime;
  private HashMap<String, Long> clusterMaxTime;
  private HashMap<String, HashSet<String>> clusterNodes;
  private HashMap<Long, GanttMessage> messages;
  
  /**
   * Creates a new instance of the logger.
   * @param path database path.
   * @param force if true, existing file will be overwritten.
   * @throws LoggerException 
   */
  public FastGanttLogger(Calendar start, File path, boolean force)
      throws LoggerException
  {
    super(TIME_NAME, path, force);

    clusterMinTime = new HashMap<String, Long>();
    clusterMaxTime = new HashMap<String, Long>();
    clusterNodes = new HashMap<String, HashSet<String>>();
    messages = new HashMap<Long, GanttMessage>();

    try
    {
      out.println(URLEncoder.encode(start.getTimeZone().getID(), ENCODING)
          + "," + start.getTimeInMillis());
      zippo.putNextEntry(new ZipEntry(DATA_NAME));
    }
    catch(Exception e)
    {
      throw new LoggerException("Unable to create gantt log.", e);
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
      // Write meta info
      zippo.putNextEntry(new ZipEntry(CLUSTERS_NAME));

      // ClusterId,minTime,maxTime,nodeCount
      for(String cluster : clusterNodes.keySet())
      {
        out.println(URLEncoder.encode(cluster, ENCODING) + ","
            + getMinTime(cluster) + "," + getMaxTime(cluster) + ","
            + clusterNodes.get(cluster).size());
      }

      zippo.putNextEntry(new ZipEntry(MESSAGES_NAME));

      for(GanttMessage message : messages.values())
      {
        for(String text : message.getMessages())
        {
          out.println("" + message.getTime() + ","
              + URLEncoder.encode(text, ENCODING));
        }
      }
    }
    catch(IOException e)
    {
      throw new LoggerException("Unable to create gantt log.", e);
    }

    // Finish
    out.close();
  }

  @Override
  public void logNodeState(NodeState state) throws GRuntimeException
  {
    // ClusterId,NodeId,StartTime,Duration,State,StateProperties
    try
    {
      out.println(URLEncoder.encode(state.getClusterId(), ENCODING) + ","
          + URLEncoder.encode(state.getNodeId(), ENCODING) + ","
          + state.getStartTime() + "," + state.getDuration() + ","
          + state.getStateName() + ","
          + URLEncoder.encode(state.getStateProperties(), ENCODING));
    }
    catch(UnsupportedEncodingException e)
    {
      throw new GRuntimeException(e);
    }

    // Update min, max time segments, cluster size.

    if(state.getDuration() < getMinTime(state.getClusterId()))
    {
      setMinTime(state.getClusterId(), state.getDuration());
    }

    if(state.getDuration() > getMaxTime(state.getClusterId()))
    {
      setMaxTime(state.getClusterId(), state.getDuration());
    }

    addClusterNode(state.getClusterId(), state.getNodeId());
  }

  private void addClusterNode(String clusterId, String nodeId)
  {
    HashSet<String> nodes = clusterNodes.get(clusterId);
    if(nodes == null)
    {
      nodes = new HashSet<String>();
      clusterNodes.put(clusterId, nodes);
    }
    nodes.add(nodeId);
  }

  private long getMinTime(String clusterId)
  {
    Long time = clusterMinTime.get(clusterId);

    if(time == null)
    {
      return Long.MAX_VALUE;
    }

    return time;
  }

  private long getMaxTime(String clusterId)
  {
    Long time = clusterMaxTime.get(clusterId);

    if(time == null)
    {
      return 0;
    }

    return time;
  }

  private void setMinTime(String clusterId, long duration)
  {
    clusterMinTime.put(clusterId, duration);
  }

  private void setMaxTime(String clusterId, long duration)
  {
    clusterMaxTime.put(clusterId, duration);
  }

  @Override
  public void logInfoMessage(long modelTime, String message)
      throws GRuntimeException
  {
    GanttMessage msg = messages.get(modelTime);
    
    if(msg == null)
    {
      msg = new GanttMessage(modelTime, message);
      messages.put(modelTime, msg);
    }
    else
    {
      msg.addMessage(message);
    }
  }
}
