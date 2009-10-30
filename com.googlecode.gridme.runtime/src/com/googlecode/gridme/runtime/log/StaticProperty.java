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

/**
 * Defines a static property of the model element.
 */
public class StaticProperty
{
  private final String elementId;
  private final String propertyName;
  private final String value;
  
  /**
   * @param elementId model element id
   * @param propertyName property name
   * @param value property value
   */
  public StaticProperty(String elementId, String propertyName, String value)
  {
    this.elementId = elementId;
    this.propertyName = propertyName;
    this.value = value;
  }

  public String getElementId()
  {
    return elementId;
  }

  public String getName()
  {
    return propertyName;
  }

  public String getValue()
  {
    return value;
  }
}
