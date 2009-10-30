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

/**
 * This interface provides methods for logging
 * model execution events such as signal sending,
 * state change, etc.
 */
public interface ModelProfileLogger extends ModelLogger
{
  /**
   * Logs element state change.
   * @param id Active element id.
   * @param oldState the name of the old state.
   * @param newState the name of the new state.
   * @param time event time.
   */
  public void stateChange(String id, String oldState, String newState, long time);
  
  /**
   * Logs signal send event.
   * @param id Active element id.
   * @param signal Signal name.
   * @param target Receiver.
   * @param time event time.
   */
  public void sendSignal(String id, String signal, String target, long time);
  
  /**
   * Logs user defined message.
   */
  public void logMessage(String id, String message, long time);
}
