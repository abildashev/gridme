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

import gmodel.Model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.googlecode.gridme.ui.GridmeUIPlugin;
import com.googlecode.gridme.ui.PlatformUtils;

public class ModelEditor extends EditorPart implements ModifyListener
{
  private ParameterTreeTable modelParameters;
  private Model modelRoot;
  private IFile modelRootFile;
  private boolean isPageModified;

  public ModelEditor()
  {
  }

  @Override
  public void doSave(IProgressMonitor monitor)
  {
    if(isPageModified)
    {
      saveModelParameters();
      try
      {
        PlatformUtils.saveModelToFile(modelRootFile.getFullPath(), modelRoot);
      }
      catch(Exception e)
      {
        GridmeUIPlugin.logErrorMessage("Unable to save grid model", e);
      }
      isPageModified = false;
      firePropertyChange(IEditorPart.PROP_DIRTY);
    }
  }

  private void saveModelParameters()
  {
    modelParameters.saveModel(modelRoot);
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

    modelRootFile = ((FileEditorInput) input).getFile();
    loadModel();
    isPageModified = false;
  }

  private void loadModel()
  {
    try
    {
      modelRoot = (Model) PlatformUtils.loadModelFromFile(modelRootFile
          .getFullPath());
    }
    catch(Exception e)
    {
      GridmeUIPlugin.logErrorMessage("Bad model editor input", e);
    }
  }

  @Override
  public boolean isDirty()
  {
    return isPageModified;
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
    page.setLayout(new GridLayout(1, false));
    GridData dta = new GridData(GridData.FILL_BOTH);

    modelParameters = new ParameterTreeTable(page, dta, this, true, modelRootFile.getProject());
    modelParameters.setInput(modelRoot);

    page.pack();
  }

  @Override
  public void setFocus()
  {
  }

  private void pageModified()
  {
    isPageModified = true;
    firePropertyChange(IEditorPart.PROP_DIRTY);
  }

  @Override
  public void modifyText(ModifyEvent e)
  {
    pageModified();
  }

  public void reload()
  {
    modelParameters.setInput(modelRoot);
  }
}
