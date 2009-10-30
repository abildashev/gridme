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
package gmodel.diagram.edit.parts;

import gmodel.diagram.part.GmodelVisualIDRegistry;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
* @generated
*/
public class GmodelEditPartFactory implements EditPartFactory
{

  /**
  * @generated
  */
  public EditPart createEditPart(EditPart context, Object model)
  {
    if(model instanceof View)
    {
      View view = (View) model;
      switch(GmodelVisualIDRegistry.getVisualID(view))
      {

        case ModelEditPart.VISUAL_ID:
          return new ModelEditPart(view);

        case ConnectionEditPart.VISUAL_ID:
          return new ConnectionEditPart(view);

        case ConnectionNameEditPart.VISUAL_ID:
          return new ConnectionNameEditPart(view);

        case GridElementEditPart.VISUAL_ID:
          return new GridElementEditPart(view);

        case GridElementNameEditPart.VISUAL_ID:
          return new GridElementNameEditPart(view);

        case LinkEditPart.VISUAL_ID:
          return new LinkEditPart(view);
      }
    }
    return createUnrecognizedEditPart(context, model);
  }

  /**
  * @generated
  */
  private EditPart createUnrecognizedEditPart(EditPart context, Object model)
  {
    // Handle creation of unrecognized child node EditParts here
    return null;
  }

  /**
  * @generated
  */
  public static CellEditorLocator getTextCellEditorLocator(
      ITextAwareEditPart source)
  {
    if(source.getFigure() instanceof WrappingLabel)
      return new TextCellEditorLocator((WrappingLabel) source.getFigure());
    else
    {
      return new LabelCellEditorLocator((Label) source.getFigure());
    }
  }

  /**
  * @generated
  */
  static private class TextCellEditorLocator implements CellEditorLocator
  {

    /**
    * @generated
    */
    private WrappingLabel wrapLabel;

    /**
    * @generated
    */
    public TextCellEditorLocator(WrappingLabel wrapLabel)
    {
      this.wrapLabel = wrapLabel;
    }

    /**
    * @generated
    */
    public WrappingLabel getWrapLabel()
    {
      return wrapLabel;
    }

    /**
    * @generated
    */
    public void relocate(CellEditor celleditor)
    {
      Text text = (Text) celleditor.getControl();
      Rectangle rect = getWrapLabel().getTextBounds().getCopy();
      getWrapLabel().translateToAbsolute(rect);
      if(getWrapLabel().isTextWrapOn() && getWrapLabel().getText().length() > 0)
      {
        rect.setSize(new Dimension(text.computeSize(rect.width, SWT.DEFAULT)));
      }
      else
      {
        int avr = FigureUtilities.getFontMetrics(text.getFont())
            .getAverageCharWidth();
        rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT, SWT.DEFAULT))
            .expand(avr * 2, 0));
      }
      if(!rect.equals(new Rectangle(text.getBounds())))
      {
        text.setBounds(rect.x, rect.y, rect.width, rect.height);
      }
    }
  }

  /**
  * @generated
  */
  private static class LabelCellEditorLocator implements CellEditorLocator
  {

    /**
    * @generated
    */
    private Label label;

    /**
    * @generated
    */
    public LabelCellEditorLocator(Label label)
    {
      this.label = label;
    }

    /**
    * @generated
    */
    public Label getLabel()
    {
      return label;
    }

    /**
    * @generated
    */
    public void relocate(CellEditor celleditor)
    {
      Text text = (Text) celleditor.getControl();
      Rectangle rect = getLabel().getTextBounds().getCopy();
      getLabel().translateToAbsolute(rect);
      int avr = FigureUtilities.getFontMetrics(text.getFont())
          .getAverageCharWidth();
      rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT, SWT.DEFAULT))
          .expand(avr * 2, 0));
      if(!rect.equals(new Rectangle(text.getBounds())))
      {
        text.setBounds(rect.x, rect.y, rect.width, rect.height);
      }
    }
  }
}
