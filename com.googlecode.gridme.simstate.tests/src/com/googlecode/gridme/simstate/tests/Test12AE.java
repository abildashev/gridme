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

public class Test12AE extends SimAE
{
  class TSig1 extends GSignal
  {
    public TSig1(ActiveElement sender)
    {
      super(sender);
    }
  }

  class TSig2 extends GSignal
  {
    public TSig2(ActiveElement sender)
    {
      super(sender);
    }
  }

  class TSig3 extends GSignal
  {
    public TSig3(ActiveElement sender)
    {
      super(sender);
    }
  }
  
  public Test12AE(String id)
  {
    super(id);
    setStatemachine(new test12STM(this));
    allowSignals(TSig1.class, TSig2.class, TSig3.class);
    getStatemachine().addParam(test12STM.PARAM_sigpar3, TSig3.class);
  }

  @Override
  public boolean checkState()
  {
    return getStatemachine().getCurrentStateName().equals(test12STM.STATE_stop);
  }

  @Override
  public Object action(int id)
  {
    switch(id)
    {
      case test12STM.ACTION_send1:
        sendSignal(new TSig1(this), this);
        break;
      case test12STM.ACTION_send2:
        sendSignal(new TSig2(this), this);
        break;
      case test12STM.ACTION_send3:
        sendSignal(new TSig3(this), this);
        break;
      case test12STM.ACTION_getSig2:
        return TSig2.class;
      case test12STM.ACTION_removeAllSignals:
        getAllSignals();
        break;
    }
    return null;
  }
}
