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
package statemachine.diagram.part;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;

/**
* @generated
*/
public class StatemachineMatchingStrategy implements IEditorMatchingStrategy
{

  /**
  * @generated
  */
  public boolean matches(IEditorReference editorRef, IEditorInput input)
  {
    IEditorInput editorInput;
    try
    {
      editorInput = editorRef.getEditorInput();
    }
    catch(PartInitException e)
    {
      return false;
    }

    if(editorInput.equals(input))
    {
      return true;
    }
    if(editorInput instanceof URIEditorInput && input instanceof URIEditorInput)
    {
      return ((URIEditorInput) editorInput).getURI().equals(
          ((URIEditorInput) input).getURI());
    }
    return false;
  }

}
