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
import gexperiment.Experiment;
import gexperiment.GexperimentFactory;
import gexperiment.GexperimentPackage;
import gexperiment.GroupRunResult;
import gexperiment.ParameterValue;
import gexperiment.Run;
import gexperiment.RunMode;
import gexperiment.RunResult;
import gexperiment.SeriesParameter;
import gexperiment.SingleRunResult;
import gexperiment.Visualizer;

import gmodel.GmodelPackage;

import gmodel.impl.GmodelPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GexperimentPackageImpl extends EPackageImpl implements GexperimentPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass experimentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass runEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass visualizerEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass chartEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass runResultEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass parameterValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass seriesParameterEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass singleRunResultEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass groupRunResultEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum runModeEEnum = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see gexperiment.GexperimentPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private GexperimentPackageImpl()
  {
    super(eNS_URI, GexperimentFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link GexperimentPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static GexperimentPackage init()
  {
    if (isInited) return (GexperimentPackage)EPackage.Registry.INSTANCE.getEPackage(GexperimentPackage.eNS_URI);

    // Obtain or create and register package
    GexperimentPackageImpl theGexperimentPackage = (GexperimentPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof GexperimentPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new GexperimentPackageImpl());

    isInited = true;

    // Obtain or create and register interdependencies
    GmodelPackageImpl theGmodelPackage = (GmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GmodelPackage.eNS_URI) instanceof GmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GmodelPackage.eNS_URI) : GmodelPackage.eINSTANCE);

    // Create package meta-data objects
    theGexperimentPackage.createPackageContents();
    theGmodelPackage.createPackageContents();

    // Initialize created meta-data
    theGexperimentPackage.initializePackageContents();
    theGmodelPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theGexperimentPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(GexperimentPackage.eNS_URI, theGexperimentPackage);
    return theGexperimentPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getExperiment()
  {
    return experimentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExperiment_Description()
  {
    return (EAttribute)experimentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExperiment_Mode()
  {
    return (EAttribute)experimentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getExperiment_Model()
  {
    return (EReference)experimentEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getExperiment_ParameterValues()
  {
    return (EReference)experimentEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getExperiment_Runs()
  {
    return (EReference)experimentEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getExperiment_Visualizers()
  {
    return (EReference)experimentEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getExperiment_Series()
  {
    return (EReference)experimentEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRun()
  {
    return runEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRun_Timezone()
  {
    return (EAttribute)runEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRun_StartRealTime()
  {
    return (EAttribute)runEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRun_Length()
  {
    return (EAttribute)runEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRun_ParameterValues()
  {
    return (EReference)runEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRun_Description()
  {
    return (EAttribute)runEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getVisualizer()
  {
    return visualizerEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVisualizer_Implementation()
  {
    return (EAttribute)visualizerEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVisualizer_Parameters()
  {
    return (EReference)visualizerEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVisualizer_StartTime()
  {
    return (EAttribute)visualizerEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVisualizer_StopTime()
  {
    return (EAttribute)visualizerEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVisualizer_Charts()
  {
    return (EReference)visualizerEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVisualizer_Input()
  {
    return (EReference)visualizerEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getChart()
  {
    return chartEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getChart_Element()
  {
    return (EAttribute)chartEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getChart_Metric()
  {
    return (EAttribute)chartEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRunResult()
  {
    return runResultEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRunResult_Active()
  {
    return (EAttribute)runResultEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getParameterValue()
  {
    return parameterValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getParameterValue_Elements()
  {
    return (EReference)parameterValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getParameterValue_Parameters()
  {
    return (EReference)parameterValueEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSeriesParameter()
  {
    return seriesParameterEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSeriesParameter_Values()
  {
    return (EAttribute)seriesParameterEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSingleRunResult()
  {
    return singleRunResultEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGroupRunResult()
  {
    return groupRunResultEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGroupRunResult_Results()
  {
    return (EReference)groupRunResultEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getRunMode()
  {
    return runModeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GexperimentFactory getGexperimentFactory()
  {
    return (GexperimentFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    experimentEClass = createEClass(EXPERIMENT);
    createEAttribute(experimentEClass, EXPERIMENT__DESCRIPTION);
    createEAttribute(experimentEClass, EXPERIMENT__MODE);
    createEReference(experimentEClass, EXPERIMENT__MODEL);
    createEReference(experimentEClass, EXPERIMENT__PARAMETER_VALUES);
    createEReference(experimentEClass, EXPERIMENT__RUNS);
    createEReference(experimentEClass, EXPERIMENT__VISUALIZERS);
    createEReference(experimentEClass, EXPERIMENT__SERIES);

    runEClass = createEClass(RUN);
    createEAttribute(runEClass, RUN__TIMEZONE);
    createEAttribute(runEClass, RUN__START_REAL_TIME);
    createEAttribute(runEClass, RUN__LENGTH);
    createEReference(runEClass, RUN__PARAMETER_VALUES);
    createEAttribute(runEClass, RUN__DESCRIPTION);

    visualizerEClass = createEClass(VISUALIZER);
    createEAttribute(visualizerEClass, VISUALIZER__IMPLEMENTATION);
    createEReference(visualizerEClass, VISUALIZER__PARAMETERS);
    createEAttribute(visualizerEClass, VISUALIZER__START_TIME);
    createEAttribute(visualizerEClass, VISUALIZER__STOP_TIME);
    createEReference(visualizerEClass, VISUALIZER__CHARTS);
    createEReference(visualizerEClass, VISUALIZER__INPUT);

    chartEClass = createEClass(CHART);
    createEAttribute(chartEClass, CHART__ELEMENT);
    createEAttribute(chartEClass, CHART__METRIC);

    runResultEClass = createEClass(RUN_RESULT);
    createEAttribute(runResultEClass, RUN_RESULT__ACTIVE);

    parameterValueEClass = createEClass(PARAMETER_VALUE);
    createEReference(parameterValueEClass, PARAMETER_VALUE__ELEMENTS);
    createEReference(parameterValueEClass, PARAMETER_VALUE__PARAMETERS);

    seriesParameterEClass = createEClass(SERIES_PARAMETER);
    createEAttribute(seriesParameterEClass, SERIES_PARAMETER__VALUES);

    singleRunResultEClass = createEClass(SINGLE_RUN_RESULT);

    groupRunResultEClass = createEClass(GROUP_RUN_RESULT);
    createEReference(groupRunResultEClass, GROUP_RUN_RESULT__RESULTS);

    // Create enums
    runModeEEnum = createEEnum(RUN_MODE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    GmodelPackage theGmodelPackage = (GmodelPackage)EPackage.Registry.INSTANCE.getEPackage(GmodelPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    runEClass.getESuperTypes().add(theGmodelPackage.getNamed());
    visualizerEClass.getESuperTypes().add(theGmodelPackage.getNamed());
    runResultEClass.getESuperTypes().add(theGmodelPackage.getNamed());
    seriesParameterEClass.getESuperTypes().add(theGmodelPackage.getNamed());
    singleRunResultEClass.getESuperTypes().add(this.getRunResult());
    groupRunResultEClass.getESuperTypes().add(this.getRunResult());

    // Initialize classes and features; add operations and parameters
    initEClass(experimentEClass, Experiment.class, "Experiment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getExperiment_Description(), ecorePackage.getEString(), "description", null, 0, 1, Experiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExperiment_Mode(), this.getRunMode(), "mode", "RELEASE", 1, 1, Experiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getExperiment_Model(), theGmodelPackage.getModel(), null, "model", null, 1, 1, Experiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getExperiment_ParameterValues(), this.getParameterValue(), null, "parameterValues", null, 0, -1, Experiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getExperiment_Runs(), this.getRun(), null, "runs", null, 0, -1, Experiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getExperiment_Visualizers(), this.getVisualizer(), null, "visualizers", null, 0, -1, Experiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getExperiment_Series(), this.getSeriesParameter(), null, "series", null, 0, -1, Experiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(runEClass, Run.class, "Run", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRun_Timezone(), ecorePackage.getEString(), "timezone", null, 1, 1, Run.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getRun_StartRealTime(), ecorePackage.getEString(), "startRealTime", null, 1, 1, Run.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getRun_Length(), ecorePackage.getEString(), "length", null, 1, 1, Run.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRun_ParameterValues(), this.getParameterValue(), null, "parameterValues", null, 0, -1, Run.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getRun_Description(), ecorePackage.getEString(), "description", null, 0, 1, Run.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(visualizerEClass, Visualizer.class, "Visualizer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVisualizer_Implementation(), ecorePackage.getEString(), "implementation", null, 1, 1, Visualizer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVisualizer_Parameters(), theGmodelPackage.getParameter(), null, "parameters", null, 0, -1, Visualizer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVisualizer_StartTime(), ecorePackage.getEString(), "startTime", null, 1, 1, Visualizer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVisualizer_StopTime(), ecorePackage.getEString(), "stopTime", null, 1, 1, Visualizer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVisualizer_Charts(), this.getChart(), null, "charts", null, 0, -1, Visualizer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVisualizer_Input(), this.getRunResult(), null, "input", null, 0, -1, Visualizer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(chartEClass, Chart.class, "Chart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getChart_Element(), ecorePackage.getEString(), "element", null, 1, 1, Chart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getChart_Metric(), ecorePackage.getEString(), "metric", null, 1, 1, Chart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(runResultEClass, RunResult.class, "RunResult", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRunResult_Active(), ecorePackage.getEBoolean(), "active", "true", 1, 1, RunResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(parameterValueEClass, ParameterValue.class, "ParameterValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getParameterValue_Elements(), theGmodelPackage.getModelElement(), null, "elements", null, 1, -1, ParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getParameterValue_Parameters(), theGmodelPackage.getRParameter(), null, "parameters", null, 1, -1, ParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(seriesParameterEClass, SeriesParameter.class, "SeriesParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSeriesParameter_Values(), ecorePackage.getEString(), "values", "", 1, -1, SeriesParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(singleRunResultEClass, SingleRunResult.class, "SingleRunResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(groupRunResultEClass, GroupRunResult.class, "GroupRunResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getGroupRunResult_Results(), this.getSingleRunResult(), null, "results", null, 1, -1, GroupRunResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(runModeEEnum, RunMode.class, "RunMode");
    addEEnumLiteral(runModeEEnum, RunMode.RELEASE);
    addEEnumLiteral(runModeEEnum, RunMode.DEBUG);

    // Create resource
    createResource(eNS_URI);
  }

} //GexperimentPackageImpl
