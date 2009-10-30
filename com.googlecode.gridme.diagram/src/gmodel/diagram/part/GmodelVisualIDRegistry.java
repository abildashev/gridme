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

import gmodel.GmodelPackage;
import gmodel.Model;
import gmodel.diagram.edit.parts.ConnectionEditPart;
import gmodel.diagram.edit.parts.ConnectionNameEditPart;
import gmodel.diagram.edit.parts.GridElementEditPart;
import gmodel.diagram.edit.parts.GridElementNameEditPart;
import gmodel.diagram.edit.parts.LinkEditPart;
import gmodel.diagram.edit.parts.ModelEditPart;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

/**
* This registry is used to determine which type of visual object should be
* created for the corresponding Diagram, Node, ChildNode or Link represented
* by a domain model object.
* 
* @generated
*/
public class GmodelVisualIDRegistry
{

  /**
  * @generated
  */
  private static final String DEBUG_KEY = "com.googlecode.gridme.diagram/debug/visualID"; //$NON-NLS-1$

  /**
  * @generated
  */
  public static int getVisualID(View view)
  {
    if(view instanceof Diagram)
    {
      if(ModelEditPart.MODEL_ID.equals(view.getType()))
      {
        return ModelEditPart.VISUAL_ID;
      }
      else
      {
        return -1;
      }
    }
    return gmodel.diagram.part.GmodelVisualIDRegistry.getVisualID(view
        .getType());
  }

  /**
  * @generated
  */
  public static String getModelID(View view)
  {
    View diagram = view.getDiagram();
    while(view != diagram)
    {
      EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
      if(annotation != null)
      {
        return (String) annotation.getDetails().get("modelID"); //$NON-NLS-1$
      }
      view = (View) view.eContainer();
    }
    return diagram != null ? diagram.getType() : null;
  }

  /**
  * @generated
  */
  public static int getVisualID(String type)
  {
    try
    {
      return Integer.parseInt(type);
    }
    catch(NumberFormatException e)
    {
      if(Boolean.TRUE.toString().equalsIgnoreCase(
          Platform.getDebugOption(DEBUG_KEY)))
      {
        GmodelDiagramEditorPlugin.getInstance().logError(
            "Unable to parse view type as a visualID number: " + type);
      }
    }
    return -1;
  }

  /**
  * @generated
  */
  public static String getType(int visualID)
  {
    return String.valueOf(visualID);
  }

  /**
  * @generated
  */
  public static int getDiagramVisualID(EObject domainElement)
  {
    if(domainElement == null)
    {
      return -1;
    }
    if(GmodelPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())
        && isDiagram((Model) domainElement))
    {
      return ModelEditPart.VISUAL_ID;
    }
    return -1;
  }

  /**
  * @generated
  */
  public static int getNodeVisualID(View containerView, EObject domainElement)
  {
    if(domainElement == null)
    {
      return -1;
    }
    String containerModelID = gmodel.diagram.part.GmodelVisualIDRegistry
        .getModelID(containerView);
    if(!ModelEditPart.MODEL_ID.equals(containerModelID))
    {
      return -1;
    }
    int containerVisualID;
    if(ModelEditPart.MODEL_ID.equals(containerModelID))
    {
      containerVisualID = gmodel.diagram.part.GmodelVisualIDRegistry
          .getVisualID(containerView);
    }
    else
    {
      if(containerView instanceof Diagram)
      {
        containerVisualID = ModelEditPart.VISUAL_ID;
      }
      else
      {
        return -1;
      }
    }
    switch(containerVisualID)
    {
      case ModelEditPart.VISUAL_ID:
        if(GmodelPackage.eINSTANCE.getConnection().isSuperTypeOf(
            domainElement.eClass()))
        {
          return ConnectionEditPart.VISUAL_ID;
        }
        if(GmodelPackage.eINSTANCE.getGridElement().isSuperTypeOf(
            domainElement.eClass()))
        {
          return GridElementEditPart.VISUAL_ID;
        }
        break;
    }
    return -1;
  }

  /**
  * @generated
  */
  public static boolean canCreateNode(View containerView, int nodeVisualID)
  {
    String containerModelID = gmodel.diagram.part.GmodelVisualIDRegistry
        .getModelID(containerView);
    if(!ModelEditPart.MODEL_ID.equals(containerModelID))
    {
      return false;
    }
    int containerVisualID;
    if(ModelEditPart.MODEL_ID.equals(containerModelID))
    {
      containerVisualID = gmodel.diagram.part.GmodelVisualIDRegistry
          .getVisualID(containerView);
    }
    else
    {
      if(containerView instanceof Diagram)
      {
        containerVisualID = ModelEditPart.VISUAL_ID;
      }
      else
      {
        return false;
      }
    }
    switch(containerVisualID)
    {
      case ConnectionEditPart.VISUAL_ID:
        if(ConnectionNameEditPart.VISUAL_ID == nodeVisualID)
        {
          return true;
        }
        break;
      case GridElementEditPart.VISUAL_ID:
        if(GridElementNameEditPart.VISUAL_ID == nodeVisualID)
        {
          return true;
        }
        break;
      case ModelEditPart.VISUAL_ID:
        if(ConnectionEditPart.VISUAL_ID == nodeVisualID)
        {
          return true;
        }
        if(GridElementEditPart.VISUAL_ID == nodeVisualID)
        {
          return true;
        }
        break;
    }
    return false;
  }

  /**
  * @generated
  */
  public static int getLinkWithClassVisualID(EObject domainElement)
  {
    if(domainElement == null)
    {
      return -1;
    }
    if(GmodelPackage.eINSTANCE.getLink().isSuperTypeOf(domainElement.eClass()))
    {
      return LinkEditPart.VISUAL_ID;
    }
    return -1;
  }

  /**
  * User can change implementation of this method to handle some specific
  * situations not covered by default logic.
  * 
  * @generated
  */
  private static boolean isDiagram(Model element)
  {
    return true;
  }

}
