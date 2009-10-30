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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.GNode;
import com.googlecode.gridme.runtime.schedule.GNodeGroup;
import com.googlecode.gridme.runtime.schedule.LocalScheduler;

/**
 * Stub cluster which is an element and a cluster so
 * it can be connected to a broker.
 */
public class TECluster extends TElement implements GCluster
{
  private int size;
  private HashMap<String, String> tags;
  
  /**
   * @param size
   */
  public TECluster(int size)
  {
    this.size = size;
    tags = new HashMap<String, String>();
  }

  @Override
  public int getFreeNodes()
  {
    return size;
  }

  @Override
  public Iterator<? extends GNode> getFreeNodesIterator()
  {
    return null;
  }

  @Override
  public int getSize()
  {
    return size;
  }

  @Override
  public String getTag(String name)
  {
    return tags.get(name);
  }

  @Override
  public void setTag(String name, String value)
  {
    tags.put(name, value);
  }

  @Override
  public Collection<String> tags()
  {
    return tags.keySet();
  }

  @Override
  public LocalScheduler getScheduler()
  {
    return null;
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
