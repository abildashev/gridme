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
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;

import statemachine.diagram.edit.policies.GStateItemSemanticEditPolicy;
import statemachine.diagram.part.StatemachineVisualIDRegistry;

/**
* @generated
*/
public class GStateEditPart extends ShapeNodeEditPart
{

  /**
  * @generated
  */
  public static final int VISUAL_ID = 1001;

  /**
  * @generated
  */
  protected IFigure contentPane;

  /**
  * @generated
  */
  protected IFigure primaryShape;

  /**
  * @generated
  */
  public GStateEditPart(View view)
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
        new GStateItemSemanticEditPolicy());
    installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
    // XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
    // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
  }

  /**
  * @generated
  */
  protected LayoutEditPolicy createLayoutEditPolicy()
  {
    LayoutEditPolicy lep = new LayoutEditPolicy()
    {

      protected EditPolicy createChildEditPolicy(EditPart child)
      {
        EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
        if(result == null)
        {
          result = new NonResizableEditPolicy();
        }
        return result;
      }

      protected Command getMoveChildrenCommand(Request request)
      {
        return null;
      }

      protected Command getCreateCommand(CreateRequest request)
      {
        return null;
      }
    };
    return lep;
  }

  /**
  * @generated
  */
  protected IFigure createNodeShape()
  {
    StateRectangle figure = new StateRectangle();
    return primaryShape = figure;
  }

  /**
  * @generated
  */
  public StateRectangle getPrimaryShape()
  {
    return (StateRectangle) primaryShape;
  }

  /**
  * @generated
  */
  protected boolean addFixedChild(EditPart childEditPart)
  {
    if(childEditPart instanceof GStateNameEditPart)
    {
      ((GStateNameEditPart) childEditPart).setLabel(getPrimaryShape()
          .getFigureStateNameLabel());
      return true;
    }
    return false;
  }

  /**
  * @generated
  */
  protected boolean removeFixedChild(EditPart childEditPart)
  {

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
  * @generated
  */
  protected void removeChildVisual(EditPart childEditPart)
  {
    if(removeFixedChild(childEditPart))
    {
      return;
    }
    super.removeChildVisual(childEditPart);
  }

  /**
  * @generated
  */
  protected IFigure getContentPaneFor(IGraphicalEditPart editPart)
  {

    return super.getContentPaneFor(editPart);
  }

  /**
  * @generated
  */
  protected NodeFigure createNodePlate()
  {
    DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode()
        .DPtoLP(40), getMapMode().DPtoLP(40));
    return result;
  }

  /**
  * Creates figure for this edit part.
  * 
  * Body of this method does not depend on settings in generation model
  * so you may safely remove <i>generated</i> tag and modify it.
  * 
  * @generated
  */
  protected NodeFigure createNodeFigure()
  {
    NodeFigure figure = createNodePlate();
    figure.setLayoutManager(new StackLayout());
    IFigure shape = createNodeShape();
    figure.add(shape);
    contentPane = setupContentPane(shape);
    return figure;
  }

  /**
  * Default implementation treats passed figure as content pane.
  * Respects layout one may have set for generated figure.
  * @param nodeShape instance of generated figure class
  * @generated
  */
  protected IFigure setupContentPane(IFigure nodeShape)
  {
    if(nodeShape.getLayoutManager() == null)
    {
      ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
      layout.setSpacing(getMapMode().DPtoLP(5));
      nodeShape.setLayoutManager(layout);
    }
    return nodeShape; // use nodeShape itself as contentPane
  }

  /**
  * @generated
  */
  public IFigure getContentPane()
  {
    if(contentPane != null)
    {
      return contentPane;
    }
    return super.getContentPane();
  }

  /**
  * @generated
  */
  public EditPart getPrimaryChildEditPart()
  {
    return getChildBySemanticHint(StatemachineVisualIDRegistry
        .getType(GStateNameEditPart.VISUAL_ID));
  }

  /**
   * @generated
   */
  public class StateRectangle extends RoundedRectangle
  {

    /**
     * @generated
     */
    private WrapLabel fFigureStateNameLabel;

    /**
     * @generated
     */
    public StateRectangle()
    {
      this.setCornerDimensions(new Dimension(getMapMode().DPtoLP(8),
          getMapMode().DPtoLP(8)));
      this.setForegroundColor(ColorConstants.lightGray);
      createContents();
    }

    /**
     * @generated
     */
    private void createContents()
    {

      fFigureStateNameLabel = new WrapLabel();
      fFigureStateNameLabel.setText("");
      fFigureStateNameLabel.setForegroundColor(ColorConstants.black);

      this.add(fFigureStateNameLabel);

    }

    /**
     * @generated
     */
    private boolean myUseLocalCoordinates = false;

    /**
     * @generated
     */
    protected boolean useLocalCoordinates()
    {
      return myUseLocalCoordinates;
    }

    /**
     * @generated
     */
    protected void setUseLocalCoordinates(boolean useLocalCoordinates)
    {
      myUseLocalCoordinates = useLocalCoordinates;
    }

    /**
     * @generated
     */
    public WrapLabel getFigureStateNameLabel()
    {
      return fFigureStateNameLabel;
    }

  }

}
