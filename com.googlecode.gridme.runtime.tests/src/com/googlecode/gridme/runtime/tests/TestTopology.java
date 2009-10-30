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

import com.googlecode.gridme.runtime.GElement;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;

/**
 * Unit test for GConnection and GElement topology 
 */
public class TestTopology
{
  /**
   * Checks if two sets of elements are equal. 
   */
  public static boolean checkResult(Collection<? extends GElement> tlist,
                                    Collection<GElement> result)
  {
    return new HashSet<GElement>(tlist).containsAll(result)
        && result.size() == tlist.size();
  }

  /**
   * C1->(E1, E2) 
   */
  @Test
  public void testGetAvailableElements1() throws GRuntimeException
  {
    List<? extends GElement> tlist = Arrays.asList(new TElement(),
        new TElement());

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist.get(0), tlist.get(1));

    assertTrue(checkResult(tlist, c1.getAvailableElements()));
  }

  /**
   * C2->C1->(E1,E2) 
   */
  @Test
  public void testGetAvailableElements2() throws GRuntimeException
  {
    List<? extends GElement> tlist = Arrays.asList(new TElement(),
        new TElement());

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist.get(0), tlist.get(1));
    TConnection c2 = new TConnection();
    c2.connectElementSendReceive(c1);

    assertTrue(checkResult(tlist, c2.getAvailableElements()));
  }

  /**
   * C2->(E3, C1->(E1, E2)) 
   */
  @Test
  public void testGetAvailableElements3() throws GRuntimeException
  {
    List<? extends GElement> tlist = Arrays.asList(new TElement(),
        new TElement(), new TElement());

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist.get(0), tlist.get(1));
    TConnection c2 = new TConnection();
    c2.connectElementSendReceive(c1, tlist.get(2));

    assertTrue(checkResult(tlist, c2.getAvailableElements()));
  }

  /**
   * C2->(E3, C1->(E1, E2, C2)) 
   */
  @Test
  public void testGetAvailableElements4() throws GRuntimeException
  {
    List<? extends GElement> tlist = Arrays.asList(new TElement(),
        new TElement(), new TElement());

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist.get(0), tlist.get(1));
    TConnection c2 = new TConnection();
    c2.connectElementSendReceive(c1, tlist.get(2));

    assertTrue(checkResult(tlist, c2.getAvailableElements()));
  }

  /**
   * C2->(E3, E1, C1->(E1, E2, C2)) 
   */
  @Test
  public void testGetAvailableElements5() throws GRuntimeException
  {
    List<? extends GElement> tlist = Arrays.asList(new TElement(),
        new TElement(), new TElement());

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist.get(0), tlist.get(1));
    TConnection c2 = new TConnection();
    c2.connectElementSendReceive(c1, tlist.get(2), tlist.get(0));

    assertTrue(checkResult(tlist, c2.getAvailableElements()));
  }

  /**
   * Internal loop
   * C3->(E4, C2->(E3, E1, C1->(E1, E2, C2))) 
   */
  @Test
  public void testGetAvailableElements6() throws GRuntimeException
  {
    List<? extends GElement> tlist = Arrays.asList(new TElement(),
        new TElement(), new TElement(), new TElement());

    TConnection c1 = new TConnection();
    c1.connectElementSendReceive(tlist.get(0), tlist.get(1));
    TConnection c2 = new TConnection();
    c2.connectElementSendReceive(c1, tlist.get(2), tlist.get(0));
    TConnection c3 = new TConnection();
    c3.connectElementSendReceive(tlist.get(3), c2);

    assertTrue(checkResult(tlist, c3.getAvailableElements()));
    assertTrue(checkResult(tlist, ((TElement) tlist.get(3))
        .getAvailableElements()));
  }
}
