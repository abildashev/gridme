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
 */
public class Test16AE extends SimAE
{
  public Test16AE(String id)
  {
    super(id);
    setStatemachine(new test16STM(this));
    getStatemachine().addParam(test16STM.PARAM_sigpar1, TestSignal.class);
    getStatemachine().addParam(test16STM.PARAM_sigpar2, TestSignal2.class);
    allowAllSignals();
  }

  @Override
  public boolean checkState()
  {
    return true;
  }

  @Override
  public Object action(int id)
  {
    switch(id)
    {
      case test16STM.ACTION_getDelay:
        long j = 1000;
        for(int i = 0; i < 100000; i++)
        {
           j *= i;
        }
        return 1;
      case test16STM.ACTION_sendSignal:
        this.sendSignal(new TestSignal(this, this), this);
        break;
      case test16STM.ACTION_clearSignalBuffer:
        removeAllSignals();
        break;
    }
    return null;
  }
}
