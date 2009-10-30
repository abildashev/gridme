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
package gmodel.diagram.edit.commands;

import gmodel.Connection;
import gmodel.GridElement;
import gmodel.Link;
import gmodel.Model;
import gmodel.diagram.edit.policies.GmodelBaseItemSemanticEditPolicy;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

/**
* @generated
*/
public class LinkReorientCommand extends EditElementCommand
{

  /**
  * @generated
  */
  private final int reorientDirection;

  /**
  * @generated
  */
  private final EObject oldEnd;

  /**
  * @generated
  */
  private final EObject newEnd;

  /**
  * @generated
  */
  public LinkReorientCommand(ReorientRelationshipRequest request)
  {
    super(request.getLabel(), request.getRelationship(), request);
    reorientDirection = request.getDirection();
    oldEnd = request.getOldRelationshipEnd();
    newEnd = request.getNewRelationshipEnd();
  }

  /**
  * @generated
  */
  public boolean canExecute()
  {
    if(false == getElementToEdit() instanceof Link)
    {
      return false;
    }
    if(reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE)
    {
      return canReorientSource();
    }
    if(reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET)
    {
      return canReorientTarget();
    }
    return false;
  }

  /**
  * @generated
  */
  protected boolean canReorientSource()
  {
    if(!(oldEnd instanceof Connection && newEnd instanceof Connection))
    {
      return false;
    }
    GridElement target = getLink().getTo();
    if(!(getLink().eContainer() instanceof Model))
    {
      return false;
    }
    Model container = (Model) getLink().eContainer();
    return GmodelBaseItemSemanticEditPolicy.LinkConstraints.canExistLink_4002(
        container, getNewSource(), target);
  }

  /**
  * @generated
  */
  protected boolean canReorientTarget()
  {
    if(!(oldEnd instanceof GridElement && newEnd instanceof GridElement))
    {
      return false;
    }
    Connection source = getLink().getFrom();
    if(!(getLink().eContainer() instanceof Model))
    {
      return false;
    }
    Model container = (Model) getLink().eContainer();
    return GmodelBaseItemSemanticEditPolicy.LinkConstraints.canExistLink_4002(
        container, source, getNewTarget());
  }

  /**
  * @generated
  */
  protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
      IAdaptable info) throws ExecutionException
  {
    if(!canExecute())
    {
      throw new ExecutionException("Invalid arguments in reorient link command"); //$NON-NLS-1$
    }
    if(reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE)
    {
      return reorientSource();
    }
    if(reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET)
    {
      return reorientTarget();
    }
    throw new IllegalStateException();
  }

  /**
  * @generated
  */
  protected CommandResult reorientSource() throws ExecutionException
  {
    getLink().setFrom(getNewSource());
    return CommandResult.newOKCommandResult(getLink());
  }

  /**
  * @generated
  */
  protected CommandResult reorientTarget() throws ExecutionException
  {
    getLink().setTo(getNewTarget());
    return CommandResult.newOKCommandResult(getLink());
  }

  /**
  * @generated
  */
  protected Link getLink()
  {
    return (Link) getElementToEdit();
  }

  /**
  * @generated
  */
  protected Connection getOldSource()
  {
    return (Connection) oldEnd;
  }

  /**
  * @generated
  */
  protected Connection getNewSource()
  {
    return (Connection) newEnd;
  }

  /**
  * @generated
  */
  protected GridElement getOldTarget()
  {
    return (GridElement) oldEnd;
  }

  /**
  * @generated
  */
  protected GridElement getNewTarget()
  {
    return (GridElement) newEnd;
  }
}
