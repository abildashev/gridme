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

public class Test11AE extends SimAE
{
  private int s1, s2;
  
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
  
  public Test11AE(String id)
  {
    super(id);
    setStatemachine(new test11STM(this));
    allowAllSignals();
  }

  @Override
  public boolean checkState()
  {
    return getStatemachine().getCurrentStateName().equals(test11STM.STATE_stop);
  }

  @Override
  public Object action(int id)
  {
    switch(id)
    {
      case test11STM.ACTION_recordSig1:
        if(s2 != 0)
        {
          s1++;
        }
        s1++;
        // Remove all signals from the queue.
        getAllSignals(TSig1.class);
        break;
        
      case test11STM.ACTION_recordSig2:
        s2++;
        // Remove all signals from the queue.
        getAllSignals(TSig2.class);
        break;
        
      case test11STM.ACTION_checkFlags:
        return s1 == s2 && s1 == 1;
        
      case test11STM.ACTION_send:
        this.sendSignal(new TSig1(this), this);
        this.sendSignal(new TSig2(this), this);
        break;
    }
    return null;
  }
}
