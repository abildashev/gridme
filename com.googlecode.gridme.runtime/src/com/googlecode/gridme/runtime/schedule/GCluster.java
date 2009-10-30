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

import java.util.Iterator;

/**
 * Represents a computational cluster.
 */
public interface GCluster extends TaggedResource
{
  /**
   * @return Cluster id
   */
  public String getId();
  
  /**
   * Returns total number of cluster nodes.
   */
  public int getSize();
  
  /**
   * Returns number of free nodes
   */
  public int getFreeNodes();
  
  /**
   * Returns an iterator that can be used to iterate over
   * free nodes.
   */
  public Iterator<? extends GNode> getFreeNodesIterator();
  
  /**
   * Returns an iterator that can be used to iterate over
   * all nodes.
   */
  public Iterator<? extends GNode> getNodesIterator();
  
  
  /**
   * @return Iterator that can be used to iterate by
   * ppn number of nodes.
   */
  public Iterator<GNodeGroup> getSMPIterator();
  
  /**
   * @return reference to the associated scheduler.
   */
  public LocalScheduler getScheduler();
  
  /**
   * @return true if some nodes were switched on or off
   */
  public boolean isChanged();

  /**
   * @return number of nodes occupied by tasks
   */
  public int getBusyNodes();
  
  /**
   * @return Number of cores per node.
   */
  public int getPPN();
}
