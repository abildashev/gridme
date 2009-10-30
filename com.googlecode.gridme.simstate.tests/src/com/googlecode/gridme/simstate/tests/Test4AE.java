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


public class Test4AE extends SimAE
{
  public Test4AE(String id)
  {
    super(id);
    setStatemachine(new test4STM(this));
  }

  @Override
  public boolean checkState()
  {
    return getStatemachine().getCurrentStateName()
        .equals(test4STM.STATE_State1);
  }

  @Override
  public Object action(int id)
  {
    switch(id)
    {
      case test4STM.ACTION_trueAction:
        return true;
      case test4STM.ACTION_falseAction:
        return false;
    }
    return null;
  }
}
