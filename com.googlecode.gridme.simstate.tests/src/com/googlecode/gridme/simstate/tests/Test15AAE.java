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

public class Test15AAE extends SimAE
{
  class TSig extends GSignal
  {
    public TSig(ActiveElement sender, 
                ActiveElement recipient, 
                Object... params)
    {
      super(sender, recipient, params);
    }
  }
  
  int count = 0;
  private ActiveElement target;
  
  public Test15AAE(String id, ActiveElement target)
  {
    super(id);
    setStatemachine(new test15ASTM(this));
    this.target = target;
  }

  @Override
  public boolean checkState()
  {
    return getStatemachine().getCurrentStateName().equals(test15ASTM.STATE_stop);
  }

  @Override
  public Object action(int id)
  {
    switch(id)
    {
      case test15ASTM.ACTION_isFinished:
        return count == 4;
      case test15ASTM.ACTION_notFinished:
        return count != 4;
      case test15ASTM.ACTION_sendSignal:
        target.sendSignal(new TSig(this, target), this);
        count++;
        break;
      case test15ASTM.ACTION_getDelay:
        if(count < 2)
        {
          return 5;
        }
        else
        {
          return 20;
        }
    }
    return null;
  }
}
