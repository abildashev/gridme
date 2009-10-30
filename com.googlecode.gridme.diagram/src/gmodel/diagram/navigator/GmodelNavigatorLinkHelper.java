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

import gmodel.diagram.part.GmodelDiagramEditorPlugin;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;
import org.eclipse.ui.part.FileEditorInput;

/**
* @generated
*/
public class GmodelNavigatorLinkHelper implements ILinkHelper
{

  /**
  * @generated
  */
  private static IEditorInput getEditorInput(Diagram diagram)
  {
    Resource diagramResource = diagram.eResource();
    for(Iterator it = diagramResource.getContents().iterator(); it.hasNext();)
    {
      EObject nextEObject = (EObject) it.next();
      if(nextEObject == diagram)
      {
        return new FileEditorInput(WorkspaceSynchronizer
            .getFile(diagramResource));
      }
      if(nextEObject instanceof Diagram)
      {
        break;
      }
    }
    URI uri = EcoreUtil.getURI(diagram);
    String editorName = uri.lastSegment()
        + "#" + diagram.eResource().getContents().indexOf(diagram); //$NON-NLS-1$
    IEditorInput editorInput = new URIEditorInput(uri, editorName);
    return editorInput;
  }

  /**
  * @generated
  */
  public IStructuredSelection findSelection(IEditorInput anInput)
  {
    IDiagramDocument document = GmodelDiagramEditorPlugin.getInstance()
        .getDocumentProvider().getDiagramDocument(anInput);
    if(document == null)
    {
      return StructuredSelection.EMPTY;
    }
    Diagram diagram = document.getDiagram();
    IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
    if(file != null)
    {
      GmodelNavigatorItem item = new GmodelNavigatorItem(diagram, file, false);
      return new StructuredSelection(item);
    }
    return StructuredSelection.EMPTY;
  }

  /**
  * @generated
  */
  public void activateEditor(IWorkbenchPage aPage,
      IStructuredSelection aSelection)
  {
    if(aSelection == null || aSelection.isEmpty())
    {
      return;
    }
    if(false == aSelection.getFirstElement() instanceof GmodelAbstractNavigatorItem)
    {
      return;
    }

    GmodelAbstractNavigatorItem abstractNavigatorItem = (GmodelAbstractNavigatorItem) aSelection
        .getFirstElement();
    View navigatorView = null;
    if(abstractNavigatorItem instanceof GmodelNavigatorItem)
    {
      navigatorView = ((GmodelNavigatorItem) abstractNavigatorItem).getView();
    }
    else if(abstractNavigatorItem instanceof GmodelNavigatorGroup)
    {
      GmodelNavigatorGroup navigatorGroup = (GmodelNavigatorGroup) abstractNavigatorItem;
      if(navigatorGroup.getParent() instanceof GmodelNavigatorItem)
      {
        navigatorView = ((GmodelNavigatorItem) navigatorGroup.getParent())
            .getView();
      }
    }
    if(navigatorView == null)
    {
      return;
    }
    IEditorInput editorInput = getEditorInput(navigatorView.getDiagram());
    IEditorPart editor = aPage.findEditor(editorInput);
    if(editor == null)
    {
      return;
    }
    aPage.bringToTop(editor);
    if(editor instanceof DiagramEditor)
    {
      DiagramEditor diagramEditor = (DiagramEditor) editor;
      ResourceSet diagramEditorResourceSet = diagramEditor.getEditingDomain()
          .getResourceSet();
      EObject selectedView = diagramEditorResourceSet.getEObject(EcoreUtil
          .getURI(navigatorView), true);
      if(selectedView == null)
      {
        return;
      }
      GraphicalViewer graphicalViewer = (GraphicalViewer) diagramEditor
          .getAdapter(GraphicalViewer.class);
      EditPart selectedEditPart = (EditPart) graphicalViewer
          .getEditPartRegistry().get(selectedView);
      if(selectedEditPart != null)
      {
        graphicalViewer.select(selectedEditPart);
      }
    }
  }

}
