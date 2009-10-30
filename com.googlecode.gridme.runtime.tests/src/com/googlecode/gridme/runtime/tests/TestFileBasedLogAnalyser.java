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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.log.AnalyserResult;
import com.googlecode.gridme.runtime.log.InTimeValue;
import com.googlecode.gridme.runtime.log.LogManifest;
import com.googlecode.gridme.runtime.log.Metric;
import com.googlecode.gridme.runtime.log.StaticProperty;
import com.googlecode.gridme.runtime.log.impl.FastLogger;
import com.googlecode.gridme.runtime.log.impl.FileBasedLogAnalyser;

/**
 * Tests for file based analyzer 
 */
public class TestFileBasedLogAnalyser
{
  private static final int DBSIZE = 10000000;
  private static final String TESTDB = "testdb";

  @Test
  public void test1() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);

      logger.logMetric(new Metric("e0", "m1", 11, 1021, "aaa1"));
      logger.logMetric(new Metric("e1", "m1", 12, 1022, "aaa1"));
      logger.logMetric(new Metric("e1", "m2", 10, 1024, "a a a-,;'2")); // target
      logger.logMetric(new Metric("e1", "m2m2", 14, 1023, "aaa2m2"));
      logger.logMetric(new Metric("e2", "m1", 15, 1025, "aaa1"));

      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      AnalyserResult result = fa.getValueChange("m2", Arrays.asList("e1"), 0,
          -1, -1);

      assertTrue(result.getValues().size() == 1
          && result.getValues().get(0).getValue() == 1024
          && result.getValues().get(0).getTime() == 10);

      assertTrue(result.getPeak().getValue() == 1024);
      assertTrue(result.getPeak().getTime() == 10);
      
      assertTrue(fa.getMetricsDescription("m1").equals("aaa1"));
      assertTrue(fa.getMetricsDescription("m2").equals("a a a-,;'2"));
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void test3() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      fa.getValueChange("y", Arrays.asList("x"), -10, -1, -1);
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  /**
   * Test end points
   */
  public void test4() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);

      logger.logMetric(new Metric("e0", "m1", 11, 1021, "aaa"));
      logger.logMetric(new Metric("e1", "m1", 12, 1022, "aaa"));
      logger.logMetric(new Metric("e1", "m2", 1, 10, "a ,a ;,a")); // target
      logger.logMetric(new Metric("e1", "m2", 2, 20, "aaa")); // target
      logger.logMetric(new Metric("e1", "m2", 3, 30, "aaa")); // target
      logger.logMetric(new Metric("e1", "m2", 4, 40, "aaa")); // target
      logger.logMetric(new Metric("e1", "m3", 14, 1023, "aaa"));
      logger.logMetric(new Metric("e2", "m1", 15, 1025, "aaa"));

      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      List<InTimeValue> results = fa.getValueChange("m2", Arrays.asList("e1"),
          1, -1, -1).getValues();

      assertTrue(results.size() == 1 && results.get(0).getValue() == 25
          && results.get(0).getTime() == 2);

      // Now with big interval
      results = fa.getValueChange("m2", Arrays.asList("e1"), 100, -1, -1)
          .getValues();

      assertTrue(results.size() == 4 && results.get(0).getValue() == 10
          && results.get(0).getTime() == 1 && results.get(1).getValue() == 20
          && results.get(1).getTime() == 2 && results.get(2).getValue() == 30
          && results.get(2).getTime() == 3 && results.get(3).getValue() == 40
          && results.get(3).getTime() == 4);
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  /**
   * Test integer intervals
   */
  public void test5() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);

      logger.logMetric(new Metric("e0", "m1", 11, 1021, "aaa"));
      logger.logMetric(new Metric("e1", "m1", 12, 1022, "aaa"));
      logger.logMetric(new Metric("e1", "m2", 1, 10, "aaa")); // target
      logger.logMetric(new Metric("e1", "m2", 2, 20, "a,a,a")); // target
      logger.logMetric(new Metric("e1", "m2", 3, 30, "a,aa")); // target
      logger.logMetric(new Metric("e1", "m2", 4, 40, "aaa")); // target
      logger.logMetric(new Metric("e1", "m3", 14, 1023, "aaa"));
      logger.logMetric(new Metric("e2", "m1", 15, 1025, "aaa"));

      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      List<InTimeValue> results = fa.getValueChange("m2", Arrays.asList("e1"),
          4, -1, -1).getValues();

      assertTrue(results.size() == 4 && results.get(0).getValue() == 10
          && results.get(0).getTime() == 1 && results.get(1).getValue() == 20
          && results.get(1).getTime() == 2 && results.get(2).getValue() == 30
          && results.get(2).getTime() == 3 && results.get(3).getValue() == 40
          && results.get(3).getTime() == 4);
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  /**
   * Test fractional intervals
   */
  public void test6() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);

      logger.logMetric(new Metric("e0", "m1", 11, 1021, "aaa"));
      logger.logMetric(new Metric("e1", "m1", 12, 1022, "aaa"));
      logger.logMetric(new Metric("e1", "m2", 1, 10, "aaa")); // target
      logger.logMetric(new Metric("e1", "m2", 2, 20, "aaa")); // target
      logger.logMetric(new Metric("e1", "m2", 3, 30, "aaa")); // target
      logger.logMetric(new Metric("e1", "m2", 4, 40, "aaa")); // target
      logger.logMetric(new Metric("e1", "m2", 5, 50, "aaa")); // target
      logger.logMetric(new Metric("e1", "m2", 6, 60, "aaa")); // target
      logger.logMetric(new Metric("e1", "m2", 7, 70, "aaa")); // target
      logger.logMetric(new Metric("e1", "m2", 8, 80, "aaa")); // target
      logger.logMetric(new Metric("e1", "m3", 14, 1023, "aaa"));
      logger.logMetric(new Metric("e2", "m1", 15, 1025, "aaa"));

      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      List<InTimeValue> results = fa.getValueChange("m2", Arrays.asList("e1"),
          3, -1, -1).getValues();

      assertTrue(results.size() == 3 && results.get(0).getValue() == 15
          && results.get(0).getTime() == 1 && results.get(1).getValue() == 35
          && results.get(1).getTime() == 3 && results.get(2).getValue() == 65
          && results.get(2).getTime() == 6);

    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  @Ignore
  /**
   * Test big database. Check memory settings.
   */
  public void test7() throws GRuntimeException
  {
    try
    {
      // Write file
      long start = System.currentTimeMillis();
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      for(int i = 0; i < DBSIZE; i++)
      {
        logger.logMetric(new Metric("e1", "m", i, i * 2, "aaa"));
        logger.logMetric(new Metric("ex", "m", i, i * 2, "aaa"));
        logger.logMetric(new Metric("e2", "m", i, i * 2, "aaa"));
      }
      logger.close();
      System.out.println("Write time: " + (System.currentTimeMillis() - start)
          / 1000 + " Sec.");

      // Read file
      start = System.currentTimeMillis();
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      fa.getValueChange("m", Arrays.asList("ex"), 100, -1, -1);
      System.out.println("Read time: " + (System.currentTimeMillis() - start)
          / 1000 + " Sec.");
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  /**
   * Test properties
   */
  public void test8() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      // Metrics
      logger.logMetric(new Metric("e0", "m1", 11, 1021));
      logger.logMetric(new Metric("e1", "m2", 1, 10));
      logger.logMetric(new Metric("e1", "m2", 1, 10));
      logger.logMetric(new Metric("e2", "m1", 15, 1025));
      // Properties
      logger.logStaticProperty(new StaticProperty("e1", "pString", "pvalue1"));
      logger.logStaticProperty(new StaticProperty("e1", "pNum", "10"));
      logger.logStaticProperty(new StaticProperty("e2", "pString", "pvalue12"));
      logger.logStaticProperty(new StaticProperty("e4", "pNum", "101"));
      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      List<InTimeValue> results = fa.getValueChange("m2", Arrays.asList("e1"),
          1, -1, -1).getValues();

      assertTrue(results.size() == 1 && results.get(0).getValue() == 10
          && results.get(0).getTime() == 1);
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test10() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      // Metrics
      logger.logMetric(new Metric("e0", "m1", 11, 1021));
      logger.logMetric(new Metric("e1", "m2", 1, 10));
      logger.logMetric(new Metric("e1", "m2", 1, 10));
      logger.logMetric(new Metric("e1", "m3", 1, 10));
      logger.logMetric(new Metric("e1", "m1", 1, 10));
      logger.logMetric(new Metric("e2", "m1", 15, 1025));
      // Properties
      logger.logStaticProperty(new StaticProperty("e1", "pString", "pvalue1\nxxx,ggg\n"));
      logger.logStaticProperty(new StaticProperty("e1", "pNum", "10"));
      logger.logStaticProperty(new StaticProperty("e2", "pString", "pvalue12"));
      logger.logStaticProperty(new StaticProperty("e4", "pNum", "101"));
      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      Collection<String> elems = fa.getElements();
      assertTrue(elems.size() == 3);

      assertTrue(elems.contains("e0"));
      Collection<String> metrics = fa.getMetrics("e0");
      assertTrue(metrics.size() == 1);
      assertTrue(metrics.contains("m1"));

      assertTrue(elems.contains("e1"));
      metrics = fa.getMetrics("e1");
      assertTrue(metrics.size() == 3);
      assertTrue(metrics.contains("m1"));
      assertTrue(metrics.contains("m2"));
      assertTrue(metrics.contains("m3"));

      assertTrue(elems.contains("e2"));
      metrics = fa.getMetrics("e2");
      assertTrue(metrics.size() == 1);
      assertTrue(metrics.contains("m1"));
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test11() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);

      logger.logMetric(new Metric("e0", "m1", 11, 1021));
      logger.logMetric(new Metric("e0", "m1", 11, 1021));
      logger.logMetric(new Metric("e0", "m1", 11, 1021));
      logger.logMetric(new Metric("e0", "m1", 11, 1021));

      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      List<InTimeValue> results = fa.getValueChange("m1", Arrays.asList("e0"),
          0, -1, -1).getValues();

      assertTrue(results.size() == 4 && results.get(0).getValue() == 1021
          && results.get(0).getTime() == 11);

    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test12() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      logger.logMetric(new Metric("e1", "m1", 1, 1));
      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      ArrayList<String> elems = new ArrayList<String>();
      elems.add("e1");
      AnalyserResult res = fa.getAggregateValueChange("m1", elems, 0, -1, -1);
      List<InTimeValue> results = res.getValues();

      assertTrue(results.size() == 1 && results.get(0).getValue() == 1
          && results.get(0).getTime() == 1);

      assertTrue(res.getPeak().getValue() == 1);
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test14() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      logger.logMetric(new Metric("e1", "m1", 1, 1));
      logger.logMetric(new Metric("e1", "m1", 2, 2));
      logger.logMetric(new Metric("e3", "m1", 2, 2));
      logger.logMetric(new Metric("e1", "m1", 3, 3));
      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));

      ArrayList<String> elems = new ArrayList<String>();
      elems.add("e1");
      AnalyserResult res = fa.getAggregateValueChange("m1", elems, 0, -1, -1);
      List<InTimeValue> results = res.getValues();

      assertTrue(results.size() == 3 && results.get(0).getValue() == 1
          && results.get(0).getTime() == 1 && results.get(1).getValue() == 2
          && results.get(1).getTime() == 2 && results.get(2).getValue() == 3
          && results.get(2).getTime() == 3);

      assertTrue(res.getPeak().getValue() == 3);
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test15() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      logger.logMetric(new Metric("e1", "m1", 1, 1));
      logger.logMetric(new Metric("e1", "m1", 1, 2));
      logger.logMetric(new Metric("e1", "m1", 1, 3));
      logger.logMetric(new Metric("e1", "m1", 1, 4));
      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));

      ArrayList<String> elems = new ArrayList<String>();
      elems.add("e1");
      AnalyserResult res = fa.getAggregateValueChange("m1", elems, 1, -1, -1);
      List<InTimeValue> results = res.getValues();

      assertTrue(results.size() == 1 && results.get(0).getValue() == 2
          && results.get(0).getTime() == 1);

      assertTrue(res.getPeak().getValue() == 4);
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test19() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      logger.logMetric(new Metric("e1", "m1", 1, 1));
      logger.logMetric(new Metric("e2", "m1", 2, 10));
      logger.logMetric(new Metric("e1", "m1", 3, 4));
      logger.logMetric(new Metric("e2", "m1", 4, 40));
      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));

      ArrayList<String> elems = new ArrayList<String>();
      elems.add("e1");
      elems.add("e2");

      List<InTimeValue> result = fa.getValueChange("m1", elems, 0, 2, 3).getValues();

      assertTrue(result.size() == 2);
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test20() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      logger.logMetric(new Metric("e1", "m1", 1, 1));
      logger.logMetric(new Metric("e2", "m1", 2, 10));
      logger.setManifest(new LogManifest("manifest,\n\n xx"));
      logger.close();

      // Read metric
      FileBasedLogAnalyser fa = new FileBasedLogAnalyser(new File(TESTDB));
      assertTrue(fa.getManifest().getDescription().equals("manifest,\n\n xx"));
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }
}
