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
package statemachine.diagram.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

/**
* @generated
*/
public abstract class StatemachineAbstractNavigatorItem extends PlatformObject
{

  /**
  * @generated
  */
  static
  {
    final Class[] supportedTypes = new Class[] { ITabbedPropertySheetPageContributor.class };
    final ITabbedPropertySheetPageContributor propertySheetPageContributor = new ITabbedPropertySheetPageContributor()
    {
      public String getContributorId()
      {
        return "com.googlecode.gridme.simstate.diagram"; //$NON-NLS-1$
      }
    };
    Platform.getAdapterManager().registerAdapters(new IAdapterFactory()
    {

      public Object getAdapter(Object adaptableObject, Class adapterType)
      {
        if(adaptableObject instanceof statemachine.diagram.navigator.StatemachineAbstractNavigatorItem
            && adapterType == ITabbedPropertySheetPageContributor.class)
        {
          return propertySheetPageContributor;
        }
        return null;
      }

      public Class[] getAdapterList()
      {
        return supportedTypes;
      }
    }, statemachine.diagram.navigator.StatemachineAbstractNavigatorItem.class);
  }

  /**
  * @generated
  */
  private Object myParent;

  /**
  * @generated
  */
  protected StatemachineAbstractNavigatorItem(Object parent)
  {
    myParent = parent;
  }

  /**
  * @generated
  */
  public Object getParent()
  {
    return myParent;
  }

}
