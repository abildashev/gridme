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

import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.openarchitectureware.workflow.WorkflowRunner;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.util.ResourceLoaderFactory;


public class SimStateGenerator implements CodeGenerator
{
  // Bundled resources and settings
  private static final String WORKFLOW_FILE = "workflow/runtime.oaw"; // worklow script
  private static final String SRC_GEN = "src-gen"; // workflow slot
  private static final String MODEL_FILE = "model"; // workflow slot

  @Override
  public void generate(IFile modelFile,
                       IPath outFolder,
                       Issues errors,
                       OawEclipseProjectResourceLoader resources,
                       IProgressMonitor monitor) throws Exception
  {
    monitor.beginTask("Generating code", 100);
    monitor.worked(10);
    // prepare workflow execution
    HashMap<String, String> properties = new HashMap<String, String>();
    HashMap<String, ?> slotMap = new HashMap<String, Object>();
    properties.put(SRC_GEN, outFolder.toString());
    properties.put(MODEL_FILE, modelFile.getFullPath().toString());
    //System.out.println("Model: " + modelFile.getFullPath().toString());
    try
    {
      resources.setRuntimeLocation(modelFile.getWorkspace().getRoot());
      ResourceLoaderFactory.setCurrentThreadResourceLoader(resources);

      // Run workflow
      WorkflowRunner runner = new WorkflowRunner();
      final boolean configOK = runner.prepare(WORKFLOW_FILE, null, properties);
      if(configOK)
      {
        runner.executeWorkflow(slotMap, errors);
      }
    }
    finally
    {
      ResourceLoaderFactory.setCurrentThreadResourceLoader(null);
    }
    monitor.worked(90);
    monitor.done();
  }
}
