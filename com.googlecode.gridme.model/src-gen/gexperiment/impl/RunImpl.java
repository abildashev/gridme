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

import gexperiment.GexperimentPackage;
import gexperiment.ParameterValue;
import gexperiment.Run;

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
 * An implementation of the model object '<em><b>Run</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link gexperiment.impl.RunImpl#getTimezone <em>Timezone</em>}</li>
 *   <li>{@link gexperiment.impl.RunImpl#getStartRealTime <em>Start Real Time</em>}</li>
 *   <li>{@link gexperiment.impl.RunImpl#getLength <em>Length</em>}</li>
 *   <li>{@link gexperiment.impl.RunImpl#getParameterValues <em>Parameter Values</em>}</li>
 *   <li>{@link gexperiment.impl.RunImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RunImpl extends NamedImpl implements Run
{
  /**
   * The default value of the '{@link #getTimezone() <em>Timezone</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTimezone()
   * @generated
   * @ordered
   */
  protected static final String TIMEZONE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTimezone() <em>Timezone</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTimezone()
   * @generated
   * @ordered
   */
  protected String timezone = TIMEZONE_EDEFAULT;

  /**
   * The default value of the '{@link #getStartRealTime() <em>Start Real Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartRealTime()
   * @generated
   * @ordered
   */
  protected static final String START_REAL_TIME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStartRealTime() <em>Start Real Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartRealTime()
   * @generated
   * @ordered
   */
  protected String startRealTime = START_REAL_TIME_EDEFAULT;

  /**
   * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLength()
   * @generated
   * @ordered
   */
  protected static final String LENGTH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLength()
   * @generated
   * @ordered
   */
  protected String length = LENGTH_EDEFAULT;

  /**
   * The cached value of the '{@link #getParameterValues() <em>Parameter Values</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameterValues()
   * @generated
   * @ordered
   */
  protected EList<ParameterValue> parameterValues;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RunImpl()
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
    return GexperimentPackage.Literals.RUN;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getTimezone()
  {
    return timezone;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTimezone(String newTimezone)
  {
    String oldTimezone = timezone;
    timezone = newTimezone;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GexperimentPackage.RUN__TIMEZONE, oldTimezone, timezone));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getStartRealTime()
  {
    return startRealTime;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setStartRealTime(String newStartRealTime)
  {
    String oldStartRealTime = startRealTime;
    startRealTime = newStartRealTime;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GexperimentPackage.RUN__START_REAL_TIME, oldStartRealTime, startRealTime));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLength()
  {
    return length;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLength(String newLength)
  {
    String oldLength = length;
    length = newLength;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GexperimentPackage.RUN__LENGTH, oldLength, length));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ParameterValue> getParameterValues()
  {
    if (parameterValues == null)
    {
      parameterValues = new EObjectContainmentEList<ParameterValue>(ParameterValue.class, this, GexperimentPackage.RUN__PARAMETER_VALUES);
    }
    return parameterValues;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(String newDescription)
  {
    String oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GexperimentPackage.RUN__DESCRIPTION, oldDescription, description));
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
      case GexperimentPackage.RUN__PARAMETER_VALUES:
        return ((InternalEList<?>)getParameterValues()).basicRemove(otherEnd, msgs);
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
      case GexperimentPackage.RUN__TIMEZONE:
        return getTimezone();
      case GexperimentPackage.RUN__START_REAL_TIME:
        return getStartRealTime();
      case GexperimentPackage.RUN__LENGTH:
        return getLength();
      case GexperimentPackage.RUN__PARAMETER_VALUES:
        return getParameterValues();
      case GexperimentPackage.RUN__DESCRIPTION:
        return getDescription();
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
      case GexperimentPackage.RUN__TIMEZONE:
        setTimezone((String)newValue);
        return;
      case GexperimentPackage.RUN__START_REAL_TIME:
        setStartRealTime((String)newValue);
        return;
      case GexperimentPackage.RUN__LENGTH:
        setLength((String)newValue);
        return;
      case GexperimentPackage.RUN__PARAMETER_VALUES:
        getParameterValues().clear();
        getParameterValues().addAll((Collection<? extends ParameterValue>)newValue);
        return;
      case GexperimentPackage.RUN__DESCRIPTION:
        setDescription((String)newValue);
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
      case GexperimentPackage.RUN__TIMEZONE:
        setTimezone(TIMEZONE_EDEFAULT);
        return;
      case GexperimentPackage.RUN__START_REAL_TIME:
        setStartRealTime(START_REAL_TIME_EDEFAULT);
        return;
      case GexperimentPackage.RUN__LENGTH:
        setLength(LENGTH_EDEFAULT);
        return;
      case GexperimentPackage.RUN__PARAMETER_VALUES:
        getParameterValues().clear();
        return;
      case GexperimentPackage.RUN__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
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
      case GexperimentPackage.RUN__TIMEZONE:
        return TIMEZONE_EDEFAULT == null ? timezone != null : !TIMEZONE_EDEFAULT.equals(timezone);
      case GexperimentPackage.RUN__START_REAL_TIME:
        return START_REAL_TIME_EDEFAULT == null ? startRealTime != null : !START_REAL_TIME_EDEFAULT.equals(startRealTime);
      case GexperimentPackage.RUN__LENGTH:
        return LENGTH_EDEFAULT == null ? length != null : !LENGTH_EDEFAULT.equals(length);
      case GexperimentPackage.RUN__PARAMETER_VALUES:
        return parameterValues != null && !parameterValues.isEmpty();
      case GexperimentPackage.RUN__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
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
    result.append(" (timezone: ");
    result.append(timezone);
    result.append(", startRealTime: ");
    result.append(startRealTime);
    result.append(", length: ");
    result.append(length);
    result.append(", description: ");
    result.append(description);
    result.append(')');
    return result.toString();
  }

} //RunImpl
