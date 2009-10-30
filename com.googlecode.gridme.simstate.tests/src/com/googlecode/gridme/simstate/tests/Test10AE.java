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


public class Test10AE extends SimAE
{
  public Test10AE(String id)
  {
    super(id);
    setStatemachine(new test10STM(this));
    getStatemachine().addParam(test10STM.PARAM_timer, 10);
  }

  @Override
  public boolean checkState()
  {
    // Model time must be 10 not 20.
    return getStatemachine().getCurrentStateName().equals(test10STM.STATE_stop)
        && getModel().getModelTime() == 10;
  }

  @Override
  public Object action(int id)
  {
    return null;
  }
}
