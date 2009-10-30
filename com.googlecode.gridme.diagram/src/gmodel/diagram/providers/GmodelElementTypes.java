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

import gmodel.GmodelPackage;
import gmodel.diagram.part.GmodelDiagramEditorPlugin;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
* @generated
*/
public class GmodelElementTypes extends ElementInitializers
{

  /**
  * @generated
  */
  private GmodelElementTypes()
  {
  }

  /**
  * @generated
  */
  private static Map elements;

  /**
  * @generated
  */
  private static ImageRegistry imageRegistry;

  /**
  * @generated
  */
  private static Set KNOWN_ELEMENT_TYPES;

  /**
  * @generated
  */
  public static final IElementType Model_1000 = getElementType("com.googlecode.gridme.diagram.Model_1000"); //$NON-NLS-1$
  /**
  * @generated
  */
  public static final IElementType Connection_2001 = getElementType("com.googlecode.gridme.diagram.Connection_2001"); //$NON-NLS-1$
  /**
  * @generated
  */
  public static final IElementType GridElement_2002 = getElementType("com.googlecode.gridme.diagram.GridElement_2002"); //$NON-NLS-1$
  /**
  * @generated
  */
  public static final IElementType Link_4002 = getElementType("com.googlecode.gridme.diagram.Link_4002"); //$NON-NLS-1$

  /**
  * @generated
  */
  private static ImageRegistry getImageRegistry()
  {
    if(imageRegistry == null)
    {
      imageRegistry = new ImageRegistry();
    }
    return imageRegistry;
  }

  /**
  * @generated
  */
  private static String getImageRegistryKey(ENamedElement element)
  {
    return element.getName();
  }

  /**
  * @generated
  */
  private static ImageDescriptor getProvidedImageDescriptor(
      ENamedElement element)
  {
    if(element instanceof EStructuralFeature)
    {
      EStructuralFeature feature = ((EStructuralFeature) element);
      EClass eContainingClass = feature.getEContainingClass();
      EClassifier eType = feature.getEType();
      if(eContainingClass != null && !eContainingClass.isAbstract())
      {
        element = eContainingClass;
      }
      else if(eType instanceof EClass && !((EClass) eType).isAbstract())
      {
        element = eType;
      }
    }
    if(element instanceof EClass)
    {
      EClass eClass = (EClass) element;
      if(!eClass.isAbstract())
      {
        return GmodelDiagramEditorPlugin.getInstance().getItemImageDescriptor(
            eClass.getEPackage().getEFactoryInstance().create(eClass));
      }
    }
    // TODO : support structural features
    return null;
  }

  /**
  * @generated
  */
  public static ImageDescriptor getImageDescriptor(ENamedElement element)
  {
    String key = getImageRegistryKey(element);
    ImageDescriptor imageDescriptor = getImageRegistry().getDescriptor(key);
    if(imageDescriptor == null)
    {
      imageDescriptor = getProvidedImageDescriptor(element);
      if(imageDescriptor == null)
      {
        imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
      }
      getImageRegistry().put(key, imageDescriptor);
    }
    return imageDescriptor;
  }

  /**
  * @generated
  */
  public static Image getImage(ENamedElement element)
  {
    String key = getImageRegistryKey(element);
    Image image = getImageRegistry().get(key);
    if(image == null)
    {
      ImageDescriptor imageDescriptor = getProvidedImageDescriptor(element);
      if(imageDescriptor == null)
      {
        imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
      }
      getImageRegistry().put(key, imageDescriptor);
      image = getImageRegistry().get(key);
    }
    return image;
  }

  /**
  * @generated
  */
  public static ImageDescriptor getImageDescriptor(IAdaptable hint)
  {
    ENamedElement element = getElement(hint);
    if(element == null)
    {
      return null;
    }
    return getImageDescriptor(element);
  }

  /**
  * @generated
  */
  public static Image getImage(IAdaptable hint)
  {
    ENamedElement element = getElement(hint);
    if(element == null)
    {
      return null;
    }
    return getImage(element);
  }

  /**
  * Returns 'type' of the ecore object associated with the hint.
  * 
  * @generated
  */
  public static ENamedElement getElement(IAdaptable hint)
  {
    Object type = hint.getAdapter(IElementType.class);
    if(elements == null)
    {
      elements = new IdentityHashMap();

      elements.put(Model_1000, GmodelPackage.eINSTANCE.getModel());

      elements.put(Connection_2001, GmodelPackage.eINSTANCE.getConnection());

      elements.put(GridElement_2002, GmodelPackage.eINSTANCE.getGridElement());

      elements.put(Link_4002, GmodelPackage.eINSTANCE.getLink());
    }
    return (ENamedElement) elements.get(type);
  }

  /**
  * @generated
  */
  private static IElementType getElementType(String id)
  {
    return ElementTypeRegistry.getInstance().getType(id);
  }

  /**
  * @generated
  */
  public static boolean isKnownElementType(IElementType elementType)
  {
    if(KNOWN_ELEMENT_TYPES == null)
    {
      KNOWN_ELEMENT_TYPES = new HashSet();
      KNOWN_ELEMENT_TYPES.add(Model_1000);
      KNOWN_ELEMENT_TYPES.add(Connection_2001);
      KNOWN_ELEMENT_TYPES.add(GridElement_2002);
      KNOWN_ELEMENT_TYPES.add(Link_4002);
    }
    return KNOWN_ELEMENT_TYPES.contains(elementType);
  }

}
