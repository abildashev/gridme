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

import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.MetaSchedulingDecision;

/**
 * The base meta scheduler which searches the list of suitable clusters 
 * for the cluster which has minimal metric value.
 * 
 * Child classes can provide different comparators to
 * order the task queue, checking if the current 
 * cluster suites and providing the metric evaluator.
 */
public abstract class BaseBestFitGlobal extends BaseFIFOGlobal
{
  /**
   * 
   */
  public BaseBestFitGlobal(Comparator<GTask> comparator)
  {
    super(comparator);
  }

  /**
   * Computes the cluster metric.
   * 
   * @param Cluster
   * @param Current task
   */
  protected abstract float getMetric(GCluster cluster, GTask task);
  
  
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
        GCluster best = null;
        float metric = Float.MAX_VALUE;
        
        for(GCluster cluster : getClusterList())
        {
          if(suites(cluster, task))
          {
            float m = getMetric(cluster, task);
            if(m < metric)
            {
              metric = m;
              best = cluster;
            }
          }
        }
        
        if(best != null)
        {
          result.add(new MetaSchedulingDecision(task, best));
        }
      }
    }
    return result;
  }
}
