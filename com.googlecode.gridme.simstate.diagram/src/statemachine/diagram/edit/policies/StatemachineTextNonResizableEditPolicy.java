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
package statemachine.diagram.edit.policies;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.NonResizableEditPolicyEx;
import org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;

/**
* @generated
*/
public class StatemachineTextNonResizableEditPolicy extends
    NonResizableEditPolicyEx
{

  /**
  * @generated
  */
  private IFigure selectionFeedbackFigure;

  /**
  * @generated
  */
  private IFigure focusFeedbackFigure;

  /**
  * @generated
  */
  protected void showPrimarySelection()
  {
    if(getHostFigure() instanceof WrapLabel)
    {
      ((WrapLabel) getHostFigure()).setSelected(true);
      ((WrapLabel) getHostFigure()).setFocus(true);
    }
    else
    {
      showSelection();
      showFocus();
    }
  }

  /**
  * @generated
  */
  protected void showSelection()
  {
    if(getHostFigure() instanceof WrapLabel)
    {
      ((WrapLabel) getHostFigure()).setSelected(true);
      ((WrapLabel) getHostFigure()).setFocus(false);
    }
    else
    {
      hideSelection();
      addFeedback(selectionFeedbackFigure = createSelectionFeedbackFigure());
      refreshSelectionFeedback();
      hideFocus();
    }
  }

  /**
  * @generated
  */
  protected void hideSelection()
  {
    if(getHostFigure() instanceof WrapLabel)
    {
      ((WrapLabel) getHostFigure()).setSelected(false);
      ((WrapLabel) getHostFigure()).setFocus(false);
    }
    else
    {
      if(selectionFeedbackFigure != null)
      {
        removeFeedback(selectionFeedbackFigure);
        selectionFeedbackFigure = null;
      }
      hideFocus();
    }
  }

  /**
  * @generated
  */
  protected void showFocus()
  {
    if(getHostFigure() instanceof WrapLabel)
    {
      ((WrapLabel) getHostFigure()).setFocus(true);
    }
    else
    {
      hideFocus();
      addFeedback(focusFeedbackFigure = createFocusFeedbackFigure());
      refreshFocusFeedback();
    }
  }

  /**
  * @generated
  */
  protected void hideFocus()
  {
    if(getHostFigure() instanceof WrapLabel)
    {
      ((WrapLabel) getHostFigure()).setFocus(false);
    }
    else
    {
      if(focusFeedbackFigure != null)
      {
        removeFeedback(focusFeedbackFigure);
        focusFeedbackFigure = null;
      }
    }
  }

  /**
  * @generated
  */
  protected Rectangle getFeedbackBounds()
  {
    Rectangle bounds;
    if(getHostFigure() instanceof Label)
    {
      bounds = ((Label) getHostFigure()).getTextBounds();
      bounds.intersect(getHostFigure().getBounds());
    }
    else
    {
      bounds = getHostFigure().getBounds().getCopy();
    }
    getHostFigure().getParent().translateToAbsolute(bounds);
    getFeedbackLayer().translateToRelative(bounds);
    return bounds;
  }

  /**
  * @generated
  */
  protected IFigure createSelectionFeedbackFigure()
  {
    if(getHostFigure() instanceof Label)
    {
      Label feedbackFigure = new Label();
      feedbackFigure.setOpaque(true);
      feedbackFigure.setBackgroundColor(ColorConstants.menuBackgroundSelected);
      feedbackFigure.setForegroundColor(ColorConstants.menuForegroundSelected);
      return feedbackFigure;
    }
    else
    {
      RectangleFigure feedbackFigure = new RectangleFigure();
      feedbackFigure.setFill(false);
      return feedbackFigure;
    }
  }

  /**
  * @generated
  */
  protected IFigure createFocusFeedbackFigure()
  {
    return new Figure()
    {

      protected void paintFigure(Graphics graphics)
      {
        graphics.drawFocus(getBounds().getResized(-1, -1));
      }
    };
  }

  /**
  * @generated
  */
  protected void updateLabel(Label target)
  {
    Label source = (Label) getHostFigure();
    target.setText(source.getText());
    target.setTextAlignment(source.getTextAlignment());
    target.setFont(source.getFont());
  }

  /**
  * @generated
  */
  protected void refreshSelectionFeedback()
  {
    if(selectionFeedbackFigure != null)
    {
      if(selectionFeedbackFigure instanceof Label)
      {
        updateLabel((Label) selectionFeedbackFigure);
        selectionFeedbackFigure.setBounds(getFeedbackBounds());
      }
      else
      {
        selectionFeedbackFigure.setBounds(getFeedbackBounds().expand(5, 5));
      }
    }
  }

  /**
  * @generated
  */
  protected void refreshFocusFeedback()
  {
    if(focusFeedbackFigure != null)
    {
      focusFeedbackFigure.setBounds(getFeedbackBounds());
    }
  }

  /**
  * @generated
  */
  public void refreshFeedback()
  {
    refreshSelectionFeedback();
    refreshFocusFeedback();
  }

  /**
  * @generated
  */
  protected List createSelectionHandles()
  {
    MoveHandle moveHandle = new MoveHandle((GraphicalEditPart) getHost());
    moveHandle.setBorder(null);
    moveHandle.setDragTracker(new DragEditPartsTrackerEx(getHost()));
    return Collections.singletonList(moveHandle);
  }
}
