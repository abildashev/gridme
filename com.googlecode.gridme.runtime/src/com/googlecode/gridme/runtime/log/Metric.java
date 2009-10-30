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


/**
 * This class define a metric that can be logged
 * by a model element while running. Metric represents
 * a state of the element at the given time moment.
 * 
 * It is recommended that you extend this class to define
 * your new metric. This will exclude the risk of duplicated
 * metric names. Try to use short names if possible. This will make a log file 
 * a little bit smaller.
 */
public class Metric
{
  private final String elementId;
  private final String metricName;
  private final InTimeValue value;
  private final String comment;

  public Metric(String elementId, String metricName, long time, long value)
  {
    this(elementId, metricName, time, value, null);
  }
  
  public Metric(String elementId, String metricName, long time, long value, String comment)
  {
    this.elementId = elementId;
    this.metricName = metricName;
    this.value = new InTimeValue(time, value);
    this.comment = comment;
  }
  
  public String getElementId()
  {
    return elementId;
  }

  public String getMetricName()
  {
    return metricName;
  }

  public long getTime()
  {
    return value.getTime();
  }

  public long getValue()
  {
    return value.getValue();
  }

  public String getComment()
  {
    return comment;
  }
}
