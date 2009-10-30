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
package statemachine.diagram.sheet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import statemachine.diagram.navigator.StatemachineNavigatorGroup;
import statemachine.diagram.part.StatemachineDiagramEditorPlugin;

/**
* @generated
*/
public class StatemachineSheetLabelProvider extends DecoratingLabelProvider
{

  /**
  * @generated
  */
  public StatemachineSheetLabelProvider()
  {
    super(new AdapterFactoryLabelProvider(StatemachineDiagramEditorPlugin
        .getInstance().getItemProvidersAdapterFactory()), null);
  }

  /**
  * @generated
  */
  public String getText(Object element)
  {
    Object selected = unwrap(element);
    if(selected instanceof StatemachineNavigatorGroup)
    {
      return ((StatemachineNavigatorGroup) selected).getGroupName();
    }
    return super.getText(selected);
  }

  /**
  * @generated
  */
  public Image getImage(Object element)
  {
    return super.getImage(unwrap(element));
  }

  /**
  * @generated
  */
  private Object unwrap(Object element)
  {
    if(element instanceof IStructuredSelection)
    {
      return unwrap(((IStructuredSelection) element).getFirstElement());
    }
    if(element instanceof EditPart)
    {
      return unwrapEditPart((EditPart) element);
    }
    if(element instanceof IAdaptable)
    {
      View view = (View) ((IAdaptable) element).getAdapter(View.class);
      if(view != null)
      {
        return unwrapView(view);
      }
    }
    return element;
  }

  /**
  * @generated
  */
  private Object unwrapEditPart(EditPart p)
  {
    if(p.getModel() instanceof View)
    {
      return unwrapView((View) p.getModel());
    }
    return p.getModel();
  }

  /**
  * @generated
  */
  private Object unwrapView(View view)
  {
    return view.getElement() == null ? view : view.getElement();
  }

}
