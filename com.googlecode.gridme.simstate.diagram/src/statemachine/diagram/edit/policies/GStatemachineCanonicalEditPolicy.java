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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalConnectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import statemachine.StatemachinePackage;
import statemachine.diagram.edit.parts.GStartStateEditPart;
import statemachine.diagram.edit.parts.GStateEditPart;
import statemachine.diagram.edit.parts.GStatemachineEditPart;
import statemachine.diagram.edit.parts.GStopStateEditPart;
import statemachine.diagram.edit.parts.TransitionEditPart;
import statemachine.diagram.part.StatemachineDiagramUpdater;
import statemachine.diagram.part.StatemachineLinkDescriptor;
import statemachine.diagram.part.StatemachineNodeDescriptor;
import statemachine.diagram.part.StatemachineVisualIDRegistry;

/**
* @generated
*/
public class GStatemachineCanonicalEditPolicy extends
    CanonicalConnectionEditPolicy
{

  /**
  * @generated
  */
  Set myFeaturesToSynchronize;

  /**
  * @generated
  */
  protected List getSemanticChildrenList()
  {
    View viewObject = (View) getHost().getModel();
    List result = new LinkedList();
    for(Iterator it = StatemachineDiagramUpdater
        .getGStatemachine_79SemanticChildren(viewObject).iterator(); it
        .hasNext();)
    {
      result.add(((StatemachineNodeDescriptor) it.next()).getModelElement());
    }
    return result;
  }

  /**
  * @generated
  */
  protected boolean shouldDeleteView(View view)
  {
    return true;
  }

  /**
  * @generated
  */
  protected boolean isOrphaned(Collection semanticChildren, final View view)
  {
    int visualID = StatemachineVisualIDRegistry.getVisualID(view);
    switch(visualID)
    {
      case GStateEditPart.VISUAL_ID:
      case GStartStateEditPart.VISUAL_ID:
      case GStopStateEditPart.VISUAL_ID:
        return !semanticChildren.contains(view.getElement())
            || visualID != StatemachineVisualIDRegistry.getNodeVisualID(
                (View) getHost().getModel(), view.getElement());
    }
    return false;
  }

  /**
  * @generated
  */
  protected String getDefaultFactoryHint()
  {
    return null;
  }

  /**
  * @generated
  */
  protected Set getFeaturesToSynchronize()
  {
    if(myFeaturesToSynchronize == null)
    {
      myFeaturesToSynchronize = new HashSet();
      myFeaturesToSynchronize.add(StatemachinePackage.eINSTANCE
          .getGCompositeState_States());
    }
    return myFeaturesToSynchronize;
  }

  /**
  * @generated
  */
  protected List getSemanticConnectionsList()
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  protected EObject getSourceElement(EObject relationship)
  {
    return null;
  }

  /**
  * @generated
  */
  protected EObject getTargetElement(EObject relationship)
  {
    return null;
  }

  /**
  * @generated
  */
  protected boolean shouldIncludeConnection(Edge connector, Collection children)
  {
    return false;
  }

  /**
  * @generated
  */
  protected void refreshSemantic()
  {
    List createdViews = new LinkedList();
    createdViews.addAll(refreshSemanticChildren());
    List createdConnectionViews = new LinkedList();
    createdConnectionViews.addAll(refreshSemanticConnections());
    createdConnectionViews.addAll(refreshConnections());

    if(createdViews.size() > 1)
    {
      // perform a layout of the container
      DeferredLayoutCommand layoutCmd = new DeferredLayoutCommand(host()
          .getEditingDomain(), createdViews, host());
      executeCommand(new ICommandProxy(layoutCmd));
    }

    createdViews.addAll(createdConnectionViews);
    makeViewsImmutable(createdViews);
  }

  /**
  * @generated
  */
  private Diagram getDiagram()
  {
    return ((View) getHost().getModel()).getDiagram();
  }

  /**
  * @generated
  */
  private Collection refreshConnections()
  {
    Map domain2NotationMap = new HashMap();
    Collection linkDescriptors = collectAllLinks(getDiagram(),
        domain2NotationMap);
    Collection existingLinks = new LinkedList(getDiagram().getEdges());
    for(Iterator linksIterator = existingLinks.iterator(); linksIterator
        .hasNext();)
    {
      Edge nextDiagramLink = (Edge) linksIterator.next();
      EObject diagramLinkObject = nextDiagramLink.getElement();
      EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
      EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
      int diagramLinkVisualID = StatemachineVisualIDRegistry
          .getVisualID(nextDiagramLink);
      for(Iterator LinkDescriptorsIterator = linkDescriptors.iterator(); LinkDescriptorsIterator
          .hasNext();)
      {
        StatemachineLinkDescriptor nextLinkDescriptor = (StatemachineLinkDescriptor) LinkDescriptorsIterator
            .next();
        if(diagramLinkObject == nextLinkDescriptor.getModelElement()
            && diagramLinkSrc == nextLinkDescriptor.getSource()
            && diagramLinkDst == nextLinkDescriptor.getDestination()
            && diagramLinkVisualID == nextLinkDescriptor.getVisualID())
        {
          linksIterator.remove();
          LinkDescriptorsIterator.remove();
        }
      }
    }
    deleteViews(existingLinks.iterator());
    return createConnections(linkDescriptors, domain2NotationMap);
  }

  /**
  * @generated
  */
  private Collection collectAllLinks(View view, Map domain2NotationMap)
  {
    if(!GStatemachineEditPart.MODEL_ID.equals(StatemachineVisualIDRegistry
        .getModelID(view)))
    {
      return Collections.EMPTY_LIST;
    }
    Collection result = new LinkedList();
    switch(StatemachineVisualIDRegistry.getVisualID(view))
    {
      case GStatemachineEditPart.VISUAL_ID:
      {
        domain2NotationMap.put(view.getElement(), view);
        result.addAll(StatemachineDiagramUpdater
            .getGStatemachine_79ContainedLinks(view));
        break;
      }
      case GStateEditPart.VISUAL_ID:
      {
        domain2NotationMap.put(view.getElement(), view);
        result.addAll(StatemachineDiagramUpdater
            .getGState_1001ContainedLinks(view));
        break;
      }
      case GStartStateEditPart.VISUAL_ID:
      {
        domain2NotationMap.put(view.getElement(), view);
        result.addAll(StatemachineDiagramUpdater
            .getGStartState_1002ContainedLinks(view));
        break;
      }
      case GStopStateEditPart.VISUAL_ID:
      {
        domain2NotationMap.put(view.getElement(), view);
        result.addAll(StatemachineDiagramUpdater
            .getGStopState_1003ContainedLinks(view));
        break;
      }
      case TransitionEditPart.VISUAL_ID:
      {
        domain2NotationMap.put(view.getElement(), view);
        result.addAll(StatemachineDiagramUpdater
            .getTransition_3001ContainedLinks(view));
        break;
      }
    }
    for(Iterator children = view.getChildren().iterator(); children.hasNext();)
    {
      result
          .addAll(collectAllLinks((View) children.next(), domain2NotationMap));
    }
    for(Iterator edges = view.getSourceEdges().iterator(); edges.hasNext();)
    {
      result.addAll(collectAllLinks((View) edges.next(), domain2NotationMap));
    }
    return result;
  }

  /**
  * @generated
  */
  private Collection createConnections(Collection linkDescriptors,
                                       Map domain2NotationMap)
  {
    List adapters = new LinkedList();
    for(Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator
        .hasNext();)
    {
      final StatemachineLinkDescriptor nextLinkDescriptor = (StatemachineLinkDescriptor) linkDescriptorsIterator
          .next();
      EditPart sourceEditPart = getEditPart(nextLinkDescriptor.getSource(),
          domain2NotationMap);
      EditPart targetEditPart = getEditPart(
          nextLinkDescriptor.getDestination(), domain2NotationMap);
      if(sourceEditPart == null || targetEditPart == null)
      {
        continue;
      }
      CreateConnectionViewRequest.ConnectionViewDescriptor descriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(
          nextLinkDescriptor.getSemanticAdapter(), null, ViewUtil.APPEND,
          false, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
      CreateConnectionViewRequest ccr = new CreateConnectionViewRequest(
          descriptor);
      ccr.setType(RequestConstants.REQ_CONNECTION_START);
      ccr.setSourceEditPart(sourceEditPart);
      sourceEditPart.getCommand(ccr);
      ccr.setTargetEditPart(targetEditPart);
      ccr.setType(RequestConstants.REQ_CONNECTION_END);
      Command cmd = targetEditPart.getCommand(ccr);
      if(cmd != null && cmd.canExecute())
      {
        executeCommand(cmd);
        IAdaptable viewAdapter = (IAdaptable) ccr.getNewObject();
        if(viewAdapter != null)
        {
          adapters.add(viewAdapter);
        }
      }
    }
    return adapters;
  }

  /**
  * @generated
  */
  private EditPart getEditPart(EObject domainModelElement,
                               Map domain2NotationMap)
  {
    View view = (View) domain2NotationMap.get(domainModelElement);
    if(view != null)
    {
      return (EditPart) getHost().getViewer().getEditPartRegistry().get(view);
    }
    return null;
  }
}
