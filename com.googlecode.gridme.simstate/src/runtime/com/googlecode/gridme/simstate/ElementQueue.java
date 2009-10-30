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
package com.googlecode.gridme.simstate;

import java.util.List;

/**
 * Provides access to the list of active elements.
 */
class ElementQueue
{
  private List<ActiveElement> elemList; // the list of all active elements
  private int i;
  
  public void setElemList(List<ActiveElement> elemList)
  {
    this.elemList = elemList;
  }

  public List<ActiveElement> getElemList()
  {
	return elemList;
  }

/**
   * @return next element to execute or null if all elements has been executed.
   */
  public synchronized ActiveElement getNextElement()
  {
    if(i < elemList.size())
    {
      return elemList.get(i++);
    }
    
    return null;
  }
  
  /**
   * Resets queue so that all elements will be ready for execution.
   */
  public synchronized void reset()
  {
    i = 0;
  }
}
