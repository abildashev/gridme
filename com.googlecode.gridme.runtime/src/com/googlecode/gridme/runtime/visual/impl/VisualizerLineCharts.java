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

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.VisualizerImplementation;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.log.AnalyserResult;
import com.googlecode.gridme.runtime.log.InTimeValue;
import com.googlecode.gridme.runtime.visual.LogEntry;
import com.googlecode.gridme.runtime.visual.VChart;
import com.googlecode.gridme.simstate.ModelProgressMonitor;

@VisualizerImplementation("Builds line charts")
public class VisualizerLineCharts extends GroupedChartsVisualizer
{
  /**
   * Default constructor.
   *
   * @throws GRuntimeException if some of the required parameters are not set.
   */
  public VisualizerLineCharts(String name)
      throws GRuntimeException
  {
    super(name);
  }

  @Override
  public void execute(String resultPath, long startTime, long stopTime, List<VChart> charts,
      List<LogEntry> logList, ModelProgressMonitor monitor) throws Exception
  {
    XYSeriesCollection seriesCollection = new XYSeriesCollection();
    Calendar startDate = null;
    int total = logList.size() * charts.size();

    monitor.begin(total);

    for(LogEntry entry : logList)
    {
      if(startDate == null)
      {
        startDate = entry.getLog().getCalendarStartTime();
      }
      for(VChart chart : charts)
      {
        XYSeries jchart = new XYSeries(entry.getName() + ":"
            + chart.getElementSpec() + ":"
            + entry.getLog().getMetricsDescription(chart.getMetricSpec()),
            false);

        AnalyserResult values = entry.getLog().getValueChange(
            chart.getMetricSpec(), Arrays.asList(chart.getElementSpec()),
            getImgWidth(), startTime, stopTime);

        for(InTimeValue val : values.getValues())
        {
          jchart.add(val.getTime(), val.getValue());
        }

        seriesCollection.addSeries(jchart);
        monitor.progress(1);
      }
    }

    assert (startDate != null);

    JFreeChart result = ChartFactory.createXYStepChart(name + " ["
        + DateFormat.getDateTimeInstance().format(startDate.getTime()) + " + "
        + RuntimeUtils.formatTime(startTime) + ", "
        + RuntimeUtils.formatTime(stopTime) + "]", "time", "metrics value "
        + getMetricsTypeName(), seriesCollection, PlotOrientation.VERTICAL,
        true, true, false);

    XYPlot plot = result.getXYPlot();

    NumberAxis myRange = new NumberAxis();
    NumberAxis myDomain = new NumberAxis();

    myDomain.setNumberFormatOverride(getCalendarTimeFormat(startDate));
    myRange.setNumberFormatOverride(getLabelFormat());

    plot.setRangeAxis(myRange);
    plot.setDomainAxis(myDomain);

    plot.setDomainGridlinesVisible(true);
    plot.getDomainAxis().setRange(startTime, stopTime);

    ChartUtilities.writeChartAsPNG(
        new FileOutputStream(new File(resultPath + ".png")), result, getImgWidth(),
        getImgHeight());

    monitor.done();
  }
}
