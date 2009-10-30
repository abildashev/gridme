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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.zip.ZipInputStream;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.exceptions.InvalidConfigurationException;

/**
 * Parser for the standard workload format files. Workload file must be in a ZIP
 * archive.
 */
public class SWFParser
{
  // Input reader
  protected BufferedReader input;
  private long lastSubmitTime;
  private WorkloadErrorHandler badTaskHandler;
  private File wfile;
  private HashSet<String> ids;
  private WorkloadComment comment;
  private boolean startBody = false;

  public SWFParser(File path) throws InvalidConfigurationException
  {
    this(path, null, null);
  }

  public SWFParser(File path, WorkloadErrorHandler badTaskHandler) throws InvalidConfigurationException
  {
    this(path, badTaskHandler, null);
  }

  /**
   * Create a new instance of the workload parser. Tasks can be accessed by
   * calling nextTask() method.
   *
   * @param path
   *          workload file path
   * @throws InvalidConfigurationException
   *           if the file can not be opened.
   */
  public SWFParser(File path, WorkloadErrorHandler badTaskHandler, WorkloadComment comment)
      throws InvalidConfigurationException
  {
    try
    {
      wfile = path;
      input = prepareStream();
      this.comment = comment;
      this.badTaskHandler = badTaskHandler;
      ids = new HashSet<String>(1000);
    }
    catch(Exception e)
    {
      throw new InvalidConfigurationException("Unable to open workload archive file: " + path, e);
    }
  }

  private BufferedReader prepareStream() throws FileNotFoundException, UnsupportedEncodingException, IOException,
      InvalidConfigurationException
  {
    ZipInputStream zin = new ZipInputStream(new FileInputStream(wfile));
    BufferedReader in = new BufferedReader(new InputStreamReader(zin, "US-ASCII"), 2 ^ 20);
    if(zin.getNextEntry() == null)
    {
      throw new InvalidConfigurationException("Workload file is not a zip archive");
    }
    return in;
  }

  public WorkloadComment getComment()
  {
    return comment;
  }

  /**
   * Returns next task from workload.
   *
   * @param elog
   *          error logger
   * @param unitsSecond
   *          how many time units are in one second
   *
   * @return null if there are no more tasks.
   */
  public TaskInfoSWF nextTask() throws GRuntimeException
  {
    TaskInfoSWF result = null;
    int counter = 0;

    try
    {
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        line = line.trim();
        counter++;

        // Skip comments
        if(line.startsWith(";"))
        {
          if(!startBody && comment != null)
          {
            comment.setAndParse(line);
          }
          continue;
        }

        startBody = true;

        if(line.isEmpty())
        {
          if(badTaskHandler != null)
          {
            badTaskHandler.error("Empty strings are not allowed in workload", counter);
          }
          continue;
        }

        // Parse single line. These are default values.
        String id = null;
        long startTime = -1;
        long runTime = -1;
        int minNodes = -1;
        long reqTime = 0;
        String part = null;
        long waitTime = -1;
        String workloadId = null;

        String[] tokens = line.split("\\s+");
        for(int i = 0; i < tokens.length; i++)
        {
          switch(i)
          {
          /*
           * Job Number -- a counter field, starting from 1.
           */
          case 0:
            workloadId = tokens[i];
            id = "t" + counter + "_" + workloadId;
            break;

          /*
           * Submit Time -- in seconds. All jobs are ordered by ascending
           * submission times.
           */
          case 1:
            startTime = Long.parseLong(tokens[i]);
            break;

          /*
           * Wait Time -- in seconds.
           */
          case 2:
            waitTime = Long.parseLong(tokens[i]);
            break;

          /*
           * Run Time -- in seconds. The wall clock time the job was running
           * (end time minus start time).
           */
          case 3:
            runTime = Long.parseLong(tokens[i]);
            break;

          /*
           * Number of Allocated Processors -- an integer.
           */
          case 4:
            minNodes = Integer.parseInt(tokens[i]);
            break;

          /*
           * Requested Time. Optional.
           */
          case 8:
            reqTime = Long.parseLong(tokens[i]);
            if(reqTime == -1)
            {
              reqTime = 0;
            }
            break;

          /*
           * Partition Number -- a natural number, between one and the number of
           * different partitions in the systems
           */
          case 15:
            part = tokens[i];
            break;
          }
        }

        result = new TaskInfoSWF(id, startTime, startTime - lastSubmitTime, minNodes, 0, runTime, reqTime, 1, part,
            waitTime, startTime + waitTime + runTime);

        result.setWorkloadId(workloadId);

        boolean eId = id != null;
        boolean eStartTime = startTime >= lastSubmitTime;
        boolean eRunTime = runTime >= 0;
        boolean eWaitTime = waitTime >= 0;
        boolean eMinNodes = minNodes > 0;
        boolean ePart = part != null;
        boolean eUnique = isUnique(id);

        if(eId && eStartTime && eRunTime && eWaitTime && eMinNodes && ePart && eUnique)
        {
          lastSubmitTime = startTime;
          ids.add(id);

          break;
        }
        else
        {
          if(badTaskHandler != null)
          {
            String errors = "";
            if(!eId) errors += "Id is not null. ";
            if(!eStartTime) errors += "Start time is not ordered. ";
            if(!eRunTime) errors += "Unknown run time. ";
            if(!eWaitTime) errors += "Unknown wait time. ";
            if(!eMinNodes) errors += "Unknwon number of nodes. ";
            if(!ePart) errors += "Unknown partition. ";
            if(!eUnique) errors += "Duplicated task id.";

            result.setErrors(errors);
            badTaskHandler.handleBadTask(result);
          }
          result = null;
          continue;
        }
      }
    }
    catch(NumberFormatException e)
    {
      throw new GRuntimeException("Workload parse error", e);
    }
    catch(IOException e)
    {
      throw new GRuntimeException("Workload parse error", e);
    }

    return result;
  }

  protected boolean isUnique(String id)
  {
    return !ids.contains(id);
  }

  /**
   * Closes the parser.
   */
  public void finish() throws GRuntimeException
  {
    try
    {
      input.close();
    }
    catch(IOException e)
    {
      throw new GRuntimeException("Unable to close workload file", e);
    }
  }

  /**
   * returns the potential number of tasks in the workload
   */
  public int fastParse() throws Exception
  {
    BufferedReader in = prepareStream();
    int count = 0;

    while(in.ready())
    {
      String line = in.readLine();
      if(line == null)
      {
        break;
      }
      // Skip comments
      if(line.startsWith(";"))
      {
        continue;
      }
      count++;
    }

    in.close();
    return count;
  }

  public String getWorkloadFileName()
  {
    return wfile.getName();
  }

  /**
   * Parses workload file into string
   */
  public String toText() throws GRuntimeException
  {
    StringBuilder str = new StringBuilder();

    try
    {
      while(input.ready())
      {
        String line = input.readLine();
        if(line == null)
        {
          break;
        }
        str.append(line.trim() + "\n");
      }
    }
    catch(IOException e)
    {
      throw new GRuntimeException("Workload parse error", e);
    }
    return str.toString();
  }
}
