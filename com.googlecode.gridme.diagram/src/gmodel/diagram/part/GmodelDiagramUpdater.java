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

import gmodel.Connection;
import gmodel.GenericModelElement;
import gmodel.GmodelPackage;
import gmodel.GridElement;
import gmodel.Link;
import gmodel.Model;
import gmodel.diagram.edit.parts.ConnectionEditPart;
import gmodel.diagram.edit.parts.GridElementEditPart;
import gmodel.diagram.edit.parts.LinkEditPart;
import gmodel.diagram.edit.parts.ModelEditPart;
import gmodel.diagram.providers.GmodelElementTypes;

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

/**
* @generated
*/
public class GmodelDiagramUpdater
{

  /**
  * @generated
  */
  public static List getSemanticChildren(View view)
  {
    switch(GmodelVisualIDRegistry.getVisualID(view))
    {
      case ModelEditPart.VISUAL_ID:
        return getModel_1000SemanticChildren(view);
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getModel_1000SemanticChildren(View view)
  {
    if(!view.isSetElement())
    {
      return Collections.EMPTY_LIST;
    }
    Model modelElement = (Model) view.getElement();
    List result = new LinkedList();
    for(Iterator it = modelElement.getElements().iterator(); it.hasNext();)
    {
      GenericModelElement childElement = (GenericModelElement) it.next();
      int visualID = GmodelVisualIDRegistry.getNodeVisualID(view, childElement);
      if(visualID == ConnectionEditPart.VISUAL_ID)
      {
        result.add(new GmodelNodeDescriptor(childElement, visualID));
        continue;
      }
      if(visualID == GridElementEditPart.VISUAL_ID)
      {
        result.add(new GmodelNodeDescriptor(childElement, visualID));
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
    switch(GmodelVisualIDRegistry.getVisualID(view))
    {
      case ModelEditPart.VISUAL_ID:
        return getModel_1000ContainedLinks(view);
      case ConnectionEditPart.VISUAL_ID:
        return getConnection_2001ContainedLinks(view);
      case GridElementEditPart.VISUAL_ID:
        return getGridElement_2002ContainedLinks(view);
      case LinkEditPart.VISUAL_ID:
        return getLink_4002ContainedLinks(view);
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getIncomingLinks(View view)
  {
    switch(GmodelVisualIDRegistry.getVisualID(view))
    {
      case ConnectionEditPart.VISUAL_ID:
        return getConnection_2001IncomingLinks(view);
      case GridElementEditPart.VISUAL_ID:
        return getGridElement_2002IncomingLinks(view);
      case LinkEditPart.VISUAL_ID:
        return getLink_4002IncomingLinks(view);
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getOutgoingLinks(View view)
  {
    switch(GmodelVisualIDRegistry.getVisualID(view))
    {
      case ConnectionEditPart.VISUAL_ID:
        return getConnection_2001OutgoingLinks(view);
      case GridElementEditPart.VISUAL_ID:
        return getGridElement_2002OutgoingLinks(view);
      case LinkEditPart.VISUAL_ID:
        return getLink_4002OutgoingLinks(view);
    }
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getModel_1000ContainedLinks(View view)
  {
    Model modelElement = (Model) view.getElement();
    List result = new LinkedList();
    result.addAll(getContainedTypeModelFacetLinks_Link_4002(modelElement));
    return result;
  }

  /**
  * @generated
  */
  public static List getConnection_2001ContainedLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getGridElement_2002ContainedLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getLink_4002ContainedLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getConnection_2001IncomingLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getGridElement_2002IncomingLinks(View view)
  {
    GridElement modelElement = (GridElement) view.getElement();
    Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
        .getResourceSet().getResources());
    List result = new LinkedList();
    result.addAll(getIncomingTypeModelFacetLinks_Link_4002(modelElement,
        crossReferences));
    return result;
  }

  /**
  * @generated
  */
  public static List getLink_4002IncomingLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getConnection_2001OutgoingLinks(View view)
  {
    Connection modelElement = (Connection) view.getElement();
    List result = new LinkedList();
    result.addAll(getOutgoingTypeModelFacetLinks_Link_4002(modelElement));
    return result;
  }

  /**
  * @generated
  */
  public static List getGridElement_2002OutgoingLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  public static List getLink_4002OutgoingLinks(View view)
  {
    return Collections.EMPTY_LIST;
  }

  /**
  * @generated
  */
  private static Collection getContainedTypeModelFacetLinks_Link_4002(
      Model container)
  {
    Collection result = new LinkedList();
    for(Iterator links = container.getElements().iterator(); links.hasNext();)
    {
      EObject linkObject = (EObject) links.next();
      if(false == linkObject instanceof Link)
      {
        continue;
      }
      Link link = (Link) linkObject;
      if(LinkEditPart.VISUAL_ID != GmodelVisualIDRegistry
          .getLinkWithClassVisualID(link))
      {
        continue;
      }
      GridElement dst = link.getTo();
      Connection src = link.getFrom();
      result.add(new GmodelLinkDescriptor(src, dst, link,
          GmodelElementTypes.Link_4002, LinkEditPart.VISUAL_ID));
    }
    return result;
  }

  /**
  * @generated
  */
  private static Collection getIncomingTypeModelFacetLinks_Link_4002(
      GridElement target, Map crossReferences)
  {
    Collection result = new LinkedList();
    Collection settings = (Collection) crossReferences.get(target);
    for(Iterator it = settings.iterator(); it.hasNext();)
    {
      EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
          .next();
      if(setting.getEStructuralFeature() != GmodelPackage.eINSTANCE
          .getLink_To()
          || false == setting.getEObject() instanceof Link)
      {
        continue;
      }
      Link link = (Link) setting.getEObject();
      if(LinkEditPart.VISUAL_ID != GmodelVisualIDRegistry
          .getLinkWithClassVisualID(link))
      {
        continue;
      }
      Connection src = link.getFrom();
      result.add(new GmodelLinkDescriptor(src, target, link,
          GmodelElementTypes.Link_4002, LinkEditPart.VISUAL_ID));
    }
    return result;
  }

  /**
  * @generated
  */
  private static Collection getOutgoingTypeModelFacetLinks_Link_4002(
      Connection source)
  {
    Model container = null;
    // Find container element for the link.
    // Climb up by containment hierarchy starting from the source
    // and return the first element that is instance of the container class.
    for(EObject element = source; element != null && container == null; element = element
        .eContainer())
    {
      if(element instanceof Model)
      {
        container = (Model) element;
      }
    }
    if(container == null)
    {
      return Collections.EMPTY_LIST;
    }
    Collection result = new LinkedList();
    for(Iterator links = container.getElements().iterator(); links.hasNext();)
    {
      EObject linkObject = (EObject) links.next();
      if(false == linkObject instanceof Link)
      {
        continue;
      }
      Link link = (Link) linkObject;
      if(LinkEditPart.VISUAL_ID != GmodelVisualIDRegistry
          .getLinkWithClassVisualID(link))
      {
        continue;
      }
      GridElement dst = link.getTo();
      Connection src = link.getFrom();
      if(src != source)
      {
        continue;
      }
      result.add(new GmodelLinkDescriptor(src, dst, link,
          GmodelElementTypes.Link_4002, LinkEditPart.VISUAL_ID));
    }
    return result;
  }

}
