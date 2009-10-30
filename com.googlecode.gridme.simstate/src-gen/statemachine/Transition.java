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
package statemachine;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link statemachine.Transition#getFrom <em>From</em>}</li>
 *   <li>{@link statemachine.Transition#getTo <em>To</em>}</li>
 *   <li>{@link statemachine.Transition#getGuard <em>Guard</em>}</li>
 *   <li>{@link statemachine.Transition#getDelay <em>Delay</em>}</li>
 *   <li>{@link statemachine.Transition#getSignals <em>Signals</em>}</li>
 *   <li>{@link statemachine.Transition#isPreserveTimers <em>Preserve Timers</em>}</li>
 * </ul>
 * </p>
 *
 * @see statemachine.StatemachinePackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends Named
{
  /**
   * Returns the value of the '<em><b>From</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>From</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>From</em>' reference.
   * @see #setFrom(GAbstractState)
   * @see statemachine.StatemachinePackage#getTransition_From()
   * @model
   * @generated
   */
  GAbstractState getFrom();

  /**
   * Sets the value of the '{@link statemachine.Transition#getFrom <em>From</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>From</em>' reference.
   * @see #getFrom()
   * @generated
   */
  void setFrom(GAbstractState value);

  /**
   * Returns the value of the '<em><b>To</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>To</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>To</em>' reference.
   * @see #setTo(GAbstractState)
   * @see statemachine.StatemachinePackage#getTransition_To()
   * @model
   * @generated
   */
  GAbstractState getTo();

  /**
   * Sets the value of the '{@link statemachine.Transition#getTo <em>To</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>To</em>' reference.
   * @see #getTo()
   * @generated
   */
  void setTo(GAbstractState value);

  /**
   * Returns the value of the '<em><b>Guard</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Guard</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Guard</em>' containment reference.
   * @see #setGuard(Value)
   * @see statemachine.StatemachinePackage#getTransition_Guard()
   * @model containment="true"
   * @generated
   */
  Value getGuard();

  /**
   * Sets the value of the '{@link statemachine.Transition#getGuard <em>Guard</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Guard</em>' containment reference.
   * @see #getGuard()
   * @generated
   */
  void setGuard(Value value);

  /**
   * Returns the value of the '<em><b>Delay</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Delay</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Delay</em>' containment reference.
   * @see #setDelay(Value)
   * @see statemachine.StatemachinePackage#getTransition_Delay()
   * @model containment="true"
   * @generated
   */
  Value getDelay();

  /**
   * Sets the value of the '{@link statemachine.Transition#getDelay <em>Delay</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Delay</em>' containment reference.
   * @see #getDelay()
   * @generated
   */
  void setDelay(Value value);

  /**
   * Returns the value of the '<em><b>Signals</b></em>' containment reference list.
   * The list contents are of type {@link statemachine.Value}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Signals</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Signals</em>' containment reference list.
   * @see statemachine.StatemachinePackage#getTransition_Signals()
   * @model containment="true"
   * @generated
   */
  EList<Value> getSignals();

  /**
   * Returns the value of the '<em><b>Preserve Timers</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Preserve Timers</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Preserve Timers</em>' attribute.
   * @see #setPreserveTimers(boolean)
   * @see statemachine.StatemachinePackage#getTransition_PreserveTimers()
   * @model
   * @generated
   */
  boolean isPreserveTimers();

  /**
   * Sets the value of the '{@link statemachine.Transition#isPreserveTimers <em>Preserve Timers</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Preserve Timers</em>' attribute.
   * @see #isPreserveTimers()
   * @generated
   */
  void setPreserveTimers(boolean value);

} // Transition
