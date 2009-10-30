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
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HtmlWorkloadErrorHandler implements WorkloadErrorHandler
{
  public class WErr
  {
    int line;
    String error;

    public WErr(int line, String error)
    {
      this.line = line;
      this.error = error;
    }
  }

  private PrintWriter out;
  private ArrayList<WErr> errors;
  private ArrayList<TaskInfoSWF> tasks;
  private int count;

  public HtmlWorkloadErrorHandler(File errFile) throws Exception
  {
    errors = new ArrayList<WErr>();
    tasks = new ArrayList<TaskInfoSWF>();
    out = new PrintWriter(new FileOutputStream(errFile));
  }

  @Override
  public void handleBadTask(TaskInfoSWF task)
  {
    tasks.add(task);
    count++;
  }

  @Override
  public void finish()
  {
    out.println("<html><head></head><body>");

    if(!errors.isEmpty())
    {
      out.println("<h1>Error list</h1>");
      out.println("<table border='1'><tr><td>Line</td><td>Message</td></tr>");
      for(WErr err : errors)
      {
        out.println("<tr><td>" + err.line + "</td><td>" + err.error
            + "</td><tr>");
      }
      out.println("</table>");

      out.println("<p/>");
    }

    if(!tasks.isEmpty())
    {
      out.println("<h1>Bad tasks</h1>");
      out.println(TaskInfoSWF.printHTMLTable(tasks));
    }
    out.println("</body></html>");

    out.close();
  }

  @Override
  public void error(String error, int line)
  {
    errors.add(new WErr(line, error));
    count++;
  }

  @Override
  public int getErrorCount()
  {
    return count;
  }
}
