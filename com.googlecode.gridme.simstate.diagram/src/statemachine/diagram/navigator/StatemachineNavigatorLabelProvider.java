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
package statemachine.diagram.navigator;

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

import statemachine.GStatemachine;
import statemachine.diagram.edit.parts.GStartStateEditPart;
import statemachine.diagram.edit.parts.GStateEditPart;
import statemachine.diagram.edit.parts.GStateNameEditPart;
import statemachine.diagram.edit.parts.GStatemachineEditPart;
import statemachine.diagram.edit.parts.GStopStateEditPart;
import statemachine.diagram.edit.parts.TransitionEditPart;
import statemachine.diagram.edit.parts.TransitionNameEditPart;
import statemachine.diagram.part.StatemachineDiagramEditorPlugin;
import statemachine.diagram.part.StatemachineVisualIDRegistry;
import statemachine.diagram.providers.StatemachineElementTypes;
import statemachine.diagram.providers.StatemachineParserProvider;

/**
* @generated
*/
public class StatemachineNavigatorLabelProvider extends LabelProvider implements
    ICommonLabelProvider, ITreePathLabelProvider
{

  /**
  * @generated
  */
  static
  {
    StatemachineDiagramEditorPlugin
        .getInstance()
        .getImageRegistry()
        .put(
            "Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
    StatemachineDiagramEditorPlugin.getInstance().getImageRegistry().put(
        "Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
  }

  /**
  * @generated
  */
  public void updateLabel(ViewerLabel label, TreePath elementPath)
  {
    Object element = elementPath.getLastSegment();
    if(element instanceof StatemachineNavigatorItem
        && !isOwnView(((StatemachineNavigatorItem) element).getView()))
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
    if(element instanceof StatemachineNavigatorGroup)
    {
      StatemachineNavigatorGroup group = (StatemachineNavigatorGroup) element;
      return StatemachineDiagramEditorPlugin.getInstance().getBundledImage(
          group.getIcon());
    }

    if(element instanceof StatemachineNavigatorItem)
    {
      StatemachineNavigatorItem navigatorItem = (StatemachineNavigatorItem) element;
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
    switch(StatemachineVisualIDRegistry.getVisualID(view))
    {
      case GStatemachineEditPart.VISUAL_ID:
        return getImage(
            "Navigator?Diagram?http://example.org/statemachine?GStatemachine", StatemachineElementTypes.GStatemachine_79); //$NON-NLS-1$
      case GStateEditPart.VISUAL_ID:
        return getImage(
            "Navigator?TopLevelNode?http://example.org/statemachine?GState", StatemachineElementTypes.GState_1001); //$NON-NLS-1$
      case GStartStateEditPart.VISUAL_ID:
        return getImage(
            "Navigator?TopLevelNode?http://example.org/statemachine?GStartState", StatemachineElementTypes.GStartState_1002); //$NON-NLS-1$
      case GStopStateEditPart.VISUAL_ID:
        return getImage(
            "Navigator?TopLevelNode?http://example.org/statemachine?GStopState", StatemachineElementTypes.GStopState_1003); //$NON-NLS-1$
      case TransitionEditPart.VISUAL_ID:
        return getImage(
            "Navigator?Link?http://example.org/statemachine?Transition", StatemachineElementTypes.Transition_3001); //$NON-NLS-1$
    }
    return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
  }

  /**
  * @generated
  */
  private Image getImage(String key, IElementType elementType)
  {
    ImageRegistry imageRegistry = StatemachineDiagramEditorPlugin.getInstance()
        .getImageRegistry();
    Image image = imageRegistry.get(key);
    if(image == null && elementType != null
        && StatemachineElementTypes.isKnownElementType(elementType))
    {
      image = StatemachineElementTypes.getImage(elementType);
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
    if(element instanceof StatemachineNavigatorGroup)
    {
      StatemachineNavigatorGroup group = (StatemachineNavigatorGroup) element;
      return group.getGroupName();
    }

    if(element instanceof StatemachineNavigatorItem)
    {
      StatemachineNavigatorItem navigatorItem = (StatemachineNavigatorItem) element;
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
    switch(StatemachineVisualIDRegistry.getVisualID(view))
    {
      case GStatemachineEditPart.VISUAL_ID:
        return getGStatemachine_79Text(view);
      case GStateEditPart.VISUAL_ID:
        return getGState_1001Text(view);
      case GStartStateEditPart.VISUAL_ID:
        return getGStartState_1002Text(view);
      case GStopStateEditPart.VISUAL_ID:
        return getGStopState_1003Text(view);
      case TransitionEditPart.VISUAL_ID:
        return getTransition_3001Text(view);
    }
    return getUnknownElementText(view);
  }

  /**
  * @generated
  */
  private String getGStatemachine_79Text(View view)
  {
    GStatemachine domainModelElement = (GStatemachine) view.getElement();
    if(domainModelElement != null)
    {
      return domainModelElement.getName();
    }
    else
    {
      StatemachineDiagramEditorPlugin.getInstance().logError(
          "No domain element for view with visualID = " + 79); //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }
  }

  /**
  * @generated
  */
  private String getGState_1001Text(View view)
  {
    IAdaptable hintAdapter = new StatemachineParserProvider.HintAdapter(
        StatemachineElementTypes.GState_1001, (view.getElement() != null ? view
            .getElement() : view), StatemachineVisualIDRegistry
            .getType(GStateNameEditPart.VISUAL_ID));
    IParser parser = ParserService.getInstance().getParser(hintAdapter);

    if(parser != null)
    {
      return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
    }
    else
    {
      StatemachineDiagramEditorPlugin.getInstance().logError(
          "Parser was not found for label " + 4001); //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }

  }

  /**
  * @generated
  */
  private String getGStartState_1002Text(View view)
  {
    return ""; //$NON-NLS-1$
  }

  /**
  * @generated
  */
  private String getGStopState_1003Text(View view)
  {
    return ""; //$NON-NLS-1$
  }

  /**
  * @generated
  */
  private String getTransition_3001Text(View view)
  {
    IAdaptable hintAdapter = new StatemachineParserProvider.HintAdapter(
        StatemachineElementTypes.Transition_3001,
        (view.getElement() != null ? view.getElement() : view),
        StatemachineVisualIDRegistry.getType(TransitionNameEditPart.VISUAL_ID));
    IParser parser = ParserService.getInstance().getParser(hintAdapter);

    if(parser != null)
    {
      return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
    }
    else
    {
      StatemachineDiagramEditorPlugin.getInstance().logError(
          "Parser was not found for label " + 4002); //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }

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
    return GStatemachineEditPart.MODEL_ID.equals(StatemachineVisualIDRegistry
        .getModelID(view));
  }

}
