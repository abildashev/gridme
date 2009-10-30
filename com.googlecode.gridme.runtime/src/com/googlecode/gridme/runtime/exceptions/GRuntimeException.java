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
package com.googlecode.gridme.runtime.exceptions;

import java.io.PrintStream;

import com.googlecode.gridme.runtime.util.StringPrintStream;

@SuppressWarnings("serial")
public class GRuntimeException extends Exception
{
  public GRuntimeException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public GRuntimeException(String message)
  {
    super(message);
  }

  public GRuntimeException(Throwable cause)
  {
    super(cause.getMessage(), cause);
  }

  public String getUserMessageString()
  {
    return getMessageString(this);
  }

  private String getMessageString(Throwable cause)
  {
    if(cause.getMessage() != null)
    {
      return getMessage();
    }

    if(cause.getCause() != null)
    {
      return getMessageString(getCause());
    }

    StringPrintStream log = new StringPrintStream(); 
    cause.printStackTrace(new PrintStream(log));
        
    return cause.getClass().getName() + ": " + log.getLog();
  }
}
