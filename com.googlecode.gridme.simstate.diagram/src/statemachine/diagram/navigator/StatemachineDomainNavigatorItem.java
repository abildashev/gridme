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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
* @generated
*/
public class StatemachineDomainNavigatorItem extends PlatformObject
{

  /**
  * @generated
  */
  static
  {
    final Class[] supportedTypes = new Class[] { EObject.class,
        IPropertySource.class };
    Platform.getAdapterManager().registerAdapters(new IAdapterFactory()
    {

      public Object getAdapter(Object adaptableObject, Class adapterType)
      {
        if(adaptableObject instanceof statemachine.diagram.navigator.StatemachineDomainNavigatorItem)
        {
          statemachine.diagram.navigator.StatemachineDomainNavigatorItem domainNavigatorItem = (statemachine.diagram.navigator.StatemachineDomainNavigatorItem) adaptableObject;
          EObject eObject = domainNavigatorItem.getEObject();
          if(adapterType == EObject.class)
          {
            return eObject;
          }
          if(adapterType == IPropertySource.class)
          {
            return domainNavigatorItem.getPropertySourceProvider()
                .getPropertySource(eObject);
          }
        }

        return null;
      }

      public Class[] getAdapterList()
      {
        return supportedTypes;
      }
    }, statemachine.diagram.navigator.StatemachineDomainNavigatorItem.class);
  }

  /**
  * @generated
  */
  private Object myParent;

  /**
  * @generated
  */
  private EObject myEObject;

  /**
  * @generated
  */
  private IPropertySourceProvider myPropertySourceProvider;

  /**
  * @generated
  */
  public StatemachineDomainNavigatorItem(
                                         EObject eObject,
                                         Object parent,
                                         IPropertySourceProvider propertySourceProvider)
  {
    myParent = parent;
    myEObject = eObject;
    myPropertySourceProvider = propertySourceProvider;
  }

  /**
  * @generated
  */
  public Object getParent()
  {
    return myParent;
  }

  /**
  * @generated
  */
  public EObject getEObject()
  {
    return myEObject;
  }

  /**
  * @generated
  */
  public IPropertySourceProvider getPropertySourceProvider()
  {
    return myPropertySourceProvider;
  }

  /**
  * @generated
  */
  public boolean equals(Object obj)
  {
    if(obj instanceof statemachine.diagram.navigator.StatemachineDomainNavigatorItem)
    {
      return EcoreUtil
          .getURI(getEObject())
          .equals(
              EcoreUtil
                  .getURI(((statemachine.diagram.navigator.StatemachineDomainNavigatorItem) obj)
                      .getEObject()));
    }
    return super.equals(obj);
  }

  /**
  * @generated
  */
  public int hashCode()
  {
    return EcoreUtil.getURI(getEObject()).hashCode();
  }

}
