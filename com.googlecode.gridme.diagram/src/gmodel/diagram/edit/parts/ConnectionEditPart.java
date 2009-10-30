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

import gmodel.diagram.edit.policies.ConnectionItemSemanticEditPolicy;
import gmodel.diagram.part.GmodelVisualIDRegistry;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class ConnectionEditPart extends ShapeNodeEditPart
{

  /**
  * @generated
  */
  public static final int VISUAL_ID = 2001;

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
  public ConnectionEditPart(View view)
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
        new ConnectionItemSemanticEditPolicy());
    installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
    // XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
    // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
  }

  /**
  * @generated
  */
  protected LayoutEditPolicy createLayoutEditPolicy()
  {

    FlowLayoutEditPolicy lep = new FlowLayoutEditPolicy()
    {

      protected Command createAddCommand(EditPart child, EditPart after)
      {
        return null;
      }

      protected Command createMoveChildCommand(EditPart child, EditPart after)
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
    ConnectionFigure figure = new ConnectionFigure();
    return primaryShape = figure;
  }

  /**
  * @generated
  */
  public ConnectionFigure getPrimaryShape()
  {
    return (ConnectionFigure) primaryShape;
  }

  /**
  * @generated
  */
  protected boolean addFixedChild(EditPart childEditPart)
  {
    if(childEditPart instanceof ConnectionNameEditPart)
    {
      ((ConnectionNameEditPart) childEditPart).setLabel(getPrimaryShape()
          .getFigureConnectionNameFigure());
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
    return getChildBySemanticHint(GmodelVisualIDRegistry
        .getType(ConnectionNameEditPart.VISUAL_ID));
  }

  /**
   * @generated
   */
  public class ConnectionFigure extends RectangleFigure
  {

    /**
     * @generated
     */
    private Label fFigureConnectionNameFigure;

    /**
     * @generated
     */
    public ConnectionFigure()
    {

      FlowLayout layoutThis = new FlowLayout();
      layoutThis.setStretchMinorAxis(false);
      layoutThis.setMinorAlignment(FlowLayout.ALIGN_LEFTTOP);

      layoutThis.setMajorAlignment(FlowLayout.ALIGN_LEFTTOP);
      layoutThis.setMajorSpacing(5);
      layoutThis.setMinorSpacing(5);
      layoutThis.setHorizontal(true);

      this.setLayoutManager(layoutThis);

      createContents();
    }

    /**
     * @generated
     */
    private void createContents()
    {

      fFigureConnectionNameFigure = new Label();
      fFigureConnectionNameFigure.setText("<...>");

      this.add(fFigureConnectionNameFigure);

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
    public Label getFigureConnectionNameFigure()
    {
      return fFigureConnectionNameFigure;
    }

  }

}
