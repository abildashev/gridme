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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.visual.impl.ClusterGanttChartBuilder;
import com.googlecode.gridme.ui.GridmeUIPlugin;

public class GanttChartEditor extends EditorPart
{
  private IFile reportFile;
  private Browser browser;

  public GanttChartEditor()
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

    reportFile = ((FileEditorInput) input).getFile();
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
    final ClusterGanttChartBuilder chart;
    try
    {
      File log = reportFile.getLocation().toFile();
      chart = new ClusterGanttChartBuilder(log);
      chart.buildCharts();
    }
    catch(GRuntimeException e)
    {
      GridmeUIPlugin.logErrorMessage("Unable to build gantt chart report", e);
      return;
    }

    final Composite page = new Composite(parent, SWT.NONE);
    page.setLayout(new GridLayout(4, false));
    GridData data;

    Label lab = new Label(page, SWT.WRAP);
    data = new GridData(GridData.FILL_HORIZONTAL);
    lab.setLayoutData(data);
    lab.setText("Select cluster:");

    final Combo clusters = new Combo(page, SWT.DROP_DOWN | SWT.READ_ONLY);
    data = new GridData(GridData.FILL_HORIZONTAL);
    data.horizontalSpan = 3;
    clusters.setLayoutData(data);

    for(String cluster : chart.getClusterNames())
    {
      clusters.add(cluster);
    }

    clusters.addModifyListener(new ModifyListener()
    {
      @Override
      public void modifyText(ModifyEvent e)
      {
        browser.setUrl(chart.getResultURL(clusters.getItem(clusters
            .getSelectionIndex())));
      }
    });

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
    data.horizontalAlignment = GridData.FILL;
    data.verticalAlignment = GridData.FILL;
    data.horizontalSpan = 4;
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = true;
    browser.setLayoutData(data);
  }

  @Override
  public void setFocus()
  {
  }
}
