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
package com.googlecode.gridme.runtime.log;

import java.util.ArrayList;
import java.util.List;

/**
 * Result that represents various simulation properties.  
 */
public class AnalyserResult
{
  private List<InTimeValue> values;
  private InTimeValue peak;
  private float arithmeticMean;
  private long aggregate;
  
  /**
   * @param values
   * @param peak
   * @param arithmeticMean
   */
  public AnalyserResult(int size)
  {
    values = new ArrayList<InTimeValue>(size);
  }

  public void addValue(InTimeValue val)
  {
    values.add(val);
  }
  
  public List<InTimeValue> getValues()
  {
    return values;
  }

  public InTimeValue getPeak()
  {
    return peak;
  }

  public float getArithmeticMean()
  {
    return arithmeticMean;
  }

  public void setPeak(InTimeValue peak)
  {
    this.peak = peak;
  }

  public void setArithmeticMean(float arithmeticMean)
  {
    this.arithmeticMean = arithmeticMean;
  }

  /**
   * Sum of all values.
   */
  public long getAggregate()
  {
    return aggregate;
  }

  public void setAggregate(long aggregate)
  {
    this.aggregate = aggregate;
  }
}
