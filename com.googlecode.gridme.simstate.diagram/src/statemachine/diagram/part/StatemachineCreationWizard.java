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
package statemachine.diagram.part;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
* @generated
*/
public class StatemachineCreationWizard extends Wizard implements INewWizard
{

  /**
  * @generated
  */
  private IWorkbench workbench;

  /**
  * @generated
  */
  protected IStructuredSelection selection;

  /**
  * @generated
  */
  protected StatemachineCreationWizardPage diagramModelFilePage;

  /**
  * @generated
  */
  protected Resource diagram;

  /**
  * @generated
  */
  private boolean openNewlyCreatedDiagramEditor = true;

  /**
  * @generated
  */
  public IWorkbench getWorkbench()
  {
    return workbench;
  }

  /**
  * @generated
  */
  public IStructuredSelection getSelection()
  {
    return selection;
  }

  /**
  * @generated
  */
  public final Resource getDiagram()
  {
    return diagram;
  }

  /**
  * @generated
  */
  public final boolean isOpenNewlyCreatedDiagramEditor()
  {
    return openNewlyCreatedDiagramEditor;
  }

  /**
  * @generated
  */
  public void setOpenNewlyCreatedDiagramEditor(boolean openNewlyCreatedDiagramEditor)
  {
    this.openNewlyCreatedDiagramEditor = openNewlyCreatedDiagramEditor;
  }

  /**
  * @generated
  */
  public void init(IWorkbench workbench, IStructuredSelection selection)
  {
    this.workbench = workbench;
    this.selection = selection;
    setWindowTitle(Messages.StatemachineCreationWizardTitle);
    setDefaultPageImageDescriptor(StatemachineDiagramEditorPlugin
        .getBundledImageDescriptor("icons/wizban/NewStatemachineWizard.gif")); //$NON-NLS-1$
    setNeedsProgressMonitor(true);
  }

  /**
  * @generated
  */
  public void addPages()
  {
    diagramModelFilePage = new StatemachineCreationWizardPage(
        "DiagramModelFile", getSelection(), "simstate"); //$NON-NLS-1$ //$NON-NLS-2$
    diagramModelFilePage
        .setTitle(Messages.StatemachineCreationWizard_DiagramModelFilePageTitle);
    diagramModelFilePage
        .setDescription(Messages.StatemachineCreationWizard_DiagramModelFilePageDescription);
    addPage(diagramModelFilePage);
  }

  /**
  * @generated
  */
  public boolean performFinish()
  {
    IRunnableWithProgress op = new WorkspaceModifyOperation(null)
    {

      protected void execute(IProgressMonitor monitor) throws CoreException,
                                                      InterruptedException
      {
        diagram = StatemachineDiagramEditorUtil.createDiagram(
            diagramModelFilePage.getURI(), monitor);
        if(isOpenNewlyCreatedDiagramEditor() && diagram != null)
        {
          try
          {
            StatemachineDiagramEditorUtil.openDiagram(diagram);
          }
          catch(PartInitException e)
          {
            ErrorDialog.openError(getContainer().getShell(),
                Messages.StatemachineCreationWizardOpenEditorError, null, e
                    .getStatus());
          }
        }
      }
    };
    try
    {
      getContainer().run(false, true, op);
    }
    catch(InterruptedException e)
    {
      return false;
    }
    catch(InvocationTargetException e)
    {
      if(e.getTargetException() instanceof CoreException)
      {
        ErrorDialog.openError(getContainer().getShell(),
            Messages.StatemachineCreationWizardCreationError, null,
            ((CoreException) e.getTargetException()).getStatus());
      }
      else
      {
        StatemachineDiagramEditorPlugin.getInstance().logError(
            "Error creating diagram", e.getTargetException()); //$NON-NLS-1$
      }
      return false;
    }
    return diagram != null;
  }
}
