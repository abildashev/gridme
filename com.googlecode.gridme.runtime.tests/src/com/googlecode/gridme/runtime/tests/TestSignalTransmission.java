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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.simstate.ActiveContainer;

/**
 * Unit test for signal sending methods of GConnection.
 * Test topology is constructed and a signal is sent
 * to the elements marked as 'receiver'. All sending
 * is performed without runtime model by flushing 
 * signal buffers.
 * 
 * @see TNamedElement  
 * @see TConnection 
 */
public class TestSignalTransmission
{
  /**
   * Check that only elements marked as "receiver" have
   * got the signal and only one. 
   */
  private boolean checkSend(TNamedElement[] tlist)
  {
    boolean got = true;
    for(TNamedElement ne : tlist)
    {
      ne.deliverSignals();
      if(ne.isReceiver())
      {
        got = got && ne.getAllSignals(TSignal.class).size() == 1;
      }
      else
      {
        got = got && ne.getAllSignals(TSignal.class).isEmpty();
      }
    }
    return got;
  }

  /**
   * C1->(E1^, ^E2)
   * 
   * E1 - sender
   * E2 - receiver
   */
  @Test
  public void testSend1() throws GRuntimeException
  {
    ActiveContainer root = new ActiveContainer("root", new DummyModel());
    
    TNamedElement[] tlist = { new TNamedElement(), new TNamedElement() };

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist[0], tlist[1]);

    root.addChild(c1, tlist[0], tlist[1]);
    
    // mark receiver
    tlist[1].setTname(TNamedElement.RNAME);
    tlist[0].testSend(true);

    assertTrue(checkSend(tlist));
    assertTrue(c1.getAllSignals().isEmpty());
  }

  /**
   * C1->(E1, E2, E3, C1)
   * 
   * E1 - sender
   * E3 - receiver
   */
  @Test
  public void testSend2() throws GRuntimeException
  {
    ActiveContainer root = new ActiveContainer("root", new DummyModel());
    
    TNamedElement[] tlist = { new TNamedElement(), new TNamedElement(),
        new TNamedElement() };

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist[0], tlist[1], tlist[2]);
    c1.connectElementReceive(c1);

    root.addChild(tlist[0], tlist[1], tlist[2], c1);
    
    // mark receiver
    tlist[2].setTname(TNamedElement.RNAME);
    tlist[0].testSend(true);

    assertTrue(checkSend(tlist));
    assertTrue(c1.getAllSignals().isEmpty());
  }

  /**
   * C2->(E4, E5, C1->(E1, E2, E3, C2))
   * 
   * E1 - sender
   * E3,E4 - receiver
   */
  @Test
  public void testSend3() throws GRuntimeException
  {
    ActiveContainer root = new ActiveContainer("root", new DummyModel());
    
    TNamedElement[] tlist = { new TNamedElement(), new TNamedElement(),
        new TNamedElement(), new TNamedElement(), new TNamedElement() };

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist[0], tlist[1], tlist[2]);
    TConnection c2 = new TConnection();
    c1.connectElementSendReceive(c2, tlist[3], tlist[4]);

    root.addChild(c1, c2, tlist[0], tlist[1], tlist[2], tlist[3], tlist[4]);
    
    // mark receiver
    tlist[2].setTname(TNamedElement.RNAME);
    tlist[3].setTname(TNamedElement.RNAME);
    // send
    tlist[0].testSend(true);

    assertTrue(checkSend(tlist));
    assertTrue(c1.getAllSignals().isEmpty());
    assertTrue(c2.getAllSignals().isEmpty());
  }

  /**
   * C1->(E1, E2) C2->(E1, E2)
   * 
   * E1 - sender
   * E2 - receiver
   */
  @Test
  public void testSend4() throws GRuntimeException
  {
    ActiveContainer root = new ActiveContainer("root", new DummyModel());
    
    TNamedElement[] tlist = { new TNamedElement(), new TNamedElement() };

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist[0], tlist[1]);
    TConnection c2 = new TConnection();
    c2.connectElementSendReceive(tlist[0], tlist[1]);

    root.addChild(c1, c2, tlist[0], tlist[1]);
    
    // mark receiver
    tlist[1].setTname(TNamedElement.RNAME);
    // send
    tlist[0].testSend(true);

    assertTrue(checkSend(tlist));
    assertTrue(c1.getAllSignals().isEmpty());
    assertTrue(c2.getAllSignals().isEmpty());
  }

  /**
   * The same test as test4 but with duplicates 
   */
  @Test
  public void testSend5() throws GRuntimeException
  {
    ActiveContainer root = new ActiveContainer("root", new DummyModel());
    
    TNamedElement[] tlist = { new TNamedElement(), new TNamedElement() };

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist[0], tlist[1]);
    TConnection c2 = new TConnection();
    c2.connectElementSendReceive(tlist[0], tlist[1]);

    root.addChild(c1, c2, tlist[0], tlist[1]);
    
    // mark receiver
    tlist[1].setTname(TNamedElement.RNAME);
    // send
    tlist[0].testSend(false);

    // Check that E2 has received 2 signals
    tlist[1].deliverSignals();

    assertTrue(tlist[1].getAllSignals(TSignal.class).size() == 2);
    assertTrue(c1.getAllSignals().isEmpty());
    assertTrue(c2.getAllSignals().isEmpty());
  }
}
