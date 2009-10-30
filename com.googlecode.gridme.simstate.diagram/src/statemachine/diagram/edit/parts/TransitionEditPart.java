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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.notation.View;

import statemachine.diagram.edit.policies.TransitionItemSemanticEditPolicy;

/**
* @generated
*/
public class TransitionEditPart extends ConnectionNodeEditPart
{

  /**
  * @generated
  */
  public static final int VISUAL_ID = 3001;

  /**
  * @generated
  */
  public TransitionEditPart(View view)
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
        new TransitionItemSemanticEditPolicy());
  }

  /**
  * @generated
  */
  protected boolean addFixedChild(EditPart childEditPart)
  {
    if(childEditPart instanceof TransitionNameEditPart)
    {
      ((TransitionNameEditPart) childEditPart).setLabel(getPrimaryShape()
          .getFigureTransitionLabel());
      return true;
    }
    return false;
  }

  /**
  * @generated
  */
  protected void addChildVisual(EditPart childEditPart, int index)
  {
    if(addFixedChild(childEditPart))
    {
      return;
    }
    super.addChildVisual(childEditPart, -1);
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
    return new TransitionFigure();
  }

  /**
  * @generated
  */
  public TransitionFigure getPrimaryShape()
  {
    return (TransitionFigure) getFigure();
  }

  /**
   * @generated
   */
  public class TransitionFigure extends PolylineConnectionEx
  {

    /**
     * @generated
     */
    private WrapLabel fFigureTransitionLabel;

    /**
     * @generated
     */
    public TransitionFigure()
    {
      this.setLineWidth(2);
      this.setForegroundColor(ColorConstants.darkGray);

      createContents();
      setTargetDecoration(createTargetDecoration());
    }

    /**
     * @generated
     */
    private void createContents()
    {

      fFigureTransitionLabel = new WrapLabel();
      fFigureTransitionLabel.setText("");
      fFigureTransitionLabel.setForegroundColor(ColorConstants.darkGreen);

      this.add(fFigureTransitionLabel);

    }

    /**
     * @generated
     */
    private RotatableDecoration createTargetDecoration()
    {
      PolylineDecoration df = new PolylineDecoration();
      PointList pl = new PointList();
      pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(1));
      pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
      pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-1));
      df.setTemplate(pl);
      df.setScale(getMapMode().DPtoLP(7), getMapMode().DPtoLP(3));
      return df;
    }

    /**
     * @generated
     */
    public WrapLabel getFigureTransitionLabel()
    {
      return fFigureTransitionLabel;
    }

  }

}
