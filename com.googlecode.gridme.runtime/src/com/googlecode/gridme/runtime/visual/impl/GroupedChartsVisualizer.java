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
package com.googlecode.gridme.runtime.visual.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Map;

import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.visual.Visualizer;

public abstract class GroupedChartsVisualizer implements Visualizer
{
  public final static int DEFAULT_CHART_WIDTH = 1024;
  public final static int DEFAULT_CHART_HEIGHT = 768;

  private static final String M_NONE = "none";
  private static final String M_TIME = "time";

  protected enum MetricType {
    NONE, TIME
  };

  private MetricType mtype;
  protected final String name;
  private int imgWidth = DEFAULT_CHART_WIDTH;
  private int imgHeight = DEFAULT_CHART_HEIGHT;

  @Parameter(description = "Image width in pixels", required = false, hasParams = false, category = 0)
  public void setWidth(String value)
  {
    imgWidth = Integer.parseInt(value);
  }

  @Parameter(description = "Image height in pixels", required = false, hasParams = false, category = 0)
  public void setHeight(String value)
  {
    imgHeight = Integer.parseInt(value);
  }

  @Parameter(description = "Type of the metric: " + M_TIME + ", " + M_NONE, required = false, hasParams = false, category = 0)
  public void setMetric(String value) throws GRuntimeException
  {
    if(M_TIME.equals(value))
    {
      mtype = MetricType.TIME;
    }
    else if(M_NONE.equals(value))
    {
    }
    else
    {
      throw new GRuntimeException("Invalid metric type");
    }
  }

  /**
   * Default constructor.
   *
   * @throws GRuntimeException if some of the required parameters are not set.
   */
  public GroupedChartsVisualizer(String name) throws GRuntimeException
  {
    this.name = name;
    mtype = MetricType.NONE;
  }

  public int getImgWidth()
  {
    return imgWidth;
  }

  public int getImgHeight()
  {
    return imgHeight;
  }

  public MetricType getMetricType()
  {
    return mtype;
  }

  @SuppressWarnings("serial")
  protected NumberFormat getLabelFormat()
  {
    NumberFormat format = null;

    if(getMetricType().equals(MetricType.TIME))
    {
      format = new NumberFormat()
      {
        @Override
        public StringBuffer format(double number, StringBuffer toAppendTo,
            FieldPosition pos)
        {
          return format((long) number, toAppendTo, pos);
        }

        @Override
        public StringBuffer format(long number, StringBuffer toAppendTo,
            FieldPosition pos)
        {
          toAppendTo.append(RuntimeUtils.formatTime((int) number));
          return toAppendTo;
        }

        @Override
        public Number parse(String source, ParsePosition parsePosition)
        {
          throw new UnsupportedOperationException();
        }
      };
    }
    else
    {
      format = new DecimalFormat();
    }

    return format;
  }

  @SuppressWarnings("serial")
  public static NumberFormat getCalendarTimeFormat(final Calendar start)
  {
    return new NumberFormat()
    {
      private DateFormat ft = DateFormat.getDateTimeInstance(DateFormat.SHORT,
          DateFormat.SHORT);

      @Override
      public StringBuffer format(double number, StringBuffer toAppendTo,
          FieldPosition pos)
      {
        return format((long) number, toAppendTo, pos);
      }

      @Override
      public StringBuffer format(long number, StringBuffer toAppendTo,
          FieldPosition pos)
      {
        Calendar now = (Calendar) start.clone();
        now.setTimeInMillis(start.getTimeInMillis() + number * 1000);
        toAppendTo.append(ft.format(now.getTime()));
        return toAppendTo;
      }

      @Override
      public Number parse(String source, ParsePosition parsePosition)
      {
        throw new UnsupportedOperationException();
      }
    };
  }

  protected String getMetricsTypeName()
  {
    switch(getMetricType())
    {
      case TIME:
        return "(time)";
    }
    return "";
  }
}
