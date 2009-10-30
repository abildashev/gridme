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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

import statemachine.GAbstractState;
import statemachine.GCompositeState;
import statemachine.GStartState;
import statemachine.GState;
import statemachine.GStatemachine;
import statemachine.GStopState;
import statemachine.StatemachinePackage;
import statemachine.Transition;
import statemachine.diagram.edit.parts.GStartStateEditPart;
import statemachine.diagram.edit.parts.GStateEditPart;
import statemachine.diagram.edit.parts.GStatemachineEditPart;
import statemachine.diagram.edit.parts.GStopStateEditPart;
import statemachine.diagram.edit.parts.TransitionEditPart;
import statemachine.diagram.providers.StatemachineElementTypes;

/**
* @generated
*/
public class StatemachineDiagramUpdater
{

  /**
  * @generated
  */
  public static List getSemanticChildren(View view)
  {
    switch(StatemachineVisualIDRegistry.getVisualID(view))
    {
      case GStatemachineEditPart.VISUAL_ID:
        return getGStatemachine_79SemanticChildren(view);
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getGStatemachine_79SemanticChildren(View view)
  {
    if(!view.isSetElement())
    {
      return Collections.EMPTY_LIST;
    }
    GStatemachine modelElement = (GStatemachine) view.getElement();
    List result = new LinkedList();
    for(Iterator it = modelElement.getStates().iterator(); it.hasNext();)
    {
      GAbstractState childElement = (GAbstractState) it.next();
      int visualID = StatemachineVisualIDRegistry.getNodeVisualID(view,
          childElement);
      if(visualID == GStateEditPart.VISUAL_ID)
      {
        result.add(new StatemachineNodeDescriptor(childElement, visualID));
        continue;
      }
      if(visualID == GStartStateEditPart.VISUAL_ID)
      {
        result.add(new StatemachineNodeDescriptor(childElement, visualID));
        continue;
      }
      if(visualID == GStopStateEditPart.VISUAL_ID)
      {
        result.add(new StatemachineNodeDescriptor(childElement, visualID));
        continue;
      }
    }
    return result;
  }

  /**
  * @generated
  */
  public static List getContainedLinks(View view)
  {
    switch(StatemachineVisualIDRegistry.getVisualID(view))
    {
      case GStatemachineEditPart.VISUAL_ID:
        return getGStatemachine_79ContainedLinks(view);
      case GStateEditPart.VISUAL_ID:
        return getGState_1001ContainedLinks(view);
      case GStartStateEditPart.VISUAL_ID:
        return getGStartState_1002ContainedLinks(view);
      case GStopStateEditPart.VISUAL_ID:
        return getGStopState_1003ContainedLinks(view);
      case TransitionEditPart.VISUAL_ID:
        return getTransition_3001ContainedLinks(view);
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getIncomingLinks(View view)
  {
    switch(StatemachineVisualIDRegistry.getVisualID(view))
    {
      case GStateEditPart.VISUAL_ID:
        return getGState_1001IncomingLinks(view);
      case GStartStateEditPart.VISUAL_ID:
        return getGStartState_1002IncomingLinks(view);
      case GStopStateEditPart.VISUAL_ID:
        return getGStopState_1003IncomingLinks(view);
      case TransitionEditPart.VISUAL_ID:
        return getTransition_3001IncomingLinks(view);
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getOutgoingLinks(View view)
  {
    switch(StatemachineVisualIDRegistry.getVisualID(view))
    {
      case GStateEditPart.VISUAL_ID:
        return getGState_1001OutgoingLinks(view);
      case GStartStateEditPart.VISUAL_ID:
        return getGStartState_1002OutgoingLinks(view);
      case GStopStateEditPart.VISUAL_ID:
        return getGStopState_1003OutgoingLinks(view);
      case TransitionEditPart.VISUAL_ID:
        return getTransition_3001OutgoingLinks(view);
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getGStatemachine_79ContainedLinks(View view)
  {
    GStatemachine modelElement = (GStatemachine) view.getElement();
    List result = new LinkedList();
    result
        .addAll(getContainedTypeModelFacetLinks_Transition_3001(modelElement));
    return result;
  }

  /**
  * @generated
  */
  public static List getGState_1001ContainedLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getGStartState_1002ContainedLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getGStopState_1003ContainedLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getTransition_3001ContainedLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getGState_1001IncomingLinks(View view)
  {
    GState modelElement = (GState) view.getElement();
    Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
        .getResourceSet().getResources());
    List result = new LinkedList();
    result.addAll(getIncomingTypeModelFacetLinks_Transition_3001(modelElement,
        crossReferences));
    return result;
  }

  /**
  * @generated
  */
  public static List getGStartState_1002IncomingLinks(View view)
  {
    GStartState modelElement = (GStartState) view.getElement();
    Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
        .getResourceSet().getResources());
    List result = new LinkedList();
    result.addAll(getIncomingTypeModelFacetLinks_Transition_3001(modelElement,
        crossReferences));
    return result;
  }

  /**
  * @generated
  */
  public static List getGStopState_1003IncomingLinks(View view)
  {
    GStopState modelElement = (GStopState) view.getElement();
    Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
        .getResourceSet().getResources());
    List result = new LinkedList();
    result.addAll(getIncomingTypeModelFacetLinks_Transition_3001(modelElement,
        crossReferences));
    return result;
  }

  /**
  * @generated
  */
  public static List getTransition_3001IncomingLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getGState_1001OutgoingLinks(View view)
  {
    GState modelElement = (GState) view.getElement();
    List result = new LinkedList();
    result.addAll(getOutgoingTypeModelFacetLinks_Transition_3001(modelElement));
    return result;
  }

  /**
  * @generated
  */
  public static List getGStartState_1002OutgoingLinks(View view)
  {
    GStartState modelElement = (GStartState) view.getElement();
    List result = new LinkedList();
    result.addAll(getOutgoingTypeModelFacetLinks_Transition_3001(modelElement));
    return result;
  }

  /**
  * @generated
  */
  public static List getGStopState_1003OutgoingLinks(View view)
  {
    GStopState modelElement = (GStopState) view.getElement();
    List result = new LinkedList();
    result.addAll(getOutgoingTypeModelFacetLinks_Transition_3001(modelElement));
    return result;
  }

  /**
  * @generated
  */
  public static List getTransition_3001OutgoingLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  private static Collection getContainedTypeModelFacetLinks_Transition_3001(GCompositeState container)
  {
    Collection result = new LinkedList();
    for(Iterator links = container.getTransitions().iterator(); links.hasNext();)
    {
      Object linkObject = links.next();
      if(false == linkObject instanceof Transition)
      {
        continue;
      }
      Transition link = (Transition) linkObject;
      if(TransitionEditPart.VISUAL_ID != StatemachineVisualIDRegistry
          .getLinkWithClassVisualID(link))
      {
        continue;
      }
      GAbstractState dst = link.getTo();
      GAbstractState src = link.getFrom();
      result.add(new StatemachineLinkDescriptor(src, dst, link,
          StatemachineElementTypes.Transition_3001,
          TransitionEditPart.VISUAL_ID));
    }
    return result;
  }

  /**
  * @generated
  */
  private static Collection getIncomingTypeModelFacetLinks_Transition_3001(GAbstractState target,
                                                                           Map crossReferences)
  {
    Collection result = new LinkedList();
    Collection settings = (Collection) crossReferences.get(target);
    for(Iterator it = settings.iterator(); it.hasNext();)
    {
      EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
          .next();
      if(setting.getEStructuralFeature() != StatemachinePackage.eINSTANCE
          .getTransition_To()
          || false == setting.getEObject() instanceof Transition)
      {
        continue;
      }
      Transition link = (Transition) setting.getEObject();
      if(TransitionEditPart.VISUAL_ID != StatemachineVisualIDRegistry
          .getLinkWithClassVisualID(link))
      {
        continue;
      }
      GAbstractState src = link.getFrom();
      result.add(new StatemachineLinkDescriptor(src, target, link,
          StatemachineElementTypes.Transition_3001,
          TransitionEditPart.VISUAL_ID));
    }
    return result;
  }

  /**
  * @generated
  */
  private static Collection getOutgoingTypeModelFacetLinks_Transition_3001(GAbstractState source)
  {
    GCompositeState container = null;
    // Find container element for the link.
    // Climb up by containment hierarchy starting from the source
    // and return the first element that is instance of the container class.
    for(EObject element = source; element != null && container == null; element = element
        .eContainer())
    {
      if(element instanceof GCompositeState)
      {
        container = (GCompositeState) element;
      }
    }
    if(container == null)
    {
      return Collections.EMPTY_LIST;
    }
    Collection result = new LinkedList();
    for(Iterator links = container.getTransitions().iterator(); links.hasNext();)
    {
      Object linkObject = links.next();
      if(false == linkObject instanceof Transition)
      {
        continue;
      }
      Transition link = (Transition) linkObject;
      if(TransitionEditPart.VISUAL_ID != StatemachineVisualIDRegistry
          .getLinkWithClassVisualID(link))
      {
        continue;
      }
      GAbstractState dst = link.getTo();
      GAbstractState src = link.getFrom();
      if(src != source)
      {
        continue;
      }
      result.add(new StatemachineLinkDescriptor(src, dst, link,
          StatemachineElementTypes.Transition_3001,
          TransitionEditPart.VISUAL_ID));
    }
    return result;
  }

}
