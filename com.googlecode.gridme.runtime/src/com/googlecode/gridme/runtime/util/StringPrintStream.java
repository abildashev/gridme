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
package com.googlecode.gridme.runtime.util;

import java.io.OutputStream;

public class StringPrintStream extends OutputStream
{
  String log;

  public StringPrintStream()
  {
    super();
    log = "";
  }

  public void close()
  {
  }

  public void flush()
  {
  }

  public void write(byte[] b)
  {
    log += new String(b);
  }

  public void write(byte[] b, int off, int len)
  {
    log += new String(b, off, len);
  }

  public void write(int b)
  {
    log += b;
  }

  public String getLog()
  {
    return log;
  }
}
