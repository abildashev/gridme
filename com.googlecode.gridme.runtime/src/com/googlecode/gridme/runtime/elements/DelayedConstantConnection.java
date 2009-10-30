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
package com.googlecode.gridme.runtime.elements;

import java.util.Iterator;

import com.googlecode.gridme.runtime.GConnection;
import com.googlecode.gridme.runtime.ModelElementImplementationDescription;
import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.simstate.GSignal;

@ModelElementImplementationDescription("Connection that waits a given number of time units and "
    + "than transmits a number of bytes. Thus there are two parameters that define "
    + "the behavior: byte count and the period. Note that the minimal item "
    + "that connection will transmit is a signal. Note also that period defines "
    + "a latency of the connection. If you want unlimited speed connection set "
    + "the zero period.")
public class DelayedConstantConnection extends GConnection
{
  private long period;
  private int countBytes;

  /**
   * Creates new connection.
   * 
   * @param id connection id
   * @param params parameters
   * @throws GRuntimeException 
   */
  public DelayedConstantConnection(String id) throws GRuntimeException
  {
    super(id);
    setStatemachine(new DelayedConstantConnectionSTM(this));
  }

  @Parameter(description = "The number of transmitted bytes", required = true, hasParams = false, category = 0)
  public void setNumberOfBytes(String value)
  {
    countBytes = Integer.parseInt(value);
  }

  @Parameter(description = "Time to wait for the next "
      + "transmission of a set of bytes.", required = true, hasParams = false, category = 0)
  public void setPeriod(String value)
  {
    period = Integer.parseInt(value);
  }

  @Override
  public Object action(int id)
  {
    switch(id)
    {
      case DelayedConstantConnectionSTM.ACTION_hasSignals:
        return !signals.isEmpty();
      case DelayedConstantConnectionSTM.ACTION_getDelay:
        return period;
      case DelayedConstantConnectionSTM.ACTION_transmit:
        doTransmit();
        break;
    }
    return null;
  }

  /**
   * Transmits constant number of signals.
   */
  private void doTransmit()
  {
    if(!signals.isEmpty())
    {
      int i = 0;
      for(Iterator<GSignal> it = signals.iterator(); it.hasNext()
          && i < countBytes;)
      {
        GSignal signal = it.next();
        transmit(signal, true);
        i += signal.getSizeBytes();
        it.remove();
      }
    }
  }
}
