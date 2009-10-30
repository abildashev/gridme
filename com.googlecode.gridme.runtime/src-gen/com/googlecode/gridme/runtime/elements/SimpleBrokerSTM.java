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
package com.googlecode.gridme.runtime.elements;

import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.ConvertUtils;
import com.googlecode.gridme.simstate.GSignal;
import com.googlecode.gridme.simstate.Statemachine;
import com.googlecode.gridme.simstate.TransitionTimer;

/**
 * This is an implementation of SimpleBroker statemachine. 
 * To use it from an active element do the following:
 *
 * <ul>
 * <li>call setStatemachine(new SimpleBrokerSTM(this)) from the constructor.</li>
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
public class SimpleBrokerSTM extends Statemachine {
	private static final int ST_start = 0;
	private static final int ST_stop = 1;
	private static final int ST_Discover = 2;
	private static final int ST_Idle = 3;
	private static final int ST_Schedule = 4;
	private static final int ST_Prepare = 5;
	private static final int ST_Hourly = 6;

	// A list of action constants
	public static final int ACTION_discover = 0;
	public static final int ACTION_schedule = 1;
	public static final int ACTION_getDelay = 2;
	public static final int ACTION_doHourly = 3;

	// A list of state names
	public static final String STATE_start = "start";
	public static final String STATE_stop = "stop";
	public static final String STATE_Discover = "Discover";
	public static final String STATE_Idle = "Idle";
	public static final String STATE_Schedule = "Schedule";
	public static final String STATE_Prepare = "Prepare";
	public static final String STATE_Hourly = "Hourly";

	// A list of parameters
	public static final String PARAM_hourSignal = "hourSignal";

	private int currentState;
	private GSignal activeSignal;
	private boolean isFinished = false;

	public SimpleBrokerSTM(ActiveElement parent) {
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

						enterState(ST_Discover);
						transition = true;
						transitions++;
						break;
					}

					break;
				case ST_stop :
					break;
				case ST_Discover :
					// no signal transition
					if (true) {
						leaveState(ST_Discover);

						// reset all timers
						getParent().clearTimers();

						enterState(ST_Idle);
						transition = true;
						transitions++;
						break;
					}

					break;
				case ST_Idle :

					activeSignal = getParent()
							.checkSignal(
									com.googlecode.gridme.runtime.schedule.impl.GTaskSignal.class);
					if (activeSignal != null) {
						if (true) {
							leaveState(ST_Idle);

							// reset all timers
							getParent().clearTimers();

							enterState(ST_Prepare);
							transition = true;
							transitions++;
							break;
						}
					}

					activeSignal = getParent()
							.checkSignal(
									(Class<? extends GSignal>) getParam(PARAM_hourSignal));
					if (activeSignal != null) {
						if (true) {
							leaveState(ST_Idle);

							// reset all timers
							getParent().clearTimers();

							enterState(ST_Hourly);
							transition = true;
							transitions++;
							break;
						}
					}

					break;
				case ST_Schedule :
					// no signal transition
					if (true) {
						leaveState(ST_Schedule);

						// reset all timers
						getParent().clearTimers();

						enterState(ST_Idle);
						transition = true;
						transitions++;
						break;
					}

					break;
				case ST_Prepare :

					if (getParent().getReadyTimer("t4") != null) {
						if (true) {
							leaveState(ST_Prepare);

							// reset all timers
							getParent().clearTimers();

							enterState(ST_Schedule);
							transition = true;
							transitions++;
							break;
						}
					}

					break;
				case ST_Hourly :
					// no signal transition
					if (true) {
						leaveState(ST_Hourly);

						// reset all timers
						getParent().clearTimers();

						enterState(ST_Idle);
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

			case ST_Discover :

				getParent().action(ACTION_discover);

				// set timers
				// set state name
				setCurrentStateName(STATE_Discover);
				break;

			case ST_Idle :
				// set timers
				// set state name
				setCurrentStateName(STATE_Idle);
				break;

			case ST_Schedule :

				getParent().action(ACTION_schedule);

				// set timers
				// set state name
				setCurrentStateName(STATE_Schedule);
				break;

			case ST_Prepare :
				// set timers

				getParent().addTimer(
						new TransitionTimer("t4", ConvertUtils
								.makeLongValue(getParent().action(
										ACTION_getDelay))));
				// set state name
				setCurrentStateName(STATE_Prepare);
				break;

			case ST_Hourly :

				getParent().action(ACTION_doHourly);

				// set timers
				// set state name
				setCurrentStateName(STATE_Hourly);
				break;

			default :
		}
		currentState = state;
	}

	private void leaveState(int state) throws Exception {
		switch (state) {

			case ST_Discover :

				break;

			case ST_Idle :

				break;

			case ST_Schedule :

				break;

			case ST_Prepare :

				break;

			case ST_Hourly :

				break;

			default :
		}
	}
}
