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

import java.io.File;

import org.junit.Test;

import com.googlecode.gridme.runtime.schedule.workload.SWFWorkload;

/**
 * Tests for visualizer.
 */
public class TestSWFWorkload
{
  private static File swf = new File("data/testSWF3.zip");

  @Test
  public void test1() throws Exception
  {
    SWFWorkload workload = SWFWorkload.load(swf, null);
    workload.toFile(new File("generatedSWF.zip"));
  }
}
