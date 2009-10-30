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

import gmodel.diagram.part.GmodelDiagramEditorPlugin;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

/**
* @generated
*/
public class GmodelDomainNavigatorLabelProvider implements ICommonLabelProvider
{

  /**
  * @generated
  */
  private AdapterFactoryLabelProvider myAdapterFactoryLabelProvider = new AdapterFactoryLabelProvider(
      GmodelDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());

  /**
  * @generated
  */
  public void init(ICommonContentExtensionSite aConfig)
  {
  }

  /**
  * @generated
  */
  public Image getImage(Object element)
  {
    if(element instanceof GmodelDomainNavigatorItem)
    {
      return myAdapterFactoryLabelProvider
          .getImage(((GmodelDomainNavigatorItem) element).getEObject());
    }
    return null;
  }

  /**
  * @generated
  */
  public String getText(Object element)
  {
    if(element instanceof GmodelDomainNavigatorItem)
    {
      return myAdapterFactoryLabelProvider
          .getText(((GmodelDomainNavigatorItem) element).getEObject());
    }
    return null;
  }

  /**
  * @generated
  */
  public void addListener(ILabelProviderListener listener)
  {
    myAdapterFactoryLabelProvider.addListener(listener);
  }

  /**
  * @generated
  */
  public void dispose()
  {
    myAdapterFactoryLabelProvider.dispose();
  }

  /**
  * @generated
  */
  public boolean isLabelProperty(Object element, String property)
  {
    return myAdapterFactoryLabelProvider.isLabelProperty(element, property);
  }

  /**
  * @generated
  */
  public void removeListener(ILabelProviderListener listener)
  {
    myAdapterFactoryLabelProvider.removeListener(listener);
  }

  /**
  * @generated
  */
  public void restoreState(IMemento aMemento)
  {
  }

  /**
  * @generated
  */
  public void saveState(IMemento aMemento)
  {
  }

  /**
  * @generated
  */
  public String getDescription(Object anElement)
  {
    return null;
  }

}
