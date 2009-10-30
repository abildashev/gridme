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
package gmodel.diagram.navigator;

import gmodel.diagram.edit.parts.ConnectionEditPart;
import gmodel.diagram.edit.parts.ConnectionNameEditPart;
import gmodel.diagram.edit.parts.GridElementEditPart;
import gmodel.diagram.edit.parts.GridElementNameEditPart;
import gmodel.diagram.edit.parts.LinkEditPart;
import gmodel.diagram.edit.parts.ModelEditPart;
import gmodel.diagram.part.GmodelDiagramEditorPlugin;
import gmodel.diagram.part.GmodelVisualIDRegistry;
import gmodel.diagram.providers.GmodelElementTypes;
import gmodel.diagram.providers.GmodelParserProvider;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

/**
* @generated
*/
public class GmodelNavigatorLabelProvider extends LabelProvider implements
    ICommonLabelProvider, ITreePathLabelProvider
{

  /**
  * @generated
  */
  static
  {
    GmodelDiagramEditorPlugin
        .getInstance()
        .getImageRegistry()
        .put(
            "Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
    GmodelDiagramEditorPlugin.getInstance().getImageRegistry().put(
        "Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
  }

  /**
  * @generated
  */
  public void updateLabel(ViewerLabel label, TreePath elementPath)
  {
    Object element = elementPath.getLastSegment();
    if(element instanceof GmodelNavigatorItem
        && !isOwnView(((GmodelNavigatorItem) element).getView()))
    {
      return;
    }
    label.setText(getText(element));
    label.setImage(getImage(element));
  }

  /**
  * @generated
  */
  public Image getImage(Object element)
  {
    if(element instanceof GmodelNavigatorGroup)
    {
      GmodelNavigatorGroup group = (GmodelNavigatorGroup) element;
      return GmodelDiagramEditorPlugin.getInstance().getBundledImage(
          group.getIcon());
    }

    if(element instanceof GmodelNavigatorItem)
    {
      GmodelNavigatorItem navigatorItem = (GmodelNavigatorItem) element;
      if(!isOwnView(navigatorItem.getView()))
      {
        return super.getImage(element);
      }
      return getImage(navigatorItem.getView());
    }

    return super.getImage(element);
  }

  /**
  * @generated
  */
  public Image getImage(View view)
  {
    switch(GmodelVisualIDRegistry.getVisualID(view))
    {
      case ModelEditPart.VISUAL_ID:
        return getImage(
            "Navigator?Diagram?http://gridme.googlecode.com/gmodel?Model", GmodelElementTypes.Model_1000); //$NON-NLS-1$
      case ConnectionEditPart.VISUAL_ID:
        return getImage(
            "Navigator?TopLevelNode?http://gridme.googlecode.com/gmodel?Connection", GmodelElementTypes.Connection_2001); //$NON-NLS-1$
      case GridElementEditPart.VISUAL_ID:
        return getImage(
            "Navigator?TopLevelNode?http://gridme.googlecode.com/gmodel?GridElement", GmodelElementTypes.GridElement_2002); //$NON-NLS-1$
      case LinkEditPart.VISUAL_ID:
        return getImage(
            "Navigator?Link?http://gridme.googlecode.com/gmodel?Link", GmodelElementTypes.Link_4002); //$NON-NLS-1$
    }
    return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
  }

  /**
  * @generated
  */
  private Image getImage(String key, IElementType elementType)
  {
    ImageRegistry imageRegistry = GmodelDiagramEditorPlugin.getInstance()
        .getImageRegistry();
    Image image = imageRegistry.get(key);
    if(image == null && elementType != null
        && GmodelElementTypes.isKnownElementType(elementType))
    {
      image = GmodelElementTypes.getImage(elementType);
      imageRegistry.put(key, image);
    }

    if(image == null)
    {
      image = imageRegistry.get("Navigator?ImageNotFound"); //$NON-NLS-1$
      imageRegistry.put(key, image);
    }
    return image;
  }

  /**
  * @generated
  */
  public String getText(Object element)
  {
    if(element instanceof GmodelNavigatorGroup)
    {
      GmodelNavigatorGroup group = (GmodelNavigatorGroup) element;
      return group.getGroupName();
    }

    if(element instanceof GmodelNavigatorItem)
    {
      GmodelNavigatorItem navigatorItem = (GmodelNavigatorItem) element;
      if(!isOwnView(navigatorItem.getView()))
      {
        return null;
      }
      return getText(navigatorItem.getView());
    }

    return super.getText(element);
  }

  /**
  * @generated
  */
  public String getText(View view)
  {
    if(view.getElement() != null && view.getElement().eIsProxy())
    {
      return getUnresolvedDomainElementProxyText(view);
    }
    switch(GmodelVisualIDRegistry.getVisualID(view))
    {
      case ModelEditPart.VISUAL_ID:
        return getModel_1000Text(view);
      case ConnectionEditPart.VISUAL_ID:
        return getConnection_2001Text(view);
      case GridElementEditPart.VISUAL_ID:
        return getGridElement_2002Text(view);
      case LinkEditPart.VISUAL_ID:
        return getLink_4002Text(view);
    }
    return getUnknownElementText(view);
  }

  /**
  * @generated
  */
  private String getModel_1000Text(View view)
  {
    return ""; //$NON-NLS-1$
  }

  /**
  * @generated
  */
  private String getConnection_2001Text(View view)
  {
    IAdaptable hintAdapter = new GmodelParserProvider.HintAdapter(
        GmodelElementTypes.Connection_2001, (view.getElement() != null ? view
            .getElement() : view), GmodelVisualIDRegistry
            .getType(ConnectionNameEditPart.VISUAL_ID));
    IParser parser = ParserService.getInstance().getParser(hintAdapter);

    if(parser != null)
    {
      return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
    }
    else
    {
      GmodelDiagramEditorPlugin.getInstance().logError(
          "Parser was not found for label " + 5001); //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }

  }

  /**
  * @generated
  */
  private String getGridElement_2002Text(View view)
  {
    IAdaptable hintAdapter = new GmodelParserProvider.HintAdapter(
        GmodelElementTypes.GridElement_2002, (view.getElement() != null ? view
            .getElement() : view), GmodelVisualIDRegistry
            .getType(GridElementNameEditPart.VISUAL_ID));
    IParser parser = ParserService.getInstance().getParser(hintAdapter);

    if(parser != null)
    {
      return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
    }
    else
    {
      GmodelDiagramEditorPlugin.getInstance().logError(
          "Parser was not found for label " + 5002); //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }

  }

  /**
  * @generated
  */
  private String getLink_4002Text(View view)
  {
    return ""; //$NON-NLS-1$
  }

  /**
  * @generated
  */
  private String getUnknownElementText(View view)
  {
    return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
  }

  /**
  * @generated
  */
  private String getUnresolvedDomainElementProxyText(View view)
  {
    return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
  }

  /**
  * @generated
  */
  public void init(ICommonContentExtensionSite aConfig)
  {
  }

  /**
  * @generated
  */
  public void restoreState(IMemento aMemento)
  {
  }

  /**
  * @generated
  */
  public void saveState(IMemento aMemento)
  {
  }

  /**
  * @generated
  */
  public String getDescription(Object anElement)
  {
    return null;
  }

  /**
  * @generated
  */
  private boolean isOwnView(View view)
  {
    return ModelEditPart.MODEL_ID.equals(GmodelVisualIDRegistry
        .getModelID(view));
  }

}
