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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.googlecode.gridme.runtime.elements.SimpleBroker;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.schedule.GCluster;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.impl.meta.FirstFitGlobal;
import com.googlecode.gridme.simstate.ActiveContainer;

/**
 * Unit tests for SimpleBroker
 */
public class TestSimpleBroker
{
  public static boolean checkResult(Collection<? extends GCluster> tlist,
                                    Collection<GCluster> result)
  {
    return new HashSet<GCluster>(tlist).containsAll(result)
        && result.size() == tlist.size();
  }

  @Test
  public void test1() throws GRuntimeException
  {
    SimpleBroker broker = new SimpleBroker("broker1");
    broker.setScheduler(new FirstFitGlobal());
    assertTrue(broker.getClusters().isEmpty());
  }

  @Test
  public void test2() throws GRuntimeException
  {
    TSimpleBroker broker = new TSimpleBroker();

    // Create a network and check that all clusters were found
    List<TECluster> tlist = Arrays.asList(new TECluster(10), new TECluster(1),
        new TECluster(5), new TECluster(4));

    // Topology: c3->(e3, c2->(c1->(e0, e1, B), e2, e0), E)

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist.get(0), tlist.get(1), broker);
    TConnection c2 = new TConnection();
    c2.connectElementSendReceive(c1, tlist.get(2), tlist.get(0));
    TConnection c3 = new TConnection();
    c3.connectElementSendReceive(tlist.get(3), c2, new TElement());

    broker.testDiscover();

    // Check size and items the order is not specified
    assertTrue(broker.getClusters().size() == 4);
    assertTrue(checkResult(tlist, broker.getClusters()));
  }

  @Test
  /**
   * The same topology as in the previous test, but with 2 brokers
   */
  public void test3() throws GRuntimeException
  {
    TSimpleBroker broker1 = new TSimpleBroker();
    TSimpleBroker broker2 = new TSimpleBroker();

    // Create a network and check that all clusters were found
    List<TECluster> tlist = Arrays.asList(new TECluster(10), new TECluster(1),
        new TECluster(5), new TECluster(4));

    // Topology: c3->(e3, c2->(c1->(e0, e1, B1), e2, e0), E, B2)

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist.get(0), tlist.get(1), broker1);
    TConnection c2 = new TConnection();
    c2.connectElementSendReceive(c1, tlist.get(2), tlist.get(0));
    TConnection c3 = new TConnection();
    c3.connectElementSendReceive(tlist.get(3), c2, new TElement(), broker2);

    broker1.testDiscover();
    broker2.testDiscover();

    // Check size and items the order is not specified
    assertTrue(broker1.getClusters().size() == 4);
    assertTrue(checkResult(tlist, broker1.getClusters()));

    assertTrue(broker2.getClusters().size() == 4);
    assertTrue(checkResult(tlist, broker2.getClusters()));
  }

  @Test
  /**
   * Test task submission
   */
  public void test4() throws GRuntimeException
  {
    ActiveContainer root = new ActiveContainer("root", new DummyModel());
    
    TSimpleBroker broker = new TSimpleBroker();
    broker.setScheduler(new FirstFitGlobal());
    
    TECluster clu1 = new TECluster(10);
    List<TECluster> tlist = Arrays.asList(clu1);

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist.get(0), broker);

    root.addChild(c1, broker, clu1);
    
    broker.testDiscover();
    broker.sendSignal(new GTaskSignal("t1", 1, 10, 11, 100, 10, null, null),
        null);
    broker.deliverSignals();
    broker.testSched();
    c1.doSend(true);
    clu1.deliverSignals();
    
    assertTrue(broker.getAllSignals().isEmpty());
    assertTrue(clu1.getAllSignals().size() == 1);
  }
}
