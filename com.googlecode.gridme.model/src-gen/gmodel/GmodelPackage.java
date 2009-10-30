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
package gmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see gmodel.GmodelFactory
 * @model kind="package"
 * @generated
 */
public interface GmodelPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "gmodel";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://gridme.googlecode.com/gmodel";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "gmodel";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  GmodelPackage eINSTANCE = gmodel.impl.GmodelPackageImpl.init();

  /**
   * The meta object id for the '{@link gmodel.impl.NamedImpl <em>Named</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gmodel.impl.NamedImpl
   * @see gmodel.impl.GmodelPackageImpl#getNamed()
   * @generated
   */
  int NAMED = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED__NAME = 0;

  /**
   * The number of structural features of the '<em>Named</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link gmodel.impl.ModelImpl <em>Model</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gmodel.impl.ModelImpl
   * @see gmodel.impl.GmodelPackageImpl#getModel()
   * @generated
   */
  int MODEL = 1;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__ELEMENTS = 0;

  /**
   * The number of structural features of the '<em>Model</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link gmodel.impl.GenericModelElementImpl <em>Generic Model Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gmodel.impl.GenericModelElementImpl
   * @see gmodel.impl.GmodelPackageImpl#getGenericModelElement()
   * @generated
   */
  int GENERIC_MODEL_ELEMENT = 6;

  /**
   * The number of structural features of the '<em>Generic Model Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_MODEL_ELEMENT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link gmodel.impl.ModelElementImpl <em>Model Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gmodel.impl.ModelElementImpl
   * @see gmodel.impl.GmodelPackageImpl#getModelElement()
   * @generated
   */
  int MODEL_ELEMENT = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_ELEMENT__NAME = GENERIC_MODEL_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Implementation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_ELEMENT__IMPLEMENTATION = GENERIC_MODEL_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_ELEMENT__PARAMETERS = GENERIC_MODEL_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Model Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_ELEMENT_FEATURE_COUNT = GENERIC_MODEL_ELEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link gmodel.impl.ConnectionImpl <em>Connection</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gmodel.impl.ConnectionImpl
   * @see gmodel.impl.GmodelPackageImpl#getConnection()
   * @generated
   */
  int CONNECTION = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTION__NAME = MODEL_ELEMENT__NAME;

  /**
   * The feature id for the '<em><b>Implementation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTION__IMPLEMENTATION = MODEL_ELEMENT__IMPLEMENTATION;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTION__PARAMETERS = MODEL_ELEMENT__PARAMETERS;

  /**
   * The number of structural features of the '<em>Connection</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTION_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link gmodel.impl.GridElementImpl <em>Grid Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gmodel.impl.GridElementImpl
   * @see gmodel.impl.GmodelPackageImpl#getGridElement()
   * @generated
   */
  int GRID_ELEMENT = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRID_ELEMENT__NAME = MODEL_ELEMENT__NAME;

  /**
   * The feature id for the '<em><b>Implementation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRID_ELEMENT__IMPLEMENTATION = MODEL_ELEMENT__IMPLEMENTATION;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRID_ELEMENT__PARAMETERS = MODEL_ELEMENT__PARAMETERS;

  /**
   * The number of structural features of the '<em>Grid Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRID_ELEMENT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link gmodel.impl.LinkImpl <em>Link</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gmodel.impl.LinkImpl
   * @see gmodel.impl.GmodelPackageImpl#getLink()
   * @generated
   */
  int LINK = 5;

  /**
   * The feature id for the '<em><b>From</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINK__FROM = GENERIC_MODEL_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>To</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINK__TO = GENERIC_MODEL_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Link</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINK_FEATURE_COUNT = GENERIC_MODEL_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link gmodel.impl.ParameterImpl <em>Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gmodel.impl.ParameterImpl
   * @see gmodel.impl.GmodelPackageImpl#getParameter()
   * @generated
   */
  int PARAMETER = 8;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__NAME = NAMED__NAME;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__VALUE = NAMED_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_FEATURE_COUNT = NAMED_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link gmodel.impl.RParameterImpl <em>RParameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gmodel.impl.RParameterImpl
   * @see gmodel.impl.GmodelPackageImpl#getRParameter()
   * @generated
   */
  int RPARAMETER = 7;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RPARAMETER__NAME = PARAMETER__NAME;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RPARAMETER__VALUE = PARAMETER__VALUE;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RPARAMETER__PARAMETERS = PARAMETER_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>RParameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RPARAMETER_FEATURE_COUNT = PARAMETER_FEATURE_COUNT + 1;


  /**
   * Returns the meta object for class '{@link gmodel.Named <em>Named</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Named</em>'.
   * @see gmodel.Named
   * @generated
   */
  EClass getNamed();

  /**
   * Returns the meta object for the attribute '{@link gmodel.Named#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see gmodel.Named#getName()
   * @see #getNamed()
   * @generated
   */
  EAttribute getNamed_Name();

  /**
   * Returns the meta object for class '{@link gmodel.Model <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Model</em>'.
   * @see gmodel.Model
   * @generated
   */
  EClass getModel();

  /**
   * Returns the meta object for the containment reference list '{@link gmodel.Model#getElements <em>Elements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elements</em>'.
   * @see gmodel.Model#getElements()
   * @see #getModel()
   * @generated
   */
  EReference getModel_Elements();

  /**
   * Returns the meta object for class '{@link gmodel.ModelElement <em>Model Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Model Element</em>'.
   * @see gmodel.ModelElement
   * @generated
   */
  EClass getModelElement();

  /**
   * Returns the meta object for the attribute '{@link gmodel.ModelElement#getImplementation <em>Implementation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Implementation</em>'.
   * @see gmodel.ModelElement#getImplementation()
   * @see #getModelElement()
   * @generated
   */
  EAttribute getModelElement_Implementation();

  /**
   * Returns the meta object for the containment reference list '{@link gmodel.ModelElement#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see gmodel.ModelElement#getParameters()
   * @see #getModelElement()
   * @generated
   */
  EReference getModelElement_Parameters();

  /**
   * Returns the meta object for class '{@link gmodel.Connection <em>Connection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Connection</em>'.
   * @see gmodel.Connection
   * @generated
   */
  EClass getConnection();

  /**
   * Returns the meta object for class '{@link gmodel.GridElement <em>Grid Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Grid Element</em>'.
   * @see gmodel.GridElement
   * @generated
   */
  EClass getGridElement();

  /**
   * Returns the meta object for class '{@link gmodel.Link <em>Link</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Link</em>'.
   * @see gmodel.Link
   * @generated
   */
  EClass getLink();

  /**
   * Returns the meta object for the reference '{@link gmodel.Link#getFrom <em>From</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>From</em>'.
   * @see gmodel.Link#getFrom()
   * @see #getLink()
   * @generated
   */
  EReference getLink_From();

  /**
   * Returns the meta object for the reference '{@link gmodel.Link#getTo <em>To</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>To</em>'.
   * @see gmodel.Link#getTo()
   * @see #getLink()
   * @generated
   */
  EReference getLink_To();

  /**
   * Returns the meta object for class '{@link gmodel.GenericModelElement <em>Generic Model Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Generic Model Element</em>'.
   * @see gmodel.GenericModelElement
   * @generated
   */
  EClass getGenericModelElement();

  /**
   * Returns the meta object for class '{@link gmodel.RParameter <em>RParameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>RParameter</em>'.
   * @see gmodel.RParameter
   * @generated
   */
  EClass getRParameter();

  /**
   * Returns the meta object for the containment reference list '{@link gmodel.RParameter#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see gmodel.RParameter#getParameters()
   * @see #getRParameter()
   * @generated
   */
  EReference getRParameter_Parameters();

  /**
   * Returns the meta object for class '{@link gmodel.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parameter</em>'.
   * @see gmodel.Parameter
   * @generated
   */
  EClass getParameter();

  /**
   * Returns the meta object for the attribute '{@link gmodel.Parameter#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see gmodel.Parameter#getValue()
   * @see #getParameter()
   * @generated
   */
  EAttribute getParameter_Value();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  GmodelFactory getGmodelFactory();

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
     * The meta object literal for the '{@link gmodel.impl.NamedImpl <em>Named</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gmodel.impl.NamedImpl
     * @see gmodel.impl.GmodelPackageImpl#getNamed()
     * @generated
     */
    EClass NAMED = eINSTANCE.getNamed();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NAMED__NAME = eINSTANCE.getNamed_Name();

    /**
     * The meta object literal for the '{@link gmodel.impl.ModelImpl <em>Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gmodel.impl.ModelImpl
     * @see gmodel.impl.GmodelPackageImpl#getModel()
     * @generated
     */
    EClass MODEL = eINSTANCE.getModel();

    /**
     * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL__ELEMENTS = eINSTANCE.getModel_Elements();

    /**
     * The meta object literal for the '{@link gmodel.impl.ModelElementImpl <em>Model Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gmodel.impl.ModelElementImpl
     * @see gmodel.impl.GmodelPackageImpl#getModelElement()
     * @generated
     */
    EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

    /**
     * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_ELEMENT__IMPLEMENTATION = eINSTANCE.getModelElement_Implementation();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL_ELEMENT__PARAMETERS = eINSTANCE.getModelElement_Parameters();

    /**
     * The meta object literal for the '{@link gmodel.impl.ConnectionImpl <em>Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gmodel.impl.ConnectionImpl
     * @see gmodel.impl.GmodelPackageImpl#getConnection()
     * @generated
     */
    EClass CONNECTION = eINSTANCE.getConnection();

    /**
     * The meta object literal for the '{@link gmodel.impl.GridElementImpl <em>Grid Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gmodel.impl.GridElementImpl
     * @see gmodel.impl.GmodelPackageImpl#getGridElement()
     * @generated
     */
    EClass GRID_ELEMENT = eINSTANCE.getGridElement();

    /**
     * The meta object literal for the '{@link gmodel.impl.LinkImpl <em>Link</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gmodel.impl.LinkImpl
     * @see gmodel.impl.GmodelPackageImpl#getLink()
     * @generated
     */
    EClass LINK = eINSTANCE.getLink();

    /**
     * The meta object literal for the '<em><b>From</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LINK__FROM = eINSTANCE.getLink_From();

    /**
     * The meta object literal for the '<em><b>To</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LINK__TO = eINSTANCE.getLink_To();

    /**
     * The meta object literal for the '{@link gmodel.impl.GenericModelElementImpl <em>Generic Model Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gmodel.impl.GenericModelElementImpl
     * @see gmodel.impl.GmodelPackageImpl#getGenericModelElement()
     * @generated
     */
    EClass GENERIC_MODEL_ELEMENT = eINSTANCE.getGenericModelElement();

    /**
     * The meta object literal for the '{@link gmodel.impl.RParameterImpl <em>RParameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gmodel.impl.RParameterImpl
     * @see gmodel.impl.GmodelPackageImpl#getRParameter()
     * @generated
     */
    EClass RPARAMETER = eINSTANCE.getRParameter();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RPARAMETER__PARAMETERS = eINSTANCE.getRParameter_Parameters();

    /**
     * The meta object literal for the '{@link gmodel.impl.ParameterImpl <em>Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gmodel.impl.ParameterImpl
     * @see gmodel.impl.GmodelPackageImpl#getParameter()
     * @generated
     */
    EClass PARAMETER = eINSTANCE.getParameter();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETER__VALUE = eINSTANCE.getParameter_Value();

  }

} //GmodelPackage
