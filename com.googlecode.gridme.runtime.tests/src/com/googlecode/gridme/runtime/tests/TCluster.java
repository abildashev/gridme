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
package com.googlecode.gridme.runtime.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GNode;
import com.googlecode.gridme.runtime.schedule.GNodeGroup;
import com.googlecode.gridme.runtime.schedule.LocalScheduler;

/**
 * Test cluster stub for testing local 
 * heuristics implementation.
 */
class TCluster implements GCluster
{
  /**
   * Node stub
   */
  private class TNode implements GNode
  {
    @Override
    public boolean isFree()
    {
      return true;
    }

    @Override
    public boolean isBusy()
    {
      return false;
    }
  }
  
  private ArrayList<TNode> nodes;
  private LocalScheduler scheduler;
  
  public TCluster(int ncount)
  {
    nodes = new ArrayList<TNode>();
    for(int i = 0; i < ncount; i++)
    {
      nodes.add(new TNode());
    }
  }

  public TCluster(int ncount, LocalScheduler sched)
  {
    this(ncount);
    setScheduler(sched);
  }
  
  @Override
  public int getFreeNodes()
  {
    return nodes.size();
  }

  @Override
  public Iterator<? extends GNode> getFreeNodesIterator()
  {
    return nodes.iterator();
  }

  @Override
  public String getId()
  {
    return null;
  }

  @Override
  public int getSize()
  {
    return nodes.size();
  }

  @Override
  public String getTag(String name)
  {
    return null;
  }

  @Override
  public void setTag(String name, String value)
  {
  }

  @Override
  public Collection<String> tags()
  {
    return null;
  }

  public void setScheduler(LocalScheduler scheduler)
  {
    this.scheduler = scheduler;
  }

  @Override
  public LocalScheduler getScheduler()
  {
    return scheduler;
  }

  @Override
  public Iterator<? extends GNode> getNodesIterator()
  {
    return null;
  }

  @Override
  public boolean isChanged()
  {
    return false;
  }

  @Override
  public int getBusyNodes()
  {
    return 0;
  }

  @Override
  public int getPPN()
  {
    return 1;
  }

  @Override
  public Iterator<GNodeGroup> getSMPIterator()
  {
    return null;
  }
}
