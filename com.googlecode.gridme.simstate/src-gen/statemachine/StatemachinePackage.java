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
package statemachine;

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
 * @see statemachine.StatemachineFactory
 * @model kind="package"
 * @generated
 */
public interface StatemachinePackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "statemachine";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://example.org/statemachine";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "com.googlecode.gridme.simstate.statemachine";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  StatemachinePackage eINSTANCE = statemachine.impl.StatemachinePackageImpl.init();

  /**
   * The meta object id for the '{@link statemachine.impl.NamedImpl <em>Named</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.NamedImpl
   * @see statemachine.impl.StatemachinePackageImpl#getNamed()
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
   * The feature id for the '<em><b>Comment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED__COMMENT = 1;

  /**
   * The number of structural features of the '<em>Named</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link statemachine.impl.GAbstractStateImpl <em>GAbstract State</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.GAbstractStateImpl
   * @see statemachine.impl.StatemachinePackageImpl#getGAbstractState()
   * @generated
   */
  int GABSTRACT_STATE = 1;

  /**
   * The feature id for the '<em><b>Actions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GABSTRACT_STATE__ACTIONS = 0;

  /**
   * The number of structural features of the '<em>GAbstract State</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GABSTRACT_STATE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link statemachine.impl.GAbstractActionImpl <em>GAbstract Action</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.GAbstractActionImpl
   * @see statemachine.impl.StatemachinePackageImpl#getGAbstractAction()
   * @generated
   */
  int GABSTRACT_ACTION = 2;

  /**
   * The feature id for the '<em><b>Kind</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GABSTRACT_ACTION__KIND = 0;

  /**
   * The number of structural features of the '<em>GAbstract Action</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GABSTRACT_ACTION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link statemachine.impl.GStateImpl <em>GState</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.GStateImpl
   * @see statemachine.impl.StatemachinePackageImpl#getGState()
   * @generated
   */
  int GSTATE = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATE__NAME = NAMED__NAME;

  /**
   * The feature id for the '<em><b>Comment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATE__COMMENT = NAMED__COMMENT;

  /**
   * The feature id for the '<em><b>Actions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATE__ACTIONS = NAMED_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>GState</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATE_FEATURE_COUNT = NAMED_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link statemachine.impl.TransitionImpl <em>Transition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.TransitionImpl
   * @see statemachine.impl.StatemachinePackageImpl#getTransition()
   * @generated
   */
  int TRANSITION = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__NAME = NAMED__NAME;

  /**
   * The feature id for the '<em><b>Comment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__COMMENT = NAMED__COMMENT;

  /**
   * The feature id for the '<em><b>From</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__FROM = NAMED_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>To</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__TO = NAMED_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Guard</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__GUARD = NAMED_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Delay</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__DELAY = NAMED_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Signals</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__SIGNALS = NAMED_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Preserve Timers</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__PRESERVE_TIMERS = NAMED_FEATURE_COUNT + 5;

  /**
   * The number of structural features of the '<em>Transition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION_FEATURE_COUNT = NAMED_FEATURE_COUNT + 6;

  /**
   * The meta object id for the '{@link statemachine.impl.GCompositeStateImpl <em>GComposite State</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.GCompositeStateImpl
   * @see statemachine.impl.StatemachinePackageImpl#getGCompositeState()
   * @generated
   */
  int GCOMPOSITE_STATE = 5;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GCOMPOSITE_STATE__NAME = GSTATE__NAME;

  /**
   * The feature id for the '<em><b>Comment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GCOMPOSITE_STATE__COMMENT = GSTATE__COMMENT;

  /**
   * The feature id for the '<em><b>Actions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GCOMPOSITE_STATE__ACTIONS = GSTATE__ACTIONS;

  /**
   * The feature id for the '<em><b>States</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GCOMPOSITE_STATE__STATES = GSTATE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GCOMPOSITE_STATE__TRANSITIONS = GSTATE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>GComposite State</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GCOMPOSITE_STATE_FEATURE_COUNT = GSTATE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link statemachine.impl.GStartStateImpl <em>GStart State</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.GStartStateImpl
   * @see statemachine.impl.StatemachinePackageImpl#getGStartState()
   * @generated
   */
  int GSTART_STATE = 6;

  /**
   * The feature id for the '<em><b>Actions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTART_STATE__ACTIONS = GABSTRACT_STATE__ACTIONS;

  /**
   * The number of structural features of the '<em>GStart State</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTART_STATE_FEATURE_COUNT = GABSTRACT_STATE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link statemachine.impl.GStopStateImpl <em>GStop State</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.GStopStateImpl
   * @see statemachine.impl.StatemachinePackageImpl#getGStopState()
   * @generated
   */
  int GSTOP_STATE = 7;

  /**
   * The feature id for the '<em><b>Actions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTOP_STATE__ACTIONS = GABSTRACT_STATE__ACTIONS;

  /**
   * The number of structural features of the '<em>GStop State</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTOP_STATE_FEATURE_COUNT = GABSTRACT_STATE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link statemachine.impl.GStatemachineImpl <em>GStatemachine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.GStatemachineImpl
   * @see statemachine.impl.StatemachinePackageImpl#getGStatemachine()
   * @generated
   */
  int GSTATEMACHINE = 8;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATEMACHINE__NAME = GCOMPOSITE_STATE__NAME;

  /**
   * The feature id for the '<em><b>Comment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATEMACHINE__COMMENT = GCOMPOSITE_STATE__COMMENT;

  /**
   * The feature id for the '<em><b>Actions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATEMACHINE__ACTIONS = GCOMPOSITE_STATE__ACTIONS;

  /**
   * The feature id for the '<em><b>States</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATEMACHINE__STATES = GCOMPOSITE_STATE__STATES;

  /**
   * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATEMACHINE__TRANSITIONS = GCOMPOSITE_STATE__TRANSITIONS;

  /**
   * The feature id for the '<em><b>Parameter</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATEMACHINE__PARAMETER = GCOMPOSITE_STATE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Package</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATEMACHINE__PACKAGE = GCOMPOSITE_STATE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>GStatemachine</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GSTATEMACHINE_FEATURE_COUNT = GCOMPOSITE_STATE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link statemachine.impl.CallActionImpl <em>Call Action</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.CallActionImpl
   * @see statemachine.impl.StatemachinePackageImpl#getCallAction()
   * @generated
   */
  int CALL_ACTION = 9;

  /**
   * The feature id for the '<em><b>Kind</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALL_ACTION__KIND = GABSTRACT_ACTION__KIND;

  /**
   * The feature id for the '<em><b>Call</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALL_ACTION__CALL = GABSTRACT_ACTION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Call Action</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALL_ACTION_FEATURE_COUNT = GABSTRACT_ACTION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link statemachine.impl.ParameterImpl <em>Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.ParameterImpl
   * @see statemachine.impl.StatemachinePackageImpl#getParameter()
   * @generated
   */
  int PARAMETER = 10;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__NAME = NAMED__NAME;

  /**
   * The feature id for the '<em><b>Comment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__COMMENT = NAMED__COMMENT;

  /**
   * The number of structural features of the '<em>Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_FEATURE_COUNT = NAMED_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link statemachine.impl.ValueImpl <em>Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.ValueImpl
   * @see statemachine.impl.StatemachinePackageImpl#getValue()
   * @generated
   */
  int VALUE = 11;

  /**
   * The number of structural features of the '<em>Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link statemachine.impl.GetParameterImpl <em>Get Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.GetParameterImpl
   * @see statemachine.impl.StatemachinePackageImpl#getGetParameter()
   * @generated
   */
  int GET_PARAMETER = 12;

  /**
   * The feature id for the '<em><b>Parameter</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GET_PARAMETER__PARAMETER = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Get Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GET_PARAMETER_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link statemachine.impl.CallImpl <em>Call</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.CallImpl
   * @see statemachine.impl.StatemachinePackageImpl#getCall()
   * @generated
   */
  int CALL = 13;

  /**
   * The feature id for the '<em><b>Action Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALL__ACTION_ID = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Call</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALL_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link statemachine.impl.ConstantValueImpl <em>Constant Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.ConstantValueImpl
   * @see statemachine.impl.StatemachinePackageImpl#getConstantValue()
   * @generated
   */
  int CONSTANT_VALUE = 14;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Constant Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link statemachine.impl.StringValueImpl <em>String Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.StringValueImpl
   * @see statemachine.impl.StatemachinePackageImpl#getStringValue()
   * @generated
   */
  int STRING_VALUE = 15;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_VALUE__VALUE = CONSTANT_VALUE__VALUE;

  /**
   * The number of structural features of the '<em>String Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_VALUE_FEATURE_COUNT = CONSTANT_VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link statemachine.impl.NumberValueImpl <em>Number Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.NumberValueImpl
   * @see statemachine.impl.StatemachinePackageImpl#getNumberValue()
   * @generated
   */
  int NUMBER_VALUE = 16;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NUMBER_VALUE__VALUE = CONSTANT_VALUE__VALUE;

  /**
   * The number of structural features of the '<em>Number Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NUMBER_VALUE_FEATURE_COUNT = CONSTANT_VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link statemachine.impl.BooleanValueImpl <em>Boolean Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.BooleanValueImpl
   * @see statemachine.impl.StatemachinePackageImpl#getBooleanValue()
   * @generated
   */
  int BOOLEAN_VALUE = 17;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_VALUE__VALUE = CONSTANT_VALUE__VALUE;

  /**
   * The number of structural features of the '<em>Boolean Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_VALUE_FEATURE_COUNT = CONSTANT_VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link statemachine.impl.LongValueImpl <em>Long Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.impl.LongValueImpl
   * @see statemachine.impl.StatemachinePackageImpl#getLongValue()
   * @generated
   */
  int LONG_VALUE = 18;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LONG_VALUE__VALUE = NUMBER_VALUE__VALUE;

  /**
   * The number of structural features of the '<em>Long Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LONG_VALUE_FEATURE_COUNT = NUMBER_VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link statemachine.ActionKind <em>Action Kind</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see statemachine.ActionKind
   * @see statemachine.impl.StatemachinePackageImpl#getActionKind()
   * @generated
   */
  int ACTION_KIND = 19;


  /**
   * Returns the meta object for class '{@link statemachine.Named <em>Named</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Named</em>'.
   * @see statemachine.Named
   * @generated
   */
  EClass getNamed();

  /**
   * Returns the meta object for the attribute '{@link statemachine.Named#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see statemachine.Named#getName()
   * @see #getNamed()
   * @generated
   */
  EAttribute getNamed_Name();

  /**
   * Returns the meta object for the attribute '{@link statemachine.Named#getComment <em>Comment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Comment</em>'.
   * @see statemachine.Named#getComment()
   * @see #getNamed()
   * @generated
   */
  EAttribute getNamed_Comment();

  /**
   * Returns the meta object for class '{@link statemachine.GAbstractState <em>GAbstract State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>GAbstract State</em>'.
   * @see statemachine.GAbstractState
   * @generated
   */
  EClass getGAbstractState();

  /**
   * Returns the meta object for the containment reference list '{@link statemachine.GAbstractState#getActions <em>Actions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Actions</em>'.
   * @see statemachine.GAbstractState#getActions()
   * @see #getGAbstractState()
   * @generated
   */
  EReference getGAbstractState_Actions();

  /**
   * Returns the meta object for class '{@link statemachine.GAbstractAction <em>GAbstract Action</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>GAbstract Action</em>'.
   * @see statemachine.GAbstractAction
   * @generated
   */
  EClass getGAbstractAction();

  /**
   * Returns the meta object for the attribute '{@link statemachine.GAbstractAction#getKind <em>Kind</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Kind</em>'.
   * @see statemachine.GAbstractAction#getKind()
   * @see #getGAbstractAction()
   * @generated
   */
  EAttribute getGAbstractAction_Kind();

  /**
   * Returns the meta object for class '{@link statemachine.GState <em>GState</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>GState</em>'.
   * @see statemachine.GState
   * @generated
   */
  EClass getGState();

  /**
   * Returns the meta object for class '{@link statemachine.Transition <em>Transition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Transition</em>'.
   * @see statemachine.Transition
   * @generated
   */
  EClass getTransition();

  /**
   * Returns the meta object for the reference '{@link statemachine.Transition#getFrom <em>From</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>From</em>'.
   * @see statemachine.Transition#getFrom()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_From();

  /**
   * Returns the meta object for the reference '{@link statemachine.Transition#getTo <em>To</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>To</em>'.
   * @see statemachine.Transition#getTo()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_To();

  /**
   * Returns the meta object for the containment reference '{@link statemachine.Transition#getGuard <em>Guard</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Guard</em>'.
   * @see statemachine.Transition#getGuard()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Guard();

  /**
   * Returns the meta object for the containment reference '{@link statemachine.Transition#getDelay <em>Delay</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Delay</em>'.
   * @see statemachine.Transition#getDelay()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Delay();

  /**
   * Returns the meta object for the containment reference list '{@link statemachine.Transition#getSignals <em>Signals</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Signals</em>'.
   * @see statemachine.Transition#getSignals()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Signals();

  /**
   * Returns the meta object for the attribute '{@link statemachine.Transition#isPreserveTimers <em>Preserve Timers</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Preserve Timers</em>'.
   * @see statemachine.Transition#isPreserveTimers()
   * @see #getTransition()
   * @generated
   */
  EAttribute getTransition_PreserveTimers();

  /**
   * Returns the meta object for class '{@link statemachine.GCompositeState <em>GComposite State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>GComposite State</em>'.
   * @see statemachine.GCompositeState
   * @generated
   */
  EClass getGCompositeState();

  /**
   * Returns the meta object for the containment reference list '{@link statemachine.GCompositeState#getStates <em>States</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>States</em>'.
   * @see statemachine.GCompositeState#getStates()
   * @see #getGCompositeState()
   * @generated
   */
  EReference getGCompositeState_States();

  /**
   * Returns the meta object for the containment reference list '{@link statemachine.GCompositeState#getTransitions <em>Transitions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Transitions</em>'.
   * @see statemachine.GCompositeState#getTransitions()
   * @see #getGCompositeState()
   * @generated
   */
  EReference getGCompositeState_Transitions();

  /**
   * Returns the meta object for class '{@link statemachine.GStartState <em>GStart State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>GStart State</em>'.
   * @see statemachine.GStartState
   * @generated
   */
  EClass getGStartState();

  /**
   * Returns the meta object for class '{@link statemachine.GStopState <em>GStop State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>GStop State</em>'.
   * @see statemachine.GStopState
   * @generated
   */
  EClass getGStopState();

  /**
   * Returns the meta object for class '{@link statemachine.GStatemachine <em>GStatemachine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>GStatemachine</em>'.
   * @see statemachine.GStatemachine
   * @generated
   */
  EClass getGStatemachine();

  /**
   * Returns the meta object for the containment reference list '{@link statemachine.GStatemachine#getParameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameter</em>'.
   * @see statemachine.GStatemachine#getParameter()
   * @see #getGStatemachine()
   * @generated
   */
  EReference getGStatemachine_Parameter();

  /**
   * Returns the meta object for the attribute '{@link statemachine.GStatemachine#getPackage <em>Package</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Package</em>'.
   * @see statemachine.GStatemachine#getPackage()
   * @see #getGStatemachine()
   * @generated
   */
  EAttribute getGStatemachine_Package();

  /**
   * Returns the meta object for class '{@link statemachine.CallAction <em>Call Action</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Call Action</em>'.
   * @see statemachine.CallAction
   * @generated
   */
  EClass getCallAction();

  /**
   * Returns the meta object for the containment reference '{@link statemachine.CallAction#getCall <em>Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Call</em>'.
   * @see statemachine.CallAction#getCall()
   * @see #getCallAction()
   * @generated
   */
  EReference getCallAction_Call();

  /**
   * Returns the meta object for class '{@link statemachine.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parameter</em>'.
   * @see statemachine.Parameter
   * @generated
   */
  EClass getParameter();

  /**
   * Returns the meta object for class '{@link statemachine.Value <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Value</em>'.
   * @see statemachine.Value
   * @generated
   */
  EClass getValue();

  /**
   * Returns the meta object for class '{@link statemachine.GetParameter <em>Get Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Get Parameter</em>'.
   * @see statemachine.GetParameter
   * @generated
   */
  EClass getGetParameter();

  /**
   * Returns the meta object for the reference '{@link statemachine.GetParameter#getParameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Parameter</em>'.
   * @see statemachine.GetParameter#getParameter()
   * @see #getGetParameter()
   * @generated
   */
  EReference getGetParameter_Parameter();

  /**
   * Returns the meta object for class '{@link statemachine.Call <em>Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Call</em>'.
   * @see statemachine.Call
   * @generated
   */
  EClass getCall();

  /**
   * Returns the meta object for the attribute '{@link statemachine.Call#getActionId <em>Action Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Action Id</em>'.
   * @see statemachine.Call#getActionId()
   * @see #getCall()
   * @generated
   */
  EAttribute getCall_ActionId();

  /**
   * Returns the meta object for class '{@link statemachine.ConstantValue <em>Constant Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Constant Value</em>'.
   * @see statemachine.ConstantValue
   * @generated
   */
  EClass getConstantValue();

  /**
   * Returns the meta object for the attribute '{@link statemachine.ConstantValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see statemachine.ConstantValue#getValue()
   * @see #getConstantValue()
   * @generated
   */
  EAttribute getConstantValue_Value();

  /**
   * Returns the meta object for class '{@link statemachine.StringValue <em>String Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>String Value</em>'.
   * @see statemachine.StringValue
   * @generated
   */
  EClass getStringValue();

  /**
   * Returns the meta object for class '{@link statemachine.NumberValue <em>Number Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Number Value</em>'.
   * @see statemachine.NumberValue
   * @generated
   */
  EClass getNumberValue();

  /**
   * Returns the meta object for class '{@link statemachine.BooleanValue <em>Boolean Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Boolean Value</em>'.
   * @see statemachine.BooleanValue
   * @generated
   */
  EClass getBooleanValue();

  /**
   * Returns the meta object for class '{@link statemachine.LongValue <em>Long Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Long Value</em>'.
   * @see statemachine.LongValue
   * @generated
   */
  EClass getLongValue();

  /**
   * Returns the meta object for enum '{@link statemachine.ActionKind <em>Action Kind</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Action Kind</em>'.
   * @see statemachine.ActionKind
   * @generated
   */
  EEnum getActionKind();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  StatemachineFactory getStatemachineFactory();

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
     * The meta object literal for the '{@link statemachine.impl.NamedImpl <em>Named</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.NamedImpl
     * @see statemachine.impl.StatemachinePackageImpl#getNamed()
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
     * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NAMED__COMMENT = eINSTANCE.getNamed_Comment();

    /**
     * The meta object literal for the '{@link statemachine.impl.GAbstractStateImpl <em>GAbstract State</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.GAbstractStateImpl
     * @see statemachine.impl.StatemachinePackageImpl#getGAbstractState()
     * @generated
     */
    EClass GABSTRACT_STATE = eINSTANCE.getGAbstractState();

    /**
     * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GABSTRACT_STATE__ACTIONS = eINSTANCE.getGAbstractState_Actions();

    /**
     * The meta object literal for the '{@link statemachine.impl.GAbstractActionImpl <em>GAbstract Action</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.GAbstractActionImpl
     * @see statemachine.impl.StatemachinePackageImpl#getGAbstractAction()
     * @generated
     */
    EClass GABSTRACT_ACTION = eINSTANCE.getGAbstractAction();

    /**
     * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GABSTRACT_ACTION__KIND = eINSTANCE.getGAbstractAction_Kind();

    /**
     * The meta object literal for the '{@link statemachine.impl.GStateImpl <em>GState</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.GStateImpl
     * @see statemachine.impl.StatemachinePackageImpl#getGState()
     * @generated
     */
    EClass GSTATE = eINSTANCE.getGState();

    /**
     * The meta object literal for the '{@link statemachine.impl.TransitionImpl <em>Transition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.TransitionImpl
     * @see statemachine.impl.StatemachinePackageImpl#getTransition()
     * @generated
     */
    EClass TRANSITION = eINSTANCE.getTransition();

    /**
     * The meta object literal for the '<em><b>From</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__FROM = eINSTANCE.getTransition_From();

    /**
     * The meta object literal for the '<em><b>To</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__TO = eINSTANCE.getTransition_To();

    /**
     * The meta object literal for the '<em><b>Guard</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__GUARD = eINSTANCE.getTransition_Guard();

    /**
     * The meta object literal for the '<em><b>Delay</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__DELAY = eINSTANCE.getTransition_Delay();

    /**
     * The meta object literal for the '<em><b>Signals</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__SIGNALS = eINSTANCE.getTransition_Signals();

    /**
     * The meta object literal for the '<em><b>Preserve Timers</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRANSITION__PRESERVE_TIMERS = eINSTANCE.getTransition_PreserveTimers();

    /**
     * The meta object literal for the '{@link statemachine.impl.GCompositeStateImpl <em>GComposite State</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.GCompositeStateImpl
     * @see statemachine.impl.StatemachinePackageImpl#getGCompositeState()
     * @generated
     */
    EClass GCOMPOSITE_STATE = eINSTANCE.getGCompositeState();

    /**
     * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GCOMPOSITE_STATE__STATES = eINSTANCE.getGCompositeState_States();

    /**
     * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GCOMPOSITE_STATE__TRANSITIONS = eINSTANCE.getGCompositeState_Transitions();

    /**
     * The meta object literal for the '{@link statemachine.impl.GStartStateImpl <em>GStart State</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.GStartStateImpl
     * @see statemachine.impl.StatemachinePackageImpl#getGStartState()
     * @generated
     */
    EClass GSTART_STATE = eINSTANCE.getGStartState();

    /**
     * The meta object literal for the '{@link statemachine.impl.GStopStateImpl <em>GStop State</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.GStopStateImpl
     * @see statemachine.impl.StatemachinePackageImpl#getGStopState()
     * @generated
     */
    EClass GSTOP_STATE = eINSTANCE.getGStopState();

    /**
     * The meta object literal for the '{@link statemachine.impl.GStatemachineImpl <em>GStatemachine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.GStatemachineImpl
     * @see statemachine.impl.StatemachinePackageImpl#getGStatemachine()
     * @generated
     */
    EClass GSTATEMACHINE = eINSTANCE.getGStatemachine();

    /**
     * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GSTATEMACHINE__PARAMETER = eINSTANCE.getGStatemachine_Parameter();

    /**
     * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GSTATEMACHINE__PACKAGE = eINSTANCE.getGStatemachine_Package();

    /**
     * The meta object literal for the '{@link statemachine.impl.CallActionImpl <em>Call Action</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.CallActionImpl
     * @see statemachine.impl.StatemachinePackageImpl#getCallAction()
     * @generated
     */
    EClass CALL_ACTION = eINSTANCE.getCallAction();

    /**
     * The meta object literal for the '<em><b>Call</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CALL_ACTION__CALL = eINSTANCE.getCallAction_Call();

    /**
     * The meta object literal for the '{@link statemachine.impl.ParameterImpl <em>Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.ParameterImpl
     * @see statemachine.impl.StatemachinePackageImpl#getParameter()
     * @generated
     */
    EClass PARAMETER = eINSTANCE.getParameter();

    /**
     * The meta object literal for the '{@link statemachine.impl.ValueImpl <em>Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.ValueImpl
     * @see statemachine.impl.StatemachinePackageImpl#getValue()
     * @generated
     */
    EClass VALUE = eINSTANCE.getValue();

    /**
     * The meta object literal for the '{@link statemachine.impl.GetParameterImpl <em>Get Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.GetParameterImpl
     * @see statemachine.impl.StatemachinePackageImpl#getGetParameter()
     * @generated
     */
    EClass GET_PARAMETER = eINSTANCE.getGetParameter();

    /**
     * The meta object literal for the '<em><b>Parameter</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GET_PARAMETER__PARAMETER = eINSTANCE.getGetParameter_Parameter();

    /**
     * The meta object literal for the '{@link statemachine.impl.CallImpl <em>Call</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.CallImpl
     * @see statemachine.impl.StatemachinePackageImpl#getCall()
     * @generated
     */
    EClass CALL = eINSTANCE.getCall();

    /**
     * The meta object literal for the '<em><b>Action Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CALL__ACTION_ID = eINSTANCE.getCall_ActionId();

    /**
     * The meta object literal for the '{@link statemachine.impl.ConstantValueImpl <em>Constant Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.ConstantValueImpl
     * @see statemachine.impl.StatemachinePackageImpl#getConstantValue()
     * @generated
     */
    EClass CONSTANT_VALUE = eINSTANCE.getConstantValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTANT_VALUE__VALUE = eINSTANCE.getConstantValue_Value();

    /**
     * The meta object literal for the '{@link statemachine.impl.StringValueImpl <em>String Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.StringValueImpl
     * @see statemachine.impl.StatemachinePackageImpl#getStringValue()
     * @generated
     */
    EClass STRING_VALUE = eINSTANCE.getStringValue();

    /**
     * The meta object literal for the '{@link statemachine.impl.NumberValueImpl <em>Number Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.NumberValueImpl
     * @see statemachine.impl.StatemachinePackageImpl#getNumberValue()
     * @generated
     */
    EClass NUMBER_VALUE = eINSTANCE.getNumberValue();

    /**
     * The meta object literal for the '{@link statemachine.impl.BooleanValueImpl <em>Boolean Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.BooleanValueImpl
     * @see statemachine.impl.StatemachinePackageImpl#getBooleanValue()
     * @generated
     */
    EClass BOOLEAN_VALUE = eINSTANCE.getBooleanValue();

    /**
     * The meta object literal for the '{@link statemachine.impl.LongValueImpl <em>Long Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.impl.LongValueImpl
     * @see statemachine.impl.StatemachinePackageImpl#getLongValue()
     * @generated
     */
    EClass LONG_VALUE = eINSTANCE.getLongValue();

    /**
     * The meta object literal for the '{@link statemachine.ActionKind <em>Action Kind</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see statemachine.ActionKind
     * @see statemachine.impl.StatemachinePackageImpl#getActionKind()
     * @generated
     */
    EEnum ACTION_KIND = eINSTANCE.getActionKind();

  }

} //StatemachinePackage
