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

import com.googlecode.gridme.runtime.GConnection;
import com.googlecode.gridme.runtime.GElement;
import com.googlecode.gridme.simstate.GSignal;

/**
 * An element that is used for testing signal transmission
 * between elements. Only the elements marked as "receiver" will
 * receive signals.
 */
class TNamedElement extends TElement
{
  public static final String RNAME = "receiver";

  private String tname;

  public String getTname()
  {
    return tname;
  }

  public void setTname(String tname)
  {
    this.tname = tname;
  }

  public boolean isReceiver()
  {
    return tname != null && tname.equals(RNAME);
  }

  /**
   * Gets all available elements with the name "receiver"
   * and sends a test signal to each of them through all available
   * connections.
   */
  public void testSend(boolean noDups)
  {
    ArrayList<GElement> rlist = new ArrayList<GElement>(); 
    
    for(GElement elem : getAvailableElements())
    {
      TNamedElement ne = (TNamedElement)elem;
      if(ne.isReceiver())
      {
        rlist.add(ne);
      }
    }
    
    // Now send a signal through all available connections 
    // to all receivers
    for(GElement element : rlist)
    {
      GSignal signal = new TSignal(element);
      for(GConnection conn : getConnections())
      {
        conn.sendSignal(signal, this);
        // Call test method to deliver signals
        ((TConnection)conn).doSend(noDups);
      }
    }
  }
}
