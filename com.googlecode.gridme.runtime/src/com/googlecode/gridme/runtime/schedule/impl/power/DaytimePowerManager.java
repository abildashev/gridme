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
package com.googlecode.gridme.runtime.schedule.impl.power;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.googlecode.gridme.runtime.ImplementationDescription;
import com.googlecode.gridme.runtime.PowerSupply;
import com.googlecode.gridme.runtime.elements.ClusterPowerManager;
import com.googlecode.gridme.runtime.elements.LocalPowerManagementDecision;
import com.googlecode.gridme.runtime.elements.PowerAwareCluster;
import com.googlecode.gridme.runtime.elements.PowerAwareNode;
import com.googlecode.gridme.runtime.elements.PowerManagementDecisionSleep;
import com.googlecode.gridme.runtime.elements.PowerManagementDecisionWakeup;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GNode;

@ImplementationDescription(value = "Cluster power manager implementation that considers " +
		"day and night power cost.", 
    category = ImplementationDescription.POWER)
public class DaytimePowerManager implements ClusterPowerManager
{
  PowerAwareCluster cluster;
  PowerSupply ps;

  public DaytimePowerManager()
  {
    ps = new PowerSupply(PowerAwareCluster.DAY_START, PowerAwareCluster.DAY_END);
  }

  @Override
  public void assignCluster(GCluster cluster)
  {
    this.cluster = (PowerAwareCluster) cluster;
  }

  @Override
  public Collection<LocalPowerManagementDecision> manage()
  {
    ArrayList<LocalPowerManagementDecision> decision = new ArrayList<LocalPowerManagementDecision>();

    // If there are free nodes and an empty task queue we can switch free nodes off.
    if(cluster.getFreeNodes() > 0
        && cluster.getScheduler().getQueue().getSize() == 0)
    {
      LocalPowerManagementDecision ld = new PowerManagementDecisionSleep();

      for(Iterator<? extends GNode> it = cluster.getFreeNodesIterator(); it
          .hasNext();)
      {
        ld.addNode(it.next());
      }

      decision.add(ld);
    }
    // If there are sleeping nodes and non empty task queue we must switch necessary nodes on.
    // If it is day now we switch only 1/2 of all available nodes, otherwise all
    else if(cluster.hasSleepingNodes()
        && cluster.getScheduler().getQueue().getSize() > 0)
    {
      LocalPowerManagementDecision ld = new PowerManagementDecisionWakeup();

      int wakeupCount = cluster.getSleepingNodes().size();

      if(ps.isDayNow(cluster.getModel().getRealTime()))
      {
        wakeupCount = cluster.getSleepingNodes().size() - cluster.getSize() / 2;
      }

      assert (wakeupCount >= 0);

      if(wakeupCount > 0)
      {
        try
        {
          cluster.getGanttLogger().logInfoMessage(
              cluster.getModel().getModelTime(),
              "queue len: " + cluster.getScheduler().getQueue().getSize());
          cluster.getGanttLogger().logInfoMessage(
              cluster.getModel().getModelTime(),
              "max task width: "
                  + cluster.getScheduler().getQueue().getMaxSize());
          cluster.getGanttLogger().logInfoMessage(
              cluster.getModel().getModelTime(),
              "wake up " + wakeupCount + " nodes");
        }
        catch(GRuntimeException e)
        {
        }
      }

      Iterator<PowerAwareNode> it = cluster.getSleepingNodes().iterator();
      while(wakeupCount > 0 && it.hasNext())
      {
        ld.addNode(it.next());
        wakeupCount--;
      }

      decision.add(ld);
    }

    return decision;
  }
}
