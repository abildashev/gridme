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
package statemachine.diagram.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;

import statemachine.diagram.edit.policies.GStatemachineCanonicalEditPolicy;
import statemachine.diagram.edit.policies.GStatemachineItemSemanticEditPolicy;

/**
* @generated
*/
public class GStatemachineEditPart extends DiagramEditPart
{

  /**
  * @generated
  */
  public final static String MODEL_ID = "Statemachine"; //$NON-NLS-1$

  /**
  * @generated
  */
  public static final int VISUAL_ID = 79;

  /**
  * @generated
  */
  public GStatemachineEditPart(View view)
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
        new GStatemachineItemSemanticEditPolicy());
    installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
        new GStatemachineCanonicalEditPolicy());
    // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.POPUPBAR_ROLE);
  }

}
