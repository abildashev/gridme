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

import gmodel.diagram.edit.parts.ConnectionEditPart;
import gmodel.diagram.edit.parts.GridElementEditPart;
import gmodel.diagram.edit.parts.ModelEditPart;
import gmodel.diagram.part.GmodelDiagramEditorPlugin;
import gmodel.diagram.part.Messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
* @generated
*/
public class GmodelModelingAssistantProvider extends ModelingAssistantProvider
{

  /**
  * @generated
  */
  public List getTypesForPopupBar(IAdaptable host)
  {
    IGraphicalEditPart editPart = (IGraphicalEditPart) host
        .getAdapter(IGraphicalEditPart.class);
    if(editPart instanceof ModelEditPart)
    {
      List types = new ArrayList();
      types.add(GmodelElementTypes.Connection_2001);
      types.add(GmodelElementTypes.GridElement_2002);
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public List getRelTypesOnSource(IAdaptable source)
  {
    IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
        .getAdapter(IGraphicalEditPart.class);
    if(sourceEditPart instanceof ConnectionEditPart)
    {
      List types = new ArrayList();
      types.add(GmodelElementTypes.Link_4002);
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public List getRelTypesOnTarget(IAdaptable target)
  {
    IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
        .getAdapter(IGraphicalEditPart.class);
    if(targetEditPart instanceof GridElementEditPart)
    {
      List types = new ArrayList();
      types.add(GmodelElementTypes.Link_4002);
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public List getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target)
  {
    IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
        .getAdapter(IGraphicalEditPart.class);
    IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
        .getAdapter(IGraphicalEditPart.class);
    if(sourceEditPart instanceof ConnectionEditPart)
    {
      List types = new ArrayList();
      if(targetEditPart instanceof GridElementEditPart)
      {
        types.add(GmodelElementTypes.Link_4002);
      }
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public List getTypesForSource(IAdaptable target, IElementType relationshipType)
  {
    IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
        .getAdapter(IGraphicalEditPart.class);
    if(targetEditPart instanceof GridElementEditPart)
    {
      List types = new ArrayList();
      if(relationshipType == GmodelElementTypes.Link_4002)
      {
        types.add(GmodelElementTypes.Connection_2001);
      }
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public List getTypesForTarget(IAdaptable source, IElementType relationshipType)
  {
    IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
        .getAdapter(IGraphicalEditPart.class);
    if(sourceEditPart instanceof ConnectionEditPart)
    {
      List types = new ArrayList();
      if(relationshipType == GmodelElementTypes.Link_4002)
      {
        types.add(GmodelElementTypes.GridElement_2002);
      }
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public EObject selectExistingElementForSource(IAdaptable target,
      IElementType relationshipType)
  {
    return selectExistingElement(target, getTypesForSource(target,
        relationshipType));
  }

  /**
  * @generated
  */
  public EObject selectExistingElementForTarget(IAdaptable source,
      IElementType relationshipType)
  {
    return selectExistingElement(source, getTypesForTarget(source,
        relationshipType));
  }

  /**
  * @generated
  */
  protected EObject selectExistingElement(IAdaptable host, Collection types)
  {
    if(types.isEmpty())
    {
      return null;
    }
    IGraphicalEditPart editPart = (IGraphicalEditPart) host
        .getAdapter(IGraphicalEditPart.class);
    if(editPart == null)
    {
      return null;
    }
    Diagram diagram = (Diagram) editPart.getRoot().getContents().getModel();
    Collection elements = new HashSet();
    for(Iterator it = diagram.getElement().eAllContents(); it.hasNext();)
    {
      EObject element = (EObject) it.next();
      if(isApplicableElement(element, types))
      {
        elements.add(element);
      }
    }
    if(elements.isEmpty())
    {
      return null;
    }
    return selectElement((EObject[]) elements.toArray(new EObject[elements
        .size()]));
  }

  /**
  * @generated
  */
  protected boolean isApplicableElement(EObject element, Collection types)
  {
    IElementType type = ElementTypeRegistry.getInstance().getElementType(
        element);
    return types.contains(type);
  }

  /**
  * @generated
  */
  protected EObject selectElement(EObject[] elements)
  {
    Shell shell = Display.getCurrent().getActiveShell();
    ILabelProvider labelProvider = new AdapterFactoryLabelProvider(
        GmodelDiagramEditorPlugin.getInstance()
            .getItemProvidersAdapterFactory());
    ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell,
        labelProvider);
    dialog.setMessage(Messages.GmodelModelingAssistantProviderMessage);
    dialog.setTitle(Messages.GmodelModelingAssistantProviderTitle);
    dialog.setMultipleSelection(false);
    dialog.setElements(elements);
    EObject selected = null;
    if(dialog.open() == Window.OK)
    {
      selected = (EObject) dialog.getFirstResult();
    }
    return selected;
  }
}
