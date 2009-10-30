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
package com.googlecode.gridme.runtime.schedule;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a task queue. 
 */
public interface GQueue
{
  /**
   * Adds a task to the queue.
   */
  public void addTask(GTask task);
  
  /**
   * Removes task from the queue.
   * 
   * @param id task id
   */
  public void removeTask(String id);
  
  /**
   * @return queue size
   */
  public int getSize();

  /**
   * Returns real queue square - the sum of all task square values.
   * The task square is computed as a product of task size (min nodes)
   * and the task real execution time.   
   * 
   * @return square value or 0 if not available
   */
  public long getRealSquare();
  
  /**
   * Returns expected queue square - the sum of all task square values.
   * The task square is computed as a product of task size (min nodes)
   * and the task requested execution time.
   * 
   * @return square value or 0 if not available
   */
  public long getExpectedSquare();
  
  /**
   * Returns the sum of all task sizes in queue.
   */
  public int getSizeSum();
  
  
  /**
   * Returns maximum task width in the queue.
   */
  public int getMaxSize();
  
  /**
   * @return an iterator that can be used to traverse all tasks from this
   * queue in the order defined by the queue. Tasks with hold active are skipped.
   */
  public Iterator<GTask> getIterator();
  
  /**
   * Notifies the queue that the given task has
   * changed its properties. Interface implementations
   * should rebuild internal hashes.  
   * 
   * @param task task reference
   * @param metaInf the flag that gives a suggestion 
   * on what had been changed.
   * @see GTask
   * 
   * @deprecated
   */
  public void taskChanged(GTask task, int metaInf);
  
  /**
   * Returns true if the task can be found in queue.
   */
  public boolean contains(String taskId);
 
  /**
   * Place a hold on task. The task will stay in queue not participating in
   * scheduling until hold is removed.
   */
  public void hold(String taskId);
  
  /**
   * Remove hold from task
   */
  public void releaseHold(String taskId, long time);
  
  /**
   * @return the list of hold tasks
   */
  public List<GTask> getHoldTasks();
}
