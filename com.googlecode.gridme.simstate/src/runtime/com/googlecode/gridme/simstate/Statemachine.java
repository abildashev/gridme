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

import java.util.HashMap;

/**
 * Parent class for all state machines. State machine may have
 * parameters.
 */
public abstract class Statemachine
{
  private StatemachineHandler parent;
  private HashMap<String, Object> parameters;
  private String currentStateName;
  private HashMap<String, Class<? extends GSignal>> sigHash;

  /**
   * Creates new state machine instance.
   * @param parent handler object
   */
  protected Statemachine(StatemachineHandler parent)
  {
    this.parent = parent;
    parameters = new HashMap<String, Object>();
    sigHash = new HashMap<String, Class<? extends GSignal>>();
  }

  /**
   * @return handler instance
   */
  public StatemachineHandler getParent()
  {
    return parent;
  }

  /**
   * Gets parameter. See the child class for the list of
   * generated constants.
   * 
   * @param name parameter name.
   * @return parameter
   */
  public Object getParam(String name)
  {
    return parameters.get(name);
  }

  /**
   * Adds parameter. See the child class for the list of
   * generated constants.
   *   
   * @param name parameter name
   * @param param parameter value
   */
  public void addParam(String name, Object param)
  {
    parameters.put(name, param);
  }

  /**
   * If state machine is in stop state.
   * @return true, if machine has stopped
   */
  public abstract boolean finished();

  /**
   * Makes a transition.
   * @return true if at least one transition has been made.
   */
  public abstract boolean run() throws Exception;

  /**
   * Returns the signal which caused the transition.
   * Does not remove the signal from the queue.
   * 
   * @return Signal
   */
  public abstract GSignal getActiveSignal();

  /**
   * @return current state
   */
  public String getCurrentStateName()
  {
    return currentStateName;
  }

  /**
   * Sets current state name. 
   */
  protected void setCurrentStateName(String currentStateName)
  {
    this.currentStateName = currentStateName;
  }
  
  /**
   * Returns signal class.
   * 
   * @param className class name
   * @return class instance
   * @throws ClassNotFoundException 
   */
  protected Class<? extends GSignal> loadSignalClass(String className) throws ClassNotFoundException
  {
    Class<? extends GSignal> result = sigHash.get(className);
    if(result == null)
    {
      try
      {
        result = (Class<? extends GSignal>) Class.forName(className);
        sigHash.put(className, result);
      }
      catch(ClassNotFoundException e)
      {
        throw e;
      }
    }
    return result;
  }
}
