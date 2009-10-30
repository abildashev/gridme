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

import com.googlecode.gridme.runtime.ImplementationDescription;
import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.TaskComparatorPriority;

@ImplementationDescription(value = "The tasks in queue are ordered by the priority in decreasing order. "
    + "Each task is sent to the cluster that has suitable size and the value "
    + "of SW/W is minimal. SW - sum of all task sizes in queue, "
    + "W - cluster size.", category = ImplementationDescription.SCHED_GLOBAL)
public class WwMinGlobal extends BaseBestFitGlobal
{
  public WwMinGlobal()
  {
    super(new TaskComparatorPriority());
  }

  @Override
  protected float getMetric(GCluster cluster, GTask task)
  {
    return (float) (cluster.getScheduler().getQueue().getSizeSum() + task
        .getNodesMin())
        / cluster.getSize();
  }

  @Override
  protected boolean suites(GCluster cluster, GTask task)
  {
    return task.getNodesMin() <= cluster.getSize();
  }
}
