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
package com.googlecode.gridme.runtime.schedule.impl.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import com.googlecode.gridme.runtime.schedule.GBroker;
import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GQueue;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.MetaScheduler;
import com.googlecode.gridme.runtime.schedule.MetaSchedulingDecision;
import com.googlecode.gridme.runtime.schedule.impl.FastQueue;

/**
 * The base FIFO meta scheduler. 
 * Task queue is scanned in the FIFO order, for each task it is checked whether or not it can be 
 * allocated to the current cluster.
 * 
 * Child classes can provide different comparators to order the task queue and checking if the current 
 * cluster suites.
 */
public abstract class BaseFIFOGlobal implements MetaScheduler
{
  protected FastQueue queue;
  protected GBroker broker;

  /**
   * By default returns the list from broker. Child classes
   * may override this method to provide custom cluster list.
   */
  protected Collection<GCluster> getClusterList()
  {
    return broker.getClusters();
  }
  
  /**
   * Checks if the given cluster suites the task. 
   */
  protected abstract boolean suites(GCluster cluster, GTask task);
  
  /**
   * Creates new instance with the given queue comparator.
   */
  public BaseFIFOGlobal(Comparator<GTask> comparator)
  {
    queue = new FastQueue(comparator);
  }
  
  @Override
  public Collection<MetaSchedulingDecision> schedule(long ctime)
  {
    Collection<MetaSchedulingDecision> result = null;
    result = new ArrayList<MetaSchedulingDecision>();

    if(!getClusterList().isEmpty())
    {
      for(Iterator<GTask> it = queue.getIterator(); it.hasNext();)
      {
        GTask task = it.next();
        for(GCluster cluster : getClusterList())
        {
          if(suites(cluster, task))
          {
            result.add(new MetaSchedulingDecision(task, cluster));
            break;
          }
        }
      }
    }
    return result;
  }
  
  @Override
  public GQueue getQueue()
  {
    return queue;
  }

  @Override
  public void assignBroker(GBroker broker)
  {
    this.broker = broker;
  }
}
