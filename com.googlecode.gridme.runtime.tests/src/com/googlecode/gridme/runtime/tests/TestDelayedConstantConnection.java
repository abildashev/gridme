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
package com.googlecode.gridme.runtime.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import com.googlecode.gridme.runtime.elements.DelayedConstantConnection;
import com.googlecode.gridme.simstate.ActiveContainer;
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.GSignal;
import com.googlecode.gridme.simstate.ModelProfileLogger;
import com.googlecode.gridme.simstate.RuntimeModel;
import com.googlecode.gridme.simstate.loggers.SimpleErrorLogger;

public class TestDelayedConstantConnection
{
  protected static final String RUNTIME_LOG = "rtlog.xml";
  protected static final long MODEL_WAIT_MS = 1000;

  class XProfile implements ModelProfileLogger
  {
    private ArrayList<Long> sendIntervals;
    private String target;

    /**
     * @param target
     */
    public XProfile(String target)
    {
      this.target = target;
      sendIntervals = new ArrayList<Long>();
    }

    @Override
    public synchronized void sendSignal(String id, String signal, String target, long time)
    {
      if(target.equals(this.target))
      {
        sendIntervals.add(time);
      }
    }

    @Override
    public synchronized void stateChange(String id,
                            String oldState,
                            String newState,
                            long time)
    {
    }

    @Override
    public void startLog()
    {
    }

    @Override
    public void stopLog()
    {
    }

    public ArrayList<Long> getSendIntervals()
    {
      return sendIntervals;
    }

    @Override
    public synchronized void logMessage(String id, String message, long time)
    {
    }
  }

  @Test
  public void test1() throws Exception
  {
    // top level container element
    ActiveElement root = new ActiveContainer("root");
    // Add two elements and one connection.
    // Receiver only receives...
    TElement receiver = new TElement();
    // Sender sends 8 signals with 1 sec period each
    TSender sender = new TSender(receiver, "sender", 1, 8);
    // Create connection
    // Connection retransmits 1024 bytes (1 signal) each 2 time units so we should
    // have 1 extra signal in buffer each time unit.
    
    DelayedConstantConnection connection = new DelayedConstantConnection("connection");
    connection.setNumberOfBytes("2048");
    connection.setPeriod("4");
    connection.connectElementSendReceive(receiver, sender);
    // Add to the container
    root.addChild(sender, receiver, connection);

    XProfile profile = new XProfile("TestElement");
    //XMLProfileLogger profile = new XMLProfileLogger(new File("xxx"));

    RuntimeModel model = new RuntimeModel(0, 100, Calendar.getInstance(), true,
        null, new SimpleErrorLogger(), profile);
    model.setModel(root);
    
    model.runModel();
    
    model.waitForCompletion(MODEL_WAIT_MS);

    // Check errors
    assertTrue(model.getErrorLogger().getLastError() == null);
    // Check signal buffers
    Collection<GSignal> b1 = receiver.getAllSignals();
    assertTrue(b1.size() == 8);
    // Check that all signals have come in correct order
    Iterator<GSignal> it = b1.iterator();

    //System.out.println(profile.getSendIntervals());

    for(int i = 0; i < 8; i++)
    {
      TNumberedSignal sig = (TNumberedSignal) it.next();
      assertTrue(sig.getIndex() == i);
    }
    
    assertArrayEquals(profile.getSendIntervals().toArray(new Long[8]),
        new Long[] { 4l, 4l, 8l, 8l, 12l, 12l, 16l, 16l});
  }

  @Test
  /**
   * Test nodelay transmission.
   */
  public void test2() throws Exception
  {
    // top level container element
    ActiveElement root = new ActiveContainer("root");
    // Add two elements and one connection.
    // Receiver only receives...
    TElement receiver = new TElement();
    // Sender sends 10 signals with 1 sec period each
    TSender sender = new TSender(receiver, "sender", 1, 10);
    // Create connection
    // Connection retransmits 1024 bytes (1 signal) each 2 time units so we should
    // have 1 extra signal in buffer each time unit.
    DelayedConstantConnection connection = new DelayedConstantConnection("connection");
    connection.setNumberOfBytes("1");
    connection.setPeriod("0");
    connection.connectElementSendReceive(receiver, sender);
    // Add to the container
    root.addChild(sender, receiver, connection);

    XProfile profile = new XProfile("TestElement");

    RuntimeModel model = new RuntimeModel(0, 100, Calendar.getInstance(), true,
        null, new SimpleErrorLogger(), profile);
    model.setModel(root);
    model.runModel();
    model.waitForCompletion(MODEL_WAIT_MS);

    // Check errors
    assertTrue(model.getErrorLogger().getLastError() == null);
    // Check signal buffers
    Collection<GSignal> b1 = receiver.getAllSignals();
    assertTrue(b1.size() == 10);
    // Check that all signals have come in correct order
    Iterator<GSignal> it = b1.iterator();

    //System.out.println(profile.getSendIntervals());

    for(int i = 0; i < 10; i++)
    {
      TNumberedSignal sig = (TNumberedSignal) it.next();
      assertTrue(sig.getIndex() == i);
    }

    assertArrayEquals(profile.getSendIntervals().toArray(new Long[10]),
        new Long[] { 1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l, 9l, 10l });
  }
}
