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

/**
 * Simple active element that has no statemachine and
 * serves only as a container for child elements.
 */
public class ActiveContainer extends ActiveElement
{
  /**
   * @param id container id
   */
  public ActiveContainer(String id)
  {
    super(id);
  }

  /**
   * @param id container id
   * @param model runtime model reference
   */
  public ActiveContainer(String id, RuntimeModel model)
  {
    super(id, model);
  }

  @Override
  public Object action(int id)
  {
    return null;
  }
}
