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
package gmodel.diagram.part;

import gmodel.diagram.edit.parts.ModelEditPart;

import org.eclipse.emf.edit.ui.action.LoadResourceAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
* @generated
*/
public class GmodelLoadResourceAction implements IObjectActionDelegate
{

  /**
  * @generated
  */
  private ModelEditPart mySelectedElement;

  /**
  * @generated
  */
  private Shell myShell;

  /**
  * @generated
  */
  public void setActivePart(IAction action, IWorkbenchPart targetPart)
  {
    myShell = targetPart.getSite().getShell();
  }

  /**
  * @generated
  */
  public void run(IAction action)
  {
    LoadResourceAction.LoadResourceDialog loadResourceDialog = new LoadResourceAction.LoadResourceDialog(
        myShell, mySelectedElement.getEditingDomain());
    loadResourceDialog.open();
  }

  /**
  * @generated
  */
  public void selectionChanged(IAction action, ISelection selection)
  {
    mySelectedElement = null;
    if(selection instanceof IStructuredSelection)
    {
      IStructuredSelection structuredSelection = (IStructuredSelection) selection;
      if(structuredSelection.size() == 1
          && structuredSelection.getFirstElement() instanceof ModelEditPart)
      {
        mySelectedElement = (ModelEditPart) structuredSelection
            .getFirstElement();
      }
    }
    action.setEnabled(isEnabled());
  }

  /**
  * @generated
  */
  private boolean isEnabled()
  {
    return mySelectedElement != null;
  }

}
