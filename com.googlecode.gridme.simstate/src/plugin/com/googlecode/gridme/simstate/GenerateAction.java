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
package com.googlecode.gridme.simstate;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

public class GenerateAction implements IObjectActionDelegate
{
  private IStructuredSelection selectedFiles;

  /**
   * @see IActionDelegate#run(IAction)
   */
  public void run(IAction action)
  {
    if(selectedFiles != null)
    {
      final Issues issues = new IssuesImpl();
      final IPath outFolder = GeneratorUtils.getOutputPath((IFile) selectedFiles.getFirstElement());

      if(outFolder != null)
      {
        try
        {
          // Generate code for each file.
          for(Iterator it = selectedFiles.iterator(); it.hasNext();)
          {
            final IFile file = (IFile) it.next();
            
            file.deleteMarkers(IMarker.PROBLEM, true,
                IResource.DEPTH_INFINITE);

            PlatformUI.getWorkbench().getProgressService().run(true, true,
                new IRunnableWithProgress()
                {
                  public void run(IProgressMonitor monitor) throws InvocationTargetException,
                                                           InterruptedException
                  {
                    try
                    {
                      new SimStateGenerator().generate(file, outFolder,
                          issues, SimStatePlugin.getInstance()
                              .getResourceLoader(), monitor);
                    }
                    catch(Exception e)
                    {
                      SimStatePlugin.logException(e);
                    }
                  }
                });
            
            GeneratorUtils.createErrorMarkers(issues, file);
          }
        }
        catch(Exception e)
        {
          SimStatePlugin.logException(e);
        }
      }
    }
  }

  /**
   * @see IActionDelegate#selectionChanged(IAction, ISelection)
   */
  public void selectionChanged(IAction action, ISelection selection)
  {
    if(!(selection instanceof IStructuredSelection) || selection.isEmpty())
    {
      action.setEnabled(false);
      selectedFiles = null;
    }
    else
    {
      action.setEnabled(true);
      selectedFiles = (IStructuredSelection) selection;
    }
  }

  @Override
  public void setActivePart(IAction action, IWorkbenchPart targetPart)
  {
  }
}
