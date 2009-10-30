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
package statemachine.diagram.parsers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

/**
* @generated
*/
public class CompositeParser implements IParser
{

  /**
  * @generated
  */
  private final IParser reader;

  /**
  * @generated
  */
  private final IParser writer;

  /**
  * @generated
  */
  public CompositeParser(IParser reader, IParser writer)
  {
    this.reader = reader;
    this.writer = writer;
  }

  /**
  * @generated
  */
  public boolean isAffectingEvent(Object event, int flags)
  {
    return reader.isAffectingEvent(event, flags);
  }

  /**
  * @generated
  */
  public String getPrintString(IAdaptable adapter, int flags)
  {
    return reader.getPrintString(adapter, flags);
  }

  /**
  * @generated
  */
  public String getEditString(IAdaptable adapter, int flags)
  {
    return reader.getEditString(adapter, flags);
  }

  /**
  * @generated
  */
  public IParserEditStatus isValidEditString(IAdaptable adapter,
                                             String editString)
  {
    return writer.isValidEditString(adapter, editString);
  }

  /**
  * @generated
  */
  public ICommand getParseCommand(IAdaptable adapter,
                                  String newString,
                                  int flags)
  {
    return writer.getParseCommand(adapter, newString, flags);
  }

  /**
  * @generated
  */
  public IContentAssistProcessor getCompletionProcessor(IAdaptable adapter)
  {
    return writer.getCompletionProcessor(adapter);
  }
}
