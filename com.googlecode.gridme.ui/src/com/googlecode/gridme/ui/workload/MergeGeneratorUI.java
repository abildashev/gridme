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
package com.googlecode.gridme.ui.workload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.googlecode.gridme.runtime.schedule.workload.MergeGenerator;
import com.googlecode.gridme.runtime.schedule.workload.SWFWorkload;
import com.googlecode.gridme.ui.GridmeUIPlugin;

public class MergeGeneratorUI extends MergeGenerator implements
    WorkloadGeneratorUI
{
  private WizardPage wpage;
  private Table srcList;
  private IPath container;
  private ArrayList<SWFWorkload> sources;

  public MergeGeneratorUI()
  {
    sources = new ArrayList<SWFWorkload>();
  }

  @Override
  public void createControls(Composite panel, WizardPage page)
  {
    this.wpage = page;
    container = ((WorkloadWizard) wpage.getWizard()).getSelectedContainer();
    panel.setLayout(new GridLayout(3, true));

    // Description
    Label lab = new Label(panel, SWT.WRAP);
    GridData dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 3;
    lab.setLayoutData(dta);
    lab.setText(getDescription());

    // Label
    lab = new Label(panel, SWT.WRAP);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 3;
    lab.setLayoutData(dta);
    lab.setText("Workloads to merge:");

    // Source list
    srcList = new Table(panel, SWT.BORDER | SWT.MULTI);
    srcList.setLinesVisible(true);
    srcList.setHeaderVisible(true);
    dta = new GridData(GridData.FILL_BOTH);
    dta.horizontalSpan = 3;
    srcList.setLayoutData(dta);
    TableColumn wName = new TableColumn(srcList, SWT.NONE);
    wName.setWidth(150);
    wName.setText("Workload name");
    TableColumn wSize = new TableColumn(srcList, SWT.NONE);
    wSize.setWidth(100);
    wSize.setText("Size");
    TableColumn wLen = new TableColumn(srcList, SWT.NONE);
    wLen.setWidth(100);
    wLen.setText("Length");

    // Controls
    Button addButton = new Button(panel, SWT.NONE);
    addButton.setText("Add");
    addButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        ElementListSelectionDialog dlg = new ElementListSelectionDialog(wpage
            .getShell(), new LabelProvider()
        {
          @Override
          public String getText(Object element)
          {
            return ((IFile) element).getName();
          }
        });
        dlg.setFilter("*");
        dlg.setIgnoreCase(true);
        dlg.setElements(getAvailableWorkloads().toArray());
        dlg.setMultipleSelection(true);

        if(dlg.open() == Window.OK && dlg.getResult() != null)
        {
          for(Object item : dlg.getResult())
          {
            addSource(((IFile) item).getLocation().toFile());
          }
        }

        checkPageComplete();
      }
    });

    Button addFileButton = new Button(panel, SWT.NONE);
    addFileButton.setText("Add external");
    addFileButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        FileDialog dlg = new FileDialog(wpage.getShell());
        dlg.setFilterExtensions(new String[] { "*.swfz" });
        String path = dlg.open();
        if(path != null)
        {
          addSource(new File(path));
        }

        checkPageComplete();
      }
    });

    final Button delButton = new Button(panel, SWT.NONE);
    delButton.setText("Delete");
    delButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        if(srcList.getSelectionIndex() != -1)
        {
          srcList.remove(srcList.getSelectionIndex());
          
          sources.clear();
          for(TableItem item : srcList.getItems())
          {
            sources.add((SWFWorkload) item.getData());
          }
        }
        checkPageComplete();
      }
    });
    delButton.setEnabled(false);

    srcList.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        if(srcList.getSelectionIndex() != -1)
        {
          delButton.setEnabled(true);
        }
        else
        {
          delButton.setEnabled(false);
        }

        checkPageComplete();
      }
    });

    checkPageComplete();
  }

  protected void addSource(File item)
  {
    try
    {
      SWFWorkload wl = SWFWorkload.load(item, null);
      TableItem ti = new TableItem(srcList, SWT.NONE);
      ti.setData(item);
      ti.setText(new String[] { item.getName(), "" + wl.getTasks().size(),
          "" + wl.getLength() });
      sources.add(wl);
    }
    catch(Exception e)
    {
      GridmeUIPlugin.logErrorMessage("Unable to parse workload " + item.getAbsolutePath(), e);
    }
  }

  @Override
  public void checkPageComplete()
  {
    wpage.setPageComplete(checkParams());
  }

  private boolean checkParams()
  {
    if(srcList.getItemCount() < 2)
    {
      wpage.setErrorMessage("Add at least 2 workload files");
      return false;
    }
    wpage.setErrorMessage(null);
    return true;
  }

  @Override
  protected List<SWFWorkload> getSources()
  {
    return sources;
  }

  /**
   * @param project may be null
   * @return the list of available workloads in the project
   */
  private List<IFile> getAvailableWorkloads()
  {
    ArrayList<IFile> result = new ArrayList<IFile>();
    IResource root = null;

    if(container != null
        && (root = ResourcesPlugin.getWorkspace().getRoot().findMember(
            container)) != null && root instanceof IContainer)
    {
      try
      {
        for(IResource file : ((IContainer)root).members())
        {
          if(file instanceof IFile
              && ((IFile) file).getFileExtension().equals("swfz"))
          {
            result.add((IFile) file);
          }
        }
      }
      catch(CoreException e)
      {
        GridmeUIPlugin.logErrorMessage(
            "Unable to get a list of available workloads", e);
      }
    }

    return result;
  }
}
