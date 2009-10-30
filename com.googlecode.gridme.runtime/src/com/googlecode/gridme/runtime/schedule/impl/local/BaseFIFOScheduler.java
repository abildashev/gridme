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
import java.util.Comparator;
import java.util.Iterator;

import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GNode;
import com.googlecode.gridme.runtime.schedule.GQueue;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.LocalScheduler;
import com.googlecode.gridme.runtime.schedule.LocalSchedulingDecision;
import com.googlecode.gridme.runtime.schedule.impl.FastQueue;

/**
 * The base class for local FIFO scheduling. 
 * Task queue is scanned in FIFO order, for each task it is checked 
 * whether or not it can be allocated to the available cluster nodes.
 * 
 * Child classes can provide different comparators to order the task queue.
 */
public abstract class BaseFIFOScheduler implements LocalScheduler
{
  protected FastQueue queue;
  protected GCluster cluster;

  /**
   * Creates a new instance. 
   * @param comparator Task comparator to construct the queue.
   */
  public BaseFIFOScheduler(Comparator<GTask> comparator)
  {
    queue = new FastQueue(comparator);
  }
  
  @Override
  public void assignCluster(GCluster cluster)
  {
    this.cluster = cluster;
  }

  @Override
  public GQueue getQueue()
  {
    return queue;
  }

  @Override
  /**
   * Scans the queue using the iterator and allocates the task
   * if it fits into the available number of free nodes.
   */
  public Collection<LocalSchedulingDecision> schedule(long ctime)
  {
    // Get initial number of free and required nodes.
    int freeNodes = cluster.getFreeNodes();
    Collection<LocalSchedulingDecision> result = new ArrayList<LocalSchedulingDecision>();

    Iterator<? extends GNode> nodeIt = cluster.getFreeNodesIterator();
    for(Iterator<GTask> taskIt = queue.getIterator(); taskIt.hasNext()
        && freeNodes > 0;)
    {
      GTask task = taskIt.next();
      if(task.getNodesMin() <= freeNodes)
      {
        // allocate
        LocalSchedulingDecision dec = new LocalSchedulingDecision(task);
        result.add(dec);
        // get nodes
        for(int i = 0; i < task.getNodesMin(); i++)
        {
          dec.addNode(nodeIt.next());
          freeNodes--;
        }
      }
      else
      {
        // No backfill stage. The task will wait until there are enough free nodes. 
        break;
      }
    }
    return result;
  }

}
