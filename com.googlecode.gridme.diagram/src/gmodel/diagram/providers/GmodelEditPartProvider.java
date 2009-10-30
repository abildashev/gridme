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
package gmodel.diagram.providers;

import gmodel.diagram.edit.parts.GmodelEditPartFactory;
import gmodel.diagram.edit.parts.ModelEditPart;
import gmodel.diagram.part.GmodelVisualIDRegistry;

import java.lang.ref.WeakReference;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateGraphicEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class GmodelEditPartProvider extends AbstractEditPartProvider
{

  /**
  * @generated
  */
  private EditPartFactory factory;

  /**
  * @generated
  */
  private boolean allowCaching;

  /**
  * @generated
  */
  private WeakReference cachedPart;

  /**
  * @generated
  */
  private WeakReference cachedView;

  /**
  * @generated
  */
  public GmodelEditPartProvider()
  {
    setFactory(new GmodelEditPartFactory());
    setAllowCaching(true);
  }

  /**
  * @generated
  */
  public final EditPartFactory getFactory()
  {
    return factory;
  }

  /**
  * @generated
  */
  protected void setFactory(EditPartFactory factory)
  {
    this.factory = factory;
  }

  /**
  * @generated
  */
  public final boolean isAllowCaching()
  {
    return allowCaching;
  }

  /**
  * @generated
  */
  protected synchronized void setAllowCaching(boolean allowCaching)
  {
    this.allowCaching = allowCaching;
    if(!allowCaching)
    {
      cachedPart = null;
      cachedView = null;
    }
  }

  /**
  * @generated
  */
  protected IGraphicalEditPart createEditPart(View view)
  {
    EditPart part = factory.createEditPart(null, view);
    if(part instanceof IGraphicalEditPart)
    {
      return (IGraphicalEditPart) part;
    }
    return null;
  }

  /**
  * @generated
  */
  protected IGraphicalEditPart getCachedPart(View view)
  {
    if(cachedView != null && cachedView.get() == view)
    {
      return (IGraphicalEditPart) cachedPart.get();
    }
    return null;
  }

  /**
  * @generated
  */
  public synchronized IGraphicalEditPart createGraphicEditPart(View view)
  {
    if(isAllowCaching())
    {
      IGraphicalEditPart part = getCachedPart(view);
      cachedPart = null;
      cachedView = null;
      if(part != null)
      {
        return part;
      }
    }
    return createEditPart(view);
  }

  /**
  * @generated
  */
  public synchronized boolean provides(IOperation operation)
  {
    if(operation instanceof CreateGraphicEditPartOperation)
    {
      View view = ((IEditPartOperation) operation).getView();
      if(!ModelEditPart.MODEL_ID
          .equals(GmodelVisualIDRegistry.getModelID(view)))
      {
        return false;
      }
      if(isAllowCaching() && getCachedPart(view) != null)
      {
        return true;
      }
      IGraphicalEditPart part = createEditPart(view);
      if(part != null)
      {
        if(isAllowCaching())
        {
          cachedPart = new WeakReference(part);
          cachedView = new WeakReference(view);
        }
        return true;
      }
    }
    return false;
  }
}
