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

import java.util.Calendar;

import org.junit.Test;

import com.googlecode.gridme.simstate.ActiveContainer;
import com.googlecode.gridme.simstate.ModelErrorLogger;
import com.googlecode.gridme.simstate.RuntimeModel;
import com.googlecode.gridme.simstate.loggers.SimpleErrorLogger;

public class PerfTests extends SimTest
{
  @Test
  /**
   * Performance test
   */
  public void test17() throws Exception
  {
    int j = 8;
    //for(int j = 3; j < 10; j++)
    {
      int elems = j;
      long time = 1000;

      System.out.println("Test with " + elems + " elements and time " + time);
      System.out.println("Init");
      // Create runtime tree 
      ModelErrorLogger elog = new SimpleErrorLogger();
      ActiveContainer root = new ActiveContainer(ELEM1);
      RuntimeModel model = new RuntimeModel(0, time, Calendar.getInstance(), false, null, elog, null);

      for(int i = 0; i < elems; i++)
      {
        root.addChild(new Test16AE("AE" + i));
      }

      System.out.println("Start");
      long start = System.currentTimeMillis();

      model.setModel(root);
      model.runModel();
      model.waitForCompletion(3600000);

      System.out.println("Completed in " + (System.currentTimeMillis() - start)
          / 1000 + " Sec.");

      // Check that no errors occured
      assertTrue(elog.getLastError() == null);
    }
  }
}
