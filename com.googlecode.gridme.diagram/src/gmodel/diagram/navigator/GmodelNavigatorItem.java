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

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class GmodelNavigatorItem extends GmodelAbstractNavigatorItem
{

  /**
  * @generated
  */
  static
  {
    final Class[] supportedTypes = new Class[] { View.class, EObject.class };
    Platform.getAdapterManager().registerAdapters(new IAdapterFactory()
    {

      public Object getAdapter(Object adaptableObject, Class adapterType)
      {
        if(adaptableObject instanceof gmodel.diagram.navigator.GmodelNavigatorItem
            && (adapterType == View.class || adapterType == EObject.class))
        {
          return ((gmodel.diagram.navigator.GmodelNavigatorItem) adaptableObject)
              .getView();
        }
        return null;
      }

      public Class[] getAdapterList()
      {
        return supportedTypes;
      }
    }, gmodel.diagram.navigator.GmodelNavigatorItem.class);
  }

  /**
  * @generated
  */
  private View myView;

  /**
  * @generated
  */
  private boolean myLeaf = false;

  /**
  * @generated
  */
  public GmodelNavigatorItem(View view, Object parent, boolean isLeaf)
  {
    super(parent);
    myView = view;
    myLeaf = isLeaf;
  }

  /**
  * @generated
  */
  public View getView()
  {
    return myView;
  }

  /**
  * @generated
  */
  public boolean isLeaf()
  {
    return myLeaf;
  }

  /**
  * @generated
  */
  public boolean equals(Object obj)
  {
    if(obj instanceof gmodel.diagram.navigator.GmodelNavigatorItem)
    {
      return EcoreUtil.getURI(getView()).equals(
          EcoreUtil.getURI(((gmodel.diagram.navigator.GmodelNavigatorItem) obj)
              .getView()));
    }
    return super.equals(obj);
  }

  /**
  * @generated
  */
  public int hashCode()
  {
    return EcoreUtil.getURI(getView()).hashCode();
  }

}
