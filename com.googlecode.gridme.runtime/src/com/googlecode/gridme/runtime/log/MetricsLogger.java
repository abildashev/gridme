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
package com.googlecode.gridme.runtime.log;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;

/**
 * This interface provides methods to log 
 * simulation data.  
 */
public interface MetricsLogger
{
  /**
   * Logs a metric. Normally metrics must be logged with an
   * increasing time value.
   * 
   * @param metric simulation data
   * 
   * @throws GRuntimeException if an error occurs while logging 
   */
  public void logMetric(Metric metric) throws GRuntimeException;
  
  /**
   * Logs element static properties
   */
  public void logStaticProperty(StaticProperty prop) throws GRuntimeException;
  
  /**
   * Logs manifest data
   */
  public void setManifest(LogManifest man) throws GRuntimeException;
}
