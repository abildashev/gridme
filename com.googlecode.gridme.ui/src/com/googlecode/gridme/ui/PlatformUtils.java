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
package com.googlecode.gridme.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class PlatformUtils
{
  /**
   * Creates a new file.
   */
  public static IFile createFile(String name, IContainer container,
      InputStream input, IProgressMonitor progressMonitor) throws Exception
  {
    IFile file = container.getFile(new Path(name));
    try
    {
      if(file.exists())
      {
        file.setContents(input, true, true, progressMonitor);
      }
      else
      {
        file.create(input, true, progressMonitor);
      }
      input.close();
    }
    finally
    {
      if(input != null)
      {
        input.close();
      }
    }
    return file;
  }

  /**
   * Creates a file resource with the root object.
   */
  public static void saveModelToFile(IPath modelPath, EObject rootObject)
      throws IOException, PartInitException
  {
    // Create a resource set
    ResourceSet resourceSet = new ResourceSetImpl();
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
        "xmi", new XMIResourceFactoryImpl());

    // Get the URI of the model file.
    URI fileURI = URI.createPlatformResourceURI(modelPath.toString(), true);
    // Create a resource for this file.
    Resource resource = resourceSet.createResource(fileURI);

    if(rootObject != null)
    {
      resource.getContents().add(rootObject);
    }

    // Save the contents of the resource to the file system.
    Map<Object, Object> options = new HashMap<Object, Object>();
    options.put(XMLResource.OPTION_ENCODING, "UTF8");
    resource.save(options);
  }

  public static EObject loadModelFromFile(IPath modelPath) throws IOException,
      PartInitException
  {
    ResourceSet resourceSet = new ResourceSetImpl();

    URI fileURI = URI.createPlatformResourceURI(modelPath.toString(), true);
    Resource resource = resourceSet.getResource(fileURI, true);

    EObject rootObj = resource.getContents().get(0);
    return rootObj;
  }

  public static void launchJavaClass(String className, String args,
      IProject project, String launchCfgName, String cfgId)
      throws CoreException
  {
    ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
    ILaunchConfigurationType type = manager.getLaunchConfigurationType(cfgId);

    // TODO: Reuse existing configurations instead of removing them
    ILaunchConfiguration[] configurations = manager
        .getLaunchConfigurations(type);
    for(int i = 0; i < configurations.length; i++)
    {
      ILaunchConfiguration configuration = configurations[i];
      if(configuration.getName().equals(launchCfgName))
      {
        configuration.delete();
        break;
      }
    }
    ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null,
        launchCfgName);

    workingCopy.setAttribute(
        IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, project.getName());
    workingCopy.setAttribute(
        IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, className);
    workingCopy.setAttribute(
        IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, args);
    workingCopy
        .setAttribute(IDebugUIConstants.ATTR_LAUNCH_IN_BACKGROUND, false);

    ILaunchConfiguration configuration = workingCopy.doSave();
    DebugUITools.launch(configuration, ILaunchManager.RUN_MODE);
  }

  public static void showErrorMessage(String message)
  {
    showErrorMessage("Error", message);
  }

  public static void showErrorMessage(String header, String message)
  {
    MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench()
        .getDisplay().getActiveShell(), SWT.ICON_ERROR | SWT.OK);
    messageBox.setText(header);
    messageBox.setMessage(message);
    messageBox.open();
  }

  public static boolean askYesNo(String header, String message)
  {
    MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench()
        .getDisplay().getActiveShell(), SWT.ICON_QUESTION | SWT.YES
        | SWT.NO);
    messageBox.setText(header);
    messageBox.setMessage(message);
    return messageBox.open() == SWT.YES;
  }
  
  public static void openEditor(IEditorInput editorInput, String editorID)
  {
    IWorkbenchWindow window = PlatformUI.getWorkbench()
        .getActiveWorkbenchWindow();
    if(window != null)
    {
      IWorkbenchPage activePage = window.getActivePage();
      if(activePage != null)
      {
        try
        {
          activePage.openEditor(editorInput, editorID);
        }
        catch(PartInitException e)
        {
          GridmeUIPlugin.logErrorMessage("Unable to open editor", e);
        }
      }
    }
  }
}
