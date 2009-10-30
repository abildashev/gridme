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
package gmodel.diagram.view.factories;

import gmodel.diagram.edit.parts.GridElementEditPart;
import gmodel.diagram.edit.parts.GridElementNameEditPart;
import gmodel.diagram.edit.parts.ModelEditPart;
import gmodel.diagram.part.GmodelVisualIDRegistry;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class GridElementViewFactory extends AbstractShapeViewFactory
{

  /**
  * @generated
  */
  protected List createStyles(View view)
  {
    List styles = new ArrayList();
    styles.add(NotationFactory.eINSTANCE.createShapeStyle());
    return styles;
  }

  /**
  * @generated
  */
  protected void decorateView(View containerView, View view,
      IAdaptable semanticAdapter, String semanticHint, int index,
      boolean persisted)
  {
    if(semanticHint == null)
    {
      semanticHint = GmodelVisualIDRegistry
          .getType(GridElementEditPart.VISUAL_ID);
      view.setType(semanticHint);
    }
    super.decorateView(containerView, view, semanticAdapter, semanticHint,
        index, persisted);
    if(!ModelEditPart.MODEL_ID.equals(GmodelVisualIDRegistry
        .getModelID(containerView)))
    {
      EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE
          .createEAnnotation();
      shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
      shortcutAnnotation.getDetails().put("modelID", ModelEditPart.MODEL_ID); //$NON-NLS-1$
      view.getEAnnotations().add(shortcutAnnotation);
    }
    IAdaptable eObjectAdapter = null;
    EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
    if(eObject != null)
    {
      eObjectAdapter = new EObjectAdapter(eObject);
    }
    getViewService().createNode(eObjectAdapter, view,
        GmodelVisualIDRegistry.getType(GridElementNameEditPart.VISUAL_ID),
        ViewUtil.APPEND, true, getPreferencesHint());
  }
}
