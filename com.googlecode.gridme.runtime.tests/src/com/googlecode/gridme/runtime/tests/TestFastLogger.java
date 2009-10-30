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

import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.exceptions.LoggerException;
import com.googlecode.gridme.runtime.log.Metric;
import com.googlecode.gridme.runtime.log.impl.FastLogger;

/**
 * Tests for file based logger 
 */
public class TestFastLogger
{
  private static final String TESTDB = "testdb";

  @BeforeClass
  public static void prepare() throws LoggerException
  {
    FastLogger.deleteDatabase(new File(TESTDB));
  }
  
  @Test
  public void test1() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      logger.logMetric(new Metric("e1", "m1", 0, 1024, "aaa"));
      logger.close();
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test(expected = LoggerException.class)
  public void test2() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      logger.logMetric(new Metric("e1", "m1", 0, 1024, "bbb"));
      logger.close();
      // Reopen
      logger = new FastLogger(new File(TESTDB), false);
      logger.logMetric(new Metric("e1", "m1", 0, 1024, "ccc"));
      logger.close();
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }

  @Test
  public void test3() throws GRuntimeException
  {
    try
    {
      FastLogger logger = new FastLogger(new File(TESTDB), false);
      logger.logMetric(new Metric("e1", "m1", 0, 1024, "ggg"));
      logger.close();
      // Reopen
      logger = new FastLogger(new File(TESTDB), true);
      logger.logMetric(new Metric("e1", "m1", 0, 1024, "fffff"));
      logger.close();
    }
    finally
    {
      FastLogger.deleteDatabase(new File(TESTDB));
    }
  }
}
