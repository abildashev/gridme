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
package statemachine.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import statemachine.GAbstractState;
import statemachine.GCompositeState;
import statemachine.Transition;
import statemachine.diagram.edit.policies.StatemachineBaseItemSemanticEditPolicy;

/**
* @generated
*/
public class TransitionReorientCommand extends EditElementCommand
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
  public TransitionReorientCommand(ReorientRelationshipRequest request)
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
    if(!(getElementToEdit() instanceof Transition))
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
    if(!(oldEnd instanceof GAbstractState && newEnd instanceof GAbstractState))
    {
      return false;
    }
    GAbstractState target = getLink().getTo();
    if(!(getLink().eContainer() instanceof GCompositeState))
    {
      return false;
    }
    GCompositeState container = (GCompositeState) getLink().eContainer();
    return StatemachineBaseItemSemanticEditPolicy.LinkConstraints
        .canExistTransition_3001(container, getNewSource(), target);
  }

  /**
  * @generated
  */
  protected boolean canReorientTarget()
  {
    if(!(oldEnd instanceof GAbstractState && newEnd instanceof GAbstractState))
    {
      return false;
    }
    GAbstractState source = getLink().getFrom();
    if(!(getLink().eContainer() instanceof GCompositeState))
    {
      return false;
    }
    GCompositeState container = (GCompositeState) getLink().eContainer();
    return StatemachineBaseItemSemanticEditPolicy.LinkConstraints
        .canExistTransition_3001(container, source, getNewTarget());
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
  protected Transition getLink()
  {
    return (Transition) getElementToEdit();
  }

  /**
  * @generated
  */
  protected GAbstractState getOldSource()
  {
    return (GAbstractState) oldEnd;
  }

  /**
  * @generated
  */
  protected GAbstractState getNewSource()
  {
    return (GAbstractState) newEnd;
  }

  /**
  * @generated
  */
  protected GAbstractState getOldTarget()
  {
    return (GAbstractState) oldEnd;
  }

  /**
  * @generated
  */
  protected GAbstractState getNewTarget()
  {
    return (GAbstractState) newEnd;
  }
}
