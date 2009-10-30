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
package statemachine.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

import statemachine.GStatemachine;
import statemachine.StatemachinePackage;
import statemachine.diagram.edit.parts.GStartStateEditPart;
import statemachine.diagram.edit.parts.GStateEditPart;
import statemachine.diagram.edit.parts.GStateNameEditPart;
import statemachine.diagram.edit.parts.GStatemachineEditPart;
import statemachine.diagram.edit.parts.GStopStateEditPart;
import statemachine.diagram.edit.parts.TransitionEditPart;
import statemachine.diagram.edit.parts.TransitionNameEditPart;

/**
* This registry is used to determine which type of visual object should be
* created for the corresponding Diagram, Node, ChildNode or Link represented
* by a domain model object.
* 
* @generated
*/
public class StatemachineVisualIDRegistry
{

  /**
  * @generated
  */
  private static final String DEBUG_KEY = StatemachineDiagramEditorPlugin
      .getInstance().getBundle().getSymbolicName()
      + "/debug/visualID"; //$NON-NLS-1$

  /**
  * @generated
  */
  public static int getVisualID(View view)
  {
    if(view instanceof Diagram)
    {
      if(GStatemachineEditPart.MODEL_ID.equals(view.getType()))
      {
        return GStatemachineEditPart.VISUAL_ID;
      }
      else
      {
        return -1;
      }
    }
    return statemachine.diagram.part.StatemachineVisualIDRegistry
        .getVisualID(view.getType());
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
        StatemachineDiagramEditorPlugin.getInstance().logError(
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
    if(StatemachinePackage.eINSTANCE.getGStatemachine().isSuperTypeOf(
        domainElement.eClass())
        && isDiagram((GStatemachine) domainElement))
    {
      return GStatemachineEditPart.VISUAL_ID;
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
    String containerModelID = statemachine.diagram.part.StatemachineVisualIDRegistry
        .getModelID(containerView);
    if(!GStatemachineEditPart.MODEL_ID.equals(containerModelID))
    {
      return -1;
    }
    int containerVisualID;
    if(GStatemachineEditPart.MODEL_ID.equals(containerModelID))
    {
      containerVisualID = statemachine.diagram.part.StatemachineVisualIDRegistry
          .getVisualID(containerView);
    }
    else
    {
      if(containerView instanceof Diagram)
      {
        containerVisualID = GStatemachineEditPart.VISUAL_ID;
      }
      else
      {
        return -1;
      }
    }
    switch(containerVisualID)
    {
      case GStatemachineEditPart.VISUAL_ID:
        if(StatemachinePackage.eINSTANCE.getGState().isSuperTypeOf(
            domainElement.eClass()))
        {
          return GStateEditPart.VISUAL_ID;
        }
        if(StatemachinePackage.eINSTANCE.getGStartState().isSuperTypeOf(
            domainElement.eClass()))
        {
          return GStartStateEditPart.VISUAL_ID;
        }
        if(StatemachinePackage.eINSTANCE.getGStopState().isSuperTypeOf(
            domainElement.eClass()))
        {
          return GStopStateEditPart.VISUAL_ID;
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
    String containerModelID = statemachine.diagram.part.StatemachineVisualIDRegistry
        .getModelID(containerView);
    if(!GStatemachineEditPart.MODEL_ID.equals(containerModelID))
    {
      return false;
    }
    int containerVisualID;
    if(GStatemachineEditPart.MODEL_ID.equals(containerModelID))
    {
      containerVisualID = statemachine.diagram.part.StatemachineVisualIDRegistry
          .getVisualID(containerView);
    }
    else
    {
      if(containerView instanceof Diagram)
      {
        containerVisualID = GStatemachineEditPart.VISUAL_ID;
      }
      else
      {
        return false;
      }
    }
    switch(containerVisualID)
    {
      case GStateEditPart.VISUAL_ID:
        if(GStateNameEditPart.VISUAL_ID == nodeVisualID)
        {
          return true;
        }
        break;
      case GStatemachineEditPart.VISUAL_ID:
        if(GStateEditPart.VISUAL_ID == nodeVisualID)
        {
          return true;
        }
        if(GStartStateEditPart.VISUAL_ID == nodeVisualID)
        {
          return true;
        }
        if(GStopStateEditPart.VISUAL_ID == nodeVisualID)
        {
          return true;
        }
        break;
      case TransitionEditPart.VISUAL_ID:
        if(TransitionNameEditPart.VISUAL_ID == nodeVisualID)
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
    if(StatemachinePackage.eINSTANCE.getTransition().isSuperTypeOf(
        domainElement.eClass()))
    {
      return TransitionEditPart.VISUAL_ID;
    }
    return -1;
  }

  /**
  * User can change implementation of this method to handle some specific
  * situations not covered by default logic.
  * 
  * @generated
  */
  private static boolean isDiagram(GStatemachine element)
  {
    return true;
  }

}
