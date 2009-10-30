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
import gmodel.GmodelFactory;
import gmodel.GmodelPackage;
import gmodel.GridElement;
import gmodel.Link;
import gmodel.Model;
import gmodel.diagram.edit.policies.GmodelBaseItemSemanticEditPolicy;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

/**
* @generated
*/
public class LinkCreateCommand extends CreateElementCommand
{

  /**
  * @generated
  */
  private final EObject source;

  /**
  * @generated
  */
  private final EObject target;

  /**
  * @generated
  */
  private Model container;

  /**
  * @generated
  */
  public LinkCreateCommand(CreateRelationshipRequest request, EObject source,
      EObject target)
  {
    super(request);
    this.source = source;
    this.target = target;
    if(request.getContainmentFeature() == null)
    {
      setContainmentFeature(GmodelPackage.eINSTANCE.getModel_Elements());
    }

    // Find container element for the new link.
    // Climb up by containment hierarchy starting from the source
    // and return the first element that is instance of the container class.
    for(EObject element = source; element != null; element = element
        .eContainer())
    {
      if(element instanceof Model)
      {
        container = (Model) element;
        super.setElementToEdit(container);
        break;
      }
    }
  }

  /**
  * @generated
  */
  public boolean canExecute()
  {
    if(source == null && target == null)
    {
      return false;
    }
    if(source != null && false == source instanceof Connection)
    {
      return false;
    }
    if(target != null && false == target instanceof GridElement)
    {
      return false;
    }
    if(getSource() == null)
    {
      return true; // link creation is in progress; source is not defined yet
    }
    // target may be null here but it's possible to check constraint
    if(getContainer() == null)
    {
      return false;
    }
    return GmodelBaseItemSemanticEditPolicy.LinkConstraints.canCreateLink_4002(
        getContainer(), getSource(), getTarget());
  }

  /**
  * @generated
  */
  protected EObject doDefaultElementCreation()
  {
    Link newElement = GmodelFactory.eINSTANCE.createLink();
    getContainer().getElements().add(newElement);
    newElement.setFrom(getSource());
    newElement.setTo(getTarget());
    return newElement;
  }

  /**
  * @generated
  */
  protected EClass getEClassToEdit()
  {
    return GmodelPackage.eINSTANCE.getModel();
  }

  /**
  * @generated
  */
  protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
      IAdaptable info) throws ExecutionException
  {
    if(!canExecute())
    {
      throw new ExecutionException("Invalid arguments in create link command"); //$NON-NLS-1$
    }
    return super.doExecuteWithResult(monitor, info);
  }

  /**
  * @generated
  */
  protected ConfigureRequest createConfigureRequest()
  {
    ConfigureRequest request = super.createConfigureRequest();
    request.setParameter(CreateRelationshipRequest.SOURCE, getSource());
    request.setParameter(CreateRelationshipRequest.TARGET, getTarget());
    return request;
  }

  /**
  * @generated
  */
  protected void setElementToEdit(EObject element)
  {
    throw new UnsupportedOperationException();
  }

  /**
  * @generated
  */
  protected Connection getSource()
  {
    return (Connection) source;
  }

  /**
  * @generated
  */
  protected GridElement getTarget()
  {
    return (GridElement) target;
  }

  /**
  * @generated
  */
  public Model getContainer()
  {
    return container;
  }
}
