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
    monitor.begin(pTotalTime);
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
            + "Density of serial tasks = " + pDensity + ", "
            + "Maximum width of tasks = " + pMaxWidth + ", " + "Total time = "
            + RuntimeUtils.formatTime(pTotalTime) + ", "
            + "Maximum length of tasks =" + RuntimeUtils.formatTime(pMaxLength)
            + "," + "Minimum length of tasks ="
            + RuntimeUtils.formatTime(pMinLength));

    double eps = Math.exp((pMinLength-pMaxLength)/60000.0);

    Random rand = new Random();
    float freq = 2.0f
        * pDensity
        * pMaxWidth
        / (pNonparalelRate + (1.0f - pNonparalelRate) * (pMaxWidth + 2.0f)
            / 2.0f) / 4000.0f;
    for(int i = 0; i < pTotalTime; i++)
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

      monitor.progress(i);
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

}
