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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.gridme.runtime.schedule.*;
import com.googlecode.gridme.runtime.schedule.impl.FastQueue;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.impl.local.BackfillLocal;
import com.googlecode.gridme.runtime.schedule.impl.local.FirstFitLocal;
import com.googlecode.gridme.runtime.schedule.impl.meta.NWMinGlobal;
import com.googlecode.gridme.runtime.schedule.impl.meta.RandomFitGlobal;
import com.googlecode.gridme.runtime.schedule.impl.meta.SqrWMinGlobal;
import com.googlecode.gridme.runtime.schedule.impl.meta.WwMinGlobal;

/**
 * Tests for the default scheduling implementation.
 */
public class TestSchedule
{
  private GTaskSignal[] tasks1;

  private GTaskSignal[] tasks2;

  private GTaskSignal[] tasks3;

  private static final int QSIZE = 20000;

  @Before
  public void initQueue()
  {
    tasks1 = new GTaskSignal[] {
        new GTaskSignal("t2", 1, 10, 100, 90, 10, null, null),
        new GTaskSignal("t1", 2, 10, 100, 90, 11, null, null),
        new GTaskSignal("t3", 3, 10, 100, 90, 10, null, null),
        new GTaskSignal("t0", 4, 10, 100, 90, 12, null, null) };

    tasks2 = new GTaskSignal[] {
        new GTaskSignal("t0", 2, 10, 100, 90, 10, null, null),
        new GTaskSignal("t1", 4, 10, 100, 90, 12, null, null),
        new GTaskSignal("t2", 3, 10, 100, 90, 20, null, null),
        new GTaskSignal("t3", 11, 11, 100, 90, 30, null, null) };

    tasks3 = new GTaskSignal[] {
        new GTaskSignal("t0", 10, 10, 100, 90, 10, null, null),
        new GTaskSignal("t1", 5, 10, 100, 90, 12, null, null),
        new GTaskSignal("t2", 1, 10, 100, 90, 20, null, null),
        new GTaskSignal("t3", 1, 11, 100, 90, 30, null, null) };
  }

  private void testGlobalEmpty(MetaScheduler sched)
  {
    TBroker broker = new TBroker();
    TCluster c1 = new TCluster(1);
    c1.setScheduler(new FirstFitLocal());
    broker.addCluster(c1);
    sched.assignBroker(broker);
    sched.getQueue().addTask(
        new GTaskSignal("t0", 2, 11, 100, 90, 30, null, null));
    assertTrue(sched.schedule(0).isEmpty());
  }

  private void testGlobalToFirst(MetaScheduler sched, GCluster c1, GCluster c2,
      GTask task)
  {
    TBroker broker = new TBroker();
    broker.addCluster(c1, c2);
    sched.assignBroker(broker);
    sched.getQueue().addTask(task);

    Collection<MetaSchedulingDecision> result = sched.schedule(0);
    Iterator<MetaSchedulingDecision> it = result.iterator();
    MetaSchedulingDecision dec = it.next();

    assertTrue(dec.getCluster() == c1);
  }

  private boolean checkSchedResult(Collection<LocalSchedulingDecision> dec,
      String... ids)
  {
    boolean result = true;
    Iterator<LocalSchedulingDecision> it = dec.iterator();
    for(int i = 0; i < ids.length && result; i++)
    {
      result = result && it.hasNext()
          && it.next().getTask().getId().equals(ids[i++]);
    }
    return result;
  }

  private void cleanQueue(GQueue queue,
      Collection<LocalSchedulingDecision> result)
  {
    for(LocalSchedulingDecision dec : result)
    {
      queue.removeTask(dec.getTask().getId());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGTaskSignal1()
  {
    new GTaskSignal(null, 1, 1, 1, 1, 1, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGTaskSignal2()
  {
    new GTaskSignal(null, 0, 1, 1, 1, 1, null, null);
  }

  @Test
  public void testFastQueue1()
  {
    FastQueue queue = new FastQueue(new TaskComparatorPriority());

    queue.addTask(tasks1[0]);
    queue.addTask(tasks1[1]);
    queue.addTask(tasks1[2]);
    queue.addTask(tasks1[3]);

    assertTrue(queue.getExpectedSquare() == 1000);
    assertTrue(queue.getRealSquare() == 900);

    int i = 0;
    for(Iterator<GTask> it = queue.getIterator(); it.hasNext(); i++)
    {
      GTask task = (GTask) it.next();
      assertTrue(task.getId().equals("t" + i));
    }

    tasks1[2].increasePriority(10);
    queue.taskChanged(tasks1[2], 0);

    i = 0;
    for(Iterator<GTask> it = queue.getIterator(); it.hasNext(); i++)
    {
      GTask task = (GTask) it.next();

      if(i == 0)
      {
        assertTrue(task.getId().equals("t3"));
      }
      else if(i == 1)
      {
        assertTrue(task.getId().equals("t0"));
      }
      else if(i == 2)
      {
        assertTrue(task.getId().equals("t1"));
      }
      else if(i == 3)
      {
        assertTrue(task.getId().equals("t2"));
      }
      else
      {
        assertTrue(false);
      }
    }

    queue.removeTask("t0");

    assertTrue(queue.getExpectedSquare() == 600);
    assertTrue(queue.getRealSquare() == 540);

    i = 0;
    for(Iterator<GTask> it = queue.getIterator(); it.hasNext(); i++)
    {
      GTask task = (GTask) it.next();

      if(i == 0)
      {
        assertTrue(task.getId().equals("t3"));
      }
      else if(i == 1)
      {
        assertTrue(task.getId().equals("t1"));
      }
      else if(i == 2)
      {
        assertTrue(task.getId().equals("t2"));
      }
      else
      {
        assertTrue(false);
      }
    }

    queue.removeTask("t2");
    assertTrue(queue.getExpectedSquare() == 500);
    assertTrue(queue.getRealSquare() == 450);
  }

  @Test
  public void testFastQueue2()
  {
    FastQueue queue = new FastQueue(new TaskComparatorPriority());

    assertTrue(queue.getExpectedSquare() == 0);
    assertTrue(queue.getRealSquare() == 0);
    queue.addTask(tasks1[0]);
    assertTrue(queue.getSize() == 1);
    queue.removeTask(tasks1[0].getId());
    assertTrue(queue.getSize() == 0);
    assertTrue(queue.getExpectedSquare() == 0);
    assertTrue(queue.getRealSquare() == 0);
  }

  @Test
  public void testFastQueue3()
  {
    FastQueue queue = new FastQueue(new TaskComparatorSize());

    queue.addTask(tasks1[0]);
    queue.addTask(tasks1[1]);
    queue.addTask(tasks1[2]);
    queue.addTask(tasks1[3]);

    Iterator<GTask> it = queue.getIterator();

    assertTrue(it.next() == tasks1[3]);
    assertTrue(it.next() == tasks1[2]);
    assertTrue(it.next() == tasks1[1]);
    assertTrue(it.next() == tasks1[0]);

    queue.removeTask(tasks1[2].getId());

    it = queue.getIterator();
    assertTrue(it.next() == tasks1[3]);
    assertTrue(it.next() == tasks1[1]);
    assertTrue(it.next() == tasks1[0]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFastQueue4()
  {
    FastQueue queue = new FastQueue(new TaskComparatorPriority());

    queue.addTask(tasks1[0]);
    queue.addTask(tasks1[0]);
  }

  @Test
  public void testFastQueue5()
  {
    FastQueue queue = new FastQueue(new TaskComparatorPriority());

    for(int i = 0; i < FastQueue.REMOVE_LIMIT * 2; i++)
    {
      queue.addTask(new GTaskSignal("t" + i, 1, 10, 100, 90, 10, null, null));
      queue.removeTask("t" + i);
    }

    assertTrue(queue.getSize() == 0);
    assertTrue(queue.getExpectedSquare() == 0);
    assertTrue(queue.getRealSquare() == 0);
    assertTrue(queue.getSizeSum() == 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFastQueue6()
  {
    FastQueue queue = new FastQueue(new TaskComparatorPriority());

    queue.addTask(tasks1[0]);
    queue.addTask(tasks1[1]);

    Iterator<GTask> it = queue.getIterator();

    assertTrue(it.next() == tasks1[1]);
    assertTrue(it.hasNext());
    assertTrue(it.hasNext());
    assertTrue(it.hasNext());
    assertTrue(it.next() == tasks1[0]);
    assertTrue(!it.hasNext());
    it.next();
  }

  @Test
  @Ignore
  /**
   * Test queue performance.
   */
  public void testFastQueue7()
  {
    FastQueue queue = new FastQueue(new TaskComparatorPriority());

    long startTime = System.currentTimeMillis();

    for(int i = 0; i < QSIZE; i++)
    {
      queue.addTask(new GTaskSignal("t" + i, 1, 10, 100, 90, Integer.MAX_VALUE
          - i, null, null));
    }

    System.out.println("Create " + QSIZE + " : "
        + (System.currentTimeMillis() - startTime) / 1000 + " Sec.");

    startTime = System.currentTimeMillis();

    for(Iterator<GTask> it = queue.getIterator(); it.hasNext();)
    {
      it.next();
    }

    System.out.println("Traverse " + QSIZE / 2 + " : "
        + (System.currentTimeMillis() - startTime) / 1000 + " Sec.");

    startTime = System.currentTimeMillis();

    for(int i = 0; i < QSIZE / 2; i++)
    {
      queue.removeTask("t" + i);
    }

    System.out.println("Delete " + QSIZE / 2 + " : "
        + (System.currentTimeMillis() - startTime) / 1000 + " Sec.");
  }

  @Test
  public void testFastQueue8()
  {
    // Sort by priority and then by size
    FastQueue queue = new FastQueue(new TaskComparatorPriority(),
        new TaskComparatorSize());

    queue.addTask(new GTaskSignal("t0", 4, 10, 100, 90, 2, null, null));
    queue.addTask(new GTaskSignal("t1", 4, 10, 100, 90, 1, null, null));
    queue.addTask(new GTaskSignal("t2", 5, 10, 100, 90, 1, null, null));
    queue.addTask(new GTaskSignal("t3", 10, 15, 100, 90, 1, null, null));

    GTask[] t = queue.toArray();

    assertTrue(t[0].getId().equals("t0"));
    assertTrue(t[1].getId().equals("t3"));
    assertTrue(t[2].getId().equals("t2"));
    assertTrue(t[3].getId().equals("t1"));

  }

  @Test
  public void testFastQueue9()
  {
    // Sort by priority and then by size
    FastQueue queue = new FastQueue(new TaskComparatorPriority(),
        new TaskComparatorSize());

    queue.addTask(new GTaskSignal("t0", 4, 10, 100, 90, 1, null, null));
    queue.addTask(new GTaskSignal("t1", 4, 10, 100, 90, 1, null, null));
    queue.hold("t0");
    Iterator<GTask> it = queue.getIterator();
    GTask t = it.next();
    assertFalse(it.hasNext());
    assertTrue("t1".equals(t.getId()));
    
    List<GTask> hl = queue.getHoldTasks();
    assertTrue(hl.size() == 1);
    assertTrue("t0".equals(hl.get(0).getId()));
    
    queue.releaseHold("t0", 1000);
    it = queue.getIterator();
    GTask t0 = it.next();
    GTask t1 = it.next();
    assertFalse(it.hasNext());
    assertTrue("t0".equals(t0.getId()));
    assertTrue("t1".equals(t1.getId()));
    
    assertTrue(t0.getReleaseTime() == 1000);
    assertTrue(t0.getSubmitTime() == 0);
    assertTrue(t1.getReleaseTime() == 0);
    assertTrue(t1.getSubmitTime() == 0);
  }
  
  @Test
  public void testFastQueue10()
  {
    FastQueue queue = new FastQueue(new TaskComparatorSize());

    queue.addTask(new GTaskSignal("t0", 1, 10, 100, 90, 10, null, null));
    queue.addTask(new GTaskSignal("t1", 1, 10, 100, 90, 10, null, null));
    queue.removeTask("t1");
    Iterator<GTask> it = queue.getIterator();
    assertTrue(it.hasNext());
    assertTrue("t0".equals(it.next().getId()));
    assertFalse(it.hasNext());
    assertFalse(it.hasNext());
    assertFalse(it.hasNext());
  }
  
  
  @Test
  public void testMauiQueue1()
  {
    // Sort by priority and then by size
    FastQueue queue = new FastQueue(new TaskComparatorPriority());

    queue.addTask(new GTaskSignal("t0", 1, 10, 100, 90, 2, null, null));
    queue.addTask(new GTaskSignal("t1", 4, 10, 100, 90, 1, null, null));
    queue.addTask(new GTaskSignal("t2", 5, 10, 100, 90, 1, null, null));
    queue.addTask(new GTaskSignal("t3", 5, 10, 100, 90, 1, null, null));
    queue.addTask(new GTaskSignal("t4", 10, 15, 100, 90, 1, null, null));

    GTask[] t = queue.toArray();

    assertTrue(t[0].getId().equals("t0"));
    assertTrue(t[1].getId().equals("t1"));
    assertTrue(t[2].getId().equals("t2"));
    assertTrue(t[3].getId().equals("t3"));
    assertTrue(t[4].getId().equals("t4"));

  }
  
  @Test
  public void testFirstFitLocal1()
  {
    FirstFitLocal sched = new FirstFitLocal();
    sched.assignCluster(new TCluster(10));

    // prepare queue
    sched.getQueue().addTask(tasks2[0]);
    sched.getQueue().addTask(tasks2[1]);
    sched.getQueue().addTask(tasks2[2]);
//    sched.getQueue().addTask(tasks2[3]);

    // schedule
    Collection<LocalSchedulingDecision> result = sched.schedule(0);
    Iterator<LocalSchedulingDecision> it = result.iterator();

    // We expect {t2, 3} {t1, 4} {t0, 2}
    assertTrue(result.size() == 3);

    // Get results
    LocalSchedulingDecision de1 = it.next();
    LocalSchedulingDecision de2 = it.next();
    LocalSchedulingDecision de3 = it.next();

    // Check nodes count and task order
    assertTrue(de1.getTask().getId().equals("t2") && de1.getNodes().size() == 3);
    assertTrue(de2.getTask().getId().equals("t1") && de2.getNodes().size() == 4);
    assertTrue(de3.getTask().getId().equals("t0") && de3.getNodes().size() == 2);

    // Check overlaping nodes
    HashSet<GNode> buf = new HashSet<GNode>();
    for(GNode node : de1.getNodes())
    {
      assertTrue(buf.add(node));
    }
    for(GNode node : de2.getNodes())
    {
      assertTrue(buf.add(node));
    }
    for(GNode node : de3.getNodes())
    {
      assertTrue(buf.add(node));
    }
  }

  @Test
  public void testFirstFitLocal2()
  {
    FirstFitLocal sched = new FirstFitLocal();
    sched.assignCluster(new TCluster(0));

    assertTrue(sched.schedule(0).isEmpty());
  }

  @Test
  public void testFirstFitLocal3()
  {
    FirstFitLocal sched = new FirstFitLocal();
    sched.assignCluster(new TCluster(10));

    sched.getQueue().addTask(tasks2[0]);
    assertTrue(sched.schedule(0).size() == 1);
    sched.getQueue().removeTask(tasks2[0].getId());
    sched.getQueue().addTask(tasks2[2]);
    assertTrue(sched.schedule(0).size() == 1);
    sched.getQueue().removeTask(tasks2[2].getId());
    assertTrue(sched.schedule(0).isEmpty());
  }

  @Test
  @Ignore
  /**
   * Test scheduler performance in create / schedule / modify cycle
   */
  public void testFirstFitLocal4()
  {
    long startTime = System.currentTimeMillis();

    FirstFitLocal sched = new FirstFitLocal();
    sched.assignCluster(new TCluster(1000));

    Random rand = new Random();
    // Fill the queue with many tasks
    for(int i = 0; i < QSIZE; i++)
    {
      sched.getQueue().addTask(
          new GTaskSignal("t" + i, rand.nextInt(10) + 1, 1, 100, 90, i, null,
              null));
    }

    System.out.println("Create " + QSIZE + ": "
        + (System.currentTimeMillis() - startTime) / 1000 + " Sec.");

    startTime = System.currentTimeMillis();

    // Now schedule on 1000 nodes
    Collection<LocalSchedulingDecision> result = sched.schedule(0);

    System.out.println("Schedule : " + result.size() + " decisions in "
        + (System.currentTimeMillis() - startTime) + " Msec.");

    // Remove scheduled nodes
    startTime = System.currentTimeMillis();

    for(LocalSchedulingDecision de : result)
    {
      sched.getQueue().removeTask(de.getTask().getId());
    }

    System.out.println("Remove tasks from queue: "
        + (System.currentTimeMillis() - startTime) + " Msec.");
  }

  @Test
  public void testFirstFitGlobal1()
  {
    FirstFitGlobalTestImpl sched = new FirstFitGlobalTestImpl();
    TBroker broker = new TBroker();
    broker.addCluster(new TCluster(1), new TCluster(2), new TCluster(3));
    sched.assignBroker(broker);
    sched.init();

    assertArrayEquals(sched.getSortedListSizes(), new int[] { 1, 2, 3 });
  }

  @Test
  public void testFirstFitGlobal2()
  {
    FirstFitGlobalTestImpl sched = new FirstFitGlobalTestImpl();
    TBroker broker = new TBroker();
    broker.addCluster(new TCluster(1));
    sched.assignBroker(broker);
    sched.init();

    assertArrayEquals(sched.getSortedListSizes(), new int[] { 1 });
  }

  @Test
  public void testFirstFitGlobal3()
  {
    FirstFitGlobalTestImpl sched = new FirstFitGlobalTestImpl();
    TBroker broker = new TBroker();
    TCluster c1 = new TCluster(1);
    TCluster c2 = new TCluster(5);
    TCluster c3 = new TCluster(10);

    broker.addCluster(c2, c1, c3);
    sched.assignBroker(broker);
    sched.init();

    sched.getQueue().addTask(tasks3[0]);
    sched.getQueue().addTask(tasks3[1]);
    sched.getQueue().addTask(tasks3[2]);
    sched.getQueue().addTask(tasks3[3]);

    Collection<MetaSchedulingDecision> result = sched.schedule(0);
    Iterator<MetaSchedulingDecision> it = result.iterator();
    MetaSchedulingDecision item = null;

    assertTrue(result.size() == 4);

    // 1 1 5 10

    item = it.next();
    assertTrue(item.getTask() == tasks3[3]);
    assertTrue(item.getCluster() == c1);

    item = it.next();
    assertTrue(item.getTask() == tasks3[2]);
    assertTrue(item.getCluster() == c1);

    item = it.next();
    assertTrue(item.getTask() == tasks3[1]);
    assertTrue(item.getCluster() == c2);

    item = it.next();
    assertTrue(item.getTask() == tasks3[0]);
    assertTrue(item.getCluster() == c3);
  }

  @Test
  public void testFirstFitGlobal4()
  {
    FirstFitGlobalTestImpl sched = new FirstFitGlobalTestImpl();
    TBroker broker = new TBroker();
    TCluster c1 = new TCluster(1);
    TCluster c2 = new TCluster(5);

    broker.addCluster(c1, c2);
    sched.assignBroker(broker);
    sched.init();

    sched.getQueue().addTask(
        new GTaskSignal("t0", 10, 11, 100, 90, 30, null, null));
    assertTrue(sched.schedule(0).isEmpty());
  }

  @Test
  public void testFirstFitGlobal5()
  {
    FirstFitGlobalTestImpl sched = new FirstFitGlobalTestImpl();
    TBroker broker = new TBroker();
    TCluster c1 = new TCluster(1);
    TCluster c2 = new TCluster(5);

    broker.addCluster(c1, c2);
    sched.assignBroker(broker);
    sched.init();

    sched.getQueue().addTask(
        new GTaskSignal("t0", 1, 11, 100, 90, 30, null, null));
    assertTrue(sched.schedule(0).size() == 1);
    sched.getQueue().removeTask("t0");
    sched.getQueue().addTask(
        new GTaskSignal("t1", 2, 11, 100, 90, 30, null, null));
    assertTrue(sched.schedule(0).size() == 1);
    sched.getQueue().removeTask("t1");
    sched.getQueue().addTask(
        new GTaskSignal("t2", 4, 11, 100, 90, 30, null, null));
    assertTrue(sched.schedule(0).size() == 1);
    sched.getQueue().removeTask("t2");
    assertTrue(sched.schedule(0).isEmpty());
  }

  @Test
  public void testRandomFitGlobal1()
  {
    RandomFitGlobal sched = new RandomFitGlobal();
    TBroker broker = new TBroker();
    TCluster c1 = new TCluster(1);

    broker.addCluster(c1);
    sched.assignBroker(broker);

    sched.getQueue().addTask(
        new GTaskSignal("t0", 1, 11, 100, 90, 30, null, null));

    Collection<MetaSchedulingDecision> result = sched.schedule(0);
    assertTrue(result.size() == 1);
  }

  @Test
  public void testRandomFitGlobal2()
  {
    testGlobalEmpty(new RandomFitGlobal());
  }

  @Test
  public void testRandomFitGlobal3()
  {
    RandomFitGlobal sched = new RandomFitGlobal();
    TBroker broker = new TBroker();
    TCluster c1 = new TCluster(1);
    TCluster c2 = new TCluster(2);
    TCluster c3 = new TCluster(3);

    broker.addCluster(c1, c2, c3);
    sched.assignBroker(broker);

    sched.getQueue().addTask(
        new GTaskSignal("t0", 2, 11, 100, 90, 30, null, null));

    int cn1 = 0, cn2 = 0;

    for(int i = 0; i < 100; i++)
    {
      Collection<MetaSchedulingDecision> result = sched.schedule(0);
      Iterator<MetaSchedulingDecision> it = result.iterator();
      MetaSchedulingDecision dec = it.next();
      System.out.print(" " + dec.getCluster().getSize());

      assertTrue(dec.getCluster().getSize() != 1);

      if(dec.getCluster().getSize() == 2)
      {
        cn1++;
      }

      if(dec.getCluster().getSize() == 3)
      {
        cn2++;
      }
    }
    System.out.println();
    System.out.println("To cluster 2: " + cn1 + " To cluster 3: " + cn2);
  }

  @Test
  public void testNWMinGlobal1()
  {
    testGlobalEmpty(new NWMinGlobal());
  }

  @Test
  public void testNWMinGlobal2()
  {
    GCluster c1 = new TCluster(2, new FirstFitLocal());
    GCluster c2 = new TCluster(3, new FirstFitLocal());
    GTask task = null;
    
    task = new GTaskSignal("t1", 1, 11,
        10, 90, 30, null, null);
    testGlobalToFirst(new NWMinGlobal(), c2, c1, task);
    c2.getScheduler().getQueue().addTask(task);

    task = new GTaskSignal("t2", 1, 11,
        10, 90, 30, null, null);
    testGlobalToFirst(new NWMinGlobal(), c1, c2, task);
    c1.getScheduler().getQueue().addTask(task);
    
    task = new GTaskSignal("t3", 1, 11,
        10, 90, 30, null, null);
    testGlobalToFirst(new NWMinGlobal(), c2, c1, task);
    c2.getScheduler().getQueue().addTask(task);

    task = new GTaskSignal("t4", 1, 11,
        10, 90, 30, null, null);
    testGlobalToFirst(new NWMinGlobal(), c1, c2, task);
    c1.getScheduler().getQueue().addTask(task);
    
    testGlobalToFirst(new NWMinGlobal(), c2, c1, new GTaskSignal("t5", 1, 11,
        10, 90, 30, null, null));
  }

  @Test
  public void testwWMinGlobal1()
  {
    testGlobalEmpty(new WwMinGlobal());
  }

  @Test
  public void testwWMinGlobal2()
  {
    GCluster c1 = new TCluster(2, new FirstFitLocal());
    GCluster c2 = new TCluster(3, new FirstFitLocal());
    GTask task = null;

    task = new GTaskSignal("t1", 1, 11,
        10, 90, 30, null, null);
    testGlobalToFirst(new WwMinGlobal(), c2, c1, task);
    c2.getScheduler().getQueue().addTask(task);
    
    task = new GTaskSignal("t2", 2, 11,
        10, 90, 30, null, null);
    testGlobalToFirst(new WwMinGlobal(), c1, c2, task);
    c1.getScheduler().getQueue().addTask(task);
    
    task = new GTaskSignal("t3", 3, 11,
        10, 90, 30, null, null);
    testGlobalToFirst(new WwMinGlobal(), c2, c1, task);
    c2.getScheduler().getQueue().addTask(task);
    
    task = new GTaskSignal("t4", 3, 11,
        10, 90, 30, null, null);
    testGlobalToFirst(new WwMinGlobal(), c2, c1, task);
    c2.getScheduler().getQueue().addTask(task);
    
    task = new GTaskSignal("t5", 2, 11,
        10, 90, 30, null, null);
    testGlobalToFirst(new WwMinGlobal(), c1, c2, task);
    c1.getScheduler().getQueue().addTask(task);
    
    testGlobalToFirst(new WwMinGlobal(), c1, c2, new GTaskSignal("t6", 1, 11,
        10, 90, 30, null, null));
  }

  @Test
  public void testwWMinGlobal3()
  {
    TCluster c1 = new TCluster(2, new FirstFitLocal());

    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t1", 1, 11, 100, 90, 30, null, null));
    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t2", 2, 11, 100, 90, 30, null, null));

    TCluster c2 = new TCluster(3, new FirstFitLocal());

    c2.getScheduler().getQueue().addTask(
        new GTaskSignal("t3", 1, 11, 100, 90, 30, null, null));
    c2.getScheduler().getQueue().addTask(
        new GTaskSignal("t4", 4, 11, 100, 90, 30, null, null));

    testGlobalToFirst(new WwMinGlobal(), c1, c2, new GTaskSignal("t0", 1, 11,
        100, 90, 30, null, null));

    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t5", 4, 11, 100, 90, 30, null, null));

    testGlobalToFirst(new WwMinGlobal(), c2, c1, new GTaskSignal("t0", 1, 11,
        100, 90, 30, null, null));
  }

  @Test
  public void testsqrMinGlobal1()
  {
    testGlobalEmpty(new SqrWMinGlobal());
  }

  @Test
  public void testsqrMinGlobal2()
  {
    testGlobalToFirst(new SqrWMinGlobal(),
        new TCluster(3, new FirstFitLocal()), new TCluster(2,
            new FirstFitLocal()), new GTaskSignal("t0", 1, 11, 100, 90, 30,
            null, null));
  }

  @Test
  public void testsqrMinGlobal3()
  {
    TCluster c1 = new TCluster(2, new FirstFitLocal());

    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t1", 1, 11, 100, 2, 30, null, null));
    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t2", 2, 11, 100, 3, 30, null, null));

    TCluster c2 = new TCluster(3, new FirstFitLocal());

    c2.getScheduler().getQueue().addTask(
        new GTaskSignal("t3", 1, 11, 100, 2, 30, null, null));
    c2.getScheduler().getQueue().addTask(
        new GTaskSignal("t4", 4, 11, 100, 4, 30, null, null));

    testGlobalToFirst(new SqrWMinGlobal(), c2, c1, new GTaskSignal("t0", 1, 11,
        100, 90, 30, null, null));

    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t5", 4, 11, 100, 2, 30, null, null));

    testGlobalToFirst(new SqrWMinGlobal(), c2, c1, new GTaskSignal("t0", 1, 11,
        100, 90, 30, null, null));
  }

  @Test
  public void testBackfill1()
  {
    LocalScheduler sched = new BackfillLocal();
    TCluster c1 = new TCluster(4, sched);
    sched.assignCluster(c1);

    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t1", 3, 3, 10, 10, 30, null, null));
    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t2", 2, 2, 10, 10, 30, null, null));
    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t3", 4, 4, 10, 10, 30, null, null));
    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t4", 1, 1, 10, 10, 30, null, null));

    Collection<LocalSchedulingDecision> result = sched.schedule(10);
    assertTrue(checkSchedResult(result, "t1", "t4"));

    cleanQueue(sched.getQueue(), result);

    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t5", 2, 2, 10, 10, 30, null, null));
    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t6", 2, 2, 10, 10, 30, null, null));

    result = sched.schedule(20);
    assertTrue(checkSchedResult(result, "t2", "t5"));
    cleanQueue(sched.getQueue(), result);

    result = sched.schedule(30);
    assertTrue(checkSchedResult(result, "t3"));
    cleanQueue(sched.getQueue(), result);

    result = sched.schedule(40);
    assertTrue(checkSchedResult(result, "t6"));
    cleanQueue(sched.getQueue(), result);
    
    result = sched.schedule(50);
    assertTrue(result.size() == 0);
  }

  @Test
  public void testBackfill2()
  {
    LocalScheduler sched = new BackfillLocal();
    TCluster c1 = new TCluster(4, sched);
    sched.assignCluster(c1);

    c1.getScheduler().getQueue().addTask(
        new GTaskSignal("t1", 5, 5, 10, 10, 30, null, null));

    Collection<LocalSchedulingDecision> result = sched.schedule(0);
    assertTrue(result.isEmpty());
  }
}
