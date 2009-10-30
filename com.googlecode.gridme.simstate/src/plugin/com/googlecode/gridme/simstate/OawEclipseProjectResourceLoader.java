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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.openarchitectureware.workflow.util.ResourceLoaderDefaultImpl;

public class OawEclipseProjectResourceLoader extends ResourceLoaderDefaultImpl
{
  Vector<OAWResourceLocation> searchPathList;
  IResource runtimeLocation;

  public OawEclipseProjectResourceLoader() throws CoreException
  {
    super();
    searchPathList = new Vector<OAWResourceLocation>();
  }

  /**
   * Runtime location will be reset each time before
   * running workflow. 
   */
  public void setRuntimeLocation(IResource loc)
  {
    runtimeLocation = loc;
  }
  
  public void addResourceLocation(OAWResourceLocation location)
  {
    searchPathList.add(location);
  }
  
  @Override
  protected URL internalGetResource(String path)
  {
    URL result = null;
    // try all plugin locations
    for(int i = 0; i < searchPathList.size(); i++)
    {
      result = searchPathList.elementAt(i).getResource(path);
      if(result != null)
      {
        break;
      }
    }
    // now try runtime location
    if(result == null)
    {
      IFile target = ((IContainer)runtimeLocation).getFile(new Path(path));
      if(target != null && target.getLocationURI() != null)
      {
        try
        {
          result = target.getLocationURI().toURL();
        }
        catch(MalformedURLException e)
        {
          e.printStackTrace(System.out);
        }
      }
    }
    return result;
  }

  @Override
  protected InputStream internalGetResourceAsStream(String file)
  {
    InputStream result = null;
    try
    {
      URL ir = internalGetResource(file);
      if(ir != null)
      {
        result = ir.openStream();
      }
    }
    catch(Throwable e)
    {
      e.printStackTrace(System.out);
    }
    return result;
  }
}
