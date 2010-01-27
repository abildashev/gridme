/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package gexperiment;

import gmodel.Named;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Series Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link gexperiment.SeriesParameter#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see gexperiment.GexperimentPackage#getSeriesParameter()
 * @model
 * @generated
 */
public interface SeriesParameter extends Named
{
  /**
   * Returns the value of the '<em><b>Values</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Values</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Values</em>' attribute list.
   * @see gexperiment.GexperimentPackage#getSeriesParameter_Values()
   * @model default="" required="true"
   * @generated
   */
  EList<String> getValues();

} // SeriesParameter
