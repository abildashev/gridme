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

import com.googlecode.gridme.runtime.schedule.workload.WorkloadTaskInfo;
import com.googlecode.gridme.simstate.ActiveElement;

/**
 * Task signal with additional information from workload file.
 */
public class GTaskSignalWl extends GTaskSignal implements WorkloadTaskInfo
{
  private long waitTime;
  private long finishTime;

  public GTaskSignalWl(String id, int minNodes, int maxNodes, long reqTime,
                       long realTime, int priority, ActiveElement sender,
                       ActiveElement recipient, long wtime, long finishTime)
  {
    super(id, minNodes, maxNodes, reqTime, realTime, priority, sender, recipient);
    waitTime = wtime;
    this.finishTime = finishTime;
  }

  @Override
  public long getWaitTimeInClusterQueue()
  {
    return waitTime;
  }

  @Override
  public long getCompletionTime()
  {
    return finishTime;
  }
}
