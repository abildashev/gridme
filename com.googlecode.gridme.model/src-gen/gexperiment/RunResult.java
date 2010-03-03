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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Run Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link gexperiment.RunResult#isActive <em>Active</em>}</li>
 * </ul>
 * </p>
 *
 * @see gexperiment.GexperimentPackage#getRunResult()
 * @model abstract="true"
 * @generated
 */
public interface RunResult extends Named
{

  /**
   * Returns the value of the '<em><b>Active</b></em>' attribute.
   * The default value is <code>"true"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Active</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Active</em>' attribute.
   * @see #setActive(boolean)
   * @see gexperiment.GexperimentPackage#getRunResult_Active()
   * @model default="true" required="true"
   * @generated
   */
  boolean isActive();

  /**
   * Sets the value of the '{@link gexperiment.RunResult#isActive <em>Active</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Active</em>' attribute.
   * @see #isActive()
   * @generated
   */
  void setActive(boolean value);
} // RunResult
