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
package com.googlecode.gridme.simstate.tests;

import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.ConvertUtils;
import com.googlecode.gridme.simstate.GSignal;
import com.googlecode.gridme.simstate.Statemachine;
import com.googlecode.gridme.simstate.TransitionTimer;

/**
 * This is an implementation of test15A statemachine. 
 * To use it from an active element do the following:
 *
 * <ul>
 * <li>call setStatemachine(new test15ASTM(this)) from the constructor.</li>
 *
 * <li>set parameters by calling this.statemachine.addParam(). See the class 
 * for a list of generated constants</li>
 *
 * <li>check that all actions referenced from the statemachine code are present. 
 * See the class for the list of generated constants.</li>
 * </ul>
 * 
 * The class will also contain a list of String constants for 
 * the used state names.  
 */
public class test15ASTM extends Statemachine {
	private static final int ST_start = 0;
	private static final int ST_stop = 1;
	private static final int ST_wait = 2;
	private static final int ST_send = 3;

	// A list of action constants
	public static final int ACTION_getDelay = 0;
	public static final int ACTION_isFinished = 1;
	public static final int ACTION_sendSignal = 2;
	public static final int ACTION_notFinished = 3;

	// A list of state names
	public static final String STATE_start = "start";
	public static final String STATE_stop = "stop";
	public static final String STATE_wait = "wait";
	public static final String STATE_send = "send";

	// A list of parameters
	public static final String PARAM_sigpar3 = "sigpar3";

	private int currentState;
	private GSignal activeSignal;
	private boolean isFinished = false;

	public test15ASTM(ActiveElement parent) {
		super(parent);
		activeSignal = null;
		currentState = ST_start;
		setCurrentStateName(STATE_start);
	}

	/**
	 * If state machine is in stop state.
	 * @return true, if machine has stopped
	 */
	public boolean finished() {
		return isFinished;
	}

	/**
	 * Makes a transition.
	 * @return true if at least one transition has been made.
	 */
	public boolean run() throws Exception {
		boolean transition = false;
		int transitions = 0;

		do {
			/*
			if(getParent().getExecControl().getStopCMD())
			{
			  break;
			}
			 */

			transition = false;

			switch (currentState) {
				case ST_start :
					// no signal transition
					if (true) {
						leaveState(ST_start);

						// reset all timers
						getParent().clearTimers();

						enterState(ST_wait);
						transition = true;
						transitions++;
						break;
					}

					break;
				case ST_stop :
					break;
				case ST_wait :

					if (getParent().getReadyTimer("t2") != null) {
						if (true) {
							leaveState(ST_wait);

							// reset all timers
							getParent().clearTimers();

							enterState(ST_send);
							transition = true;
							transitions++;
							break;
						}
					}

					break;
				case ST_send :
					// no signal transition
					if (ConvertUtils.makeBooleanValue(getParent().action(
							ACTION_isFinished))) {
						leaveState(ST_send);

						// reset all timers
						getParent().clearTimers();

						enterState(ST_stop);
						transition = true;
						transitions++;
						break;
					}

					// no signal transition
					if (ConvertUtils.makeBooleanValue(getParent().action(
							ACTION_notFinished))) {
						leaveState(ST_send);

						// reset all timers
						getParent().clearTimers();

						enterState(ST_wait);
						transition = true;
						transitions++;
						break;
					}

					break;
				default :
			}
		} while (transition);

		return transitions > 0;
	}

	/**
	 * Returns the signal which caused the transition.
	 * @return Signal
	 */
	public GSignal getActiveSignal() {
		return activeSignal;
	}

	private void enterState(int state) throws Exception {
		switch (state) {

			case ST_stop :
				// set state name
				setCurrentStateName(STATE_stop);
				isFinished = true;
				break;

			case ST_wait :
				// set timers

				getParent().addTimer(
						new TransitionTimer("t2", ConvertUtils
								.makeLongValue(getParent().action(
										ACTION_getDelay))));
				// set state name
				setCurrentStateName(STATE_wait);
				break;

			case ST_send :

				getParent().action(ACTION_sendSignal);

				// set timers
				// set state name
				setCurrentStateName(STATE_send);
				break;

			default :
		}
		currentState = state;
	}

	private void leaveState(int state) throws Exception {
		switch (state) {

			case ST_wait :

				break;

			case ST_send :

				break;

			default :
		}
	}
}
