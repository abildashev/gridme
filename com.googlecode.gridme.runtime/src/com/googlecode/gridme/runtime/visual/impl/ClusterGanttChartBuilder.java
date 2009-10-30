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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.log.GanttLogAnalyser;
import com.googlecode.gridme.runtime.log.GanttMessage;
import com.googlecode.gridme.runtime.log.NodeState;
import com.googlecode.gridme.runtime.log.NodeStateBusy;
import com.googlecode.gridme.runtime.log.NodeStatePowersave;
import com.googlecode.gridme.runtime.log.impl.FileBasedGanttAnalyser;

public class ClusterGanttChartBuilder
{
  private GanttLogAnalyser analyser;
  private File reportDir;
  private HashSet<Integer> hlines;
  private final Calendar startTime;
  private List<String> clusters;

  public ClusterGanttChartBuilder(File dataFile) throws GRuntimeException
  {
    analyser = new FileBasedGanttAnalyser(dataFile);
    reportDir = new File(dataFile + ".report");
    reportDir.mkdirs();
    hlines = new HashSet<Integer>();
    startTime = analyser.getCalendarStartTime();
    clusters = new ArrayList<String>();
  }

  public String getResultURL(String clusterName)
  {
    return "file://" + new File(reportDir, clusterName + ".html").getAbsolutePath();
  }

  public List<String> getClusterNames()
  {
    return clusters;
  }

  public void buildCharts() throws GRuntimeException
  {
    clusters = analyser.getClusters();
    for(String cluster : clusters)
    {
      buildClusterGanttChart(cluster, 1.0f);
    }
  }

  public void buildClusterGanttChart(String cluster, float scale) throws GRuntimeException
  {
    try
    {
      List<String> nodes = analyser.getNodes(cluster);

      PrintWriter html = new PrintWriter(new FileOutputStream(new File(reportDir, cluster + ".html")));
      html.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
      html.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" "
          + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
      html.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
      html.println("<head><meta http-equiv=\"Content-Type\" " + "content=\"text/html; charset=UTF-8\"/>");
      html.println("<title>Cluster " + cluster + " gantt chart</title>");

      html.println("<style>.busyHead{background-color:#3CB0FF;width:100px;}"
          + ".idleHead {background-color:#87CFFF;width:100px;}" + ".sleepHead {background-color:#97FDD2;width:100px;}"
          + ".sleep {  position:absolute;  width:5px;  background-color:#97FDD2;}"
          + ".busy {  position:absolute;  width:5px;  background-color:#3CB0FF;}"
          + ".idle {  position:absolute;  width:5px;  background-color:#87CFFF;}"
          + ".hline {z-index:1;position:absolute;background-color:gray;width:"
          + (nodes.size() * 6 + 100)
          + "px;height:1px;left:-90px;}"
          + ".hlineLabel {position:absolute;font-size:xx-small;width:100px;left:-90px;}"
          + ".hmsgLabel {position: absolute;font-size: xx-small;left: "
          + (nodes.size() * 6 + 20)
          + "px; background-color: #FFFFDA;}"
          + "</style>"
          + "<script type=\"text/javascript\">"
          // ------
          + "function positionInfo(e){"
          + "  x = document.getElementById(\"taskInfo\");  "
          + "x.style.left = e.pageX + 10 + \"px\";  "
          + "x.style.top = e.pageY + 10 + \"px\";"
          + "}"
          // ------
          + "function showTaskInfo(info){  "
          + "x = document.getElementById(\"taskInfo\");  "
          + "x.textContent = info;  "
          + "x.style.visibility = \"visible\";  "
          + "document.onmousemove = positionInfo;}"
          // ------
          + "function hideTaskInfo(info){"
          + "  x = document.getElementById(\"taskInfo\");"
          + "  x.style.visibility = \"hidden\";"
          + "  document.onmousemove = null;}"
          // ------
          + "function toggleTask(info){"
          + "elements = document.getElementsByClassName(info);"
          + "for(var j=0;j<elements.length;j++){"
          + "if(elements[j].isOn){elements[j].style.backgroundColor = \"#3CB0FF\";"
          + "}else{elements[j].style.backgroundColor = \"#FF6827\";}"
          + "elements[j].isOn = !elements[j].isOn;}}"
          + "</script>");
      html.println("</head><body>");

      html.println("<table><tr><td>Node states:</td><td><div class=\"busyHead\">Busy</div>"
          + "<div class=\"idleHead\">Idle</div><div class=\"sleepHead\">Sleeping</div></td></tr>"
          + "<tr><td>Cluster size:</td><td>" + nodes.size() + " nodes</td></tr><tr><td>Start time:</td>" + "<td>"
          + DateFormat.getDateTimeInstance().format(startTime.getTime()) + "</td></tr></table>"
          + "<div id=\"taskInfo\" style=\"z-index:2;position:absolute;" + "background-color:yellow;top:150px;"
          + "left:100px;visibility:hidden\">Task info here</div>");

      html.println("<div id=\"gantt\" style=\"position:absolute;top:150px;left:100px\">");

      final DateFormat format = DateFormat.getDateTimeInstance();
      final long startExperimentMillis = startTime.getTimeInMillis();

      int hOffset = 0;
      for(String node : nodes)
      {
        List<NodeState> states = analyser.getNodeStateChange(cluster, node);
        for(NodeState state : states)
        {
          String cstate = "idle";

          if(state instanceof NodeStateBusy)
          {
            cstate = "busy " + ((NodeStateBusy) state).getTaskId();
          }
          else if(state instanceof NodeStatePowersave)
          {
            cstate = "sleep";
          }

          int top = (int) (state.getStartTime() * scale);

          html.print("<div class=\"" + cstate + "\" style=\"height:" + (int) (state.getDuration() * scale) + "px; "
              + "left:" + hOffset + "px; top:" + top + "px;\"");

          if(state instanceof NodeStateBusy)
          {
            html.print(" onmouseover=\"showTaskInfo('" + ((NodeStateBusy) state).getTaskId() + ":" + 
                RuntimeUtils.formatTime(((NodeStateBusy) state).getDuration()) + "')\" "
                + "onmouseout=\"hideTaskInfo('" + ((NodeStateBusy) state).getTaskId() + "')\" "
                + "onclick=\"toggleTask('" + ((NodeStateBusy) state).getTaskId() + "')\"");
          }

          html.println("></div>");

          if(!hlines.contains(top))
          {
            // draw hline with label
            startTime.setTimeInMillis(startExperimentMillis + state.getStartTime() * 1000);
            makeHLine(html, format.format(startTime.getTime()), top);
            hlines.add(top);
          }
        }

        hOffset += 6;
      }

      Collection<GanttMessage> messages = analyser.getMessages();

      for(GanttMessage message : messages)
      {
        int top = (int) (message.getTime() * scale);

        if(!hlines.contains(top))
        {
          // draw hline with label
          startTime.setTimeInMillis(startExperimentMillis + message.getTime() * 1000);
          makeHLine(html, format.format(startTime.getTime()), top);
          hlines.add(top);
        }
        // Draw message
        StringBuffer label = new StringBuffer();
        for(Iterator<String> it = message.getMessages().iterator(); it.hasNext();)
        {
          label.append(it.next());
          if(it.hasNext())
          {
            label.append("<hr>");
          }
        }
        html.println("<div class=\"hmsgLabel\" style=\"top:" + top + "px\">" + label + "</div>");
      }

      html.println("</div></body></html>");
      html.close();
    }
    catch(Exception e)
    {
      throw new GRuntimeException(e);
    }
  }

  private void makeHLine(PrintWriter html, String label, long top)
  {
    html.println("<div class=\"hline\" style=\"top:" + top + "px\"></div>" + "<div class=\"hlineLabel\" style=\"top:"
        + (top - 10) + "px\">" + label + "</div>");
  }
}
