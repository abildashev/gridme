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
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.GSignal;

/**
 * Parent class for all connections. Connection is implemented
 * as an active element. To create new connection class 
 * extend this class and create a state machine that will
 * implement the behavior of this element.
 */
public abstract class GConnection extends ModelElement
{
  protected ArrayList<GElement> elements;
  protected ArrayList<GConnection> connections;

  /**
   * Creates a new connection with the given id and parent reference.
   * By default connection allows all types of signals.
   *  
   * @param id connection id
   * @param parent parent element
   */
  public GConnection(String id)
  {
    super(id);
    elements = new ArrayList<GElement>(10);
    connections = new ArrayList<GConnection>(5);
    allowAllSignals();
  }

  /**
   * Attaches an element to this connection.
   * @param elem element you want to connect
   * @param bidirectional boolean flag - if true, 
   *  add this connection to the element.
   * @throws InvalidConfigurationException if an element is alredy connected.
   */
  private void connectElement(ActiveElement elem, boolean bidirectional) throws GRuntimeException
  {
    if(elements.contains(elem) || connections.contains(elem))
    {
      throw new InvalidConfigurationException("Element is already connected");
    }

    if(elem instanceof GElement)
    {
      elements.add((GElement) elem);
      if(bidirectional)
      {
        ((GElement) elem).addConnection(this);
      }
    }
    else if(elem instanceof GConnection)
    {
      connections.add((GConnection) elem);
      if(bidirectional)
      {
        ((GConnection) elem).connectElement(this, false);
      }
    }
  }

  /**
   * Connects an element to this connection. Connection is not
   * added to the element so the element does not know about
   * this connection and can only receive signals from it.
   */
  public void connectElementReceive(ActiveElement... elements) throws GRuntimeException
  {
    for(ActiveElement elem : elements)
    {
      connectElement(elem, false);
    }
  }

  /**
   * Connects an element to this connection. Connection is
   * added to the element so the element knows about
   * this connection and can receive from and send signals to it.
   */
  public void connectElementSendReceive(ActiveElement... elements) throws GRuntimeException
  {
    for(ActiveElement elem : elements)
    {
      connectElement(elem, true);
    }
  }

  /**
   * Saves the vertex in the list.
   */
  protected void visit(HashSet<GConnection> visitList)
  {
    synchronized(visitList)
    {
      visitList.add(this);
    }
  }

  /**
   * @return true if this vertex is inside a loop.
   */
  protected synchronized boolean isVisited(HashSet<GConnection> visitList)
  {
    return visitList.contains(this);
  }

  /**
   * Transmits a signal to the recipients
   * 
   * If the signal has no recipient set an exception will
   * be thrown.
   * 
   * It is possible that the signal recipient can not be reached
   * from this connection. In this case the connection will
   * try to transmit the signal to all connections it is connected 
   * with. Be sure to check the availability of the recipient to 
   * avoid cycles at runtime.
   *  
   * @param signal signal instance
   * @param noDuplicates if true deliver the signal to the first 
   * available recipient. Otherwise multiple instances of the 
   * signal may be delivered to the recipient.
   */
  protected void transmit(GSignal signal, boolean noDuplicates)
  {
    boolean isSent = false;

    // Try to send the signal directly
    // If the signal has no recipient set an exception
    // will be thrown
    if(signal.getRecipient() == null)
    {
      throw new IllegalStateException("The signal has no recipient set. " +
      		"Sender: " + signal.getSender());
    }
    
    for(GElement elem : this.elements)
    {
      if(signal.getRecipient() == elem)
      {
        synchronized(signal)
        {
          elem.sendSignal(signal, this, noDuplicates);
          signal.setDelivered(true);
          isSent = true;
        }
        break;
      }
    }

    // If the signal was not sent try to send it 
    // via attached connections.
    if(!isSent)
    {
      for(GConnection connection : connections)
      {
        // send via this connection. Use delivered flag to
        // avoid multiple delivery
        connection.sendSignal(signal, this, noDuplicates);
      }
    }
    
    // Drop the signal
  }

  /**
   * Returns a list of elements that can be reached 
   * from this connection. This method will follow other
   * connections and find all available elements. 
   * Loops and duplicated items are skipped.
   */
  public Collection<GElement> getAvailableElements()
  {
    return getAvailableElementsInternal(new HashSet<GConnection>());
  }

  /**
   * Internal helper method to find all elements.
   */
  private Collection<GElement> getAvailableElementsInternal(HashSet<GConnection> vertexList)
  {
    // Use hash set to avoid duplicate items
    HashSet<GElement> result = new HashSet<GElement>();

    if(!isVisited(vertexList))
    {
      visit(vertexList);
      // Add all directly connected elements
      result.addAll(elements);
      // Add elements from other connections. 
      for(GConnection connection : connections)
      {
        result.addAll(connection.getAvailableElementsInternal(vertexList));
      }
    }
    return result;
  }
}
