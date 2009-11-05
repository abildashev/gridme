/*******************************************************************************
 * Copyright (c)  2009 Dmitry Grushin <dgrushin@gmail.com>
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
 *     Alexis Pospelov <alexispospelov@gmail.com> - wave workload generator core
 ******************************************************************************/
package com.googlecode.gridme.runtime.schedule.workload;

import java.util.Random;

//import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.simstate.ModelProgressMonitor;

public class WaveGenerator implements WorkloadGenerator
{
  protected Integer pMaxWidth = 32;
  protected Integer pMaxLength = 123456;
  protected Float pDensity; //density=1.2;
  protected Integer pTotalTime;//10*week_length
  protected Float pNonparalelRate = 0.90f;//one_proc_rate=0.90;
  protected Integer pMinLength = 600;

  @Override
  public SWFWorkload generate(ModelProgressMonitor monitor)
      throws GRuntimeException
  {
    SWFWorkload result = new SWFWorkload();

    //build in parameters
    int hour = 3600;
    int day_length = 24 * hour;
    //		  float day_rate[]=new float[7];
    //		  day_rate[0]=1.0f;
    //		  day_rate[1]=1.0f;
    //		  day_rate[2]=1.0f;
    //		  day_rate[3]=1.0f;
    //		  day_rate[4]=1.0f;
    //		  day_rate[5]=1.00f;
    //		  day_rate[6]=1.00f;

    //		  build in parameters

    result.getComment().setValue(
        WorkloadComment.NOTE,
        "Generator: " + getName() + ", " + "Generation parameters: "
            + "Workload density=" + pDensity + ", "
            + "Rate of serial tasks=" + pNonparalelRate + ", "
            + "Maximum width of tasks=" + pMaxWidth + ", "
            + "Workload length=" + RuntimeUtils.formatTime(pTotalTime) + ", "
            + "Maximum length of tasks=" + RuntimeUtils.formatTime(pMaxLength) + ","
            + "Minimum length of tasks="  + RuntimeUtils.formatTime(pMinLength));

    double eps = Math.exp((pMinLength-pMaxLength)/60000.0);

    Random rand = new Random();
    float freq = 2.0f
        * pDensity
        * pMaxWidth
        / (pNonparalelRate + (1.0f - pNonparalelRate) * (pMaxWidth + 2.0f)
            / 2.0f) / 4000.0f;
    for(int i = 0; i < pTotalTime && !monitor.isCancelled(); i++)
    {
      //	int k=(i/day_length)%7;
      if(rand.nextFloat() < freq
          && 2 * rand.nextFloat() < 1 + Math.sin(2 * Math.PI * (i - 8 * hour)
              / day_length))
      {
        int height = (int) ((pMinLength - 60 * Math.min(1000.0 * Math.log(eps
            + rand.nextDouble()), 0.0)));
        int width = 1;
        if(rand.nextFloat() > pNonparalelRate)
          width = 1 + rand.nextInt(pMaxWidth);

        TaskInfoSWF task = new TaskInfoSWF(null, i, -1, width, -1, height, -1,
            -1, "-1", 0, -1);

        result.addTask(task);
      }
    }

    return result;
  }

  @Override
  public String getDescription()
  {
    return "Time dependend workload generator";
  }

  @Override
  public String getName()
  {
    return "Wave generator";
  }

  @Override
  public boolean canGenerateClones()
  {
    return true;
  }
}
