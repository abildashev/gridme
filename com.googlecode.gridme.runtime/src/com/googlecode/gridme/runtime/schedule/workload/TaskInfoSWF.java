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
package com.googlecode.gridme.runtime.schedule.workload;

import java.util.List;

import com.googlecode.gridme.runtime.schedule.impl.TaskInfo;

/**
 * Task info that comes from the workload parser.
 */
public class TaskInfoSWF extends TaskInfo implements WorkloadTaskInfo
{
  private long wfwt;
  private long finTime;
  private long startTime;
  private long delay;
  private String workloadId;
  private String errors;

  public TaskInfoSWF(String id, long startTime, long delay, int minNodes, int maxNodes, long realTime, long reqTime,
      int prio, String part, long wfWaitTime, long wfFinTime)
  {
    super(id, minNodes, maxNodes, realTime, reqTime, prio, part);
    wfwt = wfWaitTime;
    finTime = wfFinTime;
    this.delay = delay;
    this.startTime = startTime;
  }

  @Override
  public long getWaitTimeInClusterQueue()
  {
    return wfwt;
  }

  @Override
  public long getCompletionTime()
  {
    return finTime;
  }

  /**
   * Task start time
   */
  public long getStartTime()
  {
    return startTime;
  }

  /**
   * Time left from the previous task generation in workload.
   */
  public long getDelayFromPreviousTask()
  {
    return delay;
  }

  public String toString()
  {
    return "" + startTime + " " + getWaitTimeInClusterQueue() + " " + getRealExecutionTime() + " " + getNodesMin()
        + " -1 -1 -1 -1 " + getRequestedExecutionTime() + " -1 -1 -1 -1 -1 -1 " + getPart() + " -1 -1";
  }

  public String toTabString()
  {
    return "<tr><td>" + errors + " </td><td>" + workloadId + "</td><td>" + startTime + "</td><td>"
        + getWaitTimeInClusterQueue() + "</td><td>" + getRealExecutionTime() + "</td><td>" + getNodesMin()
        + "</td><td>-1</td><td>-1</td><td>-1</td><td>-1</td><td>" + getRequestedExecutionTime()
        + "</td><td>-1</td><td>-1</td><td>-1</td><td>-1</td><td>-1</td><td>-1</td><td>" + getPart() + "</td><td>-1"
        + "</td><td>-1</td></tr>";
  }

  // public String getWorkloadId()
  // {
  // return workloadId;
  // }

  public void setWorkloadId(String workloadId)
  {
    this.workloadId = workloadId;
  }

  public void setStartTime(long startTime)
  {
    this.startTime = startTime;
  }

  /**
   * Builds a table with workload tasks with header.
   */
  public static String printHTMLTable(List<TaskInfoSWF> tasks)
  {
    StringBuilder str = new StringBuilder();

    str.append("<table>");
    str
        .append("<tr><td>parse status</td><td>id</td><td>submitTime</td><td>waitTime</td><td>executionTime</td><td>nodesUsed</td><td>"
            + "</td><td></td><td></td><td></td><td>requestedTime</td><td></td><td></td><td></td><td></td><td></td><td></td><td>partition</td>"
            + "<td></td><td></td></tr>");
    for(TaskInfoSWF t : tasks)
    {
      str.append(t.toTabString());
    }
    str.append("</table>");

    return str.toString();
  }

  public String getErrors()
  {
    return errors;
  }

  public void setErrors(String errors)
  {
    this.errors = errors;
  }
}
