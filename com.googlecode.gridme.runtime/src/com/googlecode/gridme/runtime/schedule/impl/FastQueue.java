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
package com.googlecode.gridme.runtime.schedule.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.googlecode.gridme.runtime.schedule.GQueue;
import com.googlecode.gridme.runtime.schedule.GTask;

/**
 * Fast queue implementation. Tasks are put into queue and 
 * sorted in decreasing order by the specified comparator.
 * Sorting is stable.
 * 
 * Tasks with the same id are not allowed. 
 */
public class FastQueue implements GQueue
{
  /**
   * Iterator that traverses all tasks in queue. Tasks with hold
   * set are skipped. 
   */
  private class TaskIterator implements Iterator<GTask>
  {
    private Iterator<? extends GTask> it;
    private GTask nextTask;

    /**
     * @param queue
     */
    public TaskIterator(FastQueue queue)
    {
      it = queue.allTasks.iterator();
    }

    @Override
    public boolean hasNext()
    {
      if(nextTask == null)
      {
        while(it.hasNext())
        {
          nextTask = it.next();
          if(!toRemove.contains(nextTask.getId())
              && !holds.contains(nextTask.getId()))
          {
            return true;
          }
        }
        nextTask = null;
        return false;
      }
      else
      {
        return true;
      }
    }

    @Override
    public GTask next()
    {
      if(!hasNext())
      {
        throw new IndexOutOfBoundsException("No more items left");
      }
      GTask result = nextTask;
      nextTask = null;
      return result;
    }

    @Override
    public void remove()
    {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Number of dirty items in the queue.
   */
  public final static int REMOVE_LIMIT = 1000;

  /**
   * Initial size of tables
   */
  private final static int TAB_SIZE = 2000;
  private final static int REM_SIZE = 500;

  /** 
   * Number of items marked for removal
   */
  private int removeCount;
  /**
   *  Total number of items in queue
   */
  private int size;
  /**
   *  All tasks ordered by priority
   */
  private ArrayList<GTask> allTasks;
  /**
   *  Holds
   */
  private HashSet<String> holds;
  /**
   *  Removed tasks cache
   */
  private HashSet<String> toRemove;
  /**
   *  Task map by id
   */
  private HashMap<String, GTask> byId;
  /**
   *  Task comparators
   */
  private List<Comparator<GTask>> comparators;
  /**
   *  Queue change flag
   */
  private boolean changed;
  /**
   *  Total size of tasks in queue
   */
  private int widthTotal;
  /**
   *  Cached value of real square
   */
  private long realSquare;
  /**
   *  Cached value of expected square
   */
  private long expectedSquare;

  /**
   * Create new instance
   */
  public FastQueue(Comparator<GTask>... comparators)
  {
    allTasks = new ArrayList<GTask>(TAB_SIZE);
    toRemove = new HashSet<String>(REM_SIZE);
    byId = new HashMap<String, GTask>(REM_SIZE);
    changed = false;
    holds = new HashSet<String>();

    if(comparators == null && comparators.length == 0)
    {
      throw new IllegalArgumentException("Comparator required");
    }

    this.comparators = Arrays.asList(comparators);
  }

  @Override
  public void addTask(GTask task)
  {
    if(byId.get(task.getId()) != null)
    {
      throw new IllegalArgumentException(
          "Task with this id already exists in queue");
    }
    // Insert task according to the comparator list
    insertTask(task);

    // Update counters
    updateTaskCounters(task, 1);

    // Insert into id tab
    byId.put(task.getId(), task);
  }

  @Override
  public Iterator<GTask> getIterator()
  {
    return new TaskIterator(this);
  }

  @Override
  public void removeTask(String id)
  {
    GTask task = byId.get(id);

    if(task != null)
    {
      toRemove.add(id);
      removeCount++;

      updateTaskCounters(task, -1);

      if(removeCount > REMOVE_LIMIT)
      {
        cleanup();
      }
      
      holds.remove(id);
    }
  }

  @Override
  public int getSize()
  {
    return size;
  }

  @Override
  @Deprecated
  public void taskChanged(GTask task, int metaInf)
  {
    allTasks.remove(task);
    insertTask(task);
  }

  /**
   * Remove all tasks marked for deletion.
   */
  protected void cleanup()
  {
    removeCount = 0;
    for(Iterator<? extends GTask> it = allTasks.iterator(); it.hasNext();)
    {
      GTask task = it.next();
      if(toRemove.contains(task.getId()))
      {
        it.remove();
        byId.remove(task.getId());
      }
    }
    toRemove.clear();
  }

  /**
   * Inserts task into queue and sorts the queue
   * by the specified order.
   * 
   * @param task task
   */
  protected void insertTask(GTask task)
  {
    int i;
    int c;
    GTask itask;
    Iterator<Comparator<GTask>> cmp = comparators.iterator();
    Comparator<GTask> comparator = cmp.next();

    for(i = 0; i < allTasks.size(); i++)
    {
      itask = allTasks.get(i);
      c = comparator.compare(task, itask);
      if(c > 0)
      {
        break;
      }
      else if(c == 0)
      {
        while(cmp.hasNext())
        {
          comparator = cmp.next();
          c = comparator.compare(task, itask);
          if(c != 0)
          {
            break;
          }
        }
        if(c > 0)
        {
          break;
        }
      }
    }
    allTasks.add(i, task);
    changed = true;
  }

  @Override
  public long getExpectedSquare()
  {
    return expectedSquare;
  }

  @Override
  public long getRealSquare()
  {
    return realSquare;
  }

  @Override
  public int getSizeSum()
  {
    return widthTotal;
  }

  /**
   * Returns an array containing all of the tasks in this queue
   * in proper sequence.
   */
  public GTask[] toArray()
  {
    GTask[] result = new GTask[getSize()];

    int i = 0;
    Iterator<GTask> it = getIterator();
    while(it.hasNext())
    {
      result[i++] = it.next();
    }

    return result;
  }

  @Override
  public boolean contains(String taskId)
  {
    return byId.get(taskId) != null && !toRemove.contains(taskId);
  }

  /**
   * @return true if a new task has been inserted into the
   * queue since the last call to isChanged()
   */
  public boolean hasNewTasks()
  {
    boolean result = changed;
    changed = false;
    return result;
  }

  /**
   * Increase or decrease square counters.

   * @param task
   * @param direction 1 or -1
   */
  private void updateTaskCounters(GTask task, int direction)
  {
    long sqr = direction * task.getNodesMin() * task.getRealExecutionTime();
    long sqrE = direction * task.getNodesMin()
        * task.getRequestedExecutionTime();

    // Increase total size
    widthTotal += direction * task.getNodesMin();

    size += direction;
    realSquare += sqr;
    expectedSquare += sqrE;
  }

  @Override
  public int getMaxSize()
  {
    int maxSize = 0;

    for(Iterator<GTask> it = getIterator(); it.hasNext();)
    {
      GTask task = it.next();

      if(task.getNodesMin() > maxSize)
      {
        maxSize = task.getNodesMin();
      }
    }

    return maxSize;
  }

  @Override
  public void hold(String taskId)
  {
    GTask task = byId.get(taskId);

    if(task == null)
    {
      throw new IllegalArgumentException("Unable to hold not existing task "
          + taskId);
    }

    holds.add(taskId);
    
    //System.out.println("Hold task " + taskId);
  }

  @Override
  public void releaseHold(String taskId, long time)
  {
    holds.remove(taskId);
    
    GTask task = byId.get(taskId);
    if(task != null)
    {
      task.setReleaseTime(time);
    }
  }

  @Override
  public List<GTask> getHoldTasks()
  {
    ArrayList<GTask> hlist = new ArrayList<GTask>(holds.size());
    
    for(String taskId: holds)
    {
      hlist.add(byId.get(taskId));
    }
    return hlist;
  }
}
