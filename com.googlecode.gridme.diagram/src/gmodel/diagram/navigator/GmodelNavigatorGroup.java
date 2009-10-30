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
package gmodel.diagram.navigator;

import java.util.Collection;
import java.util.LinkedList;

/**
* @generated
*/
public class GmodelNavigatorGroup extends GmodelAbstractNavigatorItem
{

  /**
  * @generated
  */
  private String myGroupName;

  /**
  * @generated
  */
  private String myIcon;

  /**
  * @generated
  */
  private Collection myChildren = new LinkedList();

  /**
  * @generated
  */
  GmodelNavigatorGroup(String groupName, String icon, Object parent)
  {
    super(parent);
    myGroupName = groupName;
    myIcon = icon;
  }

  /**
  * @generated
  */
  public String getGroupName()
  {
    return myGroupName;
  }

  /**
  * @generated
  */
  public String getIcon()
  {
    return myIcon;
  }

  /**
  * @generated
  */
  public Object[] getChildren()
  {
    return myChildren.toArray();
  }

  /**
  * @generated
  */
  public void addChildren(Collection children)
  {
    myChildren.addAll(children);
  }

  /**
  * @generated
  */
  public void addChild(Object child)
  {
    myChildren.add(child);
  }

  /**
  * @generated
  */
  public boolean isEmpty()
  {
    return myChildren.size() == 0;
  }

  /**
  * @generated
  */
  public boolean equals(Object obj)
  {
    if(obj instanceof gmodel.diagram.navigator.GmodelNavigatorGroup)
    {
      gmodel.diagram.navigator.GmodelNavigatorGroup anotherGroup = (gmodel.diagram.navigator.GmodelNavigatorGroup) obj;
      if(getGroupName().equals(anotherGroup.getGroupName()))
      {
        return getParent().equals(anotherGroup.getParent());
      }
    }
    return super.equals(obj);
  }

  /**
  * @generated
  */
  public int hashCode()
  {
    return getGroupName().hashCode();
  }

}
