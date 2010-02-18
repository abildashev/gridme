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

import java.util.ArrayList;
import java.util.List;

import com.googlecode.gridme.runtime.elements.PowerAwareNode;

/**
 * Cluster node.
 */
public class GNodeGroup
{
  private ArrayList<GNode> nodes = new ArrayList<GNode>();
  
  public void addNode(GNode node)
  {
    nodes.add(node);
  }
  
  public List<GNode> getNodes()
  {
    return nodes;
  }
  
  public boolean isFree()
  {
    for(GNode node : nodes)
    {
      if(!node.isFree())
      {
        return false;
      }
    }
    return true;
  }

  public boolean isSleeping()
  {
    for(GNode node : nodes)
    {
      if( !(node instanceof PowerAwareNode) || 
          !((PowerAwareNode)node).isSleeping() )
      {
        return false;
      }
    }
    return true;
  }
}
