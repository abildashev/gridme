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

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import com.googlecode.gridme.runtime.elements.GTaskFinishedSignal;
import com.googlecode.gridme.runtime.elements.SimpleCluster;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.schedule.GNode;
import com.googlecode.gridme.runtime.schedule.GNodeGroup;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.impl.local.FirstFitLocal;
import com.googlecode.gridme.simstate.ActiveContainer;
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.RuntimeModel;
import com.googlecode.gridme.simstate.loggers.SimpleErrorLogger;
import com.googlecode.gridme.simstate.loggers.XMLProfileLogger;

/**
 * Tests for simple cluster
 */
public class TestSimpleCluster
{
  protected static final String RUNTIME_LOG = "rtlog.xml";
  protected static final long MODEL_WAIT_MS = 1000;

  class TCluster extends SimpleCluster
  {
    public TCluster() throws GRuntimeException
    {
      super("Test cluster");
    }

    public void makeSched() throws GRuntimeException
    {
      // Task requires 5 nodes
      sendSignal(new GTaskSignal("t1", 5, 10, 100, 90, 10, null, null), this);
      deliverSignals();
      schedule(); // this will send signals to nodes
      deliverSignals();
      // check that signals had been delivered to nodes only once, 
      // and there are no signals left in queue and the scheduler has no
      // tasks left in queue.
      assertTrue(scheduler.getQueue().getSize() == 0);
      assertTrue(getAllSignals().size() == 0);
      
      int i = 0;
      for(ActiveElement child : childs)
      {
        int size = child.getAllSignals().size();
        if(size > 0)
        {
          assertTrue(size == 1);
          i++;
        }
      }
      assertTrue(i == 5);
      
      // Check free nodes
      //assertTrue(getFreeNodes() == 5);
    }
  }

  class XCluster extends SimpleCluster
  {
    long time;
    int callNum;

    public XCluster(long finishTime)
      throws GRuntimeException
    {
      super("XTest");
      time = finishTime;
    }

    @Override
    protected void schedule() throws GRuntimeException
    {
      // Check that task finishes at the correct time
      if(callNum == 1)
      {
        assertTrue(checkSignal(GTaskFinishedSignal.class) != null
            && getModel().getModelTime() == time);
      }
      
      callNum++;

      super.schedule();
      
      if(callNum == 0)
      {
        assertTrue(getFreeNodes() == 1);
      }
    }

    public int getCallNum()
    {
      return callNum;
    }
  }

  /**
   * Checks if the iterator provides a correct list of free nodes.
   * @param cluster cluster
   * @param expected expected list length
   * @return true if ok
   */
  private boolean checkIterator(TCluster cluster, int expected)
  {
    HashSet<GNode> buf = new HashSet<GNode>();
    boolean result = true;

    int i = 0;
    for(Iterator<? extends GNode> nit = cluster.getFreeNodesIterator(); nit
        .hasNext(); i++)
    {
      GNode node = nit.next();
      if(buf.contains(node))
      {
        result = false;
      }
      buf.add(node);
    }

    return result && i == expected;
  }

  @Test
  public void test1() throws GRuntimeException, IOException
  {
    ActiveContainer root = new ActiveContainer("root", new DummyModel());
    
    TCluster cluster = new TCluster();

    cluster.setNodes("10");
    cluster.setSpeedup("1.5");
    cluster.setScheduler(new FirstFitLocal());
    
    root.addChild(cluster);
    
    assertTrue(cluster.getSize() == 10);
    assertTrue(cluster.getSpeedup() == 1.5);
    assertTrue(cluster.getFreeNodes() == 10);
    assertTrue(checkIterator(cluster, 10));

    cluster.makeSched();
  }

  @Test
  public void test2() throws GRuntimeException,
                     IOException,
                     InterruptedException
  {
    XCluster cluster = new XCluster(100);
    
    cluster.setNodes("2");
    cluster.setSpeedup("1");
    cluster.setScheduler(new FirstFitLocal());
    
    // Create model
    RuntimeModel model = new RuntimeModel(0, 1000, Calendar.getInstance(), true,
        null, new SimpleErrorLogger(), new XMLProfileLogger(new File(RUNTIME_LOG)));
    model.setModel(cluster);
    
    // Put one task into cluster signal queue
    cluster.sendSignal(new GTaskSignal("t1", 1, 10, 11, 100, 10, null, null),
        cluster);
    
    model.runModel();
    
    model.waitForCompletion(MODEL_WAIT_MS);
    // Check errors
    assertTrue(cluster.getCallNum() == 2);
    assertTrue(cluster.getFreeNodes() == 2);
    assertTrue(model.getErrorLogger().getLastError() == null);
  }

  @Test
  /**
   * The same but with different speedup value.
   */
  public void test3() throws GRuntimeException,
                     IOException,
                     InterruptedException
  {
    XCluster cluster = new XCluster(40);
    
    cluster.setNodes("2");
    cluster.setSpeedup("2.5");
    cluster.setScheduler(new FirstFitLocal());
    
    // Create model
    RuntimeModel model = new RuntimeModel(0, 1000, Calendar.getInstance(), true,
        null, new SimpleErrorLogger(), new XMLProfileLogger(new File(RUNTIME_LOG)));
    model.setModel(cluster);
    
    // Put one task into cluster signal queue
    cluster.sendSignal(new GTaskSignal("t1", 1, 10, 11, 100, 10, null, null),
        cluster);
    
    model.runModel();
    
    model.waitForCompletion(MODEL_WAIT_MS);
    // Check errors
    assertTrue(cluster.getCallNum() == 2);
    assertTrue(cluster.getFreeNodes() == 2);
    assertTrue(model.getErrorLogger().getLastError() == null);
  }
  
  @Test
  /**
   * Test tags
   */
  public void test4() throws GRuntimeException,
                     IOException,
                     InterruptedException
  {
    XCluster cluster = new XCluster(40);
    
    cluster.setNodes("2");
    cluster.setSpeedup("2.5");
    cluster.setScheduler(new FirstFitLocal());
    cluster.setTags("t1=1,t2=3,t3=1");

    assertTrue(cluster.tags().size() == 3);
    assertTrue(cluster.getTag("t1").equals("1"));
    assertTrue(cluster.getTag("t2").equals("3"));
    assertTrue(cluster.getTag("t3").equals("1"));
    
    cluster = new XCluster(40);
    
    cluster.setNodes("2");
    cluster.setSpeedup("2.5");
    cluster.setScheduler(new FirstFitLocal());
    cluster.setTags("t1=1");
    
    assertTrue(cluster.tags().size() == 1);
    assertTrue(cluster.getTag("t1").equals("1"));
  }
  
  
  @Test
  /**
   * Test tags
   */
  public void test5() throws GRuntimeException
  {
    SimpleCluster c = new SimpleCluster("cluster");
    
    c.setNodes("10");
    c.setSMPCores(5);
    
    Iterator<? extends GNode> it = c.getNodesIterator();
    Iterator<GNodeGroup> itg = c.getSMPIterator();
    
    assertTrue(itg.hasNext());
    GNodeGroup g1 = itg.next();
    it.next();
    assertTrue(itg.hasNext());
    GNodeGroup g2 = itg.next();
    assertFalse(itg.hasNext());
    it.next();
    assertTrue(g1.getNodes().size() == 5);
    assertTrue(g2.getNodes().size() == 5);
  }  
}
