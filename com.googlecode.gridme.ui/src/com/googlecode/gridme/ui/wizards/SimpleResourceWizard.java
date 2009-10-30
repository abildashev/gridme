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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import com.googlecode.gridme.ui.GridmeUIPlugin;

public abstract class SimpleResourceWizard extends Wizard implements INewWizard
{
  protected WizardNewFileCreationPage mainPage;
  private IPath resourceName;
  protected IStructuredSelection selection;

  public SimpleResourceWizard()
  {
    setNeedsProgressMonitor(true);
  }

  protected abstract String getResourceLabel();

  protected abstract String getDefaultResourceName();

  protected abstract String getTitle();

  protected abstract String getDescription();
  
  protected abstract String getExt();

  protected abstract void createResource(IProgressMonitor monitor)
      throws Exception;

  @Override
  public void addPages()
  {
    super.addPages();
    
    mainPage = new WizardNewFileCreationPage("filePage", selection)
    {
      @Override
      protected String getNewFileLabel()
      {
        return getResourceLabel();
      }
    };
    mainPage.setTitle(getTitle());
    mainPage.setDescription(getDescription());
    mainPage.setFileName(getDefaultResourceName());
    mainPage.setFileExtension(getExt());
    addPage(mainPage);
  }

  @Override
  public boolean performFinish()
  {
    try
    {
      if(mainPage.isPageComplete())
      {
        resourceName = mainPage.getContainerFullPath().append(mainPage.getFileName());

        PlatformUI.getWorkbench().getProgressService().run(true, true,
            new IRunnableWithProgress()
            {
              public void run(IProgressMonitor monitor)
                  throws InvocationTargetException, InterruptedException
              {
                try
                {
                  createResource(monitor);
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

  @Override
  public void init(IWorkbench workbench, IStructuredSelection selection)
  {
    this.selection = selection;
  }

  protected IPath getResourcePath()
  {
    return resourceName;
  }

  public IStructuredSelection getSelection()
  {
    return selection;
  }

}
