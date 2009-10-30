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

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.exceptions.InvalidConfigurationException;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.workload.WorkloadTaskFlow;
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.RuntimeModel;
import com.googlecode.gridme.simstate.loggers.SimpleErrorLogger;

/**
 * Tests workload flow
 */
public class TestWorkloadFlow
{
  class TRecord
  {
    private long period;
    private GTask task;
    private ActiveElement recipient;

    public long getPeriod()
    {
      return period;
    }

    public GTask getTask()
    {
      return task;
    }

    public ActiveElement getRecipient()
    {
      return recipient;
    }

    /**
     * @param period
     * @param task
     * @param recipient
     */
    public TRecord(long period, GTask task, ActiveElement recipient)
    {
      this.period = period;
      this.task = task;
      this.recipient = recipient;
    }

    @Override
    public String toString()
    {
      return "" + period + ":" + task.getId() + ":" + recipient;
    }
  }

  class TFlow extends WorkloadTaskFlow
  {
    public TFlow() throws GRuntimeException
    {
      super("TestFlow");
    }

    /**
     * Test topology
     */
    public void testInit()
    {
      init();
    }

    public ActiveElement getRecipient()
    {
      return super.getRecipient();
    }

    /**
     * Reads count records and sends a task.
     */
    public ArrayList<TRecord> testRun(int count) throws GRuntimeException
    {
      ArrayList<TRecord> result = new ArrayList<TRecord>();
      for(int i = 0; i < count; i++)
      {
        prepare();
        if(!isFinished())
        {
          result.add(new TRecord(getPeriod(), getTask(), getRecipient()));
        }
        else
        {
          break;
        }
      }
      return result;
    }

    public void testPrepare() throws GRuntimeException
    {
      prepare();
    }
  }

  @Test
  /**
   * Test cluster and broker discovery process
   */
  public void test2() throws GRuntimeException
  {
    TFlow flow = new TFlow();
    
    flow.setWorkload("data/testSWF1.zip");
    flow.setStartDelay("0");
    
    TEBroker broker = new TEBroker();

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(flow, broker);

    flow.testInit();
    assertTrue(flow.getRecipient() == broker);
  }

  @Test
  /**
   * Test cluster and broker discovery process
   */
  public void test3() throws GRuntimeException
  {
    TFlow flow = new TFlow();
    
    flow.setWorkload("data/testSWF2.zip");
    flow.setStartDelay("0");


    flow.setModel(new RuntimeModel(0, 100, Calendar.getInstance(), true, 
        null, new SimpleErrorLogger(), null));
    
    TConnection c1 = new TConnection();
    TECluster t1 = new TECluster(10);
    t1.setTag(WorkloadTaskFlow.T_PART, "9");
    TECluster t2 = new TECluster(11);
    c1.connectElementSendReceive(flow, t1, t2);

    flow.testInit();
    flow.testPrepare();

    assertTrue(flow.getRecipient() == t1);
  }

  @Test
  /**
   * Test read
   */
  public void test4() throws GRuntimeException
  {
    TFlow flow = new TFlow();
    
    flow.setWorkload("data/testSWF3.zip");
    flow.setStartDelay("0");
    

    TConnection c1 = new TConnection();
    TECluster t1 = new TECluster(10);
    t1.setTag(WorkloadTaskFlow.T_PART, "9");
    TECluster t2 = new TECluster(11);
    c1.connectElementSendReceive(flow, t1, t2);

    flow.setModel(new RuntimeModel(0, 100, Calendar.getInstance(), true, 
        null, new SimpleErrorLogger(), null));

    flow.testInit();

    ArrayList<TRecord> result = flow.testRun(100);
    assertTrue(result.size() == 5);
    // Check values
    checkTRecord(result.get(0), 0, t1, "1", 10, 0, 1, 1100, 1000);
    checkTRecord(result.get(1), 10, t1, "2", 11, 0, 1, 1200, 1001);
    checkTRecord(result.get(2), 0, t1, "3", 12, 0, 1, 1400, 1002);
    checkTRecord(result.get(3), 1, t1, "7", 16, 0, 1, 1700, 0);
  }

    
  @Test
  public void test7() throws GRuntimeException
  {
    TFlow flow = new TFlow();
    
    flow.setWorkload("data/testSWF4.zip");
    flow.setStartDelay("0");
    flow.setPartitions("1");
    

    flow.setModel(new RuntimeModel(0, 100, Calendar.getInstance(), true, 
        null, new SimpleErrorLogger(), null));
    
    TConnection c1 = new TConnection();
    TECluster t1 = new TECluster(10);
    t1.setTag(WorkloadTaskFlow.T_PART, "1");
    TECluster t2 = new TECluster(11);
    t2.setTag(WorkloadTaskFlow.T_PART, "9");
    c1.connectElementSendReceive(flow, t1, t2);

    flow.testInit();

    ArrayList<TRecord> result = flow.testRun(100);
    assertTrue(result.size() == 1);
    // Check values
    checkTRecord(result.get(0), 1, t1, "3", 12, 0, 1, 1300, 1002);
  }

  private void checkTRecord(TRecord rec,
                            long period,
                            Object cluster,
                            String id,
                            int minNodes,
                            int maxNodes,
                            int prio,
                            long realTime,
                            long reqTime)
  {
    assertTrue(rec.period == period);
    assertTrue(rec.recipient == cluster);
    assertTrue(rec.task.getId().endsWith(id));
    assertTrue(rec.task.getNodesMin() == minNodes);
    assertTrue(rec.task.getNodesMax() == maxNodes);
    assertTrue(rec.task.getPriority() == prio);
    assertTrue(rec.task.getRealExecutionTime() == realTime);
    assertTrue(rec.task.getRequestedExecutionTime() == reqTime);
  }
}
