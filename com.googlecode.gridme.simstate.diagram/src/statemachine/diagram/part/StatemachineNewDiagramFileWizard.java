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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import statemachine.diagram.edit.parts.GStatemachineEditPart;

/**
* @generated
*/
public class StatemachineNewDiagramFileWizard extends Wizard
{

  /**
  * @generated
  */
  private WizardNewFileCreationPage myFileCreationPage;

  /**
  * @generated
  */
  private ModelElementSelectionPage diagramRootElementSelectionPage;

  /**
  * @generated
  */
  private TransactionalEditingDomain myEditingDomain;

  /**
  * @generated
  */
  public StatemachineNewDiagramFileWizard(
                                          URI domainModelURI,
                                          EObject diagramRoot,
                                          TransactionalEditingDomain editingDomain)
  {
    assert domainModelURI != null: "Domain model uri must be specified"; //$NON-NLS-1$
    assert diagramRoot != null: "Doagram root element must be specified"; //$NON-NLS-1$
    assert editingDomain != null: "Editing domain must be specified"; //$NON-NLS-1$

    myFileCreationPage = new WizardNewFileCreationPage(
        Messages.StatemachineNewDiagramFileWizard_CreationPageName,
        StructuredSelection.EMPTY);
    myFileCreationPage
        .setTitle(Messages.StatemachineNewDiagramFileWizard_CreationPageTitle);
    myFileCreationPage.setDescription(NLS.bind(
        Messages.StatemachineNewDiagramFileWizard_CreationPageDescription,
        GStatemachineEditPart.MODEL_ID));
    IPath filePath;
    String fileName = domainModelURI.trimFileExtension().lastSegment();
    if(domainModelURI.isPlatformResource())
    {
      filePath = new Path(domainModelURI.trimSegments(1).toPlatformString(true));
    }
    else if(domainModelURI.isFile())
    {
      filePath = new Path(domainModelURI.trimSegments(1).toFileString());
    }
    else
    {
      // TODO : use some default path
      throw new IllegalArgumentException("Unsupported URI: " + domainModelURI); //$NON-NLS-1$
    }
    myFileCreationPage.setContainerFullPath(filePath);
    myFileCreationPage.setFileName(StatemachineDiagramEditorUtil
        .getUniqueFileName(filePath, fileName, "simstate")); //$NON-NLS-1$

    diagramRootElementSelectionPage = new DiagramRootElementSelectionPage(
        Messages.StatemachineNewDiagramFileWizard_RootSelectionPageName);
    diagramRootElementSelectionPage
        .setTitle(Messages.StatemachineNewDiagramFileWizard_RootSelectionPageTitle);
    diagramRootElementSelectionPage
        .setDescription(Messages.StatemachineNewDiagramFileWizard_RootSelectionPageDescription);
    diagramRootElementSelectionPage.setModelElement(diagramRoot);

    myEditingDomain = editingDomain;
  }

  /**
  * @generated
  */
  public void addPages()
  {
    addPage(myFileCreationPage);
    addPage(diagramRootElementSelectionPage);
  }

  /**
  * @generated
  */
  public boolean performFinish()
  {
    List affectedFiles = new LinkedList();
    IFile diagramFile = myFileCreationPage.createNewFile();
    StatemachineDiagramEditorUtil.setCharset(diagramFile);
    affectedFiles.add(diagramFile);
    URI diagramModelURI = URI.createPlatformResourceURI(diagramFile
        .getFullPath().toString(), true);
    ResourceSet resourceSet = myEditingDomain.getResourceSet();
    final Resource diagramResource = resourceSet
        .createResource(diagramModelURI);
    AbstractTransactionalCommand command = new AbstractTransactionalCommand(
        myEditingDomain,
        Messages.StatemachineNewDiagramFileWizard_InitDiagramCommand,
        affectedFiles)
    {

      protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                                                  IAdaptable info) throws ExecutionException
      {
        int diagramVID = StatemachineVisualIDRegistry
            .getDiagramVisualID(diagramRootElementSelectionPage
                .getModelElement());
        if(diagramVID != GStatemachineEditPart.VISUAL_ID)
        {
          return CommandResult
              .newErrorCommandResult(Messages.StatemachineNewDiagramFileWizard_IncorrectRootError);
        }
        Diagram diagram = ViewService.createDiagram(
            diagramRootElementSelectionPage.getModelElement(),
            GStatemachineEditPart.MODEL_ID,
            StatemachineDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        diagramResource.getContents().add(diagram);
        diagramResource.getContents().add(diagram.getElement());
        return CommandResult.newOKCommandResult();
      }
    };
    try
    {
      OperationHistoryFactory.getOperationHistory().execute(command,
          new NullProgressMonitor(), null);
      diagramResource.save(StatemachineDiagramEditorUtil.getSaveOptions());
      StatemachineDiagramEditorUtil.openDiagram(diagramResource);
    }
    catch(ExecutionException e)
    {
      StatemachineDiagramEditorPlugin.getInstance().logError(
          "Unable to create model and diagram", e); //$NON-NLS-1$
    }
    catch(IOException ex)
    {
      StatemachineDiagramEditorPlugin.getInstance().logError(
          "Save operation failed for: " + diagramModelURI, ex); //$NON-NLS-1$
    }
    catch(PartInitException ex)
    {
      StatemachineDiagramEditorPlugin.getInstance().logError(
          "Unable to open editor", ex); //$NON-NLS-1$
    }
    return true;
  }

  /**
  * @generated
  */
  private static class DiagramRootElementSelectionPage extends
      ModelElementSelectionPage
  {

    /**
    * @generated
    */
    protected DiagramRootElementSelectionPage(String pageName)
    {
      super(pageName);
    }

    /**
    * @generated
    */
    protected String getSelectionTitle()
    {
      return Messages.StatemachineNewDiagramFileWizard_RootSelectionPageSelectionTitle;
    }

    /**
    * @generated
    */
    protected boolean validatePage()
    {
      if(selectedModelElement == null)
      {
        setErrorMessage(Messages.StatemachineNewDiagramFileWizard_RootSelectionPageNoSelectionMessage);
        return false;
      }
      boolean result = ViewService.getInstance().provides(
          new CreateDiagramViewOperation(new EObjectAdapter(
              selectedModelElement), GStatemachineEditPart.MODEL_ID,
              StatemachineDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT));
      setErrorMessage(result ? null
          : Messages.StatemachineNewDiagramFileWizard_RootSelectionPageInvalidSelectionMessage);
      return result;
    }
  }
}
