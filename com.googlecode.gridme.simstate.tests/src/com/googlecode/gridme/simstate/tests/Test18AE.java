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

import java.util.Random;

public class Test18AE extends SimAE
{
  private Random rand;
  private long lastTime;
  private long atime = 20;
  private boolean good = true;
  
  public Test18AE(String id)
  {
    super(id);
    setStatemachine(new test18STM(this));
    getStatemachine().addParam(test18STM.PARAM_alarmSignal, TestSignal.class);
    allowAllSignals();
    
    addAlarm(new TestAlarm(atime, 0, this));
    //System.out.println("Alarm set to " + atime);

    rand = new Random();
  }

  @Override
  public boolean checkState()
  {
    return good;
  }

  @Override
  public Object action(int id) throws Exception
  {
    switch(id)
    {
      case test18STM.ACTION_getDelay:
        long res = rand.nextInt(20);
        //System.out.println("Timer set to " + res);
        return res;
      case test18STM.ACTION_timer:
        //System.out.println("-> Timer " + getModel().getModelTime());
        break;
      case test18STM.ACTION_callAlarm:
        //System.out.println("====> Alarm " + getModel().getModelTime());
        getAllSignals();
        //System.out.println("" + (getModel().getModelTime() - lastTime));
        good &= getModel().getModelTime() - lastTime == atime;
        lastTime = getModel().getModelTime();
        break;
    }
    return null;
  }
}
