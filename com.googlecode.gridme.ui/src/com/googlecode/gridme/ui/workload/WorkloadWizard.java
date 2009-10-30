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
package com.googlecode.gridme.ui.workload;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.googlecode.gridme.ui.wizards.SimpleResourceWizard;

public class WorkloadWizard extends SimpleResourceWizard implements INewWizard
{
  protected IWorkbench workbench;
  protected WizardNewWorkloadCreationPage workloadPage;

  public WorkloadWizard()
  {
  }

  public IPath getSelectedContainer()
  {
    return mainPage.getContainerFullPath();
  }
  
  @Override
  public void addPages()
  {
    super.addPages();

    // Generator page
    workloadPage = new WizardNewWorkloadCreationPage("mainPage");
    workloadPage.setTitle("New workload file");
    workloadPage.setDescription("Runs a workload generator with specified "
        + "parameters to create a new workload file.");
    addPage(workloadPage);
  }

  @Override
  protected void createResource(IProgressMonitor monitor) throws Exception
  {
    IPath fullPath = getResourcePath();
//    if(fullPath.getFileExtension() == null)
//    {
//      fullPath = fullPath.addFileExtension("swfz");
//    }

    workloadPage.generate(monitor, ResourcesPlugin.getWorkspace().getRoot()
        .getFile(fullPath).getLocation());
  }

  @Override
  protected String getDefaultResourceName()
  {
    return "workload";
  }

  @Override
  protected String getDescription()
  {
    return "New workload file";
  }

  @Override
  protected String getResourceLabel()
  {
    return "Workload name:";
  }

  @Override
  protected String getTitle()
  {
    return "New workload";
  }

  @Override
  protected String getExt()
  {
    return "swfz";
  }
}
