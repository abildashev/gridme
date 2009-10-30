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
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.util.MeasurementUnitHelper;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractLabelViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class TransitionNameViewFactory extends AbstractLabelViewFactory
{

  /**
  * @generated
  */
  public View createView(IAdaptable semanticAdapter,
                         View containerView,
                         String semanticHint,
                         int index,
                         boolean persisted,
                         PreferencesHint preferencesHint)
  {
    Node view = (Node) super.createView(semanticAdapter, containerView,
        semanticHint, index, persisted, preferencesHint);
    Location location = (Location) view.getLayoutConstraint();
    IMapMode mapMode = MeasurementUnitHelper.getMapMode(containerView
        .getDiagram().getMeasurementUnit());
    location.setX(mapMode.DPtoLP(0));
    location.setY(mapMode.DPtoLP(40));
    return view;
  }

  /**
  * @generated
  */
  protected List createStyles(View view)
  {
    List styles = new ArrayList();
    return styles;
  }
}
