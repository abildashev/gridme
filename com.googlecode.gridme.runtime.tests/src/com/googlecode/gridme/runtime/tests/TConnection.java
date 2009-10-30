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

import java.util.HashSet;

import com.googlecode.gridme.runtime.GConnection;
import com.googlecode.gridme.simstate.GSignal;

/**
 * Simple connection class that is used for testing
 * topology of interconnected elements and the tranmission
 * of signals between elements.
 */
class TConnection extends GConnection
{
  public TConnection()
  {
    super("TestConnection");
  }

  @Override
  public Object action(int id)
  {
    return null;
  }

  private void doSendInternal(HashSet<GConnection> vlist, boolean noDups)
  {
    if(!isVisited(vlist))
    {
      visit(vlist);
      // Get signals from buffer and transmit them
      deliverSignals();
      for(GSignal signal : getAllSignals())
      {
        transmit(signal, noDups);
      }

      for(GConnection conn : connections)
      {
        // Continue transmission recursively through all attached connections
        ((TConnection) conn).doSendInternal(vlist, noDups);
      }
    }
  }

  /**
   * Sends all signals further
   */
  public void doSend(boolean noDups)
  {
    doSendInternal(new HashSet<GConnection>(), noDups);
  }
}
