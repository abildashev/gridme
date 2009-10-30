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
package com.googlecode.gridme.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.exceptions.InvalidConfigurationException;
import com.googlecode.gridme.simstate.GSignal;

/**
 * Parent class for all grid elements such as cluster, broker, 
 * client, datasource, etc. The element contains a list of 
 * connections.
 */
public abstract class GElement extends ModelElement
{
  private ArrayList<GConnection> connections;

  /**
   * Creates a new element with the given id and parent reference.
   * @param id connection id
   */
  public GElement(String id)
  {
    super(id);
    connections = new ArrayList<GConnection>();
  }

  /**
  * Connects the element to the connection.
  * @param connection connection
  * @throws GRuntimeException if this connection is 
  * already connected to the element.
  */
  public void addConnection(GConnection connection) throws GRuntimeException
  {
    if(connections.contains(connection))
    {
      throw new InvalidConfigurationException(
          "The element is already connected");
    }
    connections.add(connection);
  }

  /**
   * Returns a list of all elements that this element 
   * can communicate with.
   */
  protected Collection<GElement> getAvailableElements()
  {
    HashSet<GElement> elems = new HashSet<GElement>();
    // Ommit current element
    elems.add(this);
    for(GConnection connection : connections)
    {
      for(GElement element : connection.getAvailableElements())
      {
        elems.add(element);
      }
    }
    return elems;
  }

  /**
   * @return a list of attached connections
   */
  protected Collection<GConnection> getConnections()
  {
    return connections;
  }

  /**
   * Sends a signal through all attached connections.
   * 
   * @param signal signal
   */
  protected void sendAll(GSignal signal)
  {
    for(GConnection conn : getConnections())
    {
      conn.sendSignal(signal, this);
    }
  }
}
