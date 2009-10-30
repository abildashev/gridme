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
package com.googlecode.gridme.runtime;

import java.util.Map;

import com.googlecode.gridme.runtime.log.GanttLogger;
import com.googlecode.gridme.runtime.log.MetricsLogger;
import com.googlecode.gridme.simstate.ActiveElement;

/**
 * Parent class for the model elements. Each element may have description
 * and a set of parameters. The framework will search for the annotated
 * methods and present a user with the dialog to set necessary parameters.
 * 
 * Model parser expects classes that extend this class have a constructor
 * with parameters:
 * <ul>
 * <li> String id - element id
 * </ul>  
 */
public abstract class ModelElement extends ActiveElement
{
  private MetricsLogger metricsLogger;
  private GanttLogger ganttLogger;

  /**
   * Default constructor
   */
  public ModelElement(String id)
  {
    super(id);
  }

  /**
   * @return an instance of metrics logger. <code>null</code>
   * if the logger is not available. 
   */
  public final MetricsLogger getMetricsLogger()
  {
    return metricsLogger;
  }

  
  /**
   * @return an instance of cluster gantt chart logger. <code>null</code>
   * if the logger is not available. 
   */
  public final GanttLogger getGanttLogger()
  {
    return ganttLogger;
  }
  
  /**
   * Sets the metrics logger. 
   * @param metricsLogger logger instance.
   */
  public final void setMetricsLogger(MetricsLogger metricsLogger)
  {
    this.metricsLogger = metricsLogger;
  }

  /**
   * Sets the gantt chart logger. 
   * @param metricsLogger logger instance.
   */
  public final void setGanttLogger(GanttLogger logger)
  {
    this.ganttLogger = logger;
  }
  
  /**
   * A helper method to parse a comma-separated list of
   * tags (tag=value) pairs.
   * @param tags the map to save tags to
   * @param tstring tags string, may be null.
   */
  protected final void parseTags(Map<String, String> tags, String tstring)
  {
    if(tstring != null)
    {
      String[] tlist = tstring.split(",");
      for(String tval : tlist)
      {
        String[] tvlist = tval.split("=");
        if(tvlist.length == 2)
        {
          tags.put(tvlist[0], tvlist[1]);
        }
      }
    }
  }
}
