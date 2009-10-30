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
package com.googlecode.gridme.runtime.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.exceptions.LoggerException;
import com.googlecode.gridme.runtime.log.GanttLogAnalyser;
import com.googlecode.gridme.runtime.log.GanttMessage;
import com.googlecode.gridme.runtime.log.NodeState;
import com.googlecode.gridme.runtime.log.NodeStateBusy;
import com.googlecode.gridme.runtime.log.NodeStateIdle;
import com.googlecode.gridme.runtime.log.NodeStatePowersave;
import com.googlecode.gridme.runtime.log.impl.FastGanttLogger;
import com.googlecode.gridme.runtime.log.impl.FileBasedGanttAnalyser;
import com.googlecode.gridme.runtime.visual.impl.ClusterGanttChartBuilder;

/**
 * Tests gantt chart logger 
 */
public class TestGanttLogger
{
  private static final String TESTDB = "testdb";

  @BeforeClass
  public static void prepare() throws LoggerException
  {
    FastGanttLogger.deleteDatabase(new File(TESTDB));
  }

  @Test
  public void test1() throws GRuntimeException
  {
    try
    {
      File testdb = new File(TESTDB);

      FastGanttLogger logger = new FastGanttLogger(Calendar.getInstance(),
          testdb, false);
      logger.logNodeState(new NodeStateIdle("my-clu$ter", "node1", 0, 0));
      logger.logNodeState(new NodeStatePowersave("my-clu$ter", "node2", 0, 10));
      logger.logNodeState(new NodeStateBusy("my-clu$ter", "node2",
          "mu#$-,task", 0, 10));
      logger.logNodeState(new NodeStateBusy("my-clu$ter2", "node0", "msk1", 10,
          100));
      logger.close();

      // results: my-clu$ter - 2 nodes, min time - 0, max time = 10
      // my-clu$ter2 - 1 node, min time = 100 == maxtime.

      GanttLogAnalyser analyser = new FileBasedGanttAnalyser(testdb);

      List<String> clusters = analyser.getClusters();
      assertTrue(clusters.get(0).equals("my-clu$ter"));
      assertTrue(clusters.get(1).equals("my-clu$ter2"));

      assertTrue(analyser.getNodesCount("my-clu$ter") == 2);
      assertTrue(analyser.getNodesCount("my-clu$ter2") == 1);

      assertTrue(analyser.getMinTimeSegment("my-clu$ter") == 0);
      assertTrue(analyser.getMaxTimeSegment("my-clu$ter") == 10);
      assertTrue(analyser.getMinTimeSegment("my-clu$ter2") == 100);
      assertTrue(analyser.getMaxTimeSegment("my-clu$ter2") == 100);

      List<String> nodes1 = analyser.getNodes("my-clu$ter");
      assertTrue(nodes1.size() == 2);
      assertTrue(nodes1.get(0).equals("node1"));
      assertTrue(nodes1.get(1).equals("node2"));

      List<String> nodes2 = analyser.getNodes("my-clu$ter2");
      assertTrue(nodes2.size() == 1);
      assertTrue(nodes2.get(0).equals("node0"));

      List<NodeState> state1 = analyser.getNodeStateChange("my-clu$ter",
          "node1");
      assertTrue(state1.size() == 1);
      assertTrue(state1.get(0).equals(
          new NodeStateIdle("my-clu$ter", "node1", 0, 0)));

      List<NodeState> state2 = analyser.getNodeStateChange("my-clu$ter",
          "node2");
      assertTrue(state2.size() == 2);
      assertTrue(state2.get(0).equals(
          new NodeStatePowersave("my-clu$ter", "node2", 0, 10)));
      assertTrue(state2.get(1).equals(
          new NodeStateBusy("my-clu$ter", "node2", "mu#$-,task", 0, 10)));
    }
    finally
    {
      FastGanttLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test2() throws GRuntimeException
  {
    try
    {
      File testdb = new File(TESTDB);
      String cluster = "Test cluster";

      FastGanttLogger logger = new FastGanttLogger(Calendar.getInstance(),
          testdb, false);

      logger.logNodeState(new NodeStateIdle(cluster, "node1", 10, 50));
      logger.logNodeState(new NodeStateIdle(cluster, "node2", 0, 40));
      logger.logNodeState(new NodeStateBusy(cluster, "node1", "task1", 61, 23));
      logger.logNodeState(new NodeStateBusy(cluster, "node2", "task2", 41, 10));

      logger.logInfoMessage(10, "qlen: 100");
      logger.logInfoMessage(10, "Long long long long long meeeeeeeeeeeeeeeeeeeeeeeeeeeeessage");
      
      logger.close();

      try
      {
        ClusterGanttChartBuilder chart = new ClusterGanttChartBuilder(testdb);
        chart.buildClusterGanttChart(cluster, 1.0f);
      }
      catch(Exception e)
      {
        throw new GRuntimeException(e);
      }
    }
    finally
    {
      FastGanttLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test3() throws GRuntimeException
  {
    try
    {
      File testdb = new File(TESTDB);

      Calendar start = Calendar.getInstance();
      start.clear();
      start.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
      start.set(2009, 0, 1, 12, 0, 0);

      FastGanttLogger logger = new FastGanttLogger(start, testdb, false);
      logger.close();

      FileBasedGanttAnalyser anl = new FileBasedGanttAnalyser(testdb);

      assertTrue(start.equals(anl.getCalendarStartTime()));
    }
    finally
    {
      FastGanttLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test4() throws GRuntimeException
  {
    try
    {
      File testdb = new File(TESTDB);

      FastGanttLogger logger = new FastGanttLogger(Calendar.getInstance(), testdb, false);
      
      logger.logInfoMessage(100, "Test message 1");
      logger.logInfoMessage(900, "Test message 2");
      
      logger.close();

      FileBasedGanttAnalyser anl = new FileBasedGanttAnalyser(testdb);

      Object[] messages = anl.getMessages().toArray();
      
      assertTrue(anl.getMessages().size() == 2);
      assertTrue(((GanttMessage)messages[0]).getTime() == 100);
      assertTrue(((GanttMessage)messages[0]).getFirstMessage().equals("Test message 1"));
      assertTrue(((GanttMessage)messages[1]).getTime() == 900);
      assertTrue(((GanttMessage)messages[1]).getFirstMessage().equals("Test message 2"));
    }
    finally
    {
      FastGanttLogger.deleteDatabase(new File(TESTDB));
    }
  }
}
