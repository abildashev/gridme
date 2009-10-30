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

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import com.googlecode.gridme.runtime.elements.GTaskFinishedSignal;
import com.googlecode.gridme.runtime.elements.PowerAwareCluster;
import com.googlecode.gridme.runtime.elements.SimpleCluster;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.schedule.GNode;
import com.googlecode.gridme.runtime.schedule.impl.GTaskSignal;
import com.googlecode.gridme.runtime.schedule.impl.local.FirstFitLocal;
import com.googlecode.gridme.simstate.ActiveContainer;
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.RuntimeModel;
import com.googlecode.gridme.simstate.loggers.SimpleErrorLogger;
import com.googlecode.gridme.simstate.loggers.XMLProfileLogger;

/**
 * Tests for simple cluster
 */
public class TestPowerAwareCluster
{

  @Test
  public void test1() throws GRuntimeException, IOException
  {
    PowerAwareCluster cluster = new PowerAwareCluster("testcc");

    cluster.setNodes("10");
    cluster.setWattsIdle("100");
    cluster.setWattsBusy("300");
    cluster.setWattsSleep("20");
    cluster.setWattsWake("1000");
    cluster.setWakeTimeout("1800");
    
    assertTrue(cluster.getSize()  == 10);
    
    cluster.setWattsIdle("100");
    
    assertTrue(cluster.getSize() == 10);
  }
}
