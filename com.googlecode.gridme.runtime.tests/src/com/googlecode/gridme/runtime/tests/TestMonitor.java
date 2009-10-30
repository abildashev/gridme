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

import org.junit.Test;

import com.googlecode.gridme.runtime.opt.ConsoleProgressMonitor;

public class TestMonitor
{
  @Test
  public void testConsoleMonitor1()
  {
    ConsoleProgressMonitor mon = new ConsoleProgressMonitor();
    mon.begin(100);
    for(int i = 0; i < 100; i++)
    {
      mon.progress(1);
    }
    System.out.print(" * ");
    mon.done();
  }
  
  @Test
  public void testConsoleMonitor2()
  {
    ConsoleProgressMonitor mon = new ConsoleProgressMonitor();
    mon.begin(200);
    for(int i = 0; i < 200; i++)
    {
      mon.progress(1);
    }
    System.out.print(" * ");
    mon.done();
  }
  
  @Test
  public void testConsoleMonitor3()
  {
    ConsoleProgressMonitor mon = new ConsoleProgressMonitor();
    mon.begin(20);
    for(int i = 0; i < 2; i++)
    {
      mon.progress(10);
    }
    System.out.print(" * ");
    mon.done();
  }

  @Test(expected=IllegalStateException.class)
  public void testConsoleMonitor4()
  {
    ConsoleProgressMonitor mon = new ConsoleProgressMonitor();
    mon.begin(2);
    for(int i = 0; i < 4; i++)
    {
      mon.progress(1);
    }
  }
}
