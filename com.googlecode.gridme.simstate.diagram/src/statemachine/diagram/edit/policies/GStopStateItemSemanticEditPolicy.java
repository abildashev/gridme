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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;

import statemachine.diagram.edit.commands.TransitionCreateCommand;
import statemachine.diagram.edit.commands.TransitionReorientCommand;
import statemachine.diagram.edit.parts.TransitionEditPart;
import statemachine.diagram.providers.StatemachineElementTypes;

/**
* @generated
*/
public class GStopStateItemSemanticEditPolicy extends
    StatemachineBaseItemSemanticEditPolicy
{

  /**
  * @generated
  */
  protected Command getDestroyElementCommand(DestroyElementRequest req)
  {
    CompoundCommand cc = getDestroyEdgesCommand();
    addDestroyShortcutsCommand(cc);
    View view = (View) getHost().getModel();
    if(view.getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
      req.setElementToDestroy(view);
    }
    cc.add(getGEFWrapper(new DestroyElementCommand(req)));
    return cc.unwrap();
  }

  /**
  * @generated
  */
  protected Command getCreateRelationshipCommand(CreateRelationshipRequest req)
  {
    Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req)
        : getCompleteCreateRelationshipCommand(req);
    return command != null ? command : super.getCreateRelationshipCommand(req);
  }

  /**
  * @generated
  */
  protected Command getStartCreateRelationshipCommand(CreateRelationshipRequest req)
  {
    if(StatemachineElementTypes.Transition_3001 == req.getElementType())
    {
      return getGEFWrapper(new TransitionCreateCommand(req, req.getSource(),
          req.getTarget()));
    }
    return null;
  }

  /**
  * @generated
  */
  protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req)
  {
    if(StatemachineElementTypes.Transition_3001 == req.getElementType())
    {
      return getGEFWrapper(new TransitionCreateCommand(req, req.getSource(),
          req.getTarget()));
    }
    return null;
  }

  /**
   * Returns command to reorient EClass based link. New link target or source
   * should be the domain model element associated with this node.
   * 
   * @generated
   */
  protected Command getReorientRelationshipCommand(ReorientRelationshipRequest req)
  {
    switch(getVisualID(req))
    {
      case TransitionEditPart.VISUAL_ID:
        return getGEFWrapper(new TransitionReorientCommand(req));
    }
    return super.getReorientRelationshipCommand(req);
  }

}
