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

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IEditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.*;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import statemachine.GAbstractState;
import statemachine.GCompositeState;
import statemachine.diagram.edit.helpers.StatemachineBaseEditHelper;
import statemachine.diagram.part.StatemachineVisualIDRegistry;

/**
* @generated
*/
public class StatemachineBaseItemSemanticEditPolicy extends SemanticEditPolicy
{

  /**
  * Extended request data key to hold editpart visual id.
  * 
  * @generated
  */
  public static final String VISUAL_ID_KEY = "visual_id"; //$NON-NLS-1$

  /**
  * Extended request data key to hold editpart visual id.
  * Add visual id of edited editpart to extended data of the request
  * so command switch can decide what kind of diagram element is being edited.
  * It is done in those cases when it's not possible to deduce diagram
  * element kind from domain element.
  * 
  * @generated
  */
  public Command getCommand(Request request)
  {
    if(request instanceof ReconnectRequest)
    {
      Object view = ((ReconnectRequest) request).getConnectionEditPart()
          .getModel();
      if(view instanceof View)
      {
        Integer id = new Integer(StatemachineVisualIDRegistry
            .getVisualID((View) view));
        request.getExtendedData().put(VISUAL_ID_KEY, id);
      }
    }
    return super.getCommand(request);
  }

  /**
  * Returns visual id from request parameters.
  * 
  * @generated
  */
  protected int getVisualID(IEditCommandRequest request)
  {
    Object id = request.getParameter(VISUAL_ID_KEY);
    return id instanceof Integer ? ((Integer) id).intValue() : -1;
  }

  /**
  * @generated
  */
  protected Command getSemanticCommand(IEditCommandRequest request)
  {
    IEditCommandRequest completedRequest = completeRequest(request);
    Object editHelperContext = completedRequest.getEditHelperContext();
    if(editHelperContext instanceof View
        || (editHelperContext instanceof IEditHelperContext && ((IEditHelperContext) editHelperContext)
            .getEObject() instanceof View))
    {
      // no semantic commands are provided for pure design elements
      return null;
    }
    if(editHelperContext == null)
    {
      editHelperContext = ViewUtil.resolveSemanticElement((View) getHost()
          .getModel());
    }
    IElementType elementType = ElementTypeRegistry.getInstance()
        .getElementType(editHelperContext);
    if(elementType == ElementTypeRegistry.getInstance().getType(
        "org.eclipse.gmf.runtime.emf.type.core.default")) { //$NON-NLS-1$ 
      elementType = null;
    }
    Command semanticCommand = getSemanticCommandSwitch(completedRequest);
    if(semanticCommand != null)
    {
      ICommand command = semanticCommand instanceof ICommandProxy ? ((ICommandProxy) semanticCommand)
          .getICommand()
          : new CommandProxy(semanticCommand);
      completedRequest.setParameter(
          StatemachineBaseEditHelper.EDIT_POLICY_COMMAND, command);
    }
    if(elementType != null)
    {
      ICommand command = elementType.getEditCommand(completedRequest);
      if(command != null)
      {
        if(!(command instanceof CompositeTransactionalCommand))
        {
          TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
              .getEditingDomain();
          command = new CompositeTransactionalCommand(editingDomain, null)
              .compose(command);
        }
        semanticCommand = new ICommandProxy(command);
      }
    }
    boolean shouldProceed = true;
    if(completedRequest instanceof DestroyRequest)
    {
      shouldProceed = shouldProceed((DestroyRequest) completedRequest);
    }
    if(shouldProceed)
    {
      if(completedRequest instanceof DestroyRequest)
      {
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
            .getEditingDomain();
        Command deleteViewCommand = new ICommandProxy(new DeleteCommand(
            editingDomain, (View) getHost().getModel()));
        semanticCommand = semanticCommand == null ? deleteViewCommand
            : semanticCommand.chain(deleteViewCommand);
      }
      return semanticCommand;
    }
    return null;
  }

  /**
  * @generated
  */
  protected Command getSemanticCommandSwitch(IEditCommandRequest req)
  {
    if(req instanceof CreateRelationshipRequest)
    {
      return getCreateRelationshipCommand((CreateRelationshipRequest) req);
    }
    else if(req instanceof CreateElementRequest)
    {
      return getCreateCommand((CreateElementRequest) req);
    }
    else if(req instanceof ConfigureRequest)
    {
      return getConfigureCommand((ConfigureRequest) req);
    }
    else if(req instanceof DestroyElementRequest)
    {
      return getDestroyElementCommand((DestroyElementRequest) req);
    }
    else if(req instanceof DestroyReferenceRequest)
    {
      return getDestroyReferenceCommand((DestroyReferenceRequest) req);
    }
    else if(req instanceof DuplicateElementsRequest)
    {
      return getDuplicateCommand((DuplicateElementsRequest) req);
    }
    else if(req instanceof GetEditContextRequest)
    {
      return getEditContextCommand((GetEditContextRequest) req);
    }
    else if(req instanceof MoveRequest)
    {
      return getMoveCommand((MoveRequest) req);
    }
    else if(req instanceof ReorientReferenceRelationshipRequest)
    {
      return getReorientReferenceRelationshipCommand((ReorientReferenceRelationshipRequest) req);
    }
    else if(req instanceof ReorientRelationshipRequest)
    {
      return getReorientRelationshipCommand((ReorientRelationshipRequest) req);
    }
    else if(req instanceof SetRequest)
    {
      return getSetCommand((SetRequest) req);
    }
    return null;
  }

  /**
  * @generated
  */
  protected Command getConfigureCommand(ConfigureRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected Command getCreateRelationshipCommand(CreateRelationshipRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected Command getCreateCommand(CreateElementRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected Command getSetCommand(SetRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected Command getEditContextCommand(GetEditContextRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected Command getDestroyElementCommand(DestroyElementRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected Command getDestroyReferenceCommand(DestroyReferenceRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected Command getDuplicateCommand(DuplicateElementsRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected Command getMoveCommand(MoveRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected Command getReorientReferenceRelationshipCommand(ReorientReferenceRelationshipRequest req)
  {
    return UnexecutableCommand.INSTANCE;
  }

  /**
  * @generated
  */
  protected Command getReorientRelationshipCommand(ReorientRelationshipRequest req)
  {
    return UnexecutableCommand.INSTANCE;
  }

  /**
  * @generated
  */
  protected final Command getGEFWrapper(ICommand cmd)
  {
    return new ICommandProxy(cmd);
  }

  /**
  * @deprecated use getGEFWrapper() instead
  * @generated
  */
  protected final Command getMSLWrapper(ICommand cmd)
  {
    // XXX deprecated: use getGEFWrapper() instead
    return getGEFWrapper(cmd);
  }

  /**
  * @generated
  */
  protected EObject getSemanticElement()
  {
    return ViewUtil.resolveSemanticElement((View) getHost().getModel());
  }

  /**
  * Returns editing domain from the host edit part.
  * 
  * @generated
  */
  protected TransactionalEditingDomain getEditingDomain()
  {
    return ((IGraphicalEditPart) getHost()).getEditingDomain();
  }

  /**
  * Creates command to destroy the link.
  * 
  * @generated
  */
  protected Command getDestroyElementCommand(View view)
  {
    EditPart editPart = (EditPart) getHost().getViewer().getEditPartRegistry()
        .get(view);
    DestroyElementRequest request = new DestroyElementRequest(
        getEditingDomain(), false);
    return editPart.getCommand(new EditCommandRequestWrapper(request,
        Collections.EMPTY_MAP));
  }

  /**
  * Creates commands to destroy all host incoming and outgoing links.
  * 
  * @generated
  */
  protected CompoundCommand getDestroyEdgesCommand()
  {
    CompoundCommand cmd = new CompoundCommand();
    View view = (View) getHost().getModel();
    for(Iterator it = view.getSourceEdges().iterator(); it.hasNext();)
    {
      cmd.add(getDestroyElementCommand((Edge) it.next()));
    }
    for(Iterator it = view.getTargetEdges().iterator(); it.hasNext();)
    {
      cmd.add(getDestroyElementCommand((Edge) it.next()));
    }
    return cmd;
  }

  /**
  * @generated
  */
  protected void addDestroyShortcutsCommand(CompoundCommand command)
  {
    View view = (View) getHost().getModel();
    if(view.getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
      return;
    }
    for(Iterator it = view.getDiagram().getChildren().iterator(); it.hasNext();)
    {
      View nextView = (View) it.next();
      if(nextView.getEAnnotation("Shortcut") == null || !nextView.isSetElement() || nextView.getElement() != view.getElement()) { //$NON-NLS-1$
        continue;
      }
      command.add(getDestroyElementCommand(nextView));
    }
  }

  /**
  * @generated
  */
  public static class LinkConstraints
  {

    /**
    * @generated
    */
    public static boolean canCreateTransition_3001(GCompositeState container,
                                                   GAbstractState source,
                                                   GAbstractState target)
    {
      return canExistTransition_3001(container, source, target);
    }

    /**
    * @generated
    */
    public static boolean canExistTransition_3001(GCompositeState container,
                                                  GAbstractState source,
                                                  GAbstractState target)
    {
      return true;
    }

  }

}
