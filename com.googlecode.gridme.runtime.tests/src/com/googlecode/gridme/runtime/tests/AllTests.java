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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests for the runtime components.
 */
@RunWith(Suite.class)
@SuiteClasses( { TestTopology.class, TestSignalTransmission.class,
    TestFastLogger.class, TestFileBasedLogAnalyser.class, TestSchedule.class,
    TestSimpleCluster.class, TestDelayedConstantConnection.class,
    TestSimpleBroker.class, TestWorkloadFlow.class, TestMonitor.class,
    TestVisual.class, TestGanttLogger.class,
    TestSWFWorkload.class, TestEnergyUtils.class, TestPowerAwareCluster.class })
public class AllTests
{
}
