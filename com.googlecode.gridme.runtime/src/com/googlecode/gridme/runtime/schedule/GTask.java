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
package com.googlecode.gridme.runtime.schedule;

import javax.naming.ldap.HasControls;

/**
 * Task represents a computational request. 
 */
public interface GTask extends TaggedResource
{
  /**
   * @return Task id.
   */
  public String getId();
  
  /**
   * @return The time that was requested by user.
   */
  public long getRequestedExecutionTime();
  
  /**
   * @return Task real execution time.
   */
  public long getRealExecutionTime();
  
  /**
   * @return minimum required nodes.
   */
  public int getNodesMin();
  
  /**
   * @return maximum required nodes. 
   */
  public int getNodesMax();
  
  /**
   * @return Initial static priority
   */
  public int getPriority();
  
  /**
   * Increases task priority
   */
  public void increasePriority(int amount);
  
  /**
   * Decreases task priority
   */
  public void decreasePriority(int amount);
  
  /**
   * Sets the time when the task appeared in the queue
   */
  public void setSubmitTime(long time);
  
  public long getSubmitTime();
  
  public long getSquare();
  
  /**
   * If task was hold return the time of the last release.
   * Otherwise return submit time.
   */
  public long getReleaseTime();
  
  public void setReleaseTime(long time);
}
