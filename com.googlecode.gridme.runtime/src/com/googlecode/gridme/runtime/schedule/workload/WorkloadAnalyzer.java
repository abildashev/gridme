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
package com.googlecode.gridme.runtime.schedule.workload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.PriorityQueue;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.googlecode.gridme.runtime.Branding;
import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.html.MLFoot;
import com.googlecode.gridme.runtime.opt.ConsoleProgressMonitor;
import com.googlecode.gridme.runtime.visual.impl.GroupedChartsVisualizer;
import com.googlecode.gridme.simstate.ModelProgressMonitor;

/**
 * Parses workload file and generates an HTML report
 * containing various statistics about the workload.
 */
public class WorkloadAnalyzer
{
  public static final int BIG_CHART_WIDTH = 970;
  public static final int BIG_CHART_HEIGHT = 600;
  public static final int SMALL_CHART_WIDTH = 320;
  public static final int SMALL_CHART_HEIGHT = 320;
  private File reportFolder;

  /**
   * Each point can increase or decrease total width.
   */
  class PointWeight implements Comparable<PointWeight>
  {
    private final long time;
    private final int height;

    public PointWeight(long time, int height)
    {
      this.time = time;
      this.height = height;
    }

    @Override
    public int compareTo(PointWeight pa)
    {
      return (int) (time - pa.time);
    }

    public long getTime()
    {
      return time;
    }

    public int getHeight()
    {
      return height;
    }

    @Override
    public String toString()
    {
      return "[" + time + "," + height + "]";
    }
  }

  class JobProps
  {
    private int[] count;
    private int[] sizes;
    private String[] labels;

    public JobProps(int[] values, String[] labels)
    {
      if(labels.length != values.length + 1)
      {
        throw new IllegalArgumentException(
            "Labels size must be equal to values size + 1");
      }
      sizes = values;
      count = new int[values.length + 1];
      this.labels = labels;
    }

    public void addValue(int size)
    {
      int i;
      // the number of 1-CPU tasks is usually greater
      for(i = 0; i < sizes.length; i++)
      {
        if(size <= sizes[i])
        {
          break;
        }
      }
      count[i]++;
    }

    public PieDataset getChartData()
    {
      DefaultPieDataset result = new DefaultPieDataset();

      for(int i = 0; i < sizes.length; i++)
      {
        if(count[i] > 0)
        {
          result.setValue("" + labels[i], count[i]);
        }
      }

      if(count[sizes.length] > 0)
      {
        result.setValue(labels[sizes.length], count[sizes.length]);
      }

      return result;
    }
  }

  private ModelProgressMonitor monitor;
  // Workload file
  private File wfile;

  // A buffer for processing total width
  private PriorityQueue<PointWeight> endPoints;

  // Current width counter
  private int currentWidth;
  // Maximum task length
  private long topLength;
  // Minimum task length
  private long bottomLength = Long.MAX_VALUE;
  // Maximum task size
  private long topSize = 1;
  // Minimum task size
  private long bottomSize = Long.MAX_VALUE;
  // Total number of tasks
  private int totalCount;
  // Last task start time
  private long lastStartTime;
  // Last finish time
  private long lastFinishTime;
  // Total square
  private long totalSquare;

  // Width chart
  private XYSeries widthChartData;
  private ReduceSequence widthDataHndl;
  // Job sizes
  private JobProps jsizes;
  // Job runtime
  private JobProps jlen;
  // Size length distribution
  private XYSeries sizeTime;
  // Number of jobs
  private XYSeries countChartData;
  private XYSeries cpuCountChartData;

  /**
   * @param file workload file path
   * @param systemWidth total width of all clusters
   */
  public WorkloadAnalyzer(File file)
  {
    endPoints = new PriorityQueue<PointWeight>();
    this.wfile = file;
  }

  /**
   * Adds a new task to the chart.
   */
  private void addTask(long start, int length, int tsize)
  {
    totalCount++;
    lastStartTime = start;
    if(length > topLength)
    {
      topLength = length;
    }
    if(length < bottomLength)
    {
      bottomLength = length;
    }
    if(tsize > 1 && tsize > topSize)
    {
      topSize = tsize;
    }
    if(tsize > 1 && tsize < bottomSize)
    {
      bottomSize = tsize;
    }
    jsizes.addValue(tsize);
    jlen.addValue(length);
    if(tsize > 0 && length > 0)
    {
      sizeTime.add(tsize, length);
    }

    PointWeight p = null;
    while((p = endPoints.peek()) != null && p.getTime() <= start)
    {
      endPoints.poll();
      currentWidth += p.getHeight();
      addWidthValue(p.getTime());
    }
    endPoints.add(new PointWeight(start, tsize));
    endPoints.add(new PointWeight(start + length, -1 * tsize));

    // Add end point to the list
    p = endPoints.poll();
    currentWidth += p.getHeight();
    addWidthValue(p.getTime());

    monitor.progress(1);
  }

  private void addWidthValue(final long time)
  {
    widthDataHndl.add(time, currentWidth);
  }

  /**
   * Tells the analyzer that there are no more tasks to process.
   */
  private void finish()
  {
    PointWeight pt;
    while((pt = endPoints.poll()) != null)
    {
      currentWidth += pt.getHeight();
      addWidthValue(pt.getTime());
      if(pt.getTime() > lastFinishTime)
      {
        lastFinishTime = pt.getTime();
      }
    }
    widthDataHndl.finish();
  }

  /**
   *
   */
  public void analyze(ModelProgressMonitor monitor) throws Exception
  {
    this.monitor = monitor;
    Calendar startDate = Calendar.getInstance();

    // Prepare report folder and bad tasks printer
    reportFolder = new File(wfile + ".report");
    if(!reportFolder.exists() && !reportFolder.mkdirs())
    {
      throw new FileNotFoundException(wfile + ".report");
    }
    File efile = new File(reportFolder, "errors.html");
    WorkloadErrorHandler badTasks = new HtmlWorkloadErrorHandler(efile);

    SWFParser parser = new SWFParser(wfile, badTasks);

    // Get workload length
    int workloadLength = parser.fastParse();

    monitor.begin(workloadLength);

    // Handler for aggregate width
    widthDataHndl = new ReduceSequence(workloadLength / BIG_CHART_WIDTH)
    {
      @Override
      public void handleValue(long point, float value)
      {
        widthChartData.add(point, value);
      }
    };
    currentWidth = 0;
    widthChartData = new XYSeries("Total width of tasks", false, true);

    // Job sizes chart data
    jsizes = new JobProps(new int[] { 1, 4, 16, 64, 128, 256, 1024 },
        new String[] { "1", "2..4", "5..16", "17..64", "65..128", "129..256",
            "257..1024", ">1024" });

    // Job length chart data
    jlen = new JobProps(new int[] { 1, 10, 60, 600, 3600, 36000, 360000 },
        new String[] { "1s", "1s..10s", "10s..1m", "1m..10m", "10m..1h",
            "1h..10h", "10h..4d", ">4d" });

    // Size and time series
    sizeTime = new XYSeries("size and time", false, true);

    countChartData = new XYSeries("Jobs per hour", false, true);
    cpuCountChartData = new XYSeries("Processors per hour", false, true);

    // Process the log
    TaskInfoSWF task = null;
    long time = 0;
    int taskCount = 0, cpuCount = 0;
    while(true)
    {
      task = parser.nextTask();
      if(task == null)
      {
        break;
      }
      addTask(task.getStartTime(), (int) task.getRealExecutionTime(), task
          .getNodesMin());
      taskCount++;
      totalSquare += task.getRealExecutionTime() * task.getNodesMin();
      cpuCount += task.getNodesMin();
      if(task.getStartTime() - time >= 3600)
      {
        time = task.getStartTime();
        countChartData.add(new XYDataItem(time, taskCount));
        cpuCountChartData.add(new XYDataItem(time, cpuCount));
        taskCount = cpuCount = 0;
      }
    }
    parser.finish();
    finish();

    // Build report
    PrintWriter html = new PrintWriter(new FileOutputStream(new File(
        reportFolder, "index.html")));
    html.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" "
        + "\"http://www.w3.org/TR/html4/strict.dtd\">");
    html.println("<html>");
    html.println("<meta http-equiv=\"Content-Type\" "
        + "content=\"text/html; charset=ISO-8859-1\"/>");
    html.println("<title>" + "GridMe workload analysis report" + "</title>");
    html.println("<body>");
    html.println("<h1>Workload analysis results</h1>");

    html.println("<table>");
    html.println("<tr><td>Total number of tasks:</td><td>" + totalCount
        + "</td></tr>");
    html.println("<tr><td>Maximum task length:</td><td>"
        + RuntimeUtils.formatTime(topLength) + "</td></tr>");
    html.println("<tr><td>Minimum task length:</td><td>"
        + RuntimeUtils.formatTime(bottomLength) + "</td></tr>");

    if(topSize > 1)
    {
      html.println("<tr><td>Maximum task size:</td><td>" + topSize
          + "</td></tr>");
      html.println("<tr><td>Minimum task size:</td><td>" + bottomSize
          + "</td></tr>");
    }

    html.println("<tr><td>Last task start time:</td><td>"
        + RuntimeUtils.formatTime(lastStartTime) + "</td></tr>");
    html.println("<tr><td>Last finish time:</td><td>"
        + RuntimeUtils.formatTime(lastFinishTime) + "</td></tr>");
    html.println("<tr><td>Total square:</td><td>"
        + totalSquare + "</td></tr>");
    html.println("</table>");

    // Total width chart
    XYSeriesCollection seriesCollection = new XYSeriesCollection();
    // Main chart
    seriesCollection.addSeries(widthChartData);

    JFreeChart jchart = ChartFactory.createXYStepChart("Aggregate width",
        "time", "width", seriesCollection, PlotOrientation.VERTICAL, true,
        false, false);

    {
      XYPlot plot = jchart.getXYPlot();
      plot.setDomainGridlinesVisible(true);
      NumberAxis myDomain = new NumberAxis();
      myDomain.setNumberFormatOverride(GroupedChartsVisualizer
          .getCalendarTimeFormat(startDate));
      plot.setDomainAxis(myDomain);
    }

    ChartUtilities.writeChartAsPNG(new FileOutputStream(new File(reportFolder,
        "width.png")), jchart, BIG_CHART_WIDTH, BIG_CHART_HEIGHT);

    html.println("<img src='width.png'/>");
    html.println("<p/>");

    // Number of tasks and processors chart
    XYSeriesCollection countCollection = new XYSeriesCollection();
    countCollection.addSeries(countChartData);
    countCollection.addSeries(cpuCountChartData);
    jchart = ChartFactory.createXYStepChart("Job count", "time", "count",
        countCollection, PlotOrientation.VERTICAL, true, false, false);
    {
      XYPlot plot = jchart.getXYPlot();
      plot.setDomainGridlinesVisible(true);
      NumberAxis myDomain = new NumberAxis();
      myDomain.setNumberFormatOverride(GroupedChartsVisualizer
          .getCalendarTimeFormat(startDate));
      plot.setDomainAxis(myDomain);
    }

    ChartUtilities.writeChartAsPNG(new FileOutputStream(new File(reportFolder,
        "count.png")), jchart, BIG_CHART_WIDTH, BIG_CHART_HEIGHT);

    html.println("<img src='count.png'/>");
    html.println("<p/>");

    html.println("<table>");

    // Job size distribution chart
    jchart = ChartFactory.createPieChart("Job size", jsizes.getChartData(),
        true, false, false);
    {
      PiePlot plot = (PiePlot) jchart.getPlot();
      PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
          "{2}", new DecimalFormat("0"), new DecimalFormat("0.00%"));
      plot.setLabelGenerator(generator);
    }
    ChartUtilities.writeChartAsPNG(new FileOutputStream(new File(reportFolder,
        "sizes.png")), jchart, SMALL_CHART_WIDTH, SMALL_CHART_WIDTH);

    html.println("<tr><td>");
    html.println("<img src='sizes.png'/>");
    html.println("</td>");

    // Job runtime chart
    jchart = ChartFactory.createPieChart("Job length", jlen.getChartData(),
        true, false, false);
    {
      PiePlot plot = (PiePlot) jchart.getPlot();
      PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
          "{2}", new DecimalFormat("0"), new DecimalFormat("0.00%"));
      plot.setLabelGenerator(generator);
    }
    ChartUtilities.writeChartAsPNG(new FileOutputStream(new File(reportFolder,
        "length.png")), jchart, SMALL_CHART_WIDTH, SMALL_CHART_WIDTH);

    html.println("<td>");
    html.println("<img src='length.png'/>");
    html.println("</td>");

    // Size/Runtime distribution
    seriesCollection = new XYSeriesCollection();
    seriesCollection.addSeries(sizeTime);
    jchart = ChartFactory
        .createScatterPlot("Size and length", "size", "length",
            seriesCollection, PlotOrientation.VERTICAL, true, true, false);
    ChartUtilities.writeChartAsPNG(new FileOutputStream(new File(reportFolder,
        "sizeLen.png")), jchart, SMALL_CHART_WIDTH, SMALL_CHART_WIDTH);

    {
      XYPlot plot = jchart.getXYPlot();
      plot.setDomainAxis(new LogarithmicAxis("Size"));
      plot.setRangeAxis(new LogarithmicAxis("Length"));
    }

    html.println("<td>");
    html.println("<img src='sizeLen.png'/>");
    html.println("</td></tr>");

    html.println("</table>");

    // Errors
    badTasks.finish();
    if(badTasks.getErrorCount() > 0)
    {
      html.println("<h2>Errors</h2>");
      html.println("<b>" + badTasks.getErrorCount()
          + "</b> tasks were not parsed. " + "See <a href='" + efile.getName()
          + "'>errors</a> for more details.");
    }
    else
    {
      efile.delete();
    }

    html.println(new MLFoot(Branding.HTML_FOOTER));
    html.println("</body>");
    html.println("</html>");
    html.close();

    monitor.done();
  }

  public static void main(String[] args) throws Exception
  {
    System.out.println(Branding.consoleInfo());
    System.out.println();

    if(args.length != 1)
    {
      File workload = new File(args[0]);
      System.out.println("Processing worload file " + workload.getName()
          + " ...");
      WorkloadAnalyzer an = new WorkloadAnalyzer(workload);
      an.analyze(new ConsoleProgressMonitor());
    }
    else
    {
      usage();
    }
  }

  private static void usage()
  {
    System.out.println("Usage: <program> [WORKLOAD FILE]");
    System.exit(1);
  }

  public String getResultURL()
  {
    return "file://" + new File(reportFolder, "index.html").getAbsolutePath();
  }
}
