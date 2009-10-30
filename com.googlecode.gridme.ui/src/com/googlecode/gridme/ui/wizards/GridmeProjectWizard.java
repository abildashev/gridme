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
package com.googlecode.gridme.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.googlecode.gridme.ui.GridmeUIPlugin;
import com.googlecode.gridme.ui.PlatformUtils;

public class GridmeProjectWizard extends Wizard implements INewWizard
{
  private static final String DEFAULT_PROJECT_NAME = "GridmeProject";

  protected WizardNewProjectCreationPage mainPage;

  public GridmeProjectWizard()
  {
  }

  public void addPages()
  {
    super.addPages();
    mainPage = new WizardNewProjectCreationPage("gpage1");
    mainPage.setTitle("New Grid modeling project");
    mainPage.setDescription("Creates new empty Grid modeling project");
    mainPage.setInitialProjectName(DEFAULT_PROJECT_NAME);
    addPage(mainPage);
  }

  @Override
  public boolean performFinish()
  {
    try
    {
      if(mainPage.isPageComplete())
      {
        final String pname = mainPage.getProjectName();

        PlatformUI.getWorkbench().getProgressService().run(true, true,
            new IRunnableWithProgress()
            {
              public void run(IProgressMonitor monitor)
                  throws InvocationTargetException, InterruptedException
              {
                try
                {
                  IProject project = createGridmeProject(pname, monitor);
                  if(project != null)
                  {
                    project.open(monitor);
                  }
                }
                catch(Exception e)
                {
                  GridmeUIPlugin.logException(e);
                }
              }
            });
        return true;
      }
    }
    catch(Exception e)
    {
      GridmeUIPlugin.logException(e);
    }
    return false;
  }

  private IProject createGridmeProject(String projectName,
      IProgressMonitor monitor) throws Exception
  {
    IProject project = null;
    IWorkspace workspace = ResourcesPlugin.getWorkspace();
    project = workspace.getRoot().getProject(projectName);

    // Clean up any old project information.
    if(!project.exists())
    {
      IProjectDescription projectDescription = ResourcesPlugin.getWorkspace()
          .newProjectDescription(projectName);
      projectDescription.setLocation(null);
      project.create(projectDescription, monitor);
      projectDescription.setNatureIds(new String[] { GridmeUIPlugin.ID
          + ".GridmeNature" });
      project.open(monitor);
      project.setDescription(projectDescription, monitor);
    }

    return project;
  }

  @Override
  public void init(IWorkbench workbench, IStructuredSelection selection)
  {
  }
}
