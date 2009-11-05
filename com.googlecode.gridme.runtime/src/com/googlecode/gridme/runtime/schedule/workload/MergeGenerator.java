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

import java.util.List;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.simstate.ModelProgressMonitor;

public abstract class MergeGenerator implements WorkloadGenerator
{
  @Override
  public SWFWorkload generate(ModelProgressMonitor monitor)
      throws GRuntimeException
  {
    List<SWFWorkload> sources = getSources();
    SWFWorkload result = new SWFWorkload();
    for(SWFWorkload src : sources)
    {
      if(monitor.isCancelled())
      {
        break;
      }

      result.merge(src);
    }
    return result;
  }

  protected abstract List<SWFWorkload> getSources();

  @Override
  public String getName()
  {
    return "Merge generator";
  }

  @Override
  public String getDescription()
  {
    return "This workload generator will merge several workload logs into one.";
  }
}
