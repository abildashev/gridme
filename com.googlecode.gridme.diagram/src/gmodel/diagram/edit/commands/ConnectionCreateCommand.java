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
package gmodel.diagram.edit.commands;

import gmodel.GmodelPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class ConnectionCreateCommand extends CreateElementCommand
{

  /**
  * @generated
  */
  public ConnectionCreateCommand(CreateElementRequest req)
  {
    super(req);
  }

  /**
  * @generated
  */
  protected EObject getElementToEdit()
  {
    EObject container = ((CreateElementRequest) getRequest()).getContainer();
    if(container instanceof View)
    {
      container = ((View) container).getElement();
    }
    return container;
  }

  /**
  * @generated
  */
  protected EClass getEClassToEdit()
  {
    return GmodelPackage.eINSTANCE.getModel();
  }

}
