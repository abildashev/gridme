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
package gmodel.diagram.edit.parts;

import gmodel.diagram.edit.policies.LinkItemSemanticEditPolicy;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class LinkEditPart extends ConnectionNodeEditPart implements
    ITreeBranchEditPart
{

  /**
  * @generated
  */
  public static final int VISUAL_ID = 4002;

  /**
  * @generated
  */
  public LinkEditPart(View view)
  {
    super(view);
  }

  /**
  * @generated
  */
  protected void createDefaultEditPolicies()
  {
    super.createDefaultEditPolicies();
    installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
        new LinkItemSemanticEditPolicy());
  }

  /**
  * Creates figure for this edit part.
  * 
  * Body of this method does not depend on settings in generation model
  * so you may safely remove <i>generated</i> tag and modify it.
  * 
  * @generated
  */
  protected Connection createConnectionFigure()
  {
    return new PolylineConnection();
  }

  /**
  * @generated
  */
  public PolylineConnection getPrimaryShape()
  {
    return (PolylineConnection) getFigure();
  }

}
