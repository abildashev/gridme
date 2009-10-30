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
 * Executor that waits for a notification and then 
 * fetches elements from the queue and calls run() method
 * on them. If stop command has been issued the executor exits.
 */
class ActivePoolExecutor implements Runnable
{
  private final RuntimeModel model;

  /**
   * @param model
   */
  public ActivePoolExecutor(RuntimeModel model)
  {
    this.model = model;
  }

  @Override
  public void run()
  {
    try
    {
      final Object lock = model.getStartIterationLock();
      ActiveElement elem;
      
      while(true)
      {
        // Wait for the continuation.
        synchronized(lock)
        {
          model.reportExecutorReady();
          lock.wait();
        }

        // Exit if we have a command.
        if(model.getElemExecControl().getStopCMD())
        {
          break;
        }

        // Execute tasks if any
        while((elem = model.getElementQueue().getNextElement()) != null)
        {
          model.reportTransition(elem.run());
        }
      }
    }
    catch(Throwable e)
    {
      if(model.getErrorLogger() != null)
      {
        model.getErrorLogger().logError(e.getMessage(), e);
      }
      else
      {
        e.printStackTrace();
      }
    }
    finally
    {
      model.reportWorkerFinished();
    }
  }
}
