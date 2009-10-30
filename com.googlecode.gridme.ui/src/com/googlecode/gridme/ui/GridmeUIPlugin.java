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

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.baseadaptor.loader.BaseClassLoader;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class GridmeUIPlugin extends AbstractUIPlugin
{
  private static GridmeUIPlugin instance;
  public static final String ID = "com.googlecode.gridme.ui";

  public GridmeUIPlugin()
  {
  }

  public void stop(BundleContext context) throws Exception
  {
    instance = null;
    super.stop(context);
  }

  /**
   * 
   */
  public void start(BundleContext context) throws Exception
  {
    super.start(context);
    instance = this;
  }

  public static GridmeUIPlugin getInstance()
  {
    return instance;
  }

  public static void logException(Throwable e)
  {
    instance.getLog().log(new Status(IStatus.ERROR, ID, e.getMessage(), e));
  }

  public static void logErrorMessage(String message, Throwable cause)
  {
    instance.getLog().log(new Status(IStatus.ERROR, ID, message, cause));
  }

  public static void logErrorMessage(String message)
  {
    logErrorMessage(message, null);
  }

  public static void logWarningMessage(String message, Throwable cause)
  {
    instance.getLog().log(new Status(IStatus.WARNING, ID, message, cause));
  }

  public static void logWarningMessage(String message)
  {
    instance.getLog().log(new Status(IStatus.WARNING, ID, message));
  }
  
  public static void logInfoMessage(String message)
  {
    instance.getLog().log(new Status(IStatus.INFO, ID, message));
  }

  public static ImageDescriptor getImageDescriptor(String name)
  {
    try
    {
      URL iconsURL = getInstance().getBundle().getEntry("/icons/");
      URL url = new URL(iconsURL, name);
      return ImageDescriptor.createFromURL(url);
    }
    catch(MalformedURLException e)
    {
      return ImageDescriptor.getMissingImageDescriptor();
    }
  }
}
