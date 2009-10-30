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

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
* @generated
*/
public class GmodelLinkDescriptor extends GmodelNodeDescriptor
{

  /**
  * @generated
  */
  private EObject mySource;

  /**
  * @generated
  */
  private EObject myDestination;

  /**
  * @generated
  */
  private IAdaptable mySemanticAdapter;

  /**
  * @generated
  */
  private GmodelLinkDescriptor(EObject source, EObject destination,
      EObject linkElement, int linkVID)
  {
    super(linkElement, linkVID);
    mySource = source;
    myDestination = destination;
  }

  /**
  * @generated
  */
  public GmodelLinkDescriptor(EObject source, EObject destination,
      IElementType elementType, int linkVID)
  {
    this(source, destination, (EObject) null, linkVID);
    final IElementType elementTypeCopy = elementType;
    mySemanticAdapter = new IAdaptable()
    {
      public Object getAdapter(Class adapter)
      {
        if(IElementType.class.equals(adapter))
        {
          return elementTypeCopy;
        }
        return null;
      }
    };
  }

  /**
  * @generated
  */
  public GmodelLinkDescriptor(EObject source, EObject destination,
      EObject linkElement, IElementType elementType, int linkVID)
  {
    this(source, destination, linkElement, linkVID);
    final IElementType elementTypeCopy = elementType;
    mySemanticAdapter = new EObjectAdapter(linkElement)
    {
      public Object getAdapter(Class adapter)
      {
        if(IElementType.class.equals(adapter))
        {
          return elementTypeCopy;
        }
        return super.getAdapter(adapter);
      }
    };
  }

  /**
  * @generated
  */
  public EObject getSource()
  {
    return mySource;
  }

  /**
  * @generated
  */
  public EObject getDestination()
  {
    return myDestination;
  }

  /**
  * @generated
  */
  public IAdaptable getSemanticAdapter()
  {
    return mySemanticAdapter;
  }

}
