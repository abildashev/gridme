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

import com.googlecode.gridme.runtime.GElement;
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.GSignal;

public class TSender extends GElement
{
  // total number of signals to send
  private int count;
  // Send period
  private long period;
  // index starting from 0
  private int index;
  private ActiveElement receiver;

  public TSender(ActiveElement recv, String id, long period, int count)
  {
    super(id);
    setStatemachine(new TSenderSTM(this));
    this.period = period;
    this.count = count;
    this.receiver = recv;
  }

  @Override
  public Object action(int id)
  {
    switch(id)
    {
      case TSenderSTM.ACTION_getPeriod:
        return period;
      case TSenderSTM.ACTION_send:
        GSignal signal = new TNumberedSignal(index++);
        signal.setRecipient(receiver);
        sendAll(signal);
        count--;
        break;
      case TSenderSTM.ACTION_isFinished:
        return count == 0;
      case TSenderSTM.ACTION_notFinished:
        return count != 0;
    }
    return null;
  }
}
