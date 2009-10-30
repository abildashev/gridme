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

import gexperiment.Experiment;
import gexperiment.GexperimentPackage;
import gexperiment.ParameterValue;
import gexperiment.Run;
import gexperiment.RunMode;
import gexperiment.Visualizer;

import gmodel.Model;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Experiment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link gexperiment.impl.ExperimentImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link gexperiment.impl.ExperimentImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link gexperiment.impl.ExperimentImpl#getModel <em>Model</em>}</li>
 *   <li>{@link gexperiment.impl.ExperimentImpl#getParameterValues <em>Parameter Values</em>}</li>
 *   <li>{@link gexperiment.impl.ExperimentImpl#getRuns <em>Runs</em>}</li>
 *   <li>{@link gexperiment.impl.ExperimentImpl#getVisualizers <em>Visualizers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExperimentImpl extends EObjectImpl implements Experiment
{
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
   * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMode()
   * @generated
   * @ordered
   */
  protected static final RunMode MODE_EDEFAULT = RunMode.RELEASE;

  /**
   * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMode()
   * @generated
   * @ordered
   */
  protected RunMode mode = MODE_EDEFAULT;

  /**
   * The cached value of the '{@link #getModel() <em>Model</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModel()
   * @generated
   * @ordered
   */
  protected Model model;

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
   * The cached value of the '{@link #getRuns() <em>Runs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRuns()
   * @generated
   * @ordered
   */
  protected EList<Run> runs;

  /**
   * The cached value of the '{@link #getVisualizers() <em>Visualizers</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVisualizers()
   * @generated
   * @ordered
   */
  protected EList<Visualizer> visualizers;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ExperimentImpl()
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
    return GexperimentPackage.Literals.EXPERIMENT;
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
      eNotify(new ENotificationImpl(this, Notification.SET, GexperimentPackage.EXPERIMENT__DESCRIPTION, oldDescription, description));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RunMode getMode()
  {
    return mode;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMode(RunMode newMode)
  {
    RunMode oldMode = mode;
    mode = newMode == null ? MODE_EDEFAULT : newMode;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GexperimentPackage.EXPERIMENT__MODE, oldMode, mode));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Model getModel()
  {
    if (model != null && model.eIsProxy())
    {
      InternalEObject oldModel = (InternalEObject)model;
      model = (Model)eResolveProxy(oldModel);
      if (model != oldModel)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, GexperimentPackage.EXPERIMENT__MODEL, oldModel, model));
      }
    }
    return model;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Model basicGetModel()
  {
    return model;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setModel(Model newModel)
  {
    Model oldModel = model;
    model = newModel;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GexperimentPackage.EXPERIMENT__MODEL, oldModel, model));
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
      parameterValues = new EObjectContainmentEList<ParameterValue>(ParameterValue.class, this, GexperimentPackage.EXPERIMENT__PARAMETER_VALUES);
    }
    return parameterValues;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Run> getRuns()
  {
    if (runs == null)
    {
      runs = new EObjectContainmentEList<Run>(Run.class, this, GexperimentPackage.EXPERIMENT__RUNS);
    }
    return runs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Visualizer> getVisualizers()
  {
    if (visualizers == null)
    {
      visualizers = new EObjectContainmentEList<Visualizer>(Visualizer.class, this, GexperimentPackage.EXPERIMENT__VISUALIZERS);
    }
    return visualizers;
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
      case GexperimentPackage.EXPERIMENT__PARAMETER_VALUES:
        return ((InternalEList<?>)getParameterValues()).basicRemove(otherEnd, msgs);
      case GexperimentPackage.EXPERIMENT__RUNS:
        return ((InternalEList<?>)getRuns()).basicRemove(otherEnd, msgs);
      case GexperimentPackage.EXPERIMENT__VISUALIZERS:
        return ((InternalEList<?>)getVisualizers()).basicRemove(otherEnd, msgs);
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
      case GexperimentPackage.EXPERIMENT__DESCRIPTION:
        return getDescription();
      case GexperimentPackage.EXPERIMENT__MODE:
        return getMode();
      case GexperimentPackage.EXPERIMENT__MODEL:
        if (resolve) return getModel();
        return basicGetModel();
      case GexperimentPackage.EXPERIMENT__PARAMETER_VALUES:
        return getParameterValues();
      case GexperimentPackage.EXPERIMENT__RUNS:
        return getRuns();
      case GexperimentPackage.EXPERIMENT__VISUALIZERS:
        return getVisualizers();
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
      case GexperimentPackage.EXPERIMENT__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case GexperimentPackage.EXPERIMENT__MODE:
        setMode((RunMode)newValue);
        return;
      case GexperimentPackage.EXPERIMENT__MODEL:
        setModel((Model)newValue);
        return;
      case GexperimentPackage.EXPERIMENT__PARAMETER_VALUES:
        getParameterValues().clear();
        getParameterValues().addAll((Collection<? extends ParameterValue>)newValue);
        return;
      case GexperimentPackage.EXPERIMENT__RUNS:
        getRuns().clear();
        getRuns().addAll((Collection<? extends Run>)newValue);
        return;
      case GexperimentPackage.EXPERIMENT__VISUALIZERS:
        getVisualizers().clear();
        getVisualizers().addAll((Collection<? extends Visualizer>)newValue);
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
      case GexperimentPackage.EXPERIMENT__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
      case GexperimentPackage.EXPERIMENT__MODE:
        setMode(MODE_EDEFAULT);
        return;
      case GexperimentPackage.EXPERIMENT__MODEL:
        setModel((Model)null);
        return;
      case GexperimentPackage.EXPERIMENT__PARAMETER_VALUES:
        getParameterValues().clear();
        return;
      case GexperimentPackage.EXPERIMENT__RUNS:
        getRuns().clear();
        return;
      case GexperimentPackage.EXPERIMENT__VISUALIZERS:
        getVisualizers().clear();
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
      case GexperimentPackage.EXPERIMENT__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
      case GexperimentPackage.EXPERIMENT__MODE:
        return mode != MODE_EDEFAULT;
      case GexperimentPackage.EXPERIMENT__MODEL:
        return model != null;
      case GexperimentPackage.EXPERIMENT__PARAMETER_VALUES:
        return parameterValues != null && !parameterValues.isEmpty();
      case GexperimentPackage.EXPERIMENT__RUNS:
        return runs != null && !runs.isEmpty();
      case GexperimentPackage.EXPERIMENT__VISUALIZERS:
        return visualizers != null && !visualizers.isEmpty();
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
    result.append(" (description: ");
    result.append(description);
    result.append(", mode: ");
    result.append(mode);
    result.append(')');
    return result.toString();
  }

} //ExperimentImpl
