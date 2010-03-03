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

import gmodel.GmodelPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see gexperiment.GexperimentFactory
 * @model kind="package"
 * @generated
 */
public interface GexperimentPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "gexperiment";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://gridme.googlecode.com/gexperiment";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "gexperiment";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  GexperimentPackage eINSTANCE = gexperiment.impl.GexperimentPackageImpl.init();

  /**
   * The meta object id for the '{@link gexperiment.impl.ExperimentImpl <em>Experiment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gexperiment.impl.ExperimentImpl
   * @see gexperiment.impl.GexperimentPackageImpl#getExperiment()
   * @generated
   */
  int EXPERIMENT = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPERIMENT__DESCRIPTION = 0;

  /**
   * The feature id for the '<em><b>Mode</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPERIMENT__MODE = 1;

  /**
   * The feature id for the '<em><b>Model</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPERIMENT__MODEL = 2;

  /**
   * The feature id for the '<em><b>Parameter Values</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPERIMENT__PARAMETER_VALUES = 3;

  /**
   * The feature id for the '<em><b>Runs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPERIMENT__RUNS = 4;

  /**
   * The feature id for the '<em><b>Visualizers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPERIMENT__VISUALIZERS = 5;

  /**
   * The feature id for the '<em><b>Series</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPERIMENT__SERIES = 6;

  /**
   * The number of structural features of the '<em>Experiment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPERIMENT_FEATURE_COUNT = 7;

  /**
   * The meta object id for the '{@link gexperiment.impl.RunImpl <em>Run</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gexperiment.impl.RunImpl
   * @see gexperiment.impl.GexperimentPackageImpl#getRun()
   * @generated
   */
  int RUN = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN__NAME = GmodelPackage.NAMED__NAME;

  /**
   * The feature id for the '<em><b>Timezone</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN__TIMEZONE = GmodelPackage.NAMED_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Start Real Time</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN__START_REAL_TIME = GmodelPackage.NAMED_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Length</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN__LENGTH = GmodelPackage.NAMED_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Parameter Values</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN__PARAMETER_VALUES = GmodelPackage.NAMED_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN__DESCRIPTION = GmodelPackage.NAMED_FEATURE_COUNT + 4;

  /**
   * The number of structural features of the '<em>Run</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN_FEATURE_COUNT = GmodelPackage.NAMED_FEATURE_COUNT + 5;

  /**
   * The meta object id for the '{@link gexperiment.impl.VisualizerImpl <em>Visualizer</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gexperiment.impl.VisualizerImpl
   * @see gexperiment.impl.GexperimentPackageImpl#getVisualizer()
   * @generated
   */
  int VISUALIZER = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VISUALIZER__NAME = GmodelPackage.NAMED__NAME;

  /**
   * The feature id for the '<em><b>Implementation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VISUALIZER__IMPLEMENTATION = GmodelPackage.NAMED_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VISUALIZER__PARAMETERS = GmodelPackage.NAMED_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Start Time</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VISUALIZER__START_TIME = GmodelPackage.NAMED_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Stop Time</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VISUALIZER__STOP_TIME = GmodelPackage.NAMED_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Charts</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VISUALIZER__CHARTS = GmodelPackage.NAMED_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Input</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VISUALIZER__INPUT = GmodelPackage.NAMED_FEATURE_COUNT + 5;

  /**
   * The number of structural features of the '<em>Visualizer</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VISUALIZER_FEATURE_COUNT = GmodelPackage.NAMED_FEATURE_COUNT + 6;

  /**
   * The meta object id for the '{@link gexperiment.impl.ChartImpl <em>Chart</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gexperiment.impl.ChartImpl
   * @see gexperiment.impl.GexperimentPackageImpl#getChart()
   * @generated
   */
  int CHART = 3;

  /**
   * The feature id for the '<em><b>Element</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHART__ELEMENT = 0;

  /**
   * The feature id for the '<em><b>Metric</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHART__METRIC = 1;

  /**
   * The number of structural features of the '<em>Chart</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHART_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link gexperiment.impl.RunResultImpl <em>Run Result</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gexperiment.impl.RunResultImpl
   * @see gexperiment.impl.GexperimentPackageImpl#getRunResult()
   * @generated
   */
  int RUN_RESULT = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN_RESULT__NAME = GmodelPackage.NAMED__NAME;

  /**
   * The feature id for the '<em><b>Active</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN_RESULT__ACTIVE = GmodelPackage.NAMED_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Run Result</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN_RESULT_FEATURE_COUNT = GmodelPackage.NAMED_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link gexperiment.impl.ParameterValueImpl <em>Parameter Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gexperiment.impl.ParameterValueImpl
   * @see gexperiment.impl.GexperimentPackageImpl#getParameterValue()
   * @generated
   */
  int PARAMETER_VALUE = 5;

  /**
   * The feature id for the '<em><b>Elements</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_VALUE__ELEMENTS = 0;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_VALUE__PARAMETERS = 1;

  /**
   * The number of structural features of the '<em>Parameter Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_VALUE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link gexperiment.impl.SeriesParameterImpl <em>Series Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gexperiment.impl.SeriesParameterImpl
   * @see gexperiment.impl.GexperimentPackageImpl#getSeriesParameter()
   * @generated
   */
  int SERIES_PARAMETER = 6;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SERIES_PARAMETER__NAME = GmodelPackage.NAMED__NAME;

  /**
   * The feature id for the '<em><b>Values</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SERIES_PARAMETER__VALUES = GmodelPackage.NAMED_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Series Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SERIES_PARAMETER_FEATURE_COUNT = GmodelPackage.NAMED_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link gexperiment.impl.SingleRunResultImpl <em>Single Run Result</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gexperiment.impl.SingleRunResultImpl
   * @see gexperiment.impl.GexperimentPackageImpl#getSingleRunResult()
   * @generated
   */
  int SINGLE_RUN_RESULT = 7;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SINGLE_RUN_RESULT__NAME = RUN_RESULT__NAME;

  /**
   * The feature id for the '<em><b>Active</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SINGLE_RUN_RESULT__ACTIVE = RUN_RESULT__ACTIVE;

  /**
   * The number of structural features of the '<em>Single Run Result</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SINGLE_RUN_RESULT_FEATURE_COUNT = RUN_RESULT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link gexperiment.impl.GroupRunResultImpl <em>Group Run Result</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gexperiment.impl.GroupRunResultImpl
   * @see gexperiment.impl.GexperimentPackageImpl#getGroupRunResult()
   * @generated
   */
  int GROUP_RUN_RESULT = 8;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP_RUN_RESULT__NAME = RUN_RESULT__NAME;

  /**
   * The feature id for the '<em><b>Active</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP_RUN_RESULT__ACTIVE = RUN_RESULT__ACTIVE;

  /**
   * The feature id for the '<em><b>Results</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP_RUN_RESULT__RESULTS = RUN_RESULT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Group Run Result</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP_RUN_RESULT_FEATURE_COUNT = RUN_RESULT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link gexperiment.RunMode <em>Run Mode</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gexperiment.RunMode
   * @see gexperiment.impl.GexperimentPackageImpl#getRunMode()
   * @generated
   */
  int RUN_MODE = 9;


  /**
   * Returns the meta object for class '{@link gexperiment.Experiment <em>Experiment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Experiment</em>'.
   * @see gexperiment.Experiment
   * @generated
   */
  EClass getExperiment();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Experiment#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see gexperiment.Experiment#getDescription()
   * @see #getExperiment()
   * @generated
   */
  EAttribute getExperiment_Description();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Experiment#getMode <em>Mode</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mode</em>'.
   * @see gexperiment.Experiment#getMode()
   * @see #getExperiment()
   * @generated
   */
  EAttribute getExperiment_Mode();

  /**
   * Returns the meta object for the reference '{@link gexperiment.Experiment#getModel <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Model</em>'.
   * @see gexperiment.Experiment#getModel()
   * @see #getExperiment()
   * @generated
   */
  EReference getExperiment_Model();

  /**
   * Returns the meta object for the containment reference list '{@link gexperiment.Experiment#getParameterValues <em>Parameter Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameter Values</em>'.
   * @see gexperiment.Experiment#getParameterValues()
   * @see #getExperiment()
   * @generated
   */
  EReference getExperiment_ParameterValues();

  /**
   * Returns the meta object for the containment reference list '{@link gexperiment.Experiment#getRuns <em>Runs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Runs</em>'.
   * @see gexperiment.Experiment#getRuns()
   * @see #getExperiment()
   * @generated
   */
  EReference getExperiment_Runs();

  /**
   * Returns the meta object for the containment reference list '{@link gexperiment.Experiment#getVisualizers <em>Visualizers</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Visualizers</em>'.
   * @see gexperiment.Experiment#getVisualizers()
   * @see #getExperiment()
   * @generated
   */
  EReference getExperiment_Visualizers();

  /**
   * Returns the meta object for the containment reference list '{@link gexperiment.Experiment#getSeries <em>Series</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Series</em>'.
   * @see gexperiment.Experiment#getSeries()
   * @see #getExperiment()
   * @generated
   */
  EReference getExperiment_Series();

  /**
   * Returns the meta object for class '{@link gexperiment.Run <em>Run</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Run</em>'.
   * @see gexperiment.Run
   * @generated
   */
  EClass getRun();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Run#getTimezone <em>Timezone</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Timezone</em>'.
   * @see gexperiment.Run#getTimezone()
   * @see #getRun()
   * @generated
   */
  EAttribute getRun_Timezone();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Run#getStartRealTime <em>Start Real Time</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Start Real Time</em>'.
   * @see gexperiment.Run#getStartRealTime()
   * @see #getRun()
   * @generated
   */
  EAttribute getRun_StartRealTime();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Run#getLength <em>Length</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Length</em>'.
   * @see gexperiment.Run#getLength()
   * @see #getRun()
   * @generated
   */
  EAttribute getRun_Length();

  /**
   * Returns the meta object for the containment reference list '{@link gexperiment.Run#getParameterValues <em>Parameter Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameter Values</em>'.
   * @see gexperiment.Run#getParameterValues()
   * @see #getRun()
   * @generated
   */
  EReference getRun_ParameterValues();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Run#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see gexperiment.Run#getDescription()
   * @see #getRun()
   * @generated
   */
  EAttribute getRun_Description();

  /**
   * Returns the meta object for class '{@link gexperiment.Visualizer <em>Visualizer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Visualizer</em>'.
   * @see gexperiment.Visualizer
   * @generated
   */
  EClass getVisualizer();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Visualizer#getImplementation <em>Implementation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Implementation</em>'.
   * @see gexperiment.Visualizer#getImplementation()
   * @see #getVisualizer()
   * @generated
   */
  EAttribute getVisualizer_Implementation();

  /**
   * Returns the meta object for the containment reference list '{@link gexperiment.Visualizer#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see gexperiment.Visualizer#getParameters()
   * @see #getVisualizer()
   * @generated
   */
  EReference getVisualizer_Parameters();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Visualizer#getStartTime <em>Start Time</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Start Time</em>'.
   * @see gexperiment.Visualizer#getStartTime()
   * @see #getVisualizer()
   * @generated
   */
  EAttribute getVisualizer_StartTime();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Visualizer#getStopTime <em>Stop Time</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Stop Time</em>'.
   * @see gexperiment.Visualizer#getStopTime()
   * @see #getVisualizer()
   * @generated
   */
  EAttribute getVisualizer_StopTime();

  /**
   * Returns the meta object for the containment reference list '{@link gexperiment.Visualizer#getCharts <em>Charts</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Charts</em>'.
   * @see gexperiment.Visualizer#getCharts()
   * @see #getVisualizer()
   * @generated
   */
  EReference getVisualizer_Charts();

  /**
   * Returns the meta object for the containment reference list '{@link gexperiment.Visualizer#getInput <em>Input</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Input</em>'.
   * @see gexperiment.Visualizer#getInput()
   * @see #getVisualizer()
   * @generated
   */
  EReference getVisualizer_Input();

  /**
   * Returns the meta object for class '{@link gexperiment.Chart <em>Chart</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Chart</em>'.
   * @see gexperiment.Chart
   * @generated
   */
  EClass getChart();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Chart#getElement <em>Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Element</em>'.
   * @see gexperiment.Chart#getElement()
   * @see #getChart()
   * @generated
   */
  EAttribute getChart_Element();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.Chart#getMetric <em>Metric</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Metric</em>'.
   * @see gexperiment.Chart#getMetric()
   * @see #getChart()
   * @generated
   */
  EAttribute getChart_Metric();

  /**
   * Returns the meta object for class '{@link gexperiment.RunResult <em>Run Result</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Run Result</em>'.
   * @see gexperiment.RunResult
   * @generated
   */
  EClass getRunResult();

  /**
   * Returns the meta object for the attribute '{@link gexperiment.RunResult#isActive <em>Active</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Active</em>'.
   * @see gexperiment.RunResult#isActive()
   * @see #getRunResult()
   * @generated
   */
  EAttribute getRunResult_Active();

  /**
   * Returns the meta object for class '{@link gexperiment.ParameterValue <em>Parameter Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parameter Value</em>'.
   * @see gexperiment.ParameterValue
   * @generated
   */
  EClass getParameterValue();

  /**
   * Returns the meta object for the reference list '{@link gexperiment.ParameterValue#getElements <em>Elements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Elements</em>'.
   * @see gexperiment.ParameterValue#getElements()
   * @see #getParameterValue()
   * @generated
   */
  EReference getParameterValue_Elements();

  /**
   * Returns the meta object for the containment reference list '{@link gexperiment.ParameterValue#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see gexperiment.ParameterValue#getParameters()
   * @see #getParameterValue()
   * @generated
   */
  EReference getParameterValue_Parameters();

  /**
   * Returns the meta object for class '{@link gexperiment.SeriesParameter <em>Series Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Series Parameter</em>'.
   * @see gexperiment.SeriesParameter
   * @generated
   */
  EClass getSeriesParameter();

  /**
   * Returns the meta object for the attribute list '{@link gexperiment.SeriesParameter#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Values</em>'.
   * @see gexperiment.SeriesParameter#getValues()
   * @see #getSeriesParameter()
   * @generated
   */
  EAttribute getSeriesParameter_Values();

  /**
   * Returns the meta object for class '{@link gexperiment.SingleRunResult <em>Single Run Result</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Single Run Result</em>'.
   * @see gexperiment.SingleRunResult
   * @generated
   */
  EClass getSingleRunResult();

  /**
   * Returns the meta object for class '{@link gexperiment.GroupRunResult <em>Group Run Result</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Group Run Result</em>'.
   * @see gexperiment.GroupRunResult
   * @generated
   */
  EClass getGroupRunResult();

  /**
   * Returns the meta object for the containment reference list '{@link gexperiment.GroupRunResult#getResults <em>Results</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Results</em>'.
   * @see gexperiment.GroupRunResult#getResults()
   * @see #getGroupRunResult()
   * @generated
   */
  EReference getGroupRunResult_Results();

  /**
   * Returns the meta object for enum '{@link gexperiment.RunMode <em>Run Mode</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Run Mode</em>'.
   * @see gexperiment.RunMode
   * @generated
   */
  EEnum getRunMode();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  GexperimentFactory getGexperimentFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link gexperiment.impl.ExperimentImpl <em>Experiment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gexperiment.impl.ExperimentImpl
     * @see gexperiment.impl.GexperimentPackageImpl#getExperiment()
     * @generated
     */
    EClass EXPERIMENT = eINSTANCE.getExperiment();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPERIMENT__DESCRIPTION = eINSTANCE.getExperiment_Description();

    /**
     * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPERIMENT__MODE = eINSTANCE.getExperiment_Mode();

    /**
     * The meta object literal for the '<em><b>Model</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPERIMENT__MODEL = eINSTANCE.getExperiment_Model();

    /**
     * The meta object literal for the '<em><b>Parameter Values</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPERIMENT__PARAMETER_VALUES = eINSTANCE.getExperiment_ParameterValues();

    /**
     * The meta object literal for the '<em><b>Runs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPERIMENT__RUNS = eINSTANCE.getExperiment_Runs();

    /**
     * The meta object literal for the '<em><b>Visualizers</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPERIMENT__VISUALIZERS = eINSTANCE.getExperiment_Visualizers();

    /**
     * The meta object literal for the '<em><b>Series</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPERIMENT__SERIES = eINSTANCE.getExperiment_Series();

    /**
     * The meta object literal for the '{@link gexperiment.impl.RunImpl <em>Run</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gexperiment.impl.RunImpl
     * @see gexperiment.impl.GexperimentPackageImpl#getRun()
     * @generated
     */
    EClass RUN = eINSTANCE.getRun();

    /**
     * The meta object literal for the '<em><b>Timezone</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RUN__TIMEZONE = eINSTANCE.getRun_Timezone();

    /**
     * The meta object literal for the '<em><b>Start Real Time</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RUN__START_REAL_TIME = eINSTANCE.getRun_StartRealTime();

    /**
     * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RUN__LENGTH = eINSTANCE.getRun_Length();

    /**
     * The meta object literal for the '<em><b>Parameter Values</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RUN__PARAMETER_VALUES = eINSTANCE.getRun_ParameterValues();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RUN__DESCRIPTION = eINSTANCE.getRun_Description();

    /**
     * The meta object literal for the '{@link gexperiment.impl.VisualizerImpl <em>Visualizer</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gexperiment.impl.VisualizerImpl
     * @see gexperiment.impl.GexperimentPackageImpl#getVisualizer()
     * @generated
     */
    EClass VISUALIZER = eINSTANCE.getVisualizer();

    /**
     * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VISUALIZER__IMPLEMENTATION = eINSTANCE.getVisualizer_Implementation();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VISUALIZER__PARAMETERS = eINSTANCE.getVisualizer_Parameters();

    /**
     * The meta object literal for the '<em><b>Start Time</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VISUALIZER__START_TIME = eINSTANCE.getVisualizer_StartTime();

    /**
     * The meta object literal for the '<em><b>Stop Time</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VISUALIZER__STOP_TIME = eINSTANCE.getVisualizer_StopTime();

    /**
     * The meta object literal for the '<em><b>Charts</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VISUALIZER__CHARTS = eINSTANCE.getVisualizer_Charts();

    /**
     * The meta object literal for the '<em><b>Input</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VISUALIZER__INPUT = eINSTANCE.getVisualizer_Input();

    /**
     * The meta object literal for the '{@link gexperiment.impl.ChartImpl <em>Chart</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gexperiment.impl.ChartImpl
     * @see gexperiment.impl.GexperimentPackageImpl#getChart()
     * @generated
     */
    EClass CHART = eINSTANCE.getChart();

    /**
     * The meta object literal for the '<em><b>Element</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CHART__ELEMENT = eINSTANCE.getChart_Element();

    /**
     * The meta object literal for the '<em><b>Metric</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CHART__METRIC = eINSTANCE.getChart_Metric();

    /**
     * The meta object literal for the '{@link gexperiment.impl.RunResultImpl <em>Run Result</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gexperiment.impl.RunResultImpl
     * @see gexperiment.impl.GexperimentPackageImpl#getRunResult()
     * @generated
     */
    EClass RUN_RESULT = eINSTANCE.getRunResult();

    /**
     * The meta object literal for the '<em><b>Active</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RUN_RESULT__ACTIVE = eINSTANCE.getRunResult_Active();

    /**
     * The meta object literal for the '{@link gexperiment.impl.ParameterValueImpl <em>Parameter Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gexperiment.impl.ParameterValueImpl
     * @see gexperiment.impl.GexperimentPackageImpl#getParameterValue()
     * @generated
     */
    EClass PARAMETER_VALUE = eINSTANCE.getParameterValue();

    /**
     * The meta object literal for the '<em><b>Elements</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PARAMETER_VALUE__ELEMENTS = eINSTANCE.getParameterValue_Elements();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PARAMETER_VALUE__PARAMETERS = eINSTANCE.getParameterValue_Parameters();

    /**
     * The meta object literal for the '{@link gexperiment.impl.SeriesParameterImpl <em>Series Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gexperiment.impl.SeriesParameterImpl
     * @see gexperiment.impl.GexperimentPackageImpl#getSeriesParameter()
     * @generated
     */
    EClass SERIES_PARAMETER = eINSTANCE.getSeriesParameter();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SERIES_PARAMETER__VALUES = eINSTANCE.getSeriesParameter_Values();

    /**
     * The meta object literal for the '{@link gexperiment.impl.SingleRunResultImpl <em>Single Run Result</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gexperiment.impl.SingleRunResultImpl
     * @see gexperiment.impl.GexperimentPackageImpl#getSingleRunResult()
     * @generated
     */
    EClass SINGLE_RUN_RESULT = eINSTANCE.getSingleRunResult();

    /**
     * The meta object literal for the '{@link gexperiment.impl.GroupRunResultImpl <em>Group Run Result</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gexperiment.impl.GroupRunResultImpl
     * @see gexperiment.impl.GexperimentPackageImpl#getGroupRunResult()
     * @generated
     */
    EClass GROUP_RUN_RESULT = eINSTANCE.getGroupRunResult();

    /**
     * The meta object literal for the '<em><b>Results</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GROUP_RUN_RESULT__RESULTS = eINSTANCE.getGroupRunResult_Results();

    /**
     * The meta object literal for the '{@link gexperiment.RunMode <em>Run Mode</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gexperiment.RunMode
     * @see gexperiment.impl.GexperimentPackageImpl#getRunMode()
     * @generated
     */
    EEnum RUN_MODE = eINSTANCE.getRunMode();

  }

} //GexperimentPackage
