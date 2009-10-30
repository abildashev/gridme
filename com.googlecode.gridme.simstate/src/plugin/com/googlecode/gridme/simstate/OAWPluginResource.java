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

import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class OAWPluginResource implements OAWResourceLocation
{
  private Bundle plugin;
  private String internalPath;

  public OAWPluginResource(String pluginId, String internalPath)
  {
    plugin = Platform.getBundle(pluginId);
    this.internalPath = internalPath;
  }

  public URL getResource(String file)
  {
    URL result = null;

    // Try to get plugin resource
    if(plugin != null)
    {
      HashMap<String, String> keys = new HashMap<String, String>();
      result = FileLocator.find(plugin, new Path(internalPath + Path.SEPARATOR
          + file), keys);
    }
    return result;
  }
}
