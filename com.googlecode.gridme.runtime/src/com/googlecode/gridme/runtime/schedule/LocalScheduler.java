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

import java.util.Collection;

/**
 * The scheduler that finds task to nodes mapping  
 */
public interface LocalScheduler extends Scheduler
{
  /**
   * Before calling this method ensure that the scheduler is
   * assigned to a cluster.
   *  
   * @return a mapping of the task to nodes.
   */
  public Collection<LocalSchedulingDecision> schedule(long currentTime);
  
  /**
   * Local scheduler is always assigned to a cluster.
   * @param cluster cluster
   */
  public void assignCluster(GCluster cluster);
}
