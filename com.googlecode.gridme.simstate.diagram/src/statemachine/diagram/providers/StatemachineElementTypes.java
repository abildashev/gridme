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
package statemachine.diagram.providers;

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

import statemachine.StatemachinePackage;
import statemachine.diagram.part.StatemachineDiagramEditorPlugin;

/**
* @generated
*/
public class StatemachineElementTypes extends ElementInitializers
{

  /**
  * @generated
  */
  private StatemachineElementTypes()
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
  public static final IElementType GStatemachine_79 = getElementType("com.googlecode.gridme.simstate.diagram.GStatemachine_79"); //$NON-NLS-1$
  /**
  * @generated
  */
  public static final IElementType GState_1001 = getElementType("com.googlecode.gridme.simstate.diagram.GState_1001"); //$NON-NLS-1$
  /**
  * @generated
  */
  public static final IElementType GStartState_1002 = getElementType("com.googlecode.gridme.simstate.diagram.GStartState_1002"); //$NON-NLS-1$
  /**
  * @generated
  */
  public static final IElementType GStopState_1003 = getElementType("com.googlecode.gridme.simstate.diagram.GStopState_1003"); //$NON-NLS-1$
  /**
  * @generated
  */
  public static final IElementType Transition_3001 = getElementType("com.googlecode.gridme.simstate.diagram.Transition_3001"); //$NON-NLS-1$

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
  private static ImageDescriptor getProvidedImageDescriptor(ENamedElement element)
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
        return StatemachineDiagramEditorPlugin.getInstance()
            .getItemImageDescriptor(
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

      elements.put(GStatemachine_79, StatemachinePackage.eINSTANCE
          .getGStatemachine());

      elements.put(GState_1001, StatemachinePackage.eINSTANCE.getGState());

      elements.put(GStartState_1002, StatemachinePackage.eINSTANCE
          .getGStartState());

      elements.put(GStopState_1003, StatemachinePackage.eINSTANCE
          .getGStopState());

      elements.put(Transition_3001, StatemachinePackage.eINSTANCE
          .getTransition());
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
      KNOWN_ELEMENT_TYPES.add(GStatemachine_79);
      KNOWN_ELEMENT_TYPES.add(GState_1001);
      KNOWN_ELEMENT_TYPES.add(GStartState_1002);
      KNOWN_ELEMENT_TYPES.add(GStopState_1003);
      KNOWN_ELEMENT_TYPES.add(Transition_3001);
    }
    return KNOWN_ELEMENT_TYPES.contains(elementType);
  }

}
