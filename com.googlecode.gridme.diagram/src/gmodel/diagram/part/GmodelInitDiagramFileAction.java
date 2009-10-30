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

import gmodel.diagram.edit.parts.ModelEditPart;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
* @generated
*/
public class GmodelInitDiagramFileAction implements IObjectActionDelegate
{

  /**
  * @generated
  */
  private IWorkbenchPart targetPart;

  /**
  * @generated
  */
  private URI domainModelURI;

  /**
  * @generated
  */
  public void setActivePart(IAction action, IWorkbenchPart targetPart)
  {
    this.targetPart = targetPart;
  }

  /**
  * @generated
  */
  public void selectionChanged(IAction action, ISelection selection)
  {
    domainModelURI = null;
    action.setEnabled(false);
    if(selection instanceof IStructuredSelection == false
        || selection.isEmpty())
    {
      return;
    }
    IFile file = (IFile) ((IStructuredSelection) selection).getFirstElement();
    domainModelURI = URI.createPlatformResourceURI(file.getFullPath()
        .toString(), true);
    action.setEnabled(true);
  }

  /**
  * @generated
  */
  private Shell getShell()
  {
    return targetPart.getSite().getShell();
  }

  /**
  * @generated
  */
  public void run(IAction action)
  {
    TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
        .createEditingDomain();
    ResourceSet resourceSet = editingDomain.getResourceSet();
    EObject diagramRoot = null;
    try
    {
      Resource resource = resourceSet.getResource(domainModelURI, true);
      diagramRoot = (EObject) resource.getContents().get(0);
    }
    catch(WrappedException ex)
    {
      GmodelDiagramEditorPlugin.getInstance().logError(
          "Unable to load resource: " + domainModelURI, ex); //$NON-NLS-1$
    }
    if(diagramRoot == null)
    {
      MessageDialog
          .openError(
              getShell(),
              Messages.GmodelInitDiagramFileAction_InitDiagramFileResourceErrorDialogTitle,
              Messages.GmodelInitDiagramFileAction_InitDiagramFileResourceErrorDialogMessage);
      return;
    }
    Wizard wizard = new GmodelNewDiagramFileWizard(domainModelURI, diagramRoot,
        editingDomain);
    wizard.setWindowTitle(NLS.bind(
        Messages.GmodelInitDiagramFileAction_InitDiagramFileWizardTitle,
        ModelEditPart.MODEL_ID));
    GmodelDiagramEditorUtil.runWizard(getShell(), wizard, "InitDiagramFile"); //$NON-NLS-1$
  }
}
