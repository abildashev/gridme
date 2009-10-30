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
import java.util.Iterator;
import java.util.Random;

import com.googlecode.gridme.runtime.ImplementationDescription;
import com.googlecode.gridme.runtime.schedule.GBroker;
import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GQueue;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.MetaScheduler;
import com.googlecode.gridme.runtime.schedule.MetaSchedulingDecision;
import com.googlecode.gridme.runtime.schedule.TaskComparatorPriority;
import com.googlecode.gridme.runtime.schedule.impl.FastQueue;

@ImplementationDescription(value = "The tasks in queue are ordered by the priority "
    + "in decreasing order. Each task is sent to the cluster which is randomly choosen from the"
    + "list of clusters that have suitable size.", category = ImplementationDescription.SCHED_GLOBAL)
public class RandomFitGlobal implements MetaScheduler
{
  protected FastQueue queue;
  protected GBroker broker;
  protected Random rand;
  private ArrayList<GCluster> basket;

  /**
   * 
   */
  public RandomFitGlobal()
  {
    queue = new FastQueue(new TaskComparatorPriority());
    rand = new Random(System.currentTimeMillis());
    basket = new ArrayList<GCluster>(100);
  }

  @Override
  public Collection<MetaSchedulingDecision> schedule(long ctime)
  {
    Collection<MetaSchedulingDecision> result = null;
    result = new ArrayList<MetaSchedulingDecision>();

    if(!broker.getClusters().isEmpty())
    {
      for(Iterator<GTask> it = queue.getIterator(); it.hasNext();)
      {
        GTask task = it.next();
        basket.clear();

        for(GCluster cluster : broker.getClusters())
        {
          if(cluster.getSize() >= task.getNodesMin())
          {
            basket.add(cluster);
          }
        }

        if(!basket.isEmpty())
        {
          // Get random cluster
          result.add(new MetaSchedulingDecision(task, basket.get(rand
              .nextInt(basket.size()))));
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
