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

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import statemachine.StatemachinePackage;

/**
* @generated
*/
public class StatemachineDomainModelElementTester extends PropertyTester
{

  /**
  * @generated
  */
  public boolean test(Object receiver,
                      String method,
                      Object[] args,
                      Object expectedValue)
  {
    if(false == receiver instanceof EObject)
    {
      return false;
    }
    EObject eObject = (EObject) receiver;
    EClass eClass = eObject.eClass();
    if(eClass == StatemachinePackage.eINSTANCE.getNamed())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getGAbstractState())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getGAbstractAction())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getGState())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getTransition())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getGCompositeState())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getGStartState())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getGStopState())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getGStatemachine())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getCallAction())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getParameter())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getValue())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getGetParameter())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getCall())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getConstantValue())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getStringValue())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getNumberValue())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getBooleanValue())
    {
      return true;
    }
    if(eClass == StatemachinePackage.eINSTANCE.getLongValue())
    {
      return true;
    }
    return false;
  }

}
