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
package gmodel.diagram.part;

import gmodel.diagram.navigator.GmodelNavigatorItem;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;

/**
* @generated
*/
public class GmodelDiagramEditor extends DiagramDocumentEditor implements
    IGotoMarker
{

  /**
  * @generated
  */
  public static final String ID = "gmodel.diagram.part.GmodelDiagramEditorID"; //$NON-NLS-1$

  /**
  * @generated
  */
  public static final String CONTEXT_ID = "gmodel.diagram.ui.diagramContext"; //$NON-NLS-1$

  /**
  * @generated
  */
  public GmodelDiagramEditor()
  {
    super(true);
  }

  /**
  * @generated
  */
  protected String getContextID()
  {
    return CONTEXT_ID;
  }

  /**
  * @generated
  */
  protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot)
  {
    PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
    new GmodelPaletteFactory().fillPalette(root);
    return root;
  }

  /**
  * @generated
  */
  protected PreferencesHint getPreferencesHint()
  {
    return GmodelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
  }

  /**
  * @generated
  */
  public String getContributorId()
  {
    return GmodelDiagramEditorPlugin.ID;
  }

  /**
  * @generated
  */
  public Object getAdapter(Class type)
  {
    if(type == IShowInTargetList.class)
    {
      return new IShowInTargetList()
      {
        public String[] getShowInTargetIds()
        {
          return new String[] { ProjectExplorer.VIEW_ID };
        }
      };
    }
    return super.getAdapter(type);
  }

  /**
  * @generated
  */
  protected IDocumentProvider getDocumentProvider(IEditorInput input)
  {
    if(input instanceof IFileEditorInput || input instanceof URIEditorInput)
    {
      return GmodelDiagramEditorPlugin.getInstance().getDocumentProvider();
    }
    return super.getDocumentProvider(input);
  }

  /**
  * @generated
  */
  public TransactionalEditingDomain getEditingDomain()
  {
    IDocument document = getEditorInput() != null ? getDocumentProvider()
        .getDocument(getEditorInput()) : null;
    if(document instanceof IDiagramDocument)
    {
      return ((IDiagramDocument) document).getEditingDomain();
    }
    return super.getEditingDomain();
  }

  /**
  * @generated
  */
  protected void setDocumentProvider(IEditorInput input)
  {
    if(input instanceof IFileEditorInput || input instanceof URIEditorInput)
    {
      setDocumentProvider(GmodelDiagramEditorPlugin.getInstance()
          .getDocumentProvider());
    }
    else
    {
      super.setDocumentProvider(input);
    }
  }

  /**
  * @generated
  */
  public void gotoMarker(IMarker marker)
  {
    MarkerNavigationService.getInstance().gotoMarker(this, marker);
  }

  /**
  * @generated
  */
  public boolean isSaveAsAllowed()
  {
    return true;
  }

  /**
  * @generated
  */
  public void doSaveAs()
  {
    performSaveAs(new NullProgressMonitor());
  }

  /**
  * @generated
  */
  protected void performSaveAs(IProgressMonitor progressMonitor)
  {
    Shell shell = getSite().getShell();
    IEditorInput input = getEditorInput();
    SaveAsDialog dialog = new SaveAsDialog(shell);
    IFile original = input instanceof IFileEditorInput ? ((IFileEditorInput) input)
        .getFile()
        : null;
    if(original != null)
    {
      dialog.setOriginalFile(original);
    }
    dialog.create();
    IDocumentProvider provider = getDocumentProvider();
    if(provider == null)
    {
      // editor has been programmatically closed while the dialog was open
      return;
    }
    if(provider.isDeleted(input) && original != null)
    {
      String message = NLS.bind(Messages.GmodelDiagramEditor_SavingDeletedFile,
          original.getName());
      dialog.setErrorMessage(null);
      dialog.setMessage(message, IMessageProvider.WARNING);
    }
    if(dialog.open() == Window.CANCEL)
    {
      if(progressMonitor != null)
      {
        progressMonitor.setCanceled(true);
      }
      return;
    }
    IPath filePath = dialog.getResult();
    if(filePath == null)
    {
      if(progressMonitor != null)
      {
        progressMonitor.setCanceled(true);
      }
      return;
    }
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    IFile file = workspaceRoot.getFile(filePath);
    final IEditorInput newInput = new FileEditorInput(file);
    // Check if the editor is already open
    IEditorMatchingStrategy matchingStrategy = getEditorDescriptor()
        .getEditorMatchingStrategy();
    IEditorReference[] editorRefs = PlatformUI.getWorkbench()
        .getActiveWorkbenchWindow().getActivePage().getEditorReferences();
    for(int i = 0; i < editorRefs.length; i++)
    {
      if(matchingStrategy.matches(editorRefs[i], newInput))
      {
        MessageDialog.openWarning(shell,
            Messages.GmodelDiagramEditor_SaveAsErrorTitle,
            Messages.GmodelDiagramEditor_SaveAsErrorMessage);
        return;
      }
    }
    boolean success = false;
    try
    {
      provider.aboutToChange(newInput);
      getDocumentProvider(newInput).saveDocument(progressMonitor, newInput,
          getDocumentProvider().getDocument(getEditorInput()), true);
      success = true;
    }
    catch(CoreException x)
    {
      IStatus status = x.getStatus();
      if(status == null || status.getSeverity() != IStatus.CANCEL)
      {
        ErrorDialog.openError(shell,
            Messages.GmodelDiagramEditor_SaveErrorTitle,
            Messages.GmodelDiagramEditor_SaveErrorMessage, x.getStatus());
      }
    }
    finally
    {
      provider.changed(newInput);
      if(success)
      {
        setInput(newInput);
      }
    }
    if(progressMonitor != null)
    {
      progressMonitor.setCanceled(!success);
    }
  }

  /**
  * @generated
  */
  public ShowInContext getShowInContext()
  {
    return new ShowInContext(getEditorInput(), getNavigatorSelection());
  }

  /**
  * @generated
  */
  private ISelection getNavigatorSelection()
  {
    IDiagramDocument document = getDiagramDocument();
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
  protected void configureGraphicalViewer()
  {
    super.configureGraphicalViewer();
    DiagramEditorContextMenuProvider provider = new DiagramEditorContextMenuProvider(
        this, getDiagramGraphicalViewer());
    getDiagramGraphicalViewer().setContextMenu(provider);
    getSite().registerContextMenu(ActionIds.DIAGRAM_EDITOR_CONTEXT_MENU,
        provider, getDiagramGraphicalViewer());
  }

}
