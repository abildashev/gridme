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

public class Test19AE extends SimAE
{
  class AlarmSig1 extends GSignal
  {
    public AlarmSig1(ActiveElement sender, Object... params)
    {
      super(sender, params);
    }
  }

  class AlarmSig2 extends GSignal
  {
    public AlarmSig2(ActiveElement sender, Object... params)
    {
      super(sender, params);
    }
  }

  class TestAlarm1 extends TestAlarm
  {
    public TestAlarm1(long period, long currentTime, ActiveElement elem)
    {
      super(period, currentTime, elem);
    }

    @Override
    public GSignal getSignal()
    {
      return new AlarmSig1(element);
    }
  }

  class TestAlarm2 extends TestAlarm
  {
    public TestAlarm2(long period, long currentTime, ActiveElement elem)
    {
      super(period, currentTime, elem);
    }

    @Override
    public GSignal getSignal()
    {
      return new AlarmSig2(element);
    }
  }

  private int alarm1counter;
  private int alarm2counter;

  public Test19AE(String id)
  {
    super(id);
    setStatemachine(new test19STM(this));
    getStatemachine().addParam(test19STM.PARAM_alarmSignal1, AlarmSig1.class);
    getStatemachine().addParam(test19STM.PARAM_alarmSignal2, AlarmSig2.class);
    allowAllSignals();

    addAlarm(new TestAlarm1(10, 0, this));
    addAlarm(new TestAlarm2(30, 0, this));
  }

  @Override
  public boolean checkState()
  {
    //System.out.println("FINISHED " + getStatemachine().getCurrentStateName());
    
    return alarm2counter * 3 == alarm1counter && alarm1counter == 6;
  }

  @Override
  public Object action(int id) throws Exception
  {
    switch(id)
    {
      case test19STM.ACTION_alarm1:
        //System.out.println("333 " + getModel().getModelTime());
        getAllSignals(AlarmSig1.class);
        alarm1counter++;
        break;
      case test19STM.ACTION_alarm2:
        //System.out.println("222 " + getModel().getModelTime());
        getAllSignals(AlarmSig2.class);
        alarm2counter++;
        break;
      case test19STM.ACTION_isFinished:
        //System.out.println("111 "  + getModel().getModelTime());
        return new Boolean(alarm2counter == 2);
    }
    return null;
  }
}
