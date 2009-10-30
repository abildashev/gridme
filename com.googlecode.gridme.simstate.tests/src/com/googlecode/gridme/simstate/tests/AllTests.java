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

import org.junit.Test;

import com.googlecode.gridme.simstate.ActiveContainer;
import com.googlecode.gridme.simstate.ModelErrorLogger;
import com.googlecode.gridme.simstate.RuntimeModel;
import com.googlecode.gridme.simstate.loggers.SimpleErrorLogger;
import com.googlecode.gridme.simstate.loggers.XMLProfileLogger;

public class AllTests extends SimTest
{
  @Test
  public void testActiveElement1()
  {
    TestElement1 elem = new TestElement1("elem1");
    TestSignal sig1 = new TestSignal(elem, elem);
    elem.sendSignal(sig1, elem);
    TestSignal sig2 = new TestSignal(elem, elem);
    elem.sendSignal(sig2, elem);
    elem.deliverSignals();

    assertTrue(elem.getSignal(TestSignal.class) == sig1);
    assertTrue(elem.getSignal(TestSignal.class) == sig2);
    assertTrue(elem.getSignal(TestSignal.class) == null);

    elem.sendSignal(sig1, elem);
    elem.sendSignal(sig1, elem);
    elem.sendSignal(sig2, elem);
    elem.sendSignal(sig1, elem);
    elem.deliverSignals();

    assertTrue(elem.getAllSignals(TestSignal.class).size() == 4);
    assertTrue(elem.getAllSignals(TestSignal.class).isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testActiveElement2()
  {
    TestElement2 elem = new TestElement2("elem2");
    elem.sendSignal(new TestSignal2(elem, elem), elem);
    elem.sendSignal(new TestSignal(elem, elem), elem);
  }

  @Test
  public void test1() throws Exception
  {
    runTest(0, 1, true, true, new Test1AE(ELEM1), new Test1AE(ELEM1));
    runTest(0, 1, true, false, new Test1AE(ELEM1), new Test1AE(ELEM1));
  }

  @Test
  public void test2() throws Exception
  {
    runTest(0, 1, false, true, new Test2AE(ELEM1), new Test2AE(ELEM2));
    runTest(0, 1, false, false, new Test2AE(ELEM1), new Test2AE(ELEM2));
  }

  @Test
  public void test3() throws Exception
  {
    runTest(0, 1, true, true, new Test3AE(ELEM1), new Test3AE(ELEM2));
    runTest(0, 1, true, false, new Test3AE(ELEM1), new Test3AE(ELEM2));
  }

  @Test
  public void test4() throws Exception
  {
    runTest(0, 1, true, true, new Test4AE(ELEM1), new Test4AE(ELEM2));
    runTest(0, 1, true, false, new Test4AE(ELEM1), new Test4AE(ELEM2));
  }

  @Test
  public void test5() throws Exception
  {
    runTest(0, 20, true, true, new Test5AE(ELEM1), new Test5AE(ELEM2));
    runTest(0, 20, true, false, new Test5AE(ELEM1), new Test5AE(ELEM2));
  }

  @Test
  public void test6() throws Exception
  {
    runTest(0, 20, true, true, new Test6AE(ELEM1), new Test6AE(ELEM2));
    runTest(0, 20, true, false, new Test6AE(ELEM1), new Test6AE(ELEM2));
  }

  @Test
  public void test7() throws Exception
  {
    runTest(0, 20, true, true, new Test7AE(ELEM1), new Test7AE(ELEM2));
    runTest(0, 20, true, false, new Test7AE(ELEM1), new Test7AE(ELEM2));
  }

  @Test
  public void test8() throws Exception
  {
    runTest(0, 1, true, true, new Test8AE(ELEM1), new Test8AE(ELEM2));
    runTest(0, 1, true, false, new Test8AE(ELEM1), new Test8AE(ELEM2));
  }

  @Test
  public void test9() throws Exception
  {
    runTest(0, 20, true, true, new Test9AE(ELEM1), new Test9AE(ELEM2));
    runTest(0, 20, true, false, new Test9AE(ELEM1), new Test9AE(ELEM2));
  }

  @Test
  public void test10() throws Exception
  {
    runTest(0, 20, true, true, new Test10AE(ELEM1), new Test10AE(ELEM2));
    runTest(0, 20, true, false, new Test10AE(ELEM1), new Test10AE(ELEM2));
  }

  @Test
  public void test11() throws Exception
  {
    runTest(0, 1, true, true, new Test11AE(ELEM1), new Test11AE(ELEM2));
    runTest(0, 1, true, false, new Test11AE(ELEM1), new Test11AE(ELEM2));
  }

  @Test
  public void test12() throws Exception
  {
    runTest(0, 1, true, true, new Test12AE(ELEM1), new Test12AE(ELEM2));
    runTest(0, 1, true, false, new Test12AE(ELEM1), new Test12AE(ELEM2));
  }

  @Test
  public void test13() throws Exception
  {
    runTest(0, 10, true, true, new Test13AE(ELEM1), new Test13AE(ELEM2));
    runTest(0, 10, true, false, new Test13AE(ELEM1), new Test13AE(ELEM2));
  }

  @Test
  public void test14() throws Exception
  {
    runTest(0, 100, true, true, new Test14AE(ELEM1), new Test14AE(ELEM2));
    runTest(0, 100, true, false, new Test14AE(ELEM1), new Test14AE(ELEM2));
  }

  @Test
  public void test15() throws Exception
  {
    Test15AE e1 = new Test15AE(ELEM1);
    Test15AE e11 = new Test15AE(ELEM1);
    runTest(0, 1000, true, true, e1, new Test15AAE(ELEM2, e1));
    runTest(0, 1000, true, false, e11, new Test15AAE(ELEM2, e11));
  }

  @Test
  /**
   * Ping-pong test
   */
  public void test16() throws Exception
  {
    // Create runtime tree 
    ActiveContainer root = new ActiveContainer(ELEM1);
    PingPong p1 = new PingPong("p1", 2);
    PingPong p2 = new PingPong("p2", 2);
    root.addChild(p1, p2);

    ModelErrorLogger elog = new SimpleErrorLogger();
    RuntimeModel model = new RuntimeModel(0, 100, Calendar.getInstance(), true, null,
        elog, new XMLProfileLogger(new File(RUNTIME_LOG)));
    model.setModel(root);

    p2.sendSignal(new PingPongSignal(p1), p1);

    model.runModel();
    model.waitForCompletion(MODEL_WAIT_MS);
    // Check that no errors occurred
    assertTrue(elog.getLastError() == null);
    // Check states
    assertTrue(p1.finished() && p2.finished());
  }

  @Test
  /**
   * Alarm test
   */
  public void test17() throws Exception
  {
    Test17AE e1 = new Test17AE(ELEM1);
    runTest(0, 100, true, false, e1);
  }

  @Test
  /**
   * Alarm test
   */
  public void test18() throws Exception
  {
    Test18AE e1 = new Test18AE(ELEM1);
    runTest(0, 1000, true, false, e1);
  }

  @Test
  /**
   * Alarm test
   */
  public void test19() throws Exception
  {
    Test19AE e1 = new Test19AE(ELEM1);
    runTest(0, 1000, true, false, e1);
  }
}
