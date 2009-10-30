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

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.gridme.runtime.log.LogManifest;
import com.googlecode.gridme.runtime.log.StaticProperty;
import com.googlecode.gridme.runtime.log.impl.FastLogger;
import com.googlecode.gridme.runtime.log.impl.FileBasedLogAnalyser;
import com.googlecode.gridme.runtime.log.metrics.CLoadMetric;
import com.googlecode.gridme.runtime.log.properties.ClusterSizeProperty;
import com.googlecode.gridme.runtime.opt.ConsoleProgressMonitor;
import com.googlecode.gridme.runtime.visual.LogEntry;
import com.googlecode.gridme.runtime.visual.VChart;
import com.googlecode.gridme.runtime.visual.Visualizer;
import com.googlecode.gridme.runtime.visual.impl.VisualizerBarCharts;
import com.googlecode.gridme.runtime.visual.impl.VisualizerLineCharts;

/**
 * Tests for visualizer.
 */
public class TestVisual
{
  private static File logFile1 = new File("log1.zip");
  private static File logFile2 = new File("log2.zip");

  @Before
  public void createLogs() throws Exception
  {
    // 1 log
    FastLogger log = new FastLogger(logFile1, true);

    log.logMetric(new CLoadMetric("e1", 1, 5));
    log.logMetric(new CLoadMetric("e2", 2, 4));
    log.logMetric(new CLoadMetric("e1", 5, 8));
    log.logMetric(new CLoadMetric("e2", 6, 18));
    log.logMetric(new CLoadMetric("e1", 8, 3));
    log.logMetric(new CLoadMetric("e2", 9, 2));
    log.logMetric(new CLoadMetric("e1", 16, 10));
    log.logMetric(new CLoadMetric("e2", 17, 11));
    log.logMetric(new CLoadMetric("e1", 30, 12));
    log.logMetric(new CLoadMetric("e2", 31, 2));

    log.logMetric(new CLoadMetric("e1", 2000, 100000));
    log.logMetric(new CLoadMetric("e1", 10000, 200000));
    log.logMetric(new CLoadMetric("e1", 15000, 500501));
    log.logMetric(new CLoadMetric("e1", 20000, 300400));
    log.logMetric(new CLoadMetric("e1", 25000, 400300));
    log.logMetric(new CLoadMetric("e1", 250000, 500300));
    
    log.setManifest(new LogManifest("Long long long long long long "
        + "long long long long long long long "
        + "long long long long long long long "
        + "long long long long long long long very long description"));
    log.logStaticProperty(new ClusterSizeProperty("e1", "25"));
    log.logStaticProperty(new ClusterSizeProperty("e2", "256"));
    log
        .logStaticProperty(new StaticProperty("e2", "My Property", "Some value"));

    log.close();

    // log 2
    log = new FastLogger(logFile2, true);

    log.logMetric(new CLoadMetric("e1", 2, 1));
    log.logMetric(new CLoadMetric("e2", 4, 2));

    log.logMetric(new CLoadMetric("e1", 5, 22));
    log.logMetric(new CLoadMetric("e2", 6, 11));

    log.logMetric(new CLoadMetric("e1", 9, 4));
    log.logMetric(new CLoadMetric("e2", 10, 5));

    log.logMetric(new CLoadMetric("e1", 15, 11));
    log.logMetric(new CLoadMetric("e2", 19, 6));

    log.logMetric(new CLoadMetric("e1", 29, 8));
    log.logMetric(new CLoadMetric("e2", 32, 3));

    log.setManifest(new LogManifest("Test log2"));
    log.close();

  }
  
  @After
  public void removeLog()
  {
    logFile1.delete();
    logFile2.delete();
  }

  @Test
  public void test1() throws Exception
  {
    Visualizer v = new VisualizerLineCharts("testVisual");

    ArrayList<VChart> charts = new ArrayList<VChart>();
    charts.add(new VChart("e1", CLoadMetric.NAME));
    ArrayList<LogEntry> logList = new ArrayList<LogEntry>();
    logList.add(new LogEntry(logFile1.getName(), new FileBasedLogAnalyser(
        logFile1)));
    logList.add(new LogEntry(logFile2.getName(), new FileBasedLogAnalyser(
        logFile2)));

    v.execute("testVisual", 5, 16, charts, logList, new ConsoleProgressMonitor());
    
    VisualizerLineCharts v2 = new VisualizerLineCharts("testVisual_bigRes");
    v2.setWidth("1280");
    v2.setHeight("1024");
    v2.execute("testVisual_bigRes", 0, 33, charts, logList, new ConsoleProgressMonitor());
  }

  @Test
  public void test2() throws Exception
  {
    VisualizerBarCharts v = new VisualizerBarCharts("testVisualBars");

    ArrayList<VChart> charts = new ArrayList<VChart>();
    charts.add(new VChart("e1", CLoadMetric.NAME));
    //charts.add(new VChart("e2", CLoadMetric.NAME));
    ArrayList<LogEntry> logList = new ArrayList<LogEntry>();
    logList.add(new LogEntry(logFile1.getName(), new FileBasedLogAnalyser(
        logFile1)));
    logList.add(new LogEntry(logFile2.getName(), new FileBasedLogAnalyser(
        logFile2)));

    v.execute("testVisualBars", 5, 16, charts, logList, new ConsoleProgressMonitor());
  }
  
  @Test
  public void test3() throws Exception
  {
    Visualizer v = new VisualizerLineCharts("testVisualLong");

    ArrayList<VChart> charts = new ArrayList<VChart>();
    charts.add(new VChart("e1", CLoadMetric.NAME));
    ArrayList<LogEntry> logList = new ArrayList<LogEntry>();
    logList.add(new LogEntry(logFile1.getName(), new FileBasedLogAnalyser(
        logFile1)));

    v.execute("testVisualLong", 1000, 30000, charts, logList, new ConsoleProgressMonitor());
  }

  @Test
  public void test4() throws Exception
  {
    VisualizerLineCharts v = new VisualizerLineCharts("testVisualLongTime");
    v.setMetric("time");

    ArrayList<VChart> charts = new ArrayList<VChart>();
    charts.add(new VChart("e1", CLoadMetric.NAME));
    ArrayList<LogEntry> logList = new ArrayList<LogEntry>();
    logList.add(new LogEntry(logFile1.getName(), new FileBasedLogAnalyser(
        logFile1)));

    v.execute("testVisualLongTime", 1000, 30000, charts, logList, new ConsoleProgressMonitor());
  }
  
  @Test
  public void test5() throws Exception
  {
    VisualizerBarCharts v = new VisualizerBarCharts("testVisualBarsValueTime");
    v.setMetric("time");
    v.setOperation("max");

    ArrayList<VChart> charts = new ArrayList<VChart>();
    charts.add(new VChart("e1", CLoadMetric.NAME));
    
    ArrayList<LogEntry> logList = new ArrayList<LogEntry>();
    logList.add(new LogEntry(logFile1.getName(), new FileBasedLogAnalyser(
        logFile1)));

    v.execute("testVisualBarsValueTime", 1000, 30000, charts, logList, new ConsoleProgressMonitor());
  }
}
