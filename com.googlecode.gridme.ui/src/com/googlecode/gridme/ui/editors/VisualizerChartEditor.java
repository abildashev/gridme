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
package com.googlecode.gridme.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.googlecode.gridme.ui.GridmeUIPlugin;

public class VisualizerChartEditor extends EditorPart
{
  private Browser browser;
  private String vfile;

  public VisualizerChartEditor()
  {
  }

  @Override
  public void doSave(IProgressMonitor monitor)
  {
  }

  @Override
  public void doSaveAs()
  {
  }

  @Override
  public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
  {
    setSite(site);
    setInput(input);
    setPartName(input.getName());
    
    vfile = ((FileEditorInput)input).getFile().getLocation().toPortableString();
  }

  @Override
  public boolean isDirty()
  {
    return false;
  }

  @Override
  public boolean isSaveAsAllowed()
  {
    return false;
  }

  @Override
  public void createPartControl(Composite parent)
  {
    final Composite page = new Composite(parent, SWT.NONE);
    page.setLayout(new GridLayout(4, false));
    GridData data;

    final String text = "<html><head>"
      + "<title>HTML Test</title></head>"
      + "<body><img src='" + vfile + "'></img></body></html>";
    
    try
    {
      browser = new Browser(page, SWT.NONE);
    }
    catch(SWTError e)
    {
      GridmeUIPlugin.logErrorMessage("Could not instantiate Browser "
          + e.getMessage(), null);
      return;
    }

    data = new GridData();
    
    Button reload = new Button(page, SWT.NONE);
    reload.setText("Refresh");
    
    reload.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        browser.setText(text);
        //browser.refresh();
      }
    });
    
    data.horizontalAlignment = GridData.FILL;
    data.verticalAlignment = GridData.FILL;
    data.horizontalSpan = 4;
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = true;
    browser.setLayoutData(data);
    
    browser.setText(text);
  }

  @Override
  public void setFocus()
  {
  }
}
