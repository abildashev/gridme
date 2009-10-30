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
import gmodel.diagram.edit.parts.ConnectionNameEditPart;
import gmodel.diagram.edit.parts.GridElementEditPart;
import gmodel.diagram.edit.parts.GridElementNameEditPart;
import gmodel.diagram.edit.parts.LinkEditPart;
import gmodel.diagram.edit.parts.ModelEditPart;
import gmodel.diagram.part.GmodelVisualIDRegistry;
import gmodel.diagram.view.factories.ConnectionNameViewFactory;
import gmodel.diagram.view.factories.ConnectionViewFactory;
import gmodel.diagram.view.factories.GridElementNameViewFactory;
import gmodel.diagram.view.factories.GridElementViewFactory;
import gmodel.diagram.view.factories.LinkViewFactory;
import gmodel.diagram.view.factories.ModelViewFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class GmodelViewProvider extends AbstractViewProvider
{

  /**
  * @generated
  */
  protected Class getDiagramViewClass(IAdaptable semanticAdapter,
      String diagramKind)
  {
    EObject semanticElement = getSemanticElement(semanticAdapter);
    if(ModelEditPart.MODEL_ID.equals(diagramKind)
        && GmodelVisualIDRegistry.getDiagramVisualID(semanticElement) != -1)
    {
      return ModelViewFactory.class;
    }
    return null;
  }

  /**
  * @generated
  */
  protected Class getNodeViewClass(IAdaptable semanticAdapter,
      View containerView, String semanticHint)
  {
    if(containerView == null)
    {
      return null;
    }
    IElementType elementType = getSemanticElementType(semanticAdapter);
    EObject domainElement = getSemanticElement(semanticAdapter);
    int visualID;
    if(semanticHint == null)
    {
      // Semantic hint is not specified. Can be a result of call from CanonicalEditPolicy.
      // In this situation there should be NO elementType, visualID will be determined
      // by VisualIDRegistry.getNodeVisualID() for domainElement.
      if(elementType != null || domainElement == null)
      {
        return null;
      }
      visualID = GmodelVisualIDRegistry.getNodeVisualID(containerView,
          domainElement);
    }
    else
    {
      visualID = GmodelVisualIDRegistry.getVisualID(semanticHint);
      if(elementType != null)
      {
        // Semantic hint is specified together with element type.
        // Both parameters should describe exactly the same diagram element.
        // In addition we check that visualID returned by VisualIDRegistry.getNodeVisualID() for
        // domainElement (if specified) is the same as in element type.
        if(!GmodelElementTypes.isKnownElementType(elementType)
            || (!(elementType instanceof IHintedType)))
        {
          return null; // foreign element type
        }
        String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
        if(!semanticHint.equals(elementTypeHint))
        {
          return null; // if semantic hint is specified it should be the same as in element type
        }
        if(domainElement != null
            && visualID != GmodelVisualIDRegistry.getNodeVisualID(
                containerView, domainElement))
        {
          return null; // visual id for node EClass should match visual id from element type
        }
      }
      else
      {
        // Element type is not specified. Domain element should be present (except pure design elements).
        // This method is called with EObjectAdapter as parameter from:
        //   - ViewService.createNode(View container, EObject eObject, String type, PreferencesHint preferencesHint) 
        //   - generated ViewFactory.decorateView() for parent element
        if(!ModelEditPart.MODEL_ID.equals(GmodelVisualIDRegistry
            .getModelID(containerView)))
        {
          return null; // foreign diagram
        }
        switch(visualID)
        {
          case ConnectionEditPart.VISUAL_ID:
          case GridElementEditPart.VISUAL_ID:
            if(domainElement == null
                || visualID != GmodelVisualIDRegistry.getNodeVisualID(
                    containerView, domainElement))
            {
              return null; // visual id in semantic hint should match visual id for domain element
            }
            break;
          case ConnectionNameEditPart.VISUAL_ID:
            if(ConnectionEditPart.VISUAL_ID != GmodelVisualIDRegistry
                .getVisualID(containerView)
                || containerView.getElement() != domainElement)
            {
              return null; // wrong container
            }
            break;
          case GridElementNameEditPart.VISUAL_ID:
            if(GridElementEditPart.VISUAL_ID != GmodelVisualIDRegistry
                .getVisualID(containerView)
                || containerView.getElement() != domainElement)
            {
              return null; // wrong container
            }
            break;
          default:
            return null;
        }
      }
    }
    return getNodeViewClass(containerView, visualID);
  }

  /**
  * @generated
  */
  protected Class getNodeViewClass(View containerView, int visualID)
  {
    if(containerView == null
        || !GmodelVisualIDRegistry.canCreateNode(containerView, visualID))
    {
      return null;
    }
    switch(visualID)
    {
      case ConnectionEditPart.VISUAL_ID:
        return ConnectionViewFactory.class;
      case ConnectionNameEditPart.VISUAL_ID:
        return ConnectionNameViewFactory.class;
      case GridElementEditPart.VISUAL_ID:
        return GridElementViewFactory.class;
      case GridElementNameEditPart.VISUAL_ID:
        return GridElementNameViewFactory.class;
    }
    return null;
  }

  /**
  * @generated
  */
  protected Class getEdgeViewClass(IAdaptable semanticAdapter,
      View containerView, String semanticHint)
  {
    IElementType elementType = getSemanticElementType(semanticAdapter);
    if(!GmodelElementTypes.isKnownElementType(elementType)
        || (!(elementType instanceof IHintedType)))
    {
      return null; // foreign element type
    }
    String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
    if(elementTypeHint == null)
    {
      return null; // our hint is visual id and must be specified
    }
    if(semanticHint != null && !semanticHint.equals(elementTypeHint))
    {
      return null; // if semantic hint is specified it should be the same as in element type
    }
    int visualID = GmodelVisualIDRegistry.getVisualID(elementTypeHint);
    EObject domainElement = getSemanticElement(semanticAdapter);
    if(domainElement != null
        && visualID != GmodelVisualIDRegistry
            .getLinkWithClassVisualID(domainElement))
    {
      return null; // visual id for link EClass should match visual id from element type
    }
    return getEdgeViewClass(visualID);
  }

  /**
  * @generated
  */
  protected Class getEdgeViewClass(int visualID)
  {
    switch(visualID)
    {
      case LinkEditPart.VISUAL_ID:
        return LinkViewFactory.class;
    }
    return null;
  }

  /**
  * @generated
  */
  private IElementType getSemanticElementType(IAdaptable semanticAdapter)
  {
    if(semanticAdapter == null)
    {
      return null;
    }
    return (IElementType) semanticAdapter.getAdapter(IElementType.class);
  }
}
