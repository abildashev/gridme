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
package com.googlecode.gridme.simstate;

public interface StatemachineHandler
{
  /**
   * Returns a signal with the given name from the input queue.
   * 
   * @param signal signal class
   * @return signal instance
   */
  public GSignal checkSignal(Class<? extends GSignal> signal);

  /**
   * If a timer with 0 time value exists this method will
   * return it.
   * 
   * @return first available zero timer.
   */
  public TransitionTimer getReadyTimer(String name);

  /**
   * Removes all timers. Call this method after 
   * leaving a state.
   */
  public void clearTimers();

  /**
   * Adds a new timer.
   * 
   * @param timer timer
   */
  public void addTimer(TransitionTimer timer);

  /**
   * Provides access for the execution control commands.
   * @return control object
   */
  public ModelExecControl getExecControl();

  /**
   * Calls an action.
   * @param id named action id. See the child class that 
   * should contain a list of generated constants.
   */
  public Object action(int id) throws Exception;
}
