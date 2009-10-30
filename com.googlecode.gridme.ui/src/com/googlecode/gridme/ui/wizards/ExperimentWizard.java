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

import gexperiment.Experiment;
import gexperiment.impl.GexperimentFactoryImpl;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.googlecode.gridme.ui.PlatformUtils;

public class ExperimentWizard extends SimpleResourceWizard
{
  @Override
  protected void createResource(IProgressMonitor monitor) throws Exception
  {
    Experiment exp = GexperimentFactoryImpl.eINSTANCE.createExperiment();
    
    IPath fullPath = getResourcePath();
//    if(fullPath.getFileExtension() == null)
//    {
//      fullPath = fullPath.addFileExtension("gme");
//    }
    
    PlatformUtils.saveModelToFile(fullPath, exp);
  }

  @Override
  protected String getDefaultResourceName()
  {
    return "MyExperiment";
  }

  @Override
  protected String getDescription()
  {
    return "Creates new empty Grid model experiment";
  }

  @Override
  protected String getTitle()
  {
    return "New Grid model experiment";
  }

  @Override
  protected String getResourceLabel()
  {
    return "Experiment name:";
  }

  @Override
  protected String getExt()
  {
    return "gme";
  }
}
