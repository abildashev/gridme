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
package statemachine.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import statemachine.GAbstractState;
import statemachine.StatemachinePackage;
import statemachine.Transition;
import statemachine.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link statemachine.impl.TransitionImpl#getFrom <em>From</em>}</li>
 *   <li>{@link statemachine.impl.TransitionImpl#getTo <em>To</em>}</li>
 *   <li>{@link statemachine.impl.TransitionImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link statemachine.impl.TransitionImpl#getDelay <em>Delay</em>}</li>
 *   <li>{@link statemachine.impl.TransitionImpl#getSignals <em>Signals</em>}</li>
 *   <li>{@link statemachine.impl.TransitionImpl#isPreserveTimers <em>Preserve Timers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionImpl extends NamedImpl implements Transition
{
  /**
   * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFrom()
   * @generated
   * @ordered
   */
  protected GAbstractState from;

  /**
   * The cached value of the '{@link #getTo() <em>To</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTo()
   * @generated
   * @ordered
   */
  protected GAbstractState to;

  /**
   * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGuard()
   * @generated
   * @ordered
   */
  protected Value guard;

  /**
   * The cached value of the '{@link #getDelay() <em>Delay</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDelay()
   * @generated
   * @ordered
   */
  protected Value delay;

  /**
   * The cached value of the '{@link #getSignals() <em>Signals</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSignals()
   * @generated
   * @ordered
   */
  protected EList<Value> signals;

  /**
   * The default value of the '{@link #isPreserveTimers() <em>Preserve Timers</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isPreserveTimers()
   * @generated
   * @ordered
   */
  protected static final boolean PRESERVE_TIMERS_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isPreserveTimers() <em>Preserve Timers</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isPreserveTimers()
   * @generated
   * @ordered
   */
  protected boolean preserveTimers = PRESERVE_TIMERS_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TransitionImpl()
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
    return StatemachinePackage.Literals.TRANSITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GAbstractState getFrom()
  {
    if (from != null && from.eIsProxy())
    {
      InternalEObject oldFrom = (InternalEObject)from;
      from = (GAbstractState)eResolveProxy(oldFrom);
      if (from != oldFrom)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, StatemachinePackage.TRANSITION__FROM, oldFrom, from));
      }
    }
    return from;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GAbstractState basicGetFrom()
  {
    return from;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFrom(GAbstractState newFrom)
  {
    GAbstractState oldFrom = from;
    from = newFrom;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StatemachinePackage.TRANSITION__FROM, oldFrom, from));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GAbstractState getTo()
  {
    if (to != null && to.eIsProxy())
    {
      InternalEObject oldTo = (InternalEObject)to;
      to = (GAbstractState)eResolveProxy(oldTo);
      if (to != oldTo)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, StatemachinePackage.TRANSITION__TO, oldTo, to));
      }
    }
    return to;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GAbstractState basicGetTo()
  {
    return to;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTo(GAbstractState newTo)
  {
    GAbstractState oldTo = to;
    to = newTo;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StatemachinePackage.TRANSITION__TO, oldTo, to));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Value getGuard()
  {
    return guard;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetGuard(Value newGuard, NotificationChain msgs)
  {
    Value oldGuard = guard;
    guard = newGuard;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatemachinePackage.TRANSITION__GUARD, oldGuard, newGuard);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setGuard(Value newGuard)
  {
    if (newGuard != guard)
    {
      NotificationChain msgs = null;
      if (guard != null)
        msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatemachinePackage.TRANSITION__GUARD, null, msgs);
      if (newGuard != null)
        msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatemachinePackage.TRANSITION__GUARD, null, msgs);
      msgs = basicSetGuard(newGuard, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StatemachinePackage.TRANSITION__GUARD, newGuard, newGuard));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Value getDelay()
  {
    return delay;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDelay(Value newDelay, NotificationChain msgs)
  {
    Value oldDelay = delay;
    delay = newDelay;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatemachinePackage.TRANSITION__DELAY, oldDelay, newDelay);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDelay(Value newDelay)
  {
    if (newDelay != delay)
    {
      NotificationChain msgs = null;
      if (delay != null)
        msgs = ((InternalEObject)delay).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatemachinePackage.TRANSITION__DELAY, null, msgs);
      if (newDelay != null)
        msgs = ((InternalEObject)newDelay).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatemachinePackage.TRANSITION__DELAY, null, msgs);
      msgs = basicSetDelay(newDelay, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StatemachinePackage.TRANSITION__DELAY, newDelay, newDelay));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Value> getSignals()
  {
    if (signals == null)
    {
      signals = new EObjectContainmentEList<Value>(Value.class, this, StatemachinePackage.TRANSITION__SIGNALS);
    }
    return signals;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isPreserveTimers()
  {
    return preserveTimers;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPreserveTimers(boolean newPreserveTimers)
  {
    boolean oldPreserveTimers = preserveTimers;
    preserveTimers = newPreserveTimers;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StatemachinePackage.TRANSITION__PRESERVE_TIMERS, oldPreserveTimers, preserveTimers));
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
      case StatemachinePackage.TRANSITION__GUARD:
        return basicSetGuard(null, msgs);
      case StatemachinePackage.TRANSITION__DELAY:
        return basicSetDelay(null, msgs);
      case StatemachinePackage.TRANSITION__SIGNALS:
        return ((InternalEList<?>)getSignals()).basicRemove(otherEnd, msgs);
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
      case StatemachinePackage.TRANSITION__FROM:
        if (resolve) return getFrom();
        return basicGetFrom();
      case StatemachinePackage.TRANSITION__TO:
        if (resolve) return getTo();
        return basicGetTo();
      case StatemachinePackage.TRANSITION__GUARD:
        return getGuard();
      case StatemachinePackage.TRANSITION__DELAY:
        return getDelay();
      case StatemachinePackage.TRANSITION__SIGNALS:
        return getSignals();
      case StatemachinePackage.TRANSITION__PRESERVE_TIMERS:
        return isPreserveTimers() ? Boolean.TRUE : Boolean.FALSE;
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
      case StatemachinePackage.TRANSITION__FROM:
        setFrom((GAbstractState)newValue);
        return;
      case StatemachinePackage.TRANSITION__TO:
        setTo((GAbstractState)newValue);
        return;
      case StatemachinePackage.TRANSITION__GUARD:
        setGuard((Value)newValue);
        return;
      case StatemachinePackage.TRANSITION__DELAY:
        setDelay((Value)newValue);
        return;
      case StatemachinePackage.TRANSITION__SIGNALS:
        getSignals().clear();
        getSignals().addAll((Collection<? extends Value>)newValue);
        return;
      case StatemachinePackage.TRANSITION__PRESERVE_TIMERS:
        setPreserveTimers(((Boolean)newValue).booleanValue());
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
      case StatemachinePackage.TRANSITION__FROM:
        setFrom((GAbstractState)null);
        return;
      case StatemachinePackage.TRANSITION__TO:
        setTo((GAbstractState)null);
        return;
      case StatemachinePackage.TRANSITION__GUARD:
        setGuard((Value)null);
        return;
      case StatemachinePackage.TRANSITION__DELAY:
        setDelay((Value)null);
        return;
      case StatemachinePackage.TRANSITION__SIGNALS:
        getSignals().clear();
        return;
      case StatemachinePackage.TRANSITION__PRESERVE_TIMERS:
        setPreserveTimers(PRESERVE_TIMERS_EDEFAULT);
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
      case StatemachinePackage.TRANSITION__FROM:
        return from != null;
      case StatemachinePackage.TRANSITION__TO:
        return to != null;
      case StatemachinePackage.TRANSITION__GUARD:
        return guard != null;
      case StatemachinePackage.TRANSITION__DELAY:
        return delay != null;
      case StatemachinePackage.TRANSITION__SIGNALS:
        return signals != null && !signals.isEmpty();
      case StatemachinePackage.TRANSITION__PRESERVE_TIMERS:
        return preserveTimers != PRESERVE_TIMERS_EDEFAULT;
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
    result.append(" (preserveTimers: ");
    result.append(preserveTimers);
    result.append(')');
    return result.toString();
  }

} //TransitionImpl
