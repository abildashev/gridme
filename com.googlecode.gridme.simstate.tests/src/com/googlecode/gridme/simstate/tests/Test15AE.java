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
 * Test signal transmission with timer delay.
 * Total 4 signals: 2 signals come in 5 sec delay,
 * 2 in 20 sec delay. We receive in constant 10 sec delay. 
 */
public class Test15AE extends SimAE
{
  int count = 0;
  
  public Test15AE(String id)
  {
    super(id);
    setStatemachine(new test15STM(this));
    allowAllSignals();
  }

  @Override
  public boolean checkState()
  {
    return getStatemachine().getCurrentStateName().equals(test15STM.STATE_stop) &&
      getModel().getModelTime() == 60;
  }

  @Override
  public Object action(int id)
  {
    switch(id)
    {
      case test15STM.ACTION_getDelay:
        return 10;
      case test15STM.ACTION_isFinished:
        return count == 4;
      case test15STM.ACTION_notFinished:
        return count != 4;
      case test15STM.ACTION_logSignal:
        if(getSignal(Test15AAE.TSig.class) != null)
        {
          count++;
        }
        System.out.println();
        break;
    }
    return null;
  }
}
