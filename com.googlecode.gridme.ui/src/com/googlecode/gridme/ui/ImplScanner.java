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

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.osgi.framework.Bundle;
import org.scannotation.AnnotationDB;

import com.googlecode.gridme.runtime.ImplementationDescription;
import com.googlecode.gridme.runtime.ModelElementImplementationDescription;

/**
 * The scanner class that can be used to find all
 * implementation classes with GridMe specific annotations.
 */
public class ImplScanner implements Preferences.IPropertyChangeListener
{
  private static ImplScanner instance;
  private AnnotationDB adb;
  private static URLClassLoader cloader;

  public static ImplScanner getInstance()
  {
    if(instance == null)
    {
      instance = new ImplScanner();
      GridmeUIPlugin.getInstance().getPluginPreferences()
          .addPropertyChangeListener(instance);
      try
      {
        instance.buildIndex();
      }
      catch(Exception e)
      {
        GridmeUIPlugin.logErrorMessage("Unable to build a list of "
            + "available implementation classes", e);
      }
    }
    return instance;
  }

  private ImplScanner()
  {
    adb = new AnnotationDB();
  }

  public ClassLoader getClassLoader()
  {
    return cloader;
  }

  /**
   * Scans the classpath and builds annotation indexes.
   */
  private void buildIndex() throws Exception
  {
    ArrayList<URL> urls = new ArrayList<URL>();

    // Get paths from preferences
    Preferences prefs = GridmeUIPlugin.getInstance().getPluginPreferences();
    String[] prefPaths = prefs.getString(GridmePreferencePage.CLASSPATH).split(
        File.pathSeparator);
    for(String str : prefPaths)
    {
      File file = new File(str);
      try
      {
        URL u = file.toURI().toURL();
        urls.add(u);
      }
      catch(MalformedURLException e)
      {
        GridmeUIPlugin.logErrorMessage("Can not scan the path " + str + " for available implementation classes.");
      }
    }

    // Add urls to classloader
    cloader = new URLClassLoader(urls.toArray(new URL[urls.size()]), getClass()
        .getClassLoader());

    // Add eclipse classpath from gridme runtime bundle
    Bundle runtimeBundle = Platform.getBundle("com.googlecode.gridme.runtime");
    URL u = runtimeBundle.getEntry("/");
    if(u != null)
    {
      urls.add(FileLocator.resolve(u));
    }
    else
    {
      GridmeUIPlugin.logErrorMessage("Unable to scan com.googlecode.gridme.runtime for available implementation classes.");
    }
    adb.scanArchives(urls.toArray(new URL[urls.size()]));
  }

  /**
   * @return All classes that have ImplementationDescription
   * annotation assigned.
   */
  public Set<String> getImplementations(int category)
  {
    Set<String> all = adb.getAnnotationIndex().get(
        ImplementationDescription.class.getCanonicalName());
    Set<String> sel = new HashSet<String>();
    if(all != null)
    {
      for(String iname : all)
      {
        ImplementationInfo info = getImplementationInfo(iname);
        if(info != null && info.getCategory() == category)
        {
          sel.add(iname);
        }
      }
    }

    return sel;
  }

  /**
   * @return All classes that have ModelElementImplementationDescription
   * annotation assigned.
   */
  public Set<String> getModelElements()
  {
    return adb.getAnnotationIndex().get(
        ModelElementImplementationDescription.class.getCanonicalName());
  }

  public ImplementationInfo getImplementationInfo(String className)
  {
    ImplementationInfo result = null;
    try
    {
      //Class c = Class.forName(className);
      Class c = getClassLoader().loadClass(className);

      Annotation info = c
          .getAnnotation(ModelElementImplementationDescription.class);
      if(info != null)
      {
        result = new ImplementationInfo(c.getSimpleName(),
            ((ModelElementImplementationDescription) info).value(), 0);
      }
      else
      {
        info = c.getAnnotation(ImplementationDescription.class);
        result = new ImplementationInfo(c.getSimpleName(),
            ((ImplementationDescription) info).value(),
            ((ImplementationDescription) info).category());
      }
    }
    catch(Exception e)
    {
    }
    return result;
  }

  @Override
  public void propertyChange(PropertyChangeEvent event)
  {
    if(event.getProperty().equals(GridmePreferencePage.CLASSPATH))
    {
      try
      {
        instance.buildIndex();
      }
      catch(Exception e)
      {
        GridmeUIPlugin.logErrorMessage("Unable to build a list of "
            + "available implementation classes", e);
      }
    }
  }
}
