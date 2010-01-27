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

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;

/**
 * This interface provides a common way to
 * analyze simulation log data. 
 */
public interface LogAnalyser
{
  /**
   * Returns log manifest.
   */
  public LogManifest getManifest() throws GRuntimeException;

  /**
   * Returns a list of available elements in the log.
   */
  public List<String> getElements() throws GRuntimeException;

  /**
   * Returns a list of available metrics corresponding for the
   * given element id.
   */
  public List<String> getMetrics(String elementId) throws GRuntimeException;

  /**
   * Returns a list of all available metrics
   */
  public List<String> getMetrics() throws GRuntimeException;
  
  /**
   * Returns a list of properties for the given element id.
   */
  public List<StaticProperty> getProperties(String elementId) throws GRuntimeException;

  /**
   * Returns metrics description. 
   * 
   * @param metricsName name of the metrics
   * @throws GRuntimeException if an IOError occurs
   */
  public String getMetricsDescription(String metricsName)
      throws GRuntimeException;

  /**
   * @param metricsName the name of the metrics
   * 
   * @param elements list of the model elements. If there are more then 1 element in
   * the list the average metrics value will be produced.
   * 
   * @param maxLength the maximum length of the result list. If there are more
   * items than maxLength value, the list should be divided into maxLength intervals
   * and the average value must be taken on each interval. <p/> 
   * If the value is 0 than the length is not limited. 
   *  
   * @param start start time if -1 - ignore start time
   * @param stop stop time if -1 - ignore stop time
   *  
   * @throws GRuntimeException if an error occurs
   */
  public AnalyserResult getValueChange(String metricsName,
      List<String> elements, int maxLength, long start, long stop)
      throws GRuntimeException;

  /**
   * Metrics value is computed as an aggregate value for all input elements.
   */
  public AnalyserResult getAggregateValueChange(String metricsName,
      List<String> elements, int maxLength, long start, long stop)
      throws GRuntimeException;

  public Calendar getCalendarStartTime() throws GRuntimeException;
  
  
  public Map<String,String> getParameterValues() throws GRuntimeException;
}
