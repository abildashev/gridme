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

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.openarchitectureware.workflow.issues.Issues;

/**
 * Various generation utilities.
 */
public class GeneratorUtils
{
  /**
   * Checks whether this file contains an experiment definition.
   */
  public static boolean isExperimentFile(IFile file)
  {
    return "gme".equals(file.getFileExtension());
  }

  /**
   * Checks whether this file contains a model definition.
   */
  public static boolean isModelFile(IFile file)
  {
    return "gmm".equals(file.getFileExtension());
  }

  /**
   * Loads emf model from file
   */
  private static EObject loadResource(IFile file)
  {
    ResourceSet resourceSet = new ResourceSetImpl();
    URI fileURI = URI.createFileURI(file.getLocation().toString());
    Resource poResource = resourceSet.getResource(fileURI, true);
    return poResource.getContents().get(0);
  }

  /**
   * Checks whether this file contains a visualizer definition.
   */
  public static boolean isVisualizerFile(IFile file)
  {
    return "gmv".equals(file.getFileExtension());
  }

  public static void createErrorMarkers(Issues issues, IFile selectedFile)
      throws Exception
  {
    //Create markers for errors and warnings
    for(int i = 0; i < issues.getErrors().length; i++)
    {
      IMarker marker = selectedFile.createMarker(IMarker.PROBLEM);
      marker.setAttribute(IMarker.MESSAGE, issues.getErrors()[i].getMessage());
      marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
    }

    for(int i = 0; i < issues.getWarnings().length; i++)
    {
      IMarker marker = selectedFile.createMarker(IMarker.PROBLEM);
      marker
          .setAttribute(IMarker.MESSAGE, issues.getWarnings()[i].getMessage());
      marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
    }

  }

  /**
   * Opens a dialog to ask for a destination folder.
   */
  public static IPath getOutputPath(IFile selectedFile)
  {
    ContainerSelectionDialog dialog = new ContainerSelectionDialog(Display
        .getCurrent().getActiveShell(), selectedFile.getParent(), true,
        "Select output folder");

    dialog.open();
    Object[] outPath = dialog.getResult();
    IPath oPath = null;

    if(outPath != null)
    {
      // is it bad?
      oPath = new Path(selectedFile.getWorkspace().getRoot().getLocation()
          .toString()
          + Path.SEPARATOR + outPath[0]);
    }
    return oPath;
  }

  /**
   * Constructs a generated source file name for the experiment. 
   */
  public static IFile getExperimentSourceFile(String name, IProject project)
  {
    return project.getFile(new Path("gsrc" + Path.SEPARATOR + name + ".java"));
  }

  public static String getFileNameWOExtension(String name)
  {
    return name.split("\\.")[0];
  }

  public static String resourceName(EObject object)
  {
    return getFileNameWOExtension((new File(object.eResource().getURI()
        .toFileString())).getName());
  }
}
