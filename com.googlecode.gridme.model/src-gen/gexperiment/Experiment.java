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

import gmodel.Model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Experiment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link gexperiment.Experiment#getDescription <em>Description</em>}</li>
 *   <li>{@link gexperiment.Experiment#getMode <em>Mode</em>}</li>
 *   <li>{@link gexperiment.Experiment#getModel <em>Model</em>}</li>
 *   <li>{@link gexperiment.Experiment#getParameterValues <em>Parameter Values</em>}</li>
 *   <li>{@link gexperiment.Experiment#getRuns <em>Runs</em>}</li>
 *   <li>{@link gexperiment.Experiment#getVisualizers <em>Visualizers</em>}</li>
 *   <li>{@link gexperiment.Experiment#getSeries <em>Series</em>}</li>
 * </ul>
 * </p>
 *
 * @see gexperiment.GexperimentPackage#getExperiment()
 * @model
 * @generated
 */
public interface Experiment extends EObject
{
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
   * @see gexperiment.GexperimentPackage#getExperiment_Description()
   * @model
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link gexperiment.Experiment#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Returns the value of the '<em><b>Mode</b></em>' attribute.
   * The default value is <code>"RELEASE"</code>.
   * The literals are from the enumeration {@link gexperiment.RunMode}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mode</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mode</em>' attribute.
   * @see gexperiment.RunMode
   * @see #setMode(RunMode)
   * @see gexperiment.GexperimentPackage#getExperiment_Mode()
   * @model default="RELEASE" required="true"
   * @generated
   */
  RunMode getMode();

  /**
   * Sets the value of the '{@link gexperiment.Experiment#getMode <em>Mode</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mode</em>' attribute.
   * @see gexperiment.RunMode
   * @see #getMode()
   * @generated
   */
  void setMode(RunMode value);

  /**
   * Returns the value of the '<em><b>Model</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Model</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Model</em>' reference.
   * @see #setModel(Model)
   * @see gexperiment.GexperimentPackage#getExperiment_Model()
   * @model required="true"
   * @generated
   */
  Model getModel();

  /**
   * Sets the value of the '{@link gexperiment.Experiment#getModel <em>Model</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Model</em>' reference.
   * @see #getModel()
   * @generated
   */
  void setModel(Model value);

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
   * @see gexperiment.GexperimentPackage#getExperiment_ParameterValues()
   * @model containment="true"
   * @generated
   */
  EList<ParameterValue> getParameterValues();

  /**
   * Returns the value of the '<em><b>Runs</b></em>' containment reference list.
   * The list contents are of type {@link gexperiment.Run}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Runs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Runs</em>' containment reference list.
   * @see gexperiment.GexperimentPackage#getExperiment_Runs()
   * @model containment="true"
   * @generated
   */
  EList<Run> getRuns();

  /**
   * Returns the value of the '<em><b>Visualizers</b></em>' containment reference list.
   * The list contents are of type {@link gexperiment.Visualizer}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Visualizers</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Visualizers</em>' containment reference list.
   * @see gexperiment.GexperimentPackage#getExperiment_Visualizers()
   * @model containment="true"
   * @generated
   */
  EList<Visualizer> getVisualizers();

  /**
   * Returns the value of the '<em><b>Series</b></em>' containment reference list.
   * The list contents are of type {@link gexperiment.SeriesParameter}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Series</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Series</em>' containment reference list.
   * @see gexperiment.GexperimentPackage#getExperiment_Series()
   * @model containment="true"
   * @generated
   */
  EList<SeriesParameter> getSeries();

} // Experiment
