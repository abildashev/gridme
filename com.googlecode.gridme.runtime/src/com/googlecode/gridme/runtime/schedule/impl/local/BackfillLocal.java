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
package com.googlecode.gridme.runtime.schedule.impl.local;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.googlecode.gridme.runtime.ImplementationDescription;
import com.googlecode.gridme.runtime.elements.BaseCluster;
import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GNode;
import com.googlecode.gridme.runtime.schedule.GQueue;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.LocalScheduler;
import com.googlecode.gridme.runtime.schedule.LocalSchedulingDecision;
import com.googlecode.gridme.runtime.schedule.TaskComparatorPriority;
import com.googlecode.gridme.runtime.schedule.impl.FastQueue;
import com.googlecode.gridme.runtime.schedule.impl.ProcSlot;
import com.googlecode.gridme.runtime.schedule.impl.TaskReservation;

@ImplementationDescription(value = "Classic backfill implementation for a HPC cluster. "
    + "The task queue is scanned and for each task it "
    + "is checked whether or not it can be allocated to the "
    + "available cluster nodes. The queue is ordered by "
    + "priority in decreasing order. If the task can not be allocated a "
    + "reservation is created and less priority tasks can be allocated to "
    + "the available space.", category = ImplementationDescription.SCHED_LOCAL)
public class BackfillLocal implements LocalScheduler
{
  private static class ProcWindow
  {
    private long startTime;
    private long duration;
    private ArrayList<ProcSlot> slots;

    public ProcWindow(long duration)
    {
      slots = new ArrayList<ProcSlot>();
      this.duration = duration;
    }

    public long getStartTime()
    {
      return startTime;
    }

    public ArrayList<ProcSlot> getSlots()
    {
      return slots;
    }

    public void addSlot(ProcSlot slot)
    {
      startTime = slot.getStartTime();
      slots.add(slot);
      // remove bad slots
      for(Iterator<ProcSlot> it = slots.iterator(); it.hasNext();)
      {
        ProcSlot sl = it.next();
        if(sl.getStopTime() < startTime + duration)
        {
          it.remove();
        }
      }
    }

    public int size()
    {
      return slots.size();
    }
  }

  protected FastQueue queue;
  protected GCluster cluster;
  protected ArrayList<TaskReservation> reservations;
  protected HashSet<GTask> reservedTasks;
  protected ArrayList<ProcSlot> freeSlots;

  public BackfillLocal()
  {
    queue = new FastQueue(new TaskComparatorPriority());
    reservations = new ArrayList<TaskReservation>();
    reservedTasks = new HashSet<GTask>();
    freeSlots = new ArrayList<ProcSlot>();
  }

  @Override
  public void assignCluster(GCluster cluster)
  {
    this.cluster = cluster;
    createSlotsForFreeNodes(0);
  }

  @Override
  public GQueue getQueue()
  {
    return queue;
  }

  @Override
  public Collection<LocalSchedulingDecision> schedule(long ctime)
  {
    Collection<LocalSchedulingDecision> result = new ArrayList<LocalSchedulingDecision>();

    // Remove old slots and align existing
    cleanSlots(ctime);
    // Clean finished reservations
    removeFinishedReservations(ctime);

    if(queue.hasNewTasks() || cluster.isChanged())
    {
      refreshReservations(ctime);
    }

    // Allocate ready reservations
    for(TaskReservation res : reservations)
    {
      if(res.isReady(ctime))
      {
        if(queue.contains(res.getTask().getId()))
        {
          LocalSchedulingDecision dec = new LocalSchedulingDecision(res
              .getTask());
          for(GNode node : res.getNodes())
          {
            dec.addNode(node);
          }
          result.add(dec);
          res.setAllocated();
        }
        else
        {
          System.out.println("Ready reservation but no task in queue. Time="
              + ((BaseCluster) cluster).getModel().getModelTime() + " Task="
              + res.getTask().getId());
        }
      }
    }
    return result;
  }

  /**
   * Removes old slots and aligns existing slots
   * to the current model time. 
   */
  protected void cleanSlots(long ctime)
  {
    for(Iterator<ProcSlot> it = freeSlots.iterator(); it.hasNext();)
    {
      ProcSlot slot = it.next();
      if(slot.getStopTime() <= ctime)
      {
        // Remove old slot.
        it.remove();
      }
      else if(slot.getStartTime() < ctime)
      {
        // Set the start time equal to the model time.
        slot.setStartTime(ctime);
      }
    }
  }

  /**
   * Go through the queue and create reservations for the tasks
   * that cannot be found in the reserved tasks hash map. 
   */
  protected void createReservations(long ctime)
  {
    for(Iterator<GTask> it = queue.getIterator(); it.hasNext();)
    {
      GTask task = it.next();

      if(!reservedTasks.contains(task))
      {
        createReservationForTask(ctime, task);
      }
    }
  }

  /**
   * Creates a reservation for the task. Adds task to the 
   * reserved task hash map.
   */
  protected void createReservationForTask(long ctime, GTask task)
  {
    long duration = task.getRequestedExecutionTime() != 0 ? task
        .getRequestedExecutionTime() : task.getRealExecutionTime();

    // Find free slots for the task.
    ProcWindow window = findWindow(duration, task.getNodesMin());

    if(window != null)
    {
      long startTime = window.getStartTime();

      // Create reservation
      TaskReservation res = new TaskReservation(task, startTime, duration);
      for(ProcSlot slot : window.getSlots())
      {
        res.addNode(slot.getNode());
        useSlot(slot, startTime, duration);
      }
      reservations.add(res);
      reservedTasks.add(task);
    }
    else
    {
      // We can not find required number of free slots. That means the task
      // is too large for the cluster.
    }
  }

  /**
   * Splits the slot by allocating part of it. 
   */
  protected void useSlot(ProcSlot slot, long startTime, long duration)
  {
    freeSlots.remove(slot);

    if(slot.getStartTime() < startTime)
    {
      freeSlots.add(new ProcSlot(slot.getNode(), slot.getStartTime(), startTime
          - slot.getStartTime()));
    }

    if(slot.getStopTime() > startTime + duration)
    {
      freeSlots.add(new ProcSlot(slot.getNode(), startTime + duration, slot
          .getStopTime()
          - (startTime + duration)));
    }
  }

  /**
   * Finds free slots for the task.
   */
  protected ProcWindow findWindow(long duration, int count)
  {
    ArrayList<ProcSlot> goodSlots = new ArrayList<ProcSlot>();

    for(ProcSlot slot : freeSlots)
    {
      if(slot.getDuration() >= duration)
      {
        insertByStartTime(goodSlots, slot);
      }
    }

    ProcWindow result = new ProcWindow(duration);

    for(ProcSlot sl : goodSlots)
    {
      result.addSlot(sl);
      if(result.size() >= count)
      {
        return result;
      }
    }

    return null;
  }

  /**
   * Inserts a slot in the list of free slots.
   */
  protected void insertByStartTime(ArrayList<ProcSlot> goodSlots, ProcSlot slot)
  {
    int i = 0;

    for(; i < goodSlots.size(); i++)
    {
      if(slot.getStartTime() < goodSlots.get(i).getStartTime())
      {
        break;
      }
    }

    goodSlots.add(i, slot);
  }

  /**
   * Remove reservations that have finished - 
   * stop time is less then the model time.
   */
  protected void removeFinishedReservations(long ctime)
  {
    for(Iterator<TaskReservation> it = reservations.iterator(); it.hasNext();)
    {
      TaskReservation res = it.next();
      if(res.getStopTime() <= ctime)
      {
        // Remove reservation
        it.remove();
        // Remove corresponding task from the hash
        reservedTasks.remove(res.getTask());
      }
    }
  }

  protected void deleteReservation(TaskReservation res)
  {
    reservations.remove(res);
    reservedTasks.remove(res.getTask());
  }

  /**
   * Rebuilds the list of all reservations.
   */
  protected void refreshReservations(long ctime)
  {
    freeSlots.clear();

    // Now we iterate over the list of reservations, removing
    // those that have not yet started, keeping already started, 
    // and rebuilding the list of free slots.
    for(Iterator<TaskReservation> it = reservations.iterator(); it.hasNext();)
    {
      TaskReservation res = it.next();
      if(res.getStartTime() < ctime && res.getStopTime() > ctime)
      {
        // Add free slots for the active reservation - available cpus after reservation finish
        for(GNode node : res.getNodes())
        {
          freeSlots.add(new ProcSlot(node, res.getStopTime()));
        }
      }
      else
      {
        // Remove reservation
        it.remove();
        // Remove corresponding task from the hash
        reservedTasks.remove(res.getTask());
      }
    }

    // And create slots for the free nodes
    createSlotsForFreeNodes(ctime);

    // Now create reservations for all tasks
    createReservations(ctime);
  }

  private void createSlotsForFreeNodes(long ctime)
  {
    // Create free slots
    for(Iterator<? extends GNode> it = cluster.getFreeNodesIterator(); it
        .hasNext();)
    {
      GNode node = it.next();
      freeSlots.add(new ProcSlot(node, ctime));
    }
  }
}
