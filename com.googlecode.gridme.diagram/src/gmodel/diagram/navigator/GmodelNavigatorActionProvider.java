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
package gmodel.diagram.navigator;

import gmodel.diagram.edit.parts.ModelEditPart;
import gmodel.diagram.part.GmodelDiagramEditor;
import gmodel.diagram.part.GmodelDiagramEditorPlugin;
import gmodel.diagram.part.GmodelVisualIDRegistry;
import gmodel.diagram.part.Messages;

import java.util.Iterator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.part.FileEditorInput;

/**
* @generated
*/
public class GmodelNavigatorActionProvider extends CommonActionProvider
{

  /**
  * @generated
  */
  private boolean myContribute;

  /**
  * @generated
  */
  private OpenDiagramAction myOpenDiagramAction;

  /**
  * @generated
  */
  public void init(ICommonActionExtensionSite aSite)
  {
    super.init(aSite);
    if(aSite.getViewSite() instanceof ICommonViewerWorkbenchSite)
    {
      myContribute = true;
      makeActions((ICommonViewerWorkbenchSite) aSite.getViewSite());
    }
    else
    {
      myContribute = false;
    }
  }

  /**
  * @generated
  */
  private void makeActions(ICommonViewerWorkbenchSite viewerSite)
  {
    myOpenDiagramAction = new OpenDiagramAction(viewerSite);
  }

  /**
  * @generated
  */
  public void fillActionBars(IActionBars actionBars)
  {
    if(!myContribute)
    {
      return;
    }
    IStructuredSelection selection = (IStructuredSelection) getContext()
        .getSelection();
    myOpenDiagramAction.selectionChanged(selection);
    if(myOpenDiagramAction.isEnabled())
    {
      actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN,
          myOpenDiagramAction);
    }
  }

  /**
  * @generated
  */
  public void fillContextMenu(IMenuManager menu)
  {
  }

  /**
  * @generated
  */
  private class OpenDiagramAction extends Action
  {

    /**
    * @generated
    */
    private Diagram myDiagram;

    /**
    * @generated
    */
    private ICommonViewerWorkbenchSite myViewerSite;

    /**
    * @generated
    */
    public OpenDiagramAction(ICommonViewerWorkbenchSite viewerSite)
    {
      super(Messages.NavigatorActionProvider_OpenDiagramActionName);
      myViewerSite = viewerSite;
    }

    /**
    * @generated
    */
    public final void selectionChanged(IStructuredSelection selection)
    {
      myDiagram = null;
      if(selection.size() == 1)
      {
        Object selectedElement = selection.getFirstElement();
        if(selectedElement instanceof GmodelNavigatorItem)
        {
          selectedElement = ((GmodelNavigatorItem) selectedElement).getView();
        }
        else if(selectedElement instanceof IAdaptable)
        {
          selectedElement = ((IAdaptable) selectedElement)
              .getAdapter(View.class);
        }
        if(selectedElement instanceof Diagram)
        {
          Diagram diagram = (Diagram) selectedElement;
          if(ModelEditPart.MODEL_ID.equals(GmodelVisualIDRegistry
              .getModelID(diagram)))
          {
            myDiagram = diagram;
          }
        }
      }
      setEnabled(myDiagram != null);
    }

    /**
    * @generated
    */
    public void run()
    {
      if(myDiagram == null || myDiagram.eResource() == null)
      {
        return;
      }

      IEditorInput editorInput = getEditorInput();
      IWorkbenchPage page = myViewerSite.getPage();
      try
      {
        page.openEditor(editorInput, GmodelDiagramEditor.ID);
      }
      catch(PartInitException e)
      {
        GmodelDiagramEditorPlugin.getInstance().logError(
            "Exception while openning diagram", e); //$NON-NLS-1$
      }
    }

    /**
    * @generated
    */
    private IEditorInput getEditorInput()
    {
      for(Iterator it = myDiagram.eResource().getContents().iterator(); it
          .hasNext();)
      {
        EObject nextEObject = (EObject) it.next();
        if(nextEObject == myDiagram)
        {
          return new FileEditorInput(WorkspaceSynchronizer.getFile(myDiagram
              .eResource()));
        }
        if(nextEObject instanceof Diagram)
        {
          break;
        }
      }
      URI uri = EcoreUtil.getURI(myDiagram);
      String editorName = uri.lastSegment()
          + "#" + myDiagram.eResource().getContents().indexOf(myDiagram); //$NON-NLS-1$
      IEditorInput editorInput = new URIEditorInput(uri, editorName);
      return editorInput;
    }

  }

}
