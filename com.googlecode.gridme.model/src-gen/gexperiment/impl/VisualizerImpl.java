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
package gexperiment.impl;

import gexperiment.Chart;
import gexperiment.GexperimentPackage;
import gexperiment.RunResult;
import gexperiment.Visualizer;

import gmodel.Parameter;

import gmodel.impl.NamedImpl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Visualizer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link gexperiment.impl.VisualizerImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link gexperiment.impl.VisualizerImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link gexperiment.impl.VisualizerImpl#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link gexperiment.impl.VisualizerImpl#getStopTime <em>Stop Time</em>}</li>
 *   <li>{@link gexperiment.impl.VisualizerImpl#getCharts <em>Charts</em>}</li>
 *   <li>{@link gexperiment.impl.VisualizerImpl#getInput <em>Input</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VisualizerImpl extends NamedImpl implements Visualizer
{
  /**
   * The default value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImplementation()
   * @generated
   * @ordered
   */
  protected static final String IMPLEMENTATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImplementation()
   * @generated
   * @ordered
   */
  protected String implementation = IMPLEMENTATION_EDEFAULT;

  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected EList<Parameter> parameters;

  /**
   * The default value of the '{@link #getStartTime() <em>Start Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartTime()
   * @generated
   * @ordered
   */
  protected static final String START_TIME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStartTime() <em>Start Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartTime()
   * @generated
   * @ordered
   */
  protected String startTime = START_TIME_EDEFAULT;

  /**
   * The default value of the '{@link #getStopTime() <em>Stop Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStopTime()
   * @generated
   * @ordered
   */
  protected static final String STOP_TIME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStopTime() <em>Stop Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStopTime()
   * @generated
   * @ordered
   */
  protected String stopTime = STOP_TIME_EDEFAULT;

  /**
   * The cached value of the '{@link #getCharts() <em>Charts</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCharts()
   * @generated
   * @ordered
   */
  protected EList<Chart> charts;

  /**
   * The cached value of the '{@link #getInput() <em>Input</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInput()
   * @generated
   * @ordered
   */
  protected EList<RunResult> input;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VisualizerImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return GexperimentPackage.Literals.VISUALIZER;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getImplementation()
  {
    return implementation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setImplementation(String newImplementation)
  {
    String oldImplementation = implementation;
    implementation = newImplementation;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GexperimentPackage.VISUALIZER__IMPLEMENTATION, oldImplementation, implementation));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Parameter> getParameters()
  {
    if (parameters == null)
    {
      parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, GexperimentPackage.VISUALIZER__PARAMETERS);
    }
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getStartTime()
  {
    return startTime;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setStartTime(String newStartTime)
  {
    String oldStartTime = startTime;
    startTime = newStartTime;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GexperimentPackage.VISUALIZER__START_TIME, oldStartTime, startTime));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getStopTime()
  {
    return stopTime;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setStopTime(String newStopTime)
  {
    String oldStopTime = stopTime;
    stopTime = newStopTime;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GexperimentPackage.VISUALIZER__STOP_TIME, oldStopTime, stopTime));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Chart> getCharts()
  {
    if (charts == null)
    {
      charts = new EObjectContainmentEList<Chart>(Chart.class, this, GexperimentPackage.VISUALIZER__CHARTS);
    }
    return charts;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<RunResult> getInput()
  {
    if (input == null)
    {
      input = new EObjectContainmentEList<RunResult>(RunResult.class, this, GexperimentPackage.VISUALIZER__INPUT);
    }
    return input;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case GexperimentPackage.VISUALIZER__PARAMETERS:
        return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
      case GexperimentPackage.VISUALIZER__CHARTS:
        return ((InternalEList<?>)getCharts()).basicRemove(otherEnd, msgs);
      case GexperimentPackage.VISUALIZER__INPUT:
        return ((InternalEList<?>)getInput()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case GexperimentPackage.VISUALIZER__IMPLEMENTATION:
        return getImplementation();
      case GexperimentPackage.VISUALIZER__PARAMETERS:
        return getParameters();
      case GexperimentPackage.VISUALIZER__START_TIME:
        return getStartTime();
      case GexperimentPackage.VISUALIZER__STOP_TIME:
        return getStopTime();
      case GexperimentPackage.VISUALIZER__CHARTS:
        return getCharts();
      case GexperimentPackage.VISUALIZER__INPUT:
        return getInput();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case GexperimentPackage.VISUALIZER__IMPLEMENTATION:
        setImplementation((String)newValue);
        return;
      case GexperimentPackage.VISUALIZER__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends Parameter>)newValue);
        return;
      case GexperimentPackage.VISUALIZER__START_TIME:
        setStartTime((String)newValue);
        return;
      case GexperimentPackage.VISUALIZER__STOP_TIME:
        setStopTime((String)newValue);
        return;
      case GexperimentPackage.VISUALIZER__CHARTS:
        getCharts().clear();
        getCharts().addAll((Collection<? extends Chart>)newValue);
        return;
      case GexperimentPackage.VISUALIZER__INPUT:
        getInput().clear();
        getInput().addAll((Collection<? extends RunResult>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case GexperimentPackage.VISUALIZER__IMPLEMENTATION:
        setImplementation(IMPLEMENTATION_EDEFAULT);
        return;
      case GexperimentPackage.VISUALIZER__PARAMETERS:
        getParameters().clear();
        return;
      case GexperimentPackage.VISUALIZER__START_TIME:
        setStartTime(START_TIME_EDEFAULT);
        return;
      case GexperimentPackage.VISUALIZER__STOP_TIME:
        setStopTime(STOP_TIME_EDEFAULT);
        return;
      case GexperimentPackage.VISUALIZER__CHARTS:
        getCharts().clear();
        return;
      case GexperimentPackage.VISUALIZER__INPUT:
        getInput().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case GexperimentPackage.VISUALIZER__IMPLEMENTATION:
        return IMPLEMENTATION_EDEFAULT == null ? implementation != null : !IMPLEMENTATION_EDEFAULT.equals(implementation);
      case GexperimentPackage.VISUALIZER__PARAMETERS:
        return parameters != null && !parameters.isEmpty();
      case GexperimentPackage.VISUALIZER__START_TIME:
        return START_TIME_EDEFAULT == null ? startTime != null : !START_TIME_EDEFAULT.equals(startTime);
      case GexperimentPackage.VISUALIZER__STOP_TIME:
        return STOP_TIME_EDEFAULT == null ? stopTime != null : !STOP_TIME_EDEFAULT.equals(stopTime);
      case GexperimentPackage.VISUALIZER__CHARTS:
        return charts != null && !charts.isEmpty();
      case GexperimentPackage.VISUALIZER__INPUT:
        return input != null && !input.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (implementation: ");
    result.append(implementation);
    result.append(", startTime: ");
    result.append(startTime);
    result.append(", stopTime: ");
    result.append(stopTime);
    result.append(')');
    return result.toString();
  }

} //VisualizerImpl
