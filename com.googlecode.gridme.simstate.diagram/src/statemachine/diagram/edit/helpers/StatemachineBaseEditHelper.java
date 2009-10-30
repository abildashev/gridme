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
package statemachine.diagram.edit.helpers;

import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

/**
* @generated
*/
public class StatemachineBaseEditHelper extends AbstractEditHelper
{

  /**
  * @generated
  */
  public static final String EDIT_POLICY_COMMAND = "edit policy command"; //$NON-NLS-1$

  /**
  * @generated
  */
  protected ICommand getInsteadCommand(IEditCommandRequest req)
  {
    ICommand epCommand = (ICommand) req.getParameter(EDIT_POLICY_COMMAND);
    req.setParameter(EDIT_POLICY_COMMAND, null);
    ICommand ehCommand = super.getInsteadCommand(req);
    if(epCommand == null)
    {
      return ehCommand;
    }
    if(ehCommand == null)
    {
      return epCommand;
    }
    CompositeCommand command = new CompositeCommand(null);
    command.add(epCommand);
    command.add(ehCommand);
    return command;
  }

  /**
  * @generated
  */
  protected ICommand getCreateCommand(CreateElementRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected ICommand getCreateRelationshipCommand(CreateRelationshipRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected ICommand getDestroyElementCommand(DestroyElementRequest req)
  {
    return null;
  }

  /**
  * @generated
  */
  protected ICommand getDestroyReferenceCommand(DestroyReferenceRequest req)
  {
    return null;
  }
}
