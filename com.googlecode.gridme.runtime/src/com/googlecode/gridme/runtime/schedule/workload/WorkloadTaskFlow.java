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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.googlecode.gridme.runtime.GElement;
import com.googlecode.gridme.runtime.ModelElementImplementationDescription;
import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.elements.SimpleTaskFlow;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.schedule.GBroker;
import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GTask;
import com.googlecode.gridme.runtime.schedule.TaggedResource;
import com.googlecode.gridme.simstate.ActiveElement;

/**
 * The standard workload format (swf) was defined in order 
 * to ease the use of workload logs and models. This class
 * can parse workload archive file in SWF format and generate 
 * a task flow in the model.
 */
@ModelElementImplementationDescription("This task flow implementation parses standard workload "
    + "format file and generates the sequence of tasks. The workload "
    + "file must be a zip archive of workload format "
    + "(see http://www.cs.huji.ac.il/labs/parallel/workload/index.html). \n\n"
    + "If connected to a broker it will send all tasks to a broker "
    + "(first found). If no broker is available the flow will send tasks "
    + "to the corresponding clusters which have a tag 'partition' set. "
    + " In this case all tasks must have not null partition id set. "
    + "Partition will be used as an identification of cluster to send task to. \n"
    + " If task has no partition id set it will be sent to a broker or a first "
    + "found cluster.")
public class WorkloadTaskFlow extends SimpleTaskFlow
{
  /**
   * Default partition tag name.
   */
  public static final String T_PART = "partition";

  // Clusters are organized in a map with partition id as a key.
  protected HashMap<String, ActiveElement> clusters;

  // All clusters without partition id
  protected ArrayList<ActiveElement> unknownClusters;

  // Broker recipient
  protected ActiveElement broker;

  // Task
  protected TaskInfoSWF task;

  // Workload parser
  private SWFParser parser;

  // Start delay
  private long startDelay;

  // time between last and current task
  private long delay;

  // Allowed partitions
  private Set<String> parts;

  // a finished flag for state machine
  private boolean finished = false;

  /**
   * Creates new instance of workload task flow.
   * 
   * @param id flow id
   * @param params parameters.
   * @throws GRuntimeException 
   */
  public WorkloadTaskFlow(String id) throws GRuntimeException
  {
    super(id);
  }

  @Parameter(description = "The time in seconds from start of the experiment till "
      + "the first workload task will be generated. Default value is 0.", required = false, hasParams = false, category = 0)
  public void setStartDelay(String value)
  {
    startDelay = Integer.parseInt(value);
  }

  @Parameter(description = "Comma separated list of partitions to use. "
      + "The tasks will be sent to clusters that are marked with corresponding "
      + "tag 'partition'", required = false, hasParams = false, category = 0)
  public void setPartitions(String value)
  {
    parts = new HashSet<String>();
    final String[] tokens = value.split(",");
    for(int i = 0; i < tokens.length; i++)
    {
      parts.add(tokens[i]);
    }
  }

  @Parameter(description = "The full path to the workload archive file", required = true, hasParams = false, category = Parameter.WORKLOAD)
  public void setWorkload(String value) throws GRuntimeException
  {
    File wfile = null;
    if(getModel() != null)
    {
      wfile = getModel().getFileByName(value);
    }
    else
    {
      wfile = new File(value);
    }

    if(wfile != null)
    {
      parser = new SWFParser(wfile, null);
    }
    else
    {
      throw new GRuntimeException("Unable to open workload file " + value);
    }
  }

  protected HashMap<String, ActiveElement> getClusters()
  {
    return clusters;
  }

  protected ActiveElement getBroker()
  {
    return broker;
  }

  @Override
  protected long getPeriod()
  {
    return delay;
  }

  @Override
  protected ActiveElement getRecipient()
  {
    if(broker != null)
    {
      return broker;
    }
    else if(isPartitionSet())
    {
      return clusters.get(task.getPart());
    }
    return getFirstCluster();
  }

  /**
   * Returns first cluster found. The behavior of this function
   * is not specified when there are more then 2 clusters available.  
   */
  private ActiveElement getFirstCluster()
  {
    ActiveElement result = null;
    if(!unknownClusters.isEmpty())
    {
      result = unknownClusters.get(0);
    }
    else if(!clusters.isEmpty())
    {
      result = clusters.values().iterator().next();
    }
    return result;
  }

  @Override
  protected GTask getTask()
  {
    return task;
  }

  @Override
  /**
   * Discover brokers and clusters.
   */
  protected void init()
  {
    clusters = new HashMap<String, ActiveElement>();
    unknownClusters = new ArrayList<ActiveElement>();
    Collection<GElement> all = getAvailableElements();
    for(GElement element : all)
    {
      if(element instanceof GBroker)
      {
        // A broker found. Discard all clusters and use this 
        // broker as a target for task submission.
        broker = element;
        break;
      }
      else if(element instanceof GCluster)
      {
        String part = ((TaggedResource) element).getTag(T_PART);
        if(part != null)
        {
          clusters.put(part, element);
        }
        else
        {
          unknownClusters.add(element);
        }
      }
    }
  }

  @Override
  /**
   * Read next record and initialize delay and task. If task has no id set
   * it will be sent to a broker (if exists) or a first found cluster.
   */
  protected void prepare() throws GRuntimeException
  {
    do
    {
      task = parser.nextTask();
      if(task == null)
      {
        finished = true;
        break;
      }
      else if((isPartitionSet() && (parts == null || parts.contains(task
          .getPart())))
          || (parts == null && !isPartitionSet()))
      {
        // Good task
        delay = task.getDelayFromPreviousTask();
      }
      else
      {
        // Try a next task. Do not report an error because it 
        // is a valid task, we just need another one.
        task = null;
      }
    }
    while(task == null);
  }

  private boolean isPartitionSet()
  {
    return Integer.parseInt(task.getPart()) > 0;
  }

  @Override
  protected boolean isFinished()
  {
    return finished;
  }

  @Override
  protected long getStartDelay()
  {
    return startDelay;
  }
}
