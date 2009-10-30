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
package gmodel.diagram.edit.policies;

import gmodel.diagram.edit.commands.LinkCreateCommand;
import gmodel.diagram.edit.commands.LinkReorientCommand;
import gmodel.diagram.edit.parts.LinkEditPart;
import gmodel.diagram.providers.GmodelElementTypes;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class GridElementItemSemanticEditPolicy extends
    GmodelBaseItemSemanticEditPolicy
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
  protected Command getStartCreateRelationshipCommand(
      CreateRelationshipRequest req)
  {
    if(GmodelElementTypes.Link_4002 == req.getElementType())
    {
      return null;
    }
    return null;
  }

  /**
  * @generated
  */
  protected Command getCompleteCreateRelationshipCommand(
      CreateRelationshipRequest req)
  {
    if(GmodelElementTypes.Link_4002 == req.getElementType())
    {
      return getGEFWrapper(new LinkCreateCommand(req, req.getSource(), req
          .getTarget()));
    }
    return null;
  }

  /**
   * Returns command to reorient EClass based link. New link target or source
   * should be the domain model element associated with this node.
   * 
   * @generated
   */
  protected Command getReorientRelationshipCommand(
      ReorientRelationshipRequest req)
  {
    switch(getVisualID(req))
    {
      case LinkEditPart.VISUAL_ID:
        return getGEFWrapper(new LinkReorientCommand(req));
    }
    return super.getReorientRelationshipCommand(req);
  }

}
