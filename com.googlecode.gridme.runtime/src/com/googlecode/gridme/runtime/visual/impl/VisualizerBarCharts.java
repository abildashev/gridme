/*******************************************************************************
l * Copyright (c) 2009 Dmitry Grushin <dgrushin@gmail.com>.
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
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.VisualizerImplementation;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.log.AnalyserResult;
import com.googlecode.gridme.runtime.visual.LogEntry;
import com.googlecode.gridme.runtime.visual.VChart;
import com.googlecode.gridme.simstate.ModelProgressMonitor;

@VisualizerImplementation("Builds line charts")
public class VisualizerBarCharts extends GroupedChartsVisualizer
{
  private enum Operation {
    AVERAGE, MAX, AGGREGATE
  };

  private static final String OP_AVG = "average";
  private static final String OP_MAX = "max";
  private static final String OP_AGG = "aggregate";

  private Operation operation;

  @Parameter(description = "How to compute a value: " + OP_AVG + ", "
      + OP_MAX + ", " + OP_AGG, required = false, hasParams = false, category = 0)
  public void setOperation(String value) throws GRuntimeException
  {
    if(OP_MAX.equals(value))
    {
      operation = Operation.MAX;
    }
    else if(OP_AGG.equals(value))
    {
      operation = Operation.AGGREGATE;
    }
    else if(OP_AVG.equals(value))
    {
    }
    else
    {
      throw new GRuntimeException("Invalid operation");
    }
  }

  /**
   * Default constructor.
   *
   * @throws GRuntimeException if some of the required parameters are not set.
   */
  public VisualizerBarCharts(String name) throws GRuntimeException
  {
    super(name);
    operation = Operation.AVERAGE;
  }

  @SuppressWarnings("serial")
  @Override
  public void execute(String resultPath, long startTime, long stopTime,
      List<VChart> charts, List<LogEntry> logList, ModelProgressMonitor monitor)
      throws Exception
  {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    int total = logList.size() * charts.size();
    PrintWriter txtOut = new PrintWriter(new File(resultPath + ".txt"));
    NumberFormat fmt = new DecimalFormat();
    fmt.setMaximumFractionDigits(3);
    monitor.begin(total);

    for(LogEntry entry : logList)
    {
      for(VChart chart : charts)
      {
        AnalyserResult values = entry.getLog().getValueChange(
            chart.getMetricSpec(), Arrays.asList(chart.getElementSpec()),
            getImgWidth(), startTime, stopTime);

        double value = 0;

        switch(operation)
        {
          case AVERAGE:
            value = values.getArithmeticMean();
            break;

          case MAX:
            value = values.getPeak().getValue();
            break;

          case AGGREGATE:
            value = values.getAggregate();
            break;

          default:
            assert (false);
        }

        dataset.addValue(value, chart.getElementSpec() + ":"
            + entry.getLog().getMetricsDescription(chart.getMetricSpec()),
            entry.getName());

        txtOut.println(fmt.format(value));

        monitor.progress(1);
      }
      txtOut.println();
    }

    JFreeChart barChart = ChartFactory.createBarChart(name + " ["
        + RuntimeUtils.formatTime(startTime) + ", "
        + RuntimeUtils.formatTime(stopTime) + "]", // chart title
        "", // domain axis label
        "metrics value " + getMetricsTypeName(), // range axis label
        dataset, // data
        PlotOrientation.VERTICAL, // orientation
        true, // include legend
        true, // tooltips?
        false // URLs?
        );

    CategoryPlot plot = (CategoryPlot) barChart.getPlot();
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setMaximumBarWidth(0.05);
    renderer.setItemMargin(0.0);

    NumberAxis myRange = new NumberAxis();
    myRange.setNumberFormatOverride(getLabelFormat());
    plot.setRangeAxis(myRange);

    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
        "{2}", getLabelFormat()));
    renderer.setBaseItemLabelsVisible(true);

    ChartUtilities.writeChartAsPNG(new FileOutputStream(new File(resultPath
        + ".png")), barChart, getImgWidth(), getImgHeight());

    monitor.done();
    txtOut.close();
  }
}
