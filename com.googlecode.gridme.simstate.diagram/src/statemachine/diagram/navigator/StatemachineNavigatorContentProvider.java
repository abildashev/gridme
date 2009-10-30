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
package statemachine.diagram.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

import statemachine.diagram.edit.parts.GStartStateEditPart;
import statemachine.diagram.edit.parts.GStateEditPart;
import statemachine.diagram.edit.parts.GStatemachineEditPart;
import statemachine.diagram.edit.parts.GStopStateEditPart;
import statemachine.diagram.edit.parts.TransitionEditPart;
import statemachine.diagram.part.Messages;
import statemachine.diagram.part.StatemachineVisualIDRegistry;

/**
* @generated
*/
public class StatemachineNavigatorContentProvider implements
    ICommonContentProvider
{

  /**
  * @generated
  */
  private static final Object[] EMPTY_ARRAY = new Object[0];

  /**
  * @generated
  */
  private Viewer myViewer;

  /**
  * @generated
  */
  private AdapterFactoryEditingDomain myEditingDomain;

  /**
  * @generated
  */
  private WorkspaceSynchronizer myWorkspaceSynchronizer;

  /**
  * @generated
  */
  private Runnable myViewerRefreshRunnable;

  /**
  * @generated
  */
  public StatemachineNavigatorContentProvider()
  {
    TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
        .createEditingDomain();
    myEditingDomain = (AdapterFactoryEditingDomain) editingDomain;
    myEditingDomain.setResourceToReadOnlyMap(new HashMap()
    {
      public Object get(Object key)
      {
        if(!containsKey(key))
        {
          put(key, Boolean.TRUE);
        }
        return super.get(key);
      }
    });
    myViewerRefreshRunnable = new Runnable()
    {
      public void run()
      {
        if(myViewer != null)
        {
          myViewer.refresh();
        }
      }
    };
    myWorkspaceSynchronizer = new WorkspaceSynchronizer(editingDomain,
        new WorkspaceSynchronizer.Delegate()
        {
          public void dispose()
          {
          }

          public boolean handleResourceChanged(final Resource resource)
          {
            for(Iterator it = myEditingDomain.getResourceSet().getResources()
                .iterator(); it.hasNext();)
            {
              Resource nextResource = (Resource) it.next();
              nextResource.unload();
            }
            if(myViewer != null)
            {
              myViewer.getControl().getDisplay().asyncExec(
                  myViewerRefreshRunnable);
            }
            return true;
          }

          public boolean handleResourceDeleted(Resource resource)
          {
            for(Iterator it = myEditingDomain.getResourceSet().getResources()
                .iterator(); it.hasNext();)
            {
              Resource nextResource = (Resource) it.next();
              nextResource.unload();
            }
            if(myViewer != null)
            {
              myViewer.getControl().getDisplay().asyncExec(
                  myViewerRefreshRunnable);
            }
            return true;
          }

          public boolean handleResourceMoved(Resource resource, final URI newURI)
          {
            for(Iterator it = myEditingDomain.getResourceSet().getResources()
                .iterator(); it.hasNext();)
            {
              Resource nextResource = (Resource) it.next();
              nextResource.unload();
            }
            if(myViewer != null)
            {
              myViewer.getControl().getDisplay().asyncExec(
                  myViewerRefreshRunnable);
            }
            return true;
          }
        });
  }

  /**
  * @generated
  */
  public void dispose()
  {
    myWorkspaceSynchronizer.dispose();
    myWorkspaceSynchronizer = null;
    myViewerRefreshRunnable = null;
    for(Iterator it = myEditingDomain.getResourceSet().getResources()
        .iterator(); it.hasNext();)
    {
      Resource resource = (Resource) it.next();
      resource.unload();
    }
    ((TransactionalEditingDomain) myEditingDomain).dispose();
    myEditingDomain = null;
  }

  /**
  * @generated
  */
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
  {
    myViewer = viewer;
  }

  /**
  * @generated
  */
  public Object[] getElements(Object inputElement)
  {
    return getChildren(inputElement);
  }

  /**
  * @generated
  */
  public void restoreState(IMemento aMemento)
  {
  }

  /**
  * @generated
  */
  public void saveState(IMemento aMemento)
  {
  }

  /**
  * @generated
  */
  public void init(ICommonContentExtensionSite aConfig)
  {
  }

  /**
  * @generated
  */
  public Object[] getChildren(Object parentElement)
  {
    if(parentElement instanceof IFile)
    {
      IFile file = (IFile) parentElement;
      URI fileURI = URI.createPlatformResourceURI(
          file.getFullPath().toString(), true);
      Resource resource = myEditingDomain.getResourceSet().getResource(fileURI,
          true);
      Collection result = new ArrayList();
      result.addAll(createNavigatorItems(selectViewsByType(resource
          .getContents(), GStatemachineEditPart.MODEL_ID), file, false));
      return result.toArray();
    }

    if(parentElement instanceof StatemachineNavigatorGroup)
    {
      StatemachineNavigatorGroup group = (StatemachineNavigatorGroup) parentElement;
      return group.getChildren();
    }

    if(parentElement instanceof StatemachineNavigatorItem)
    {
      StatemachineNavigatorItem navigatorItem = (StatemachineNavigatorItem) parentElement;
      if(navigatorItem.isLeaf() || !isOwnView(navigatorItem.getView()))
      {
        return EMPTY_ARRAY;
      }
      return getViewChildren(navigatorItem.getView(), parentElement);
    }

    return EMPTY_ARRAY;
  }

  /**
  * @generated
  */
  private Object[] getViewChildren(View view, Object parentElement)
  {
    switch(StatemachineVisualIDRegistry.getVisualID(view))
    {

      case GStatemachineEditPart.VISUAL_ID:
      {
        Collection result = new ArrayList();
        StatemachineNavigatorGroup links = new StatemachineNavigatorGroup(
            Messages.NavigatorGroupName_GStatemachine_79_links,
            "icons/linksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
        Collection connectedViews = getChildrenByType(Collections
            .singleton(view), GStateEditPart.VISUAL_ID);
        result
            .addAll(createNavigatorItems(connectedViews, parentElement, false));
        connectedViews = getChildrenByType(Collections.singleton(view),
            GStartStateEditPart.VISUAL_ID);
        result
            .addAll(createNavigatorItems(connectedViews, parentElement, false));
        connectedViews = getChildrenByType(Collections.singleton(view),
            GStopStateEditPart.VISUAL_ID);
        result
            .addAll(createNavigatorItems(connectedViews, parentElement, false));
        connectedViews = getDiagramLinksByType(Collections.singleton(view),
            TransitionEditPart.VISUAL_ID);
        links.addChildren(createNavigatorItems(connectedViews, links, false));
        if(!links.isEmpty())
        {
          result.add(links);
        }
        return result.toArray();
      }

      case GStateEditPart.VISUAL_ID:
      {
        Collection result = new ArrayList();
        StatemachineNavigatorGroup incominglinks = new StatemachineNavigatorGroup(
            Messages.NavigatorGroupName_GState_1001_incominglinks,
            "icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
        StatemachineNavigatorGroup outgoinglinks = new StatemachineNavigatorGroup(
            Messages.NavigatorGroupName_GState_1001_outgoinglinks,
            "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
        Collection connectedViews = getIncomingLinksByType(Collections
            .singleton(view), TransitionEditPart.VISUAL_ID);
        incominglinks.addChildren(createNavigatorItems(connectedViews,
            incominglinks, true));
        connectedViews = getOutgoingLinksByType(Collections.singleton(view),
            TransitionEditPart.VISUAL_ID);
        outgoinglinks.addChildren(createNavigatorItems(connectedViews,
            outgoinglinks, true));
        if(!incominglinks.isEmpty())
        {
          result.add(incominglinks);
        }
        if(!outgoinglinks.isEmpty())
        {
          result.add(outgoinglinks);
        }
        return result.toArray();
      }

      case GStartStateEditPart.VISUAL_ID:
      {
        Collection result = new ArrayList();
        StatemachineNavigatorGroup incominglinks = new StatemachineNavigatorGroup(
            Messages.NavigatorGroupName_GStartState_1002_incominglinks,
            "icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
        StatemachineNavigatorGroup outgoinglinks = new StatemachineNavigatorGroup(
            Messages.NavigatorGroupName_GStartState_1002_outgoinglinks,
            "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
        Collection connectedViews = getIncomingLinksByType(Collections
            .singleton(view), TransitionEditPart.VISUAL_ID);
        incominglinks.addChildren(createNavigatorItems(connectedViews,
            incominglinks, true));
        connectedViews = getOutgoingLinksByType(Collections.singleton(view),
            TransitionEditPart.VISUAL_ID);
        outgoinglinks.addChildren(createNavigatorItems(connectedViews,
            outgoinglinks, true));
        if(!incominglinks.isEmpty())
        {
          result.add(incominglinks);
        }
        if(!outgoinglinks.isEmpty())
        {
          result.add(outgoinglinks);
        }
        return result.toArray();
      }

      case GStopStateEditPart.VISUAL_ID:
      {
        Collection result = new ArrayList();
        StatemachineNavigatorGroup incominglinks = new StatemachineNavigatorGroup(
            Messages.NavigatorGroupName_GStopState_1003_incominglinks,
            "icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
        StatemachineNavigatorGroup outgoinglinks = new StatemachineNavigatorGroup(
            Messages.NavigatorGroupName_GStopState_1003_outgoinglinks,
            "icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
        Collection connectedViews = getIncomingLinksByType(Collections
            .singleton(view), TransitionEditPart.VISUAL_ID);
        incominglinks.addChildren(createNavigatorItems(connectedViews,
            incominglinks, true));
        connectedViews = getOutgoingLinksByType(Collections.singleton(view),
            TransitionEditPart.VISUAL_ID);
        outgoinglinks.addChildren(createNavigatorItems(connectedViews,
            outgoinglinks, true));
        if(!incominglinks.isEmpty())
        {
          result.add(incominglinks);
        }
        if(!outgoinglinks.isEmpty())
        {
          result.add(outgoinglinks);
        }
        return result.toArray();
      }

      case TransitionEditPart.VISUAL_ID:
      {
        Collection result = new ArrayList();
        StatemachineNavigatorGroup target = new StatemachineNavigatorGroup(
            Messages.NavigatorGroupName_Transition_3001_target,
            "icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
        StatemachineNavigatorGroup source = new StatemachineNavigatorGroup(
            Messages.NavigatorGroupName_Transition_3001_source,
            "icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
        Collection connectedViews = getLinksTargetByType(Collections
            .singleton(view), GStateEditPart.VISUAL_ID);
        target.addChildren(createNavigatorItems(connectedViews, target, true));
        connectedViews = getLinksTargetByType(Collections.singleton(view),
            GStartStateEditPart.VISUAL_ID);
        target.addChildren(createNavigatorItems(connectedViews, target, true));
        connectedViews = getLinksTargetByType(Collections.singleton(view),
            GStopStateEditPart.VISUAL_ID);
        target.addChildren(createNavigatorItems(connectedViews, target, true));
        connectedViews = getLinksSourceByType(Collections.singleton(view),
            GStateEditPart.VISUAL_ID);
        source.addChildren(createNavigatorItems(connectedViews, source, true));
        connectedViews = getLinksSourceByType(Collections.singleton(view),
            GStartStateEditPart.VISUAL_ID);
        source.addChildren(createNavigatorItems(connectedViews, source, true));
        connectedViews = getLinksSourceByType(Collections.singleton(view),
            GStopStateEditPart.VISUAL_ID);
        source.addChildren(createNavigatorItems(connectedViews, source, true));
        if(!target.isEmpty())
        {
          result.add(target);
        }
        if(!source.isEmpty())
        {
          result.add(source);
        }
        return result.toArray();
      }
    }
    return EMPTY_ARRAY;
  }

  /**
  * @generated
  */
  private Collection getLinksSourceByType(Collection edges, int visualID)
  {
    Collection result = new ArrayList();
    String type = StatemachineVisualIDRegistry.getType(visualID);
    for(Iterator it = edges.iterator(); it.hasNext();)
    {
      Edge nextEdge = (Edge) it.next();
      View nextEdgeSource = nextEdge.getSource();
      if(type.equals(nextEdgeSource.getType()) && isOwnView(nextEdgeSource))
      {
        result.add(nextEdgeSource);
      }
    }
    return result;
  }

  /**
  * @generated
  */
  private Collection getLinksTargetByType(Collection edges, int visualID)
  {
    Collection result = new ArrayList();
    String type = StatemachineVisualIDRegistry.getType(visualID);
    for(Iterator it = edges.iterator(); it.hasNext();)
    {
      Edge nextEdge = (Edge) it.next();
      View nextEdgeTarget = nextEdge.getTarget();
      if(type.equals(nextEdgeTarget.getType()) && isOwnView(nextEdgeTarget))
      {
        result.add(nextEdgeTarget);
      }
    }
    return result;
  }

  /**
  * @generated
  */
  private Collection getOutgoingLinksByType(Collection nodes, int visualID)
  {
    Collection result = new ArrayList();
    String type = StatemachineVisualIDRegistry.getType(visualID);
    for(Iterator it = nodes.iterator(); it.hasNext();)
    {
      View nextNode = (View) it.next();
      result.addAll(selectViewsByType(nextNode.getSourceEdges(), type));
    }
    return result;
  }

  /**
  * @generated
  */
  private Collection getIncomingLinksByType(Collection nodes, int visualID)
  {
    Collection result = new ArrayList();
    String type = StatemachineVisualIDRegistry.getType(visualID);
    for(Iterator it = nodes.iterator(); it.hasNext();)
    {
      View nextNode = (View) it.next();
      result.addAll(selectViewsByType(nextNode.getTargetEdges(), type));
    }
    return result;
  }

  /**
  * @generated
  */
  private Collection getChildrenByType(Collection nodes, int visualID)
  {
    Collection result = new ArrayList();
    String type = StatemachineVisualIDRegistry.getType(visualID);
    for(Iterator it = nodes.iterator(); it.hasNext();)
    {
      View nextNode = (View) it.next();
      result.addAll(selectViewsByType(nextNode.getChildren(), type));
    }
    return result;
  }

  /**
  * @generated
  */
  private Collection getDiagramLinksByType(Collection diagrams, int visualID)
  {
    Collection result = new ArrayList();
    String type = StatemachineVisualIDRegistry.getType(visualID);
    for(Iterator it = diagrams.iterator(); it.hasNext();)
    {
      Diagram nextDiagram = (Diagram) it.next();
      result.addAll(selectViewsByType(nextDiagram.getEdges(), type));
    }
    return result;
  }

  /**
  * @generated
  */
  private Collection selectViewsByType(Collection views, String type)
  {
    Collection result = new ArrayList();
    for(Iterator it = views.iterator(); it.hasNext();)
    {
      View nextView = (View) it.next();
      if(type.equals(nextView.getType()) && isOwnView(nextView))
      {
        result.add(nextView);
      }
    }
    return result;
  }

  /**
  * @generated
  */
  private boolean isOwnView(View view)
  {
    return GStatemachineEditPart.MODEL_ID.equals(StatemachineVisualIDRegistry
        .getModelID(view));
  }

  /**
  * @generated
  */
  private Collection createNavigatorItems(Collection views,
                                          Object parent,
                                          boolean isLeafs)
  {
    Collection result = new ArrayList();
    for(Iterator it = views.iterator(); it.hasNext();)
    {
      result.add(new StatemachineNavigatorItem((View) it.next(), parent,
          isLeafs));
    }
    return result;
  }

  /**
  * @generated
  */
  public Object getParent(Object element)
  {
    if(element instanceof StatemachineAbstractNavigatorItem)
    {
      StatemachineAbstractNavigatorItem abstractNavigatorItem = (StatemachineAbstractNavigatorItem) element;
      return abstractNavigatorItem.getParent();
    }
    return null;
  }

  /**
  * @generated
  */
  public boolean hasChildren(Object element)
  {
    return element instanceof IFile || getChildren(element).length > 0;
  }

}
