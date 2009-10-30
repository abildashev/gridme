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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class SimStatePlugin extends AbstractUIPlugin
{
  // The plug-in ID
  public static final String PLUGIN_ID = "com.googlecode.gridme.simstate";
  // The shared instance
  private static SimStatePlugin instance;
  // OAW resource loader
  private OawEclipseProjectResourceLoader resourceLoader;

  /**
   * 
   */
  public void start(BundleContext context) throws Exception
  {
    super.start(context);
    instance = this;
    resourceLoader = new OawEclipseProjectResourceLoader();
    // Add resource locations for generator and model plugins.
    resourceLoader.addResourceLocation(new OAWPluginResource(PLUGIN_ID, "src"));
  }

  /**
   * 
   */
  public void stop(BundleContext context) throws Exception
  {
    instance = null;
    super.stop(context);
  }

  /**
   * @return the shared instance
   */
  public static SimStatePlugin getInstance()
  {
    return instance;
  }

  public OawEclipseProjectResourceLoader getResourceLoader()
  {
    return resourceLoader;
  }

  public static void logException(Exception e)
  {
    assert(instance != null);
    
    instance.getLog().log(
        new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e.getCause()));
  }
}
