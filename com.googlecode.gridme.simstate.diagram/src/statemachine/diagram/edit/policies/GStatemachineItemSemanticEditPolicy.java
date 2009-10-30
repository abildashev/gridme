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
package statemachine.diagram.edit.policies;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;

import statemachine.StatemachinePackage;
import statemachine.diagram.edit.commands.GStartStateCreateCommand;
import statemachine.diagram.edit.commands.GStateCreateCommand;
import statemachine.diagram.edit.commands.GStopStateCreateCommand;
import statemachine.diagram.providers.StatemachineElementTypes;

/**
* @generated
*/
public class GStatemachineItemSemanticEditPolicy extends
    StatemachineBaseItemSemanticEditPolicy
{

  /**
  * @generated
  */
  protected Command getCreateCommand(CreateElementRequest req)
  {
    if(StatemachineElementTypes.GState_1001 == req.getElementType())
    {
      if(req.getContainmentFeature() == null)
      {
        req.setContainmentFeature(StatemachinePackage.eINSTANCE
            .getGCompositeState_States());
      }
      return getGEFWrapper(new GStateCreateCommand(req));
    }
    if(StatemachineElementTypes.GStartState_1002 == req.getElementType())
    {
      if(req.getContainmentFeature() == null)
      {
        req.setContainmentFeature(StatemachinePackage.eINSTANCE
            .getGCompositeState_States());
      }
      return getGEFWrapper(new GStartStateCreateCommand(req));
    }
    if(StatemachineElementTypes.GStopState_1003 == req.getElementType())
    {
      if(req.getContainmentFeature() == null)
      {
        req.setContainmentFeature(StatemachinePackage.eINSTANCE
            .getGCompositeState_States());
      }
      return getGEFWrapper(new GStopStateCreateCommand(req));
    }
    return super.getCreateCommand(req);
  }

  /**
  * @generated
  */
  protected Command getDuplicateCommand(DuplicateElementsRequest req)
  {
    TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
        .getEditingDomain();
    return getGEFWrapper(new DuplicateAnythingCommand(editingDomain, req));
  }

  /**
  * @generated
  */
  private static class DuplicateAnythingCommand extends
      DuplicateEObjectsCommand
  {

    /**
    * @generated
    */
    public DuplicateAnythingCommand(TransactionalEditingDomain editingDomain,
                                    DuplicateElementsRequest req)
    {
      super(editingDomain, req.getLabel(), req.getElementsToBeDuplicated(), req
          .getAllDuplicatedElementsMap());
    }

  }

}
