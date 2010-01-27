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
package gexperiment.util;

import gexperiment.*;

import gmodel.Named;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see gexperiment.GexperimentPackage
 * @generated
 */
public class GexperimentAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static GexperimentPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GexperimentAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = GexperimentPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected GexperimentSwitch<Adapter> modelSwitch =
    new GexperimentSwitch<Adapter>()
    {
      @Override
      public Adapter caseExperiment(Experiment object)
      {
        return createExperimentAdapter();
      }
      @Override
      public Adapter caseRun(Run object)
      {
        return createRunAdapter();
      }
      @Override
      public Adapter caseVisualizer(Visualizer object)
      {
        return createVisualizerAdapter();
      }
      @Override
      public Adapter caseChart(Chart object)
      {
        return createChartAdapter();
      }
      @Override
      public Adapter caseRunResult(RunResult object)
      {
        return createRunResultAdapter();
      }
      @Override
      public Adapter caseParameterValue(ParameterValue object)
      {
        return createParameterValueAdapter();
      }
      @Override
      public Adapter caseSeriesParameter(SeriesParameter object)
      {
        return createSeriesParameterAdapter();
      }
      @Override
      public Adapter caseNamed(Named object)
      {
        return createNamedAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link gexperiment.Experiment <em>Experiment</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see gexperiment.Experiment
   * @generated
   */
  public Adapter createExperimentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link gexperiment.Run <em>Run</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see gexperiment.Run
   * @generated
   */
  public Adapter createRunAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link gexperiment.Visualizer <em>Visualizer</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see gexperiment.Visualizer
   * @generated
   */
  public Adapter createVisualizerAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link gexperiment.Chart <em>Chart</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see gexperiment.Chart
   * @generated
   */
  public Adapter createChartAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link gexperiment.RunResult <em>Run Result</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see gexperiment.RunResult
   * @generated
   */
  public Adapter createRunResultAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link gexperiment.ParameterValue <em>Parameter Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see gexperiment.ParameterValue
   * @generated
   */
  public Adapter createParameterValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link gexperiment.SeriesParameter <em>Series Parameter</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see gexperiment.SeriesParameter
   * @generated
   */
  public Adapter createSeriesParameterAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link gmodel.Named <em>Named</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see gmodel.Named
   * @generated
   */
  public Adapter createNamedAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //GexperimentAdapterFactory
