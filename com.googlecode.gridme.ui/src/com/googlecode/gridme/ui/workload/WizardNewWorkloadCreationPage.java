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

import java.util.HashMap;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.schedule.workload.SWFWorkload;
import com.googlecode.gridme.ui.GridmeUIPlugin;
import com.googlecode.gridme.ui.UIProgressMonitor;
import com.googlecode.gridme.ui.wizards.ValidatingPage;

public class WizardNewWorkloadCreationPage extends WizardPage implements
    ValidatingPage
{
  class GeneratorParameters
  {
    private final WorkloadGeneratorUI generator;
    private Composite params;

    public GeneratorParameters(WorkloadGeneratorUI generator)
    {
      this.generator = generator;
    }

    public WorkloadGeneratorUI getGenerator()
    {
      return generator;
    }

    public Composite getParamsPanel()
    {
      return params;
    }

    public void setParamsPanel(Composite params)
    {
      this.params = params;
    }
  }

  private Combo generator;
  private Composite generatorParamsPanel;
  private WorkloadGeneratorUI activeGenerator;

  @SuppressWarnings("unchecked")
  private static final Class[] availableGenerators = new Class[] { SimpleGeneratorUI.class, 
    MergeGeneratorUI.class, WaveGeneratorUI.class};

  private HashMap<Integer, GeneratorParameters> loadedGenerators;

  //  private IFile selectedFile;

  protected WizardNewWorkloadCreationPage(String pageName)
  {
    super(pageName);
    loadedGenerators = new HashMap<Integer, GeneratorParameters>(
        availableGenerators.length);
  }

  @Override
  public void createControl(final Composite parent)
  {
    final Composite page = new Composite(parent, SWT.NONE);
    page.setLayout(new GridLayout(3, false));

    // Generator
    new Label(page, SWT.NONE).setText("Select workload generator:");
    generator = new Combo(page, SWT.DROP_DOWN | SWT.READ_ONLY);
    GridData dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 2;
    generator.setLayoutData(dta);

    // Create generator list
    int index = 0;
    for(Class genClass : availableGenerators)
    {
      try
      {
        WorkloadGeneratorUI gen = (WorkloadGeneratorUI) genClass.newInstance();
        generator.add(gen.getName());
        loadedGenerators.put(index++, new GeneratorParameters(gen));
      }
      catch(Exception e)
      {
        GridmeUIPlugin.logException(e);
      }
    }

    generator.addModifyListener(new ModifyListener()
    {
      public void modifyText(ModifyEvent e)
      {
        showGeneratorParameters();
      }
    });

    // Create stack layout with composites for each generator
    generatorParamsPanel = new Composite(page, SWT.NONE);
    generatorParamsPanel.setLayout(new StackLayout());
    dta = new GridData(GridData.FILL_BOTH);
    dta.horizontalSpan = 3;
    generatorParamsPanel.setLayoutData(dta);

    page.pack();

    // For wizard
    setControl(page);
    setPageComplete(false);
  }

  public boolean generate(IProgressMonitor monitor, IPath fileName)
  {
    try
    {
      SWFWorkload result = activeGenerator.generate(new UIProgressMonitor(
          "Generating workload", monitor));
      result.toFile(fileName.toFile());
    }
    catch(GRuntimeException e)
    {
      GridmeUIPlugin.logException(e);
      return false;
    }

    monitor.done();
    return true;
  }

  private void showGeneratorParameters()
  {
    GeneratorParameters genSel = loadedGenerators.get(generator
        .getSelectionIndex());
    assert (genSel != null);

    // Create new panel for the selected generator
    if(genSel.getParamsPanel() == null)
    {
      Composite paramsPanel = new Composite(generatorParamsPanel, SWT.NONE);
      genSel.setParamsPanel(paramsPanel);
      genSel.getGenerator().createControls(paramsPanel, this);
      paramsPanel.pack();
    }

    // Show this panel
    ((StackLayout) generatorParamsPanel.getLayout()).topControl = genSel
        .getParamsPanel();
    generatorParamsPanel.layout();

    activeGenerator = genSel.getGenerator();
  }

  @Override
  public void checkPageComplete()
  {
    if(checkParams())
    {
      setPageComplete(true);
      setMessage(null);
      activeGenerator.checkPageComplete();
    }
    else
    {
      setPageComplete(false);
    }
  }

  private boolean checkParams()
  {
    return activeGenerator != null;
  }
}
