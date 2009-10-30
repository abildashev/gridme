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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.exceptions.LoggerException;
import com.googlecode.gridme.runtime.log.GanttLogAnalyser;
import com.googlecode.gridme.runtime.log.GanttMessage;
import com.googlecode.gridme.runtime.log.NodeState;
import com.googlecode.gridme.runtime.log.NodeStateBusy;
import com.googlecode.gridme.runtime.log.NodeStateIdle;
import com.googlecode.gridme.runtime.log.NodeStatePowersave;

/**
 * 
 */
public class FileBasedGanttAnalyser extends ZipLogAnalyser implements
    GanttLogAnalyser
{
  private Calendar startTime;

  /**
   * 
   */
  public FileBasedGanttAnalyser(File path) throws LoggerException
  {
    super(path);

    try
    {
      BufferedReader input = getDataReader(FastGanttLogger.E_TIME);
      if(input.ready())
      {
        String line = input.readLine();
        String[] tokens = line.split(",");

        startTime = Calendar.getInstance();
        startTime.setTimeZone(TimeZone.getTimeZone(URLDecoder.decode(tokens[0],
            FastGanttLogger.ENCODING)));
        startTime.setTimeInMillis(Long.parseLong(tokens[1]));
      }
      input.close();
    }
    catch(Exception e)
    {
      throw new LoggerException("Time read error " + path.getAbsolutePath(), e);
    }
  }

  private String[] getClusterProps(String clusterId) throws GRuntimeException
  {
    String[] result = null;

    try
    {
      clusterId = URLEncoder.encode(clusterId, FastGanttLogger.ENCODING);
      BufferedReader input = getDataReader(FastGanttLogger.E_CLUSTERS);
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        result = line.split(",");
        if(clusterId.equals(result[0]))
        {
          break;
        }
      }
      input.close();
    }
    catch(Exception e)
    {
      throw new LoggerException("Cluster list read error "
          + path.getAbsolutePath(), e);
    }

    if(result == null)
    {
      throw new GRuntimeException("No cluster with such id exists: "
          + clusterId + " log path: " + path.getAbsolutePath());
    }

    return result;
  }

  @Override
  public long getMaxTimeSegment(String clusterId) throws GRuntimeException
  {
    String[] cprops = getClusterProps(clusterId);
    return Long.parseLong(cprops[2]);
  }

  @Override
  public long getMinTimeSegment(String clusterId) throws GRuntimeException
  {
    String[] cprops = getClusterProps(clusterId);
    return Long.parseLong(cprops[1]);
  }

  @Override
  public List<NodeState> getNodeStateChange(String clusterId, String nodeId)
      throws GRuntimeException
  {
    ArrayList<NodeState> result = new ArrayList<NodeState>();

    try
    {
      clusterId = URLEncoder.encode(clusterId, FastGanttLogger.ENCODING);
      nodeId = URLEncoder.encode(nodeId, FastGanttLogger.ENCODING);

      BufferedReader input = getDataReader(FastGanttLogger.E_DATA);
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        String[] tk = line.split(",");
        if(clusterId.equals(tk[0]))
        {
          if(nodeId.equals(tk[1]))
          {
            result.add(createNodeState(tk));
          }
        }
      }
      input.close();
    }
    catch(Exception e)
    {
      throw new LoggerException("Cluster list read error "
          + path.getAbsolutePath(), e);
    }

    return result;
  }

  private NodeState createNodeState(String[] tk) throws NumberFormatException,
      UnsupportedEncodingException, GRuntimeException
  {
    String cluster = URLDecoder.decode(tk[0], FastGanttLogger.ENCODING);
    String node = URLDecoder.decode(tk[1], FastGanttLogger.ENCODING);
    long time = Long.parseLong(tk[2]);
    long duration = Long.parseLong(tk[3]);

    if(tk[4].equals(NodeStateBusy.STATE_NAME))
    {
      return new NodeStateBusy(cluster, node, URLDecoder.decode(tk[5],
          FastGanttLogger.ENCODING), time, duration);
    }
    if(tk[4].equals(NodeStateIdle.STATE_NAME))
    {
      return new NodeStateIdle(cluster, node, time, duration);
    }
    if(tk[4].equals(NodeStatePowersave.STATE_NAME))
    {
      return new NodeStatePowersave(cluster, node, time, duration);
    }

    throw new GRuntimeException("Unknown node state in log file: " + tk[4]);
  }

  @Override
  public List<String> getNodes(String clusterId) throws GRuntimeException
  {
    HashSet<String> nodes = new HashSet<String>();

    try
    {
      clusterId = URLEncoder.encode(clusterId, FastGanttLogger.ENCODING);
      BufferedReader input = getDataReader(FastGanttLogger.E_DATA);
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        String[] tk = line.split(",");
        if(clusterId.equals(tk[0]))
        {
          nodes.add(URLDecoder.decode(tk[1], FastGanttLogger.ENCODING));
        }
      }
      input.close();
    }
    catch(Exception e)
    {
      throw new LoggerException("Data list read error "
          + path.getAbsolutePath(), e);
    }

    ArrayList<String> result = new ArrayList<String>();
    for(String node : nodes)
    {
      result.add(node);
    }

    return result;
  }

  @Override
  public int getNodesCount(String clusterId) throws GRuntimeException
  {
    String[] cprops = getClusterProps(clusterId);
    return Integer.parseInt(cprops[3]);
  }

  @Override
  public List<String> getClusters() throws GRuntimeException
  {
    ArrayList<String> clusters = new ArrayList<String>();

    try
    {
      BufferedReader input = getDataReader(FastGanttLogger.E_CLUSTERS);
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        String[] tk = line.split(",");
        clusters.add(URLDecoder.decode(tk[0], FastGanttLogger.ENCODING));
      }
      input.close();
    }
    catch(Exception e)
    {
      throw new LoggerException("Cluster list read error "
          + path.getAbsolutePath(), e);
    }
    return clusters;
  }

  @Override
  public Calendar getCalendarStartTime() throws GRuntimeException
  {
    return startTime;
  }

  @Override
  public Collection<GanttMessage> getMessages() throws GRuntimeException
  {
    HashMap<Long, GanttMessage> messages = new HashMap<Long, GanttMessage>();

    try
    {
      BufferedReader input = getDataReader(FastGanttLogger.E_MESSAGES);
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        String[] tk = line.split(",");
        long time = Long.parseLong(tk[0]);
        String text = URLDecoder.decode(tk[1], FastGanttLogger.ENCODING);
        GanttMessage msg = messages.get(time);
        if(msg == null)
        {
          msg = new GanttMessage(time, text);
          messages.put(time, msg);
        }
        else
        {
          msg.addMessage(text);
        }
      }
      input.close();
    }
    catch(Exception e)
    {
      throw new LoggerException("Messages list read error "
          + path.getAbsolutePath(), e);
    }

    return messages.values();
  }
}
