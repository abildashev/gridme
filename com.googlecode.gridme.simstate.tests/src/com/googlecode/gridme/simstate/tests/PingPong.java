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
package com.googlecode.gridme.simstate.tests;

import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.GSignal;

/**
 * Simple ping-pong element
 */
class PingPong extends ActiveElement
{
  // Number of signals to process
  private int sendCount;
  
  public PingPong(String id, int count)
  {
    super(id);
    allowSignals(PingPongSignal.class);
    setStatemachine(new PingPongSTM(this));
    getStatemachine().addParam(PingPongSTM.PARAM_signal, PingPongSignal.class);
    this.sendCount = count;
  }

  @Override
  public Object action(int id)
  {
    switch(id)
    {
      case PingPongSTM.ACTION_reply:
        GSignal signal = getSignal(PingPongSignal.class);
        ActiveElement sender = signal.getSender();
        signal.setSender(this);
        sender.sendSignal(signal, this);
        sendCount--;
        break;
      case PingPongSTM.ACTION_finished:
        return sendCount == 0;
      case PingPongSTM.ACTION_notFinished:
        return sendCount > 0;
    }
    return null;
  }

  public boolean finished()
  {
    return getStatemachine().finished();
  }
}
