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

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

/**
* @generated
*/
public class GmodelDiagramUpdateCommand implements IHandler
{

  /**
  * @generated
  */
  public void addHandlerListener(IHandlerListener handlerListener)
  {
  }

  /**
  * @generated
  */
  public void dispose()
  {
  }

  /**
  * @generated
  */
  public Object execute(ExecutionEvent event) throws ExecutionException
  {
    ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
        .getSelectionService().getSelection();
    if(selection instanceof IStructuredSelection)
    {
      IStructuredSelection structuredSelection = (IStructuredSelection) selection;
      if(structuredSelection.size() != 1)
      {
        return null;
      }
      if(structuredSelection.getFirstElement() instanceof EditPart
          && ((EditPart) structuredSelection.getFirstElement()).getModel() instanceof View)
      {
        EObject modelElement = ((View) ((EditPart) structuredSelection
            .getFirstElement()).getModel()).getElement();
        List editPolicies = CanonicalEditPolicy
            .getRegisteredEditPolicies(modelElement);
        for(Iterator it = editPolicies.iterator(); it.hasNext();)
        {
          CanonicalEditPolicy nextEditPolicy = (CanonicalEditPolicy) it.next();
          nextEditPolicy.refresh();
        }

      }
    }
    return null;
  }

  /**
  * @generated
  */
  public boolean isEnabled()
  {
    return true;
  }

  /**
  * @generated
  */
  public boolean isHandled()
  {
    return true;
  }

  /**
  * @generated
  */
  public void removeHandlerListener(IHandlerListener handlerListener)
  {
  }

}
