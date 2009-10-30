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

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
* @generated
*/
public class GmodelDomainModelElementTester extends PropertyTester
{

  /**
  * @generated
  */
  public boolean test(Object receiver, String method, Object[] args,
      Object expectedValue)
  {
    if(false == receiver instanceof EObject)
    {
      return false;
    }
    EObject eObject = (EObject) receiver;
    EClass eClass = eObject.eClass();
    if(eClass == GmodelPackage.eINSTANCE.getNamed())
    {
      return true;
    }
    if(eClass == GmodelPackage.eINSTANCE.getModel())
    {
      return true;
    }
    if(eClass == GmodelPackage.eINSTANCE.getModelElement())
    {
      return true;
    }
    if(eClass == GmodelPackage.eINSTANCE.getConnection())
    {
      return true;
    }
    if(eClass == GmodelPackage.eINSTANCE.getGridElement())
    {
      return true;
    }
    if(eClass == GmodelPackage.eINSTANCE.getLink())
    {
      return true;
    }
    if(eClass == GmodelPackage.eINSTANCE.getGenericModelElement())
    {
      return true;
    }
    if(eClass == GmodelPackage.eINSTANCE.getRParameter())
    {
      return true;
    }
    if(eClass == GmodelPackage.eINSTANCE.getParameter())
    {
      return true;
    }
    return false;
  }

}
