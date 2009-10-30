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
import gmodel.Parameter;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Visualizer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link gexperiment.Visualizer#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link gexperiment.Visualizer#getParameters <em>Parameters</em>}</li>
 *   <li>{@link gexperiment.Visualizer#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link gexperiment.Visualizer#getStopTime <em>Stop Time</em>}</li>
 *   <li>{@link gexperiment.Visualizer#getCharts <em>Charts</em>}</li>
 *   <li>{@link gexperiment.Visualizer#getInput <em>Input</em>}</li>
 * </ul>
 * </p>
 *
 * @see gexperiment.GexperimentPackage#getVisualizer()
 * @model
 * @generated
 */
public interface Visualizer extends Named
{
  /**
   * Returns the value of the '<em><b>Implementation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Implementation</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Implementation</em>' attribute.
   * @see #setImplementation(String)
   * @see gexperiment.GexperimentPackage#getVisualizer_Implementation()
   * @model required="true"
   * @generated
   */
  String getImplementation();

  /**
   * Sets the value of the '{@link gexperiment.Visualizer#getImplementation <em>Implementation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Implementation</em>' attribute.
   * @see #getImplementation()
   * @generated
   */
  void setImplementation(String value);

  /**
   * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
   * The list contents are of type {@link gmodel.Parameter}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameters</em>' containment reference list.
   * @see gexperiment.GexperimentPackage#getVisualizer_Parameters()
   * @model containment="true"
   * @generated
   */
  EList<Parameter> getParameters();

  /**
   * Returns the value of the '<em><b>Start Time</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Start Time</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Start Time</em>' attribute.
   * @see #setStartTime(String)
   * @see gexperiment.GexperimentPackage#getVisualizer_StartTime()
   * @model required="true"
   * @generated
   */
  String getStartTime();

  /**
   * Sets the value of the '{@link gexperiment.Visualizer#getStartTime <em>Start Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Start Time</em>' attribute.
   * @see #getStartTime()
   * @generated
   */
  void setStartTime(String value);

  /**
   * Returns the value of the '<em><b>Stop Time</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Stop Time</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Stop Time</em>' attribute.
   * @see #setStopTime(String)
   * @see gexperiment.GexperimentPackage#getVisualizer_StopTime()
   * @model required="true"
   * @generated
   */
  String getStopTime();

  /**
   * Sets the value of the '{@link gexperiment.Visualizer#getStopTime <em>Stop Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Stop Time</em>' attribute.
   * @see #getStopTime()
   * @generated
   */
  void setStopTime(String value);

  /**
   * Returns the value of the '<em><b>Charts</b></em>' containment reference list.
   * The list contents are of type {@link gexperiment.Chart}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Charts</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Charts</em>' containment reference list.
   * @see gexperiment.GexperimentPackage#getVisualizer_Charts()
   * @model containment="true"
   * @generated
   */
  EList<Chart> getCharts();

  /**
   * Returns the value of the '<em><b>Input</b></em>' containment reference list.
   * The list contents are of type {@link gexperiment.RunResult}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Input</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Input</em>' containment reference list.
   * @see gexperiment.GexperimentPackage#getVisualizer_Input()
   * @model containment="true"
   * @generated
   */
  EList<RunResult> getInput();

} // Visualizer
