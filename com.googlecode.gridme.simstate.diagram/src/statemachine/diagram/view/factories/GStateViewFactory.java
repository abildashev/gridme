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
package statemachine.diagram.view.factories;

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

import statemachine.diagram.edit.parts.GStateEditPart;
import statemachine.diagram.edit.parts.GStateNameEditPart;
import statemachine.diagram.edit.parts.GStatemachineEditPart;
import statemachine.diagram.part.StatemachineVisualIDRegistry;

/**
* @generated
*/
public class GStateViewFactory extends AbstractShapeViewFactory
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
  protected void decorateView(View containerView,
                              View view,
                              IAdaptable semanticAdapter,
                              String semanticHint,
                              int index,
                              boolean persisted)
  {
    if(semanticHint == null)
    {
      semanticHint = StatemachineVisualIDRegistry
          .getType(GStateEditPart.VISUAL_ID);
      view.setType(semanticHint);
    }
    super.decorateView(containerView, view, semanticAdapter, semanticHint,
        index, persisted);
    if(!GStatemachineEditPart.MODEL_ID.equals(StatemachineVisualIDRegistry
        .getModelID(containerView)))
    {
      EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE
          .createEAnnotation();
      shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
      shortcutAnnotation.getDetails().put(
          "modelID", GStatemachineEditPart.MODEL_ID); //$NON-NLS-1$
      view.getEAnnotations().add(shortcutAnnotation);
    }
    IAdaptable eObjectAdapter = null;
    EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
    if(eObject != null)
    {
      eObjectAdapter = new EObjectAdapter(eObject);
    }
    getViewService().createNode(eObjectAdapter, view,
        StatemachineVisualIDRegistry.getType(GStateNameEditPart.VISUAL_ID),
        ViewUtil.APPEND, true, getPreferencesHint());
  }
}
