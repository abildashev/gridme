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

import gmodel.Model;
import gmodel.impl.GmodelFactoryImpl;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.googlecode.gridme.ui.PlatformUtils;

public class ModelWizard extends SimpleResourceWizard
{
  @Override
  protected void createResource(IProgressMonitor monitor) throws Exception
  {
    Model mdl = GmodelFactoryImpl.eINSTANCE.createModel();
    
    IPath fullPath = getResourcePath();
//    if(fullPath.getFileExtension() == null)
//    {
//      fullPath = fullPath.addFileExtension("gmm");
//    }
    
    PlatformUtils.saveModelToFile(fullPath, mdl);
  }

  @Override
  protected String getDefaultResourceName()
  {
    return "MyGridModel";
  }

  @Override
  protected String getDescription()
  {
    return "Creates new empty Grid model";
  }

  @Override
  protected String getTitle()
  {
    return "New Grid model";
  }

  @Override
  protected String getResourceLabel()
  {
    return "Model name:";
  }

  @Override
  protected String getExt()
  {
    return "gmm";
  }
}
