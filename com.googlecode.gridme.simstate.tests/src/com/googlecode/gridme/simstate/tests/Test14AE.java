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

/**
 * 5 iterations with increasing delay.
 */
public class Test14AE extends SimAE
{
  int lastDelay = 0;
  int count = 0;
  
  public Test14AE(String id)
  {
    super(id);
    setStatemachine(new test14STM(this));
  }

  @Override
  public boolean checkState()
  {
    return getStatemachine().getCurrentStateName().equals(test14STM.STATE_stop) &&
      getModel().getModelTime() == 100 && count == 5;
  }

  @Override
  public Object action(int id)
  {
    switch(id)
    {
      case test14STM.ACTION_getDelay:
        return lastDelay;
      case test14STM.ACTION_enter:
        count++;
        lastDelay += 10;
        break;
      case test14STM.ACTION_finished:
        return count == 5;
      case test14STM.ACTION_notFinished:
        return count != 5;
    }
    
    return null;
  }
}
