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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipInputStream;

import com.googlecode.gridme.runtime.exceptions.LoggerException;

public abstract class ZipLogAnalyser
{
  protected final File path;
  
  public ZipLogAnalyser(File path) throws LoggerException
  {
    if(!path.exists())
    {
      throw new LoggerException("Database does not exist "
          + path.getAbsolutePath(), null);
    }
    this.path = path;
  }
  
  /**
   * Returns new reader from the zip input positioned at the
   * specified entry.
   * 
   * @param entry entry number, starting from 1.

   * @return new Reader
   */
  protected BufferedReader getDataReader(int entry) throws Exception
  {
    ZipInputStream zippi = new ZipInputStream(new FileInputStream(path));
    for(int i = 0; i < entry; i++)
    {
      zippi.getNextEntry();
    }
    return new BufferedReader(new InputStreamReader(zippi, "UTF-8"));
  }
}
