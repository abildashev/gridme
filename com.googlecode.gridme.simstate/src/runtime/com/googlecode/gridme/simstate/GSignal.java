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
package com.googlecode.gridme.simstate;

import java.util.ArrayList;

/**
 * Represents a signal. The signal can be sent to an 
 * active element instance. The signal may have parameters.
 * 
 * If the recipient of the signal is null that means the signal 
 * has no recipient and can be sent to any active element instance.
 * 
 * Signal has a size in bytes. Default size is 1024 bytes.
 */
public abstract class GSignal
{
  private final ArrayList<Object> parameters;
  private ActiveElement sender;
  private ActiveElement recipient;
  private boolean delivered;
  private int sizeBytes;

  /**
   * Creates a new instance of signal.
   * @param sender - original signal sender
   * @param recipient - target recipient can be null
   * @param  params - the list of parameters
   */
  public GSignal(ActiveElement sender, ActiveElement recipient, Object... params)
  {
    this.sender = sender;
    this.recipient = recipient;
    parameters = new ArrayList<Object>();
    delivered = false;
    sizeBytes = 1024;
    // Add parameters
    for(Object param : params)
    {
      this.addParameter(param);
    }
  }

  /**
   * Helper constructor without recipient. The recipient can be 
   * set later by calling setRecipient().
   *  
   * @param sender sender
   * @param params signal parameters
   */
  public GSignal(ActiveElement sender, Object... params)
  {
    this(sender, null , params);
  }
  
  /**
   * Adds parameter to the list.
   * @param param - signal parameter
   */
  public void addParameter(Object param)
  {
    parameters.add(param);
  }

  /**
   * @return signal parameters
   */
  public ArrayList<Object> getParameters()
  {
    return parameters;
  }

  /**
   * Returns parameter with the given index.
   * @param index parameter index, starting from 0
   * @return parameter
   * @throws IndexOutOfBoundsException if no parameter can be 
   * found with the given index.
   */
  public Object getParameter(int index) throws IndexOutOfBoundsException
  {
    return parameters.get(index);
  }

  /**
   * @return active element that have sent the signal
   */
  public ActiveElement getSender()
  {
    return sender;
  }

  /**
   * Returns signal recipient
   */
  public ActiveElement getRecipient()
  {
    return recipient;
  }

  /**
   * Checks if signal has been already delivered.
   */
  public boolean isDelivered()
  {
    return delivered;
  }

  /**
   * Sets delivered flag 
   */
  public void setDelivered(boolean delivered)
  {
    this.delivered = delivered;
  }

  public void setSender(ActiveElement sender)
  {
    this.sender = sender;
  }

  public void setRecipient(ActiveElement recipient)
  {
    this.recipient = recipient;
  }

  /**
   * Returns signal size. Signal size can be used to
   * model the signal transmission throught the network
   * connections.
   */
  public int getSizeBytes()
  {
    return sizeBytes;
  }
}
