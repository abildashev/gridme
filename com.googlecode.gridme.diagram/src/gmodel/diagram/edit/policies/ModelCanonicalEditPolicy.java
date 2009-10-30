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

import gmodel.GmodelPackage;
import gmodel.diagram.edit.parts.ConnectionEditPart;
import gmodel.diagram.edit.parts.GridElementEditPart;
import gmodel.diagram.edit.parts.LinkEditPart;
import gmodel.diagram.edit.parts.ModelEditPart;
import gmodel.diagram.part.GmodelDiagramUpdater;
import gmodel.diagram.part.GmodelLinkDescriptor;
import gmodel.diagram.part.GmodelNodeDescriptor;
import gmodel.diagram.part.GmodelVisualIDRegistry;

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

/**
* @generated
*/
public class ModelCanonicalEditPolicy extends CanonicalConnectionEditPolicy
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
    for(Iterator it = GmodelDiagramUpdater.getModel_1000SemanticChildren(
        viewObject).iterator(); it.hasNext();)
    {
      result.add(((GmodelNodeDescriptor) it.next()).getModelElement());
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
    int visualID = GmodelVisualIDRegistry.getVisualID(view);
    switch(visualID)
    {
      case ConnectionEditPart.VISUAL_ID:
      case GridElementEditPart.VISUAL_ID:
        if(!semanticChildren.contains(view.getElement()))
        {
          return true;
        }
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
      myFeaturesToSynchronize.add(GmodelPackage.eINSTANCE.getModel_Elements());
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
      int diagramLinkVisualID = GmodelVisualIDRegistry
          .getVisualID(nextDiagramLink);
      if(diagramLinkVisualID == -1)
      {
        if(nextDiagramLink.getSource() != null
            && nextDiagramLink.getTarget() != null)
        {
          linksIterator.remove();
        }
        continue;
      }
      EObject diagramLinkObject = nextDiagramLink.getElement();
      EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
      EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
      for(Iterator LinkDescriptorsIterator = linkDescriptors.iterator(); LinkDescriptorsIterator
          .hasNext();)
      {
        GmodelLinkDescriptor nextLinkDescriptor = (GmodelLinkDescriptor) LinkDescriptorsIterator
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
    if(!ModelEditPart.MODEL_ID.equals(GmodelVisualIDRegistry.getModelID(view)))
    {
      return Collections.EMPTY_LIST;
    }
    Collection result = new LinkedList();
    switch(GmodelVisualIDRegistry.getVisualID(view))
    {
      case ModelEditPart.VISUAL_ID:
      {
        if(!domain2NotationMap.containsKey(view.getElement()))
        {
          result.addAll(GmodelDiagramUpdater.getModel_1000ContainedLinks(view));
        }
        if(!domain2NotationMap.containsKey(view.getElement())
            || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
          domain2NotationMap.put(view.getElement(), view);
        }
        break;
      }
      case ConnectionEditPart.VISUAL_ID:
      {
        if(!domain2NotationMap.containsKey(view.getElement()))
        {
          result.addAll(GmodelDiagramUpdater
              .getConnection_2001ContainedLinks(view));
        }
        if(!domain2NotationMap.containsKey(view.getElement())
            || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
          domain2NotationMap.put(view.getElement(), view);
        }
        break;
      }
      case GridElementEditPart.VISUAL_ID:
      {
        if(!domain2NotationMap.containsKey(view.getElement()))
        {
          result.addAll(GmodelDiagramUpdater
              .getGridElement_2002ContainedLinks(view));
        }
        if(!domain2NotationMap.containsKey(view.getElement())
            || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
          domain2NotationMap.put(view.getElement(), view);
        }
        break;
      }
      case LinkEditPart.VISUAL_ID:
      {
        if(!domain2NotationMap.containsKey(view.getElement()))
        {
          result.addAll(GmodelDiagramUpdater.getLink_4002ContainedLinks(view));
        }
        if(!domain2NotationMap.containsKey(view.getElement())
            || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
          domain2NotationMap.put(view.getElement(), view);
        }
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
      final GmodelLinkDescriptor nextLinkDescriptor = (GmodelLinkDescriptor) linkDescriptorsIterator
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
