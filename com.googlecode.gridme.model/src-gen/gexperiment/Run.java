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
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package gexperiment;

import gmodel.Named;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Run</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link gexperiment.Run#getTimezone <em>Timezone</em>}</li>
 *   <li>{@link gexperiment.Run#getStartRealTime <em>Start Real Time</em>}</li>
 *   <li>{@link gexperiment.Run#getLength <em>Length</em>}</li>
 *   <li>{@link gexperiment.Run#getParameterValues <em>Parameter Values</em>}</li>
 *   <li>{@link gexperiment.Run#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see gexperiment.GexperimentPackage#getRun()
 * @model
 * @generated
 */
public interface Run extends Named
{
  /**
   * Returns the value of the '<em><b>Timezone</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Timezone</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Timezone</em>' attribute.
   * @see #setTimezone(String)
   * @see gexperiment.GexperimentPackage#getRun_Timezone()
   * @model required="true"
   * @generated
   */
  String getTimezone();

  /**
   * Sets the value of the '{@link gexperiment.Run#getTimezone <em>Timezone</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Timezone</em>' attribute.
   * @see #getTimezone()
   * @generated
   */
  void setTimezone(String value);

  /**
   * Returns the value of the '<em><b>Start Real Time</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Start Real Time</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Start Real Time</em>' attribute.
   * @see #setStartRealTime(String)
   * @see gexperiment.GexperimentPackage#getRun_StartRealTime()
   * @model required="true"
   * @generated
   */
  String getStartRealTime();

  /**
   * Sets the value of the '{@link gexperiment.Run#getStartRealTime <em>Start Real Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Start Real Time</em>' attribute.
   * @see #getStartRealTime()
   * @generated
   */
  void setStartRealTime(String value);

  /**
   * Returns the value of the '<em><b>Length</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Length</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Length</em>' attribute.
   * @see #setLength(String)
   * @see gexperiment.GexperimentPackage#getRun_Length()
   * @model required="true"
   * @generated
   */
  String getLength();

  /**
   * Sets the value of the '{@link gexperiment.Run#getLength <em>Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Length</em>' attribute.
   * @see #getLength()
   * @generated
   */
  void setLength(String value);

  /**
   * Returns the value of the '<em><b>Parameter Values</b></em>' containment reference list.
   * The list contents are of type {@link gexperiment.ParameterValue}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameter Values</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameter Values</em>' containment reference list.
   * @see gexperiment.GexperimentPackage#getRun_ParameterValues()
   * @model containment="true"
   * @generated
   */
  EList<ParameterValue> getParameterValues();

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #setDescription(String)
   * @see gexperiment.GexperimentPackage#getRun_Description()
   * @model
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link gexperiment.Run#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

} // Run
