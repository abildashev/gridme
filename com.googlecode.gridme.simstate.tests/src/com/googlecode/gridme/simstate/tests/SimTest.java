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
package com.googlecode.gridme.simstate.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Calendar;

import com.googlecode.gridme.simstate.ActiveContainer;
import com.googlecode.gridme.simstate.ModelErrorLogger;
import com.googlecode.gridme.simstate.RuntimeModel;
import com.googlecode.gridme.simstate.loggers.SimpleErrorLogger;
import com.googlecode.gridme.simstate.loggers.XMLProfileLogger;

public abstract class SimTest
{
  protected static final String RUNTIME_LOG = "rtlog.xml";
  protected static final String ELEM1 = "simroot1";
  protected static final String ELEM2 = "simroot2";
  protected static final long MODEL_WAIT_MS = 10000;

  /**
   * Executes test.
   * @param checkException TODO
   * @param useThreads TODO
   * @throws Exception
   */
  public void runTest(long start,
                      long stop,
                      boolean checkException,
                      boolean useThreads,
                      SimAE... tests) throws Exception
  {
    ActiveContainer root = new ActiveContainer("root");
    ModelErrorLogger elog = new SimpleErrorLogger();

    for(SimAE t : tests)
    {
      root.addChild(t);
    }

    RuntimeModel model = new RuntimeModel(start, stop, Calendar.getInstance(), useThreads, null, elog,
        new XMLProfileLogger(new File(RUNTIME_LOG)));
    model.setModel(root);

    model.runModel();

    assertTrue(model.waitForCompletion(MODEL_WAIT_MS));

    // Check that no errors occurred
    if(checkException)
    {
      assertTrue(elog.getLastError() == null);
    }
    // Check in what state we are
    for(SimAE t : tests)
    {
      assertTrue(t.checkState());
    }
  }
}
