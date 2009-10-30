package com.googlecode.gridme.runtime.schedule.impl.power;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
import com.googlecode.gridme.runtime.schedule.GNodeGroup;
import com.googlecode.gridme.runtime.schedule.GQueue;
import com.googlecode.gridme.runtime.schedule.GTask;

public abstract class TotalRequiredWidthPM implements ClusterPowerManager
{
  protected PowerAwareCluster cluster;
  protected PowerSupply powerSuply;

  public TotalRequiredWidthPM()
  {
    powerSuply = new PowerSupply(PowerAwareCluster.DAY_START, PowerAwareCluster.DAY_END);
  }

  @Override
  public void assignCluster(GCluster cluster)
  {
    this.cluster = (PowerAwareCluster) cluster;
  }

  /**
   * Returns true if this task needs nodes for execution.
   */
  protected abstract boolean mustExecute(GTask task);

  /**
   * Computes W = total width of tasks in the queue for which mustExecute
   * returns true. Leaves W number of nodes on.
   */
  @Override
  public Collection<LocalPowerManagementDecision> manage()
  {
    ArrayList<LocalPowerManagementDecision> decision = new ArrayList<LocalPowerManagementDecision>();

    int widthRequired = 0;
    for(Iterator<GTask> it = cluster.getScheduler().getQueue().getIterator(); it.hasNext();)
    {
      GTask task = it.next();
      if(mustExecute(task))
      {
        widthRequired += task.getNodesMin();
      }
    }

    powerOn(cluster, decision, widthRequired - cluster.getFreeNodes());

    return decision;
  }

  /**
   * Creates LocalPowerManagementDecision objects and appends them to the list.
   * 
   * Depending on the value of widthRequired parameter for the given cluster:
   * <dl>
   * <li>switches nodes on if number of free nodes is less than widthRequired
   * <li>switches nodes off otherwise
   * </dl>
   * 
   * @param decision
   *          - the list of decisions
   * @param onCount
   *          - number of nodes to power on. If <0 - number of nodes to power
   *          down.
   */
  protected void powerOn(PowerAwareCluster cluster, ArrayList<LocalPowerManagementDecision> decision, int onCount)
  {
    int nCount = 0;
    LocalPowerManagementDecision ld;

    // Switch sleeping nodes on
    if(onCount > 0)
    {
      ld = new PowerManagementDecisionWakeup();
      for(Iterator<GNodeGroup> it = cluster.getSMPIterator(); it.hasNext() && nCount < onCount;)
      {
        GNodeGroup ng = it.next();
        
        boolean groupOk = true;
        for(GNode nd : ng.getNodes())
        {
          PowerAwareNode node = (PowerAwareNode) nd;
          groupOk = groupOk && node.isSleeping() && node.canPowerOn();
        }
        if(groupOk)
        {
          ld.addNodes(ng.getNodes());
          nCount += ng.getNodes().size();
        }
      }
      printDebugNodes("on", ld.getNodes().size());
      decision.add(ld);
    }

    // Switch free nodes off
    if(onCount < 0)
    {
      nCount = 0;
      int offCount = -1 * onCount;
      ld = new PowerManagementDecisionSleep();
      for(Iterator<GNodeGroup> it = cluster.getSMPIterator(); it.hasNext() && nCount < offCount;)
      {
        GNodeGroup ng = it.next();
        if(ng.isFree())
        {
          ld.addNodes(ng.getNodes());
        }
        nCount += ng.getNodes().size();
      }
      printDebugNodes("off", ld.getNodes().size());
      decision.add(ld);
    }
  }

  private void printDebugNodes(String message, int nodes)
  {
    if(cluster.getGanttLogger() != null && nodes > 0)
    {
      try
      {
        cluster.getGanttLogger().logInfoMessage(cluster.getModel().getModelTime(),
            message + " " + nodes + " cores (" + nodes / cluster.getPPN() + " nodes)");
        String qstr = queue2Str();
        if(!qstr.isEmpty())
        {
          cluster.getGanttLogger().logInfoMessage(cluster.getModel().getModelTime(), qstr);
        }
      }
      catch(GRuntimeException e)
      {
      }
    }
  }

  private String queue2Str()
  {
    GQueue q = cluster.getScheduler().getQueue();
    StringBuilder str = new StringBuilder();
    for(Iterator<GTask> it = q.getIterator(); it.hasNext();)
    {
      GTask task = it.next();
      str.append(task.getId() + "=" + task.getNodesMin() + "x" + task.getRealExecutionTime());
      if(it.hasNext())
      {
        str.append(" ");
      }
    }
    return str.toString();
  }
}
