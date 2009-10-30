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
package com.googlecode.gridme.runtime.log.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.googlecode.gridme.runtime.exceptions.LoggerException;

public abstract class TextBasedLogger
{
  public static final String ENCODING = "ISO-8859-1";
  
  protected PrintStream out;
  protected ZipOutputStream zippo;

  public TextBasedLogger(String firstZipEntry, File path, boolean force) throws LoggerException
  {
    if(path.exists() && !force)
    {
      throw new LoggerException("Database already exists "
          + path.getAbsolutePath(), null);
    }
    try
    {
      zippo = new ZipOutputStream(new FileOutputStream(path));
      zippo.putNextEntry(new ZipEntry(firstZipEntry));
      out = new PrintStream(zippo, false, "UTF-8");
    }
    catch(Exception e)
    {
      throw new LoggerException("Database create error "
          + path.getAbsolutePath(), e);
    }
  }
  
  /**
   * Deletes database files.
   *
   * @param path db path
   * @throws LoggerException 
   */
  public static void deleteDatabase(File path) throws LoggerException
  {
    // The directory is now empty so delete it
    if(path.exists() && !path.delete())
    {
      throw new LoggerException("Unable to delete database", null);
    }
  }
}
