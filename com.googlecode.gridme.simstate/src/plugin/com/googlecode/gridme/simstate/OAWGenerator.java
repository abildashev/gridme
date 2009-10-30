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

/**
 * Default code generator that takes the model and produces
 * a set of source files in the output folder. Default values
 * for workflow scipt, workflow slot and model slot can be 
 * changed by corresponding methods. 
 */
public class OAWGenerator implements CodeGenerator
{
  // worklow script
  protected String workflowFile = "workflow/generator.oaw";
  
  // workflow slot for generated files
  protected String outputDir = "src-gen";
  
  // workflow slot for model instance
  protected String modelSlot = "model";

  /**
   * Set workflow script name. 
   */
  public void setWorkflowFile(String workflowFile)
  {
    this.workflowFile = workflowFile;
  }

  /**
   * Workflow slot name for output destination.
   */
  public void setOutputDir(String outputDir)
  {
    this.outputDir = outputDir;
  }

  /**
   * Slot name for the model instance.
   */
  public void setModelFile(String modelFile)
  {
    this.modelSlot = modelFile;
  }

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
    properties.put(outputDir, outFolder.toString());
    properties.put(modelSlot, modelFile.getFullPath().toString());
    //System.out.println("Model: " + modelFile.getFullPath().toString());
    try
    {
      resources.setRuntimeLocation(modelFile.getWorkspace().getRoot());
      ResourceLoaderFactory.setCurrentThreadResourceLoader(resources);

      // Run workflow
      WorkflowRunner runner = new WorkflowRunner();
      if(!runner.prepare(workflowFile, null, properties)
          || !runner.executeWorkflow(slotMap, errors))
      {
        throw new GeneratorException("Code generation failed");
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
