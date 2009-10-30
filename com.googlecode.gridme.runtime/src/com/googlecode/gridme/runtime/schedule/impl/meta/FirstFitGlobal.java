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

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

import com.googlecode.gridme.runtime.ImplementationDescription;
import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.TaskComparatorPriority;

@ImplementationDescription(value = "The scheduler scans the task queue and tries "
    + "to allocate task at the first cluster that has suitable size. "
    + "The tasks in queue are ordered by the priority in decreasing order."
    + "The list of clusters is sorted by size in increasing order. ", category = ImplementationDescription.SCHED_GLOBAL)
public class FirstFitGlobal extends BaseFIFOGlobal
{
  // The sorted list of clusters
  protected LinkedList<GCluster> clusters;

  /**
   * 
   */
  public FirstFitGlobal()
  {
    super(new TaskComparatorPriority());
  }

  @Override
  protected Collection<GCluster> getClusterList()
  {
    if(clusters == null)
    {
      init();
    }
    return clusters;
  }

  @Override
  protected boolean suites(GCluster cluster, GTask task)
  {
    return task.getNodesMin() <= cluster.getSize();
  }

  /**
   * Builds an ordered list of clusters.
   */
  protected void init()
  {
    clusters = new LinkedList<GCluster>();
    for(GCluster clToInsert : broker.getClusters())
    {
      boolean inserted = false;
      for(ListIterator<GCluster> it = clusters.listIterator(); it.hasNext();)
      {
        GCluster current = it.next();
        if(clToInsert.getSize() <= current.getSize())
        {
          it.previous();
          it.add(clToInsert);
          inserted = true;
          break;
        }
      }
      if(!inserted)
      {
        clusters.add(clToInsert);
      }
    }
  }
}
