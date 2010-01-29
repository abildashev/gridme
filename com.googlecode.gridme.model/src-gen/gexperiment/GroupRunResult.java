/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package gexperiment;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group Run Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link gexperiment.GroupRunResult#getResults <em>Results</em>}</li>
 * </ul>
 * </p>
 *
 * @see gexperiment.GexperimentPackage#getGroupRunResult()
 * @model
 * @generated
 */
public interface GroupRunResult extends RunResult
{
  /**
   * Returns the value of the '<em><b>Results</b></em>' containment reference list.
   * The list contents are of type {@link gexperiment.SingleRunResult}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Results</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Results</em>' containment reference list.
   * @see gexperiment.GexperimentPackage#getGroupRunResult_Results()
   * @model containment="true" required="true"
   * @generated
   */
  EList<SingleRunResult> getResults();

} // GroupRunResult
