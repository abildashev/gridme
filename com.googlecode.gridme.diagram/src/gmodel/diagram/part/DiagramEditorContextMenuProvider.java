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
package gmodel.diagram.part;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.ContributionItemService;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContextMenuProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchPart;

/**
* @generated
*/
public class DiagramEditorContextMenuProvider extends
    DiagramContextMenuProvider
{

  /**
  * @generated
  */
  private IWorkbenchPart part;

  /**
  * @generated
  */
  private DeleteElementAction deleteAction;

  /**
  * @generated
  */
  public DiagramEditorContextMenuProvider(IWorkbenchPart part,
      EditPartViewer viewer)
  {
    super(part, viewer);
    this.part = part;
    deleteAction = new DeleteElementAction(part);
    deleteAction.init();
  }

  /**
  * @generated
  */
  public void dispose()
  {
    if(deleteAction != null)
    {
      deleteAction.dispose();
      deleteAction = null;
    }
    super.dispose();
  }

  /**
  * @generated
  */
  public void buildContextMenu(final IMenuManager menu)
  {
    getViewer().flush();
    try
    {
      TransactionUtil.getEditingDomain(
          (EObject) getViewer().getContents().getModel()).runExclusive(
          new Runnable()
          {

            public void run()
            {
              ContributionItemService.getInstance().contributeToPopupMenu(
                  DiagramEditorContextMenuProvider.this, part);
              menu.remove(ActionIds.ACTION_DELETE_FROM_MODEL);
              menu.appendToGroup("editGroup", deleteAction);
            }
          });
    }
    catch(Exception e)
    {
      GmodelDiagramEditorPlugin.getInstance().logError(
          "Error building context menu", e);
    }
  }
}
