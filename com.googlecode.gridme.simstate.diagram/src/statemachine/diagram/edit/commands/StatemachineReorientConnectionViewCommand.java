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

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class StatemachineReorientConnectionViewCommand extends
    AbstractTransactionalCommand
{

  /**
  * @generated
  */
  private IAdaptable edgeAdaptor;

  /**
  * @generated
  */
  public StatemachineReorientConnectionViewCommand(
                                                   TransactionalEditingDomain editingDomain,
                                                   String label)
  {
    super(editingDomain, label, null);
  }

  /**
  * @generated
  */
  public List getAffectedFiles()
  {
    View view = (View) edgeAdaptor.getAdapter(View.class);
    if(view != null)
    {
      return getWorkspaceFiles(view);
    }
    return super.getAffectedFiles();
  }

  /**
  * @generated
  */
  public IAdaptable getEdgeAdaptor()
  {
    return edgeAdaptor;
  }

  /**
  * @generated
  */
  public void setEdgeAdaptor(IAdaptable edgeAdaptor)
  {
    this.edgeAdaptor = edgeAdaptor;
  }

  /**
  * @generated
  */
  protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor,
                                              IAdaptable info)
  {
    assert null != edgeAdaptor: "Null child in StatemachineReorientConnectionViewCommand"; //$NON-NLS-1$
    Edge edge = (Edge) getEdgeAdaptor().getAdapter(Edge.class);
    assert null != edge: "Null edge in StatemachineReorientConnectionViewCommand"; //$NON-NLS-1$
    View tempView = edge.getSource();
    edge.setSource(edge.getTarget());
    edge.setTarget(tempView);
    return CommandResult.newOKCommandResult();
  }
}
