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

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.schedule.workload.SWFParser;
import com.googlecode.gridme.runtime.schedule.workload.SWFWorkload;
import com.googlecode.gridme.runtime.schedule.workload.WorkloadAnalyzer;
import com.googlecode.gridme.simstate.SimStateGenerator;
import com.googlecode.gridme.simstate.SimStatePlugin;
import com.googlecode.gridme.ui.GridmeUIPlugin;
import com.googlecode.gridme.ui.UIProgressMonitor;

public class WorkloadEditor extends MultiPageEditorPart
{
  private Browser browser;
  private StyledText sourceText;

  public WorkloadEditor()
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
  public void setFocus()
  {
  }

  @Override
  protected void createPages()
  {
    createReportPage();
    createEditorPage();
    createSourcePage();

    makeOverview();
  }

  private void createEditorPage()
  {
    final Composite page = new Composite(getContainer(), SWT.NONE);
    page.setLayout(new GridLayout(10, false));

    GridData data = new GridData();
    data.horizontalAlignment = GridData.FILL;
    data.heightHint = 100;
    data.horizontalSpan = 10;
    data.grabExcessHorizontalSpace = true;

    Button align = new Button(page, SWT.PUSH);
    align.setText("Align to 0 start time");
    align.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        try
        {
          SWFWorkload workload = SWFWorkload.load(getWorkloadFile(), null);
          workload.align();
          workload.toFile(getWorkloadFile());
          makeOverview();
        }
        catch(GRuntimeException err)
        {
          GridmeUIPlugin.logErrorMessage("Error loading workload file", err);
        }
      }
    });

    page.pack();
    int index = addPage(page);
    setPageText(index, "Editor");
  }

  private void createSourcePage()
  {
    final Composite page = new Composite(getContainer(), SWT.NONE);
    page.setLayout(new FillLayout());

    sourceText = new StyledText(page, SWT.H_SCROLL | SWT.V_SCROLL);
    sourceText.setEditable(false);

    page.pack();
    int index = addPage(page);
    setPageText(index, "Source");
  }

  private void createReportPage()
  {
    final Composite page = new Composite(getContainer(), SWT.NONE);
    page.setLayout(new GridLayout(4, false));
    GridData data;

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

    Button back = new Button(page, SWT.NONE);
    back.setText("Back");

    back.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        browser.back();
      }
    });

    Button forward = new Button(page, SWT.NONE);
    forward.setText("Forward");

    forward.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        browser.forward();
      }
    });

    Button refresh = new Button(page, SWT.NONE);
    refresh.setText("Refresh");

    refresh.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        makeOverview();
      }
    });

    data.horizontalAlignment = GridData.FILL;
    data.verticalAlignment = GridData.FILL;
    data.horizontalSpan = 4;
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = true;
    browser.setLayoutData(data);

    page.pack();
    int index = addPage(page);
    setPageText(index, "Overview");
  }

  private void makeOverview()
  {
    try
    {
      File log = getWorkloadFile();
      final WorkloadAnalyzer wa = new WorkloadAnalyzer(log);

      PlatformUI.getWorkbench().getProgressService().run(true, true,
          new IRunnableWithProgress()
          {
            public void run(final IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException
            {
              try
              {
                wa
                    .analyze(new UIProgressMonitor("Analysing workload",
                        monitor));
              }
              catch(Exception e)
              {
                throw new InvocationTargetException(e,
                    "Workload analysis failed: " + e.getMessage());
              }
            }
          });

      browser.setUrl(wa.getResultURL());

      SWFParser parser = new SWFParser(getWorkloadFile());
      sourceText.setText(parser.toText());
    }
    catch(Exception e)
    {
      GridmeUIPlugin.logErrorMessage("Unable to create workload editor", e);
    }
  }

  private File getWorkloadFile()
  {
    return ((FileEditorInput) getEditorInput()).getFile().getLocation()
        .toFile();
  }
}
