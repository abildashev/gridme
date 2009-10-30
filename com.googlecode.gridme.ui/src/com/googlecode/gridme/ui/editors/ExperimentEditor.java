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

import gexperiment.Chart;
import gexperiment.Experiment;
import gexperiment.ParameterValue;
import gexperiment.Run;
import gexperiment.RunMode;
import gexperiment.RunResult;
import gexperiment.Visualizer;
import gexperiment.impl.GexperimentFactoryImpl;
import gmodel.GenericModelElement;
import gmodel.Link;
import gmodel.Model;
import gmodel.ModelElement;
import gmodel.RParameter;
import gmodel.impl.GmodelFactoryImpl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import org.apache.tools.ant.taskdefs.Sleep;
import org.aspencloud.widgets.ACW;
import org.aspencloud.widgets.cdatepicker.CDatepickerCombo;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.internal.ui.dialogs.StatusInfo;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.googlecode.gridme.runtime.GConnection;
import com.googlecode.gridme.runtime.GElement;
import com.googlecode.gridme.runtime.GridRuntimeModel;
import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.exceptions.GExecException;
import com.googlecode.gridme.runtime.exceptions.GRuntimeException;
import com.googlecode.gridme.runtime.log.GanttLogger;
import com.googlecode.gridme.runtime.log.LogManifest;
import com.googlecode.gridme.runtime.log.MetricsLogger;
import com.googlecode.gridme.runtime.log.impl.FastGanttLogger;
import com.googlecode.gridme.runtime.log.impl.FastLogger;
import com.googlecode.gridme.runtime.log.impl.FileBasedLogAnalyser;
import com.googlecode.gridme.runtime.visual.LogEntry;
import com.googlecode.gridme.runtime.visual.VChart;
import com.googlecode.gridme.runtime.visual.impl.VisualizerBarCharts;
import com.googlecode.gridme.runtime.visual.impl.VisualizerLineCharts;
import com.googlecode.gridme.simstate.ActiveContainer;
import com.googlecode.gridme.simstate.ActiveElement;
import com.googlecode.gridme.simstate.ModelErrorLogger;
import com.googlecode.gridme.simstate.ModelProfileLogger;
import com.googlecode.gridme.simstate.ModelProgressMonitor;
import com.googlecode.gridme.simstate.RuntimeModel;
import com.googlecode.gridme.simstate.loggers.XMLProfileLogger;
import com.googlecode.gridme.ui.CleanTimeZones;
import com.googlecode.gridme.ui.GridmePreferencePage;
import com.googlecode.gridme.ui.GridmeUIPlugin;
import com.googlecode.gridme.ui.ImplScanner;
import com.googlecode.gridme.ui.PlatformUtils;
import com.googlecode.gridme.ui.UIErrorLogger;
import com.googlecode.gridme.ui.UIProgressMonitor;

public class ExperimentEditor extends MultiPageEditorPart implements
    ModifyListener, SelectionListener
{
  class DelInputAction extends Action
  {
    @Override
    public String getText()
    {
      return "Delete";
    }

    @Override
    public void run()
    {
      if(visualizerInputList.getSelectionCount() > 0)
      {
        for(TableItem item : visualizerInputList.getSelection())
        {
          RunResult target = (RunResult) item.getData();
          int i = 0;
          for(RunResult input : selectedVisualizer.getInput())
          {
            if(input.equals(target))
            {
              selectedVisualizer.getInput().remove(i);
              break;
            }
            i++;
          }
          visualizerInputList.remove(visualizerInputList.indexOf(item));
        }
        pageModified();
      }
    }
  }

  class AddInputAction extends Action
  {
    @Override
    public String getText()
    {
      return "Add";
    }

    @Override
    public void run()
    {
      ElementListSelectionDialog dlg = new ElementListSelectionDialog(
          visualizerInputList.getShell(), new LabelProvider());
      dlg.setFilter("*");
      dlg.setIgnoreCase(true);
      dlg.setElements(getAvailableInputs().toArray());
      dlg.setMultipleSelection(true);

      if(dlg.open() == Window.OK && dlg.getResult() != null)
      {
        for(Object item : dlg.getResult())
        {
          RunResult input = GexperimentFactoryImpl.eINSTANCE.createRunResult();
          input.setName((String) item);
          selectedVisualizer.getInput().add(input);
          addVisualizerInput(input);
        }
        pageModified();
      }
    }
  }

  class AddChartAction extends Action
  {
    @Override
    public String getText()
    {
      return "Add";
    }

    @Override
    public void run()
    {
      String element = null;

      ElementListSelectionDialog dlg = new ElementListSelectionDialog(
          visualizerElementsList.getShell(), new LabelProvider());
      dlg.setFilter("*");
      dlg.setIgnoreCase(true);
      dlg.setElements(getAvailableElements().toArray());
      dlg.setValidator(new ISelectionStatusValidator()
      {
        @Override
        public IStatus validate(Object[] selection)
        {
          return new StatusInfo(IStatus.INFO,
              getMetricsDescription((String) selection[0]));
        }
      });

      if(dlg.open() == Window.OK && dlg.getFirstResult() != null)
      {
        element = (String) dlg.getFirstResult();
        dlg.setElements(getAvailableMetrics(element).toArray());
        if(dlg.open() == Window.OK && dlg.getFirstResult() != null)
        {
          Chart chart = GexperimentFactoryImpl.eINSTANCE.createChart();
          chart.setElement(element);
          chart.setMetric((String) dlg.getFirstResult());
          addVisualizerChart(chart);
          pageModified();
        }
      }
    }
  }

  class DelChartAction extends Action
  {
    @Override
    public String getText()
    {
      return "Delete";
    }

    @Override
    public void run()
    {
      if(visualizerElementsList.getSelectionCount() > 0)
      {
        for(TableItem item : visualizerElementsList.getSelection())
        {
          Chart target = (Chart) item.getData();
          int i = 0;
          for(Chart chart : selectedVisualizer.getCharts())
          {
            if(chart == target)
            {
              selectedVisualizer.getCharts().remove(i);
              pageModified();
              break;
            }
            i++;
          }
          visualizerElementsList.remove(visualizerElementsList.indexOf(item));
        }
      }
    }
  }

  class ExecutionScenarioWizard extends Wizard
  {
    protected ExecutionScenarioWizardPage mainPage;
    protected Run run;
    private boolean allProperties = true;

    public ExecutionScenarioWizard()
    {
    }

    public ExecutionScenarioWizard(Run run, boolean full)
    {
      this.run = run;
      this.allProperties = full;
    }

    @Override
    public void addPages()
    {
      super.addPages();

      setWindowTitle("Create experiment execution scenario");
      mainPage = new ExecutionScenarioWizardPage("mainPage", allProperties, run);
      mainPage.setTitle("New execution scenario");
      addPage(mainPage);
    }

    public Run getRun()
    {
      return run;
    }

    @Override
    public boolean performFinish()
    {
      if(run == null)
      {
        run = GexperimentFactoryImpl.eINSTANCE.createRun();
      }

      if(allProperties)
      {
        run.setName(mainPage.getRunName());
        run.setDescription(mainPage.getRunDescription());
      }

      run.setLength(mainPage.getRunLength());
      Calendar start = Calendar.getInstance();
      start.setTime(mainPage.getRunStart());
      start.setTimeZone(TimeZone.getTimeZone(mainPage.getRunTimezone()));
      run.setStartRealTime("" + start.getTimeInMillis());
      run.setTimezone(mainPage.getRunTimezone());

      return true;
    }
  }

  class ExecutionScenarioWizardPage extends WizardPage
  {
    private Text description;
    private boolean isPageComplete;
    private Text length;
    private ControlDecoration lengthDeco;
    private Text name;
    private ControlDecoration nameDeco;
    private Run run;
    private CDatepickerCombo startTime;
    private Combo timezone;
    private HashSet<String> existingNames;
    private boolean full;

    protected ExecutionScenarioWizardPage(String pageName, boolean full, Run run)
    {
      super(pageName);
      setPageComplete(false);
      this.run = run;
      existingNames = new HashSet<String>();
      for(Run rn : modelRoot.getRuns())
      {
        if(rn != run)
        {
          existingNames.add(rn.getName());
        }
      }
      this.full = full;
    }

    private boolean validateInternal()
    {
      if(name.getText().isEmpty())
      {
        setErrorMessage("Enter a valid name");
        nameDeco.show();
        return false;
      }
      if(existingNames.contains(name.getText()))
      {
        setErrorMessage("Run scenario with such " + "name already exists");
        nameDeco.show();
        return false;
      }
      nameDeco.hide();
      try
      {
        RuntimeUtils.parseTimeText(length.getText());
      }
      catch(NumberFormatException err)
      {
        setErrorMessage("Run length must be positive. "
            + "Format is: 'days hours minutes seconds'. For example '1d 10h 23m 9s'. "
            + "You can enter hours, minutes and seconds in any combination. For example "
            + "'10h 5s' '11m' '5h 10m' are all valid. \n");
        lengthDeco.show();
        return false;
      }
      lengthDeco.hide();
      return true;
    }

    private void validatePage()
    {
      setErrorMessage(null);
      isPageComplete = validateInternal();
      setPageComplete(isPageComplete);
    }

    String getRunName()
    {
      return name.getText();
    }

    @Override
    public void createControl(Composite parent)
    {
      Composite panel = new Composite(parent, SWT.NONE);
      setControl(panel);
      panel.setLayout(new GridLayout(2, true));

      ModifyListener checker = new ModifyListener()
      {
        public void modifyText(ModifyEvent e)
        {
          validatePage();
        }
      };

      Label lab = new Label(panel, SWT.WRAP);
      GridData dta = new GridData(GridData.FILL_HORIZONTAL);
      lab.setLayoutData(dta);
      lab.setText("Name:");
      name = new Text(panel, SWT.BORDER);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      nameDeco = createErrorDecorator(name, "Enter a new value");
      name.setLayoutData(dta);
      name.addModifyListener(checker);

      lab = new Label(panel, SWT.WRAP);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      lab.setLayoutData(dta);
      lab.setText("Description:");
      description = new Text(panel, SWT.BORDER | SWT.WRAP | SWT.MULTI
          | SWT.H_SCROLL | SWT.V_SCROLL);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      dta.heightHint = 120;
      description.setLayoutData(dta);

      lab = new Label(panel, SWT.WRAP);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      lab.setLayoutData(dta);
      lab.setText("Length:");
      length = new Text(panel, SWT.BORDER);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      lengthDeco = createErrorDecorator(length, "Enter a new value");
      length.setLayoutData(dta);
      length.addModifyListener(checker);

      // Use http://calypsorcp.sourceforge.net/widgets.php
      lab = new Label(panel, SWT.WRAP);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      lab.setLayoutData(dta);
      lab.setText("Start date:");

      // An exception occurs when using DROP_DOWN style
      // at CButton.java line 122 - fillColor is disposed...

      if(run != null)
      {
        name.setText(run.getName());
        description.setText(run.getDescription());
        length.setText(run.getLength());
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(Long.parseLong(run.getStartRealTime()));
        startDate.setTimeZone(TimeZone.getTimeZone(run.getTimezone()));
        startTime = new CDatepickerCombo(panel, ACW.NONE, startDate.getTime());
      }
      else
      {
        startTime = new CDatepickerCombo(panel, ACW.NONE);
      }
      startTime.setFormat(ACW.DATE_LONG | ACW.TIME_MEDIUM);
      //startTime.getCDatepicker().setGridVisible(true);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      startTime.setLayoutData(dta);

      lab = new Label(panel, SWT.WRAP);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      lab.setLayoutData(dta);
      lab.setText("Timezone:");
      timezone = new Combo(panel, SWT.DROP_DOWN | SWT.READ_ONLY);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      timezone.setLayoutData(dta);

      String[] ids = CleanTimeZones.get();
      int curZoneIndex;
      String selectedZoneId = null;
      if(run != null)
      {
        selectedZoneId = run.getTimezone();
      }
      else
      {
        selectedZoneId = TimeZone.getDefault().getID();
      }
      for(curZoneIndex = 0; curZoneIndex < ids.length; curZoneIndex++)
      {
        if(selectedZoneId.equals(ids[curZoneIndex]))
        {
          break;
        }
      }
      timezone.setItems(ids);
      timezone.select(curZoneIndex);

      if(!full)
      {
        name.setEnabled(false);
        description.setEnabled(false);
      }
      
      validatePage();
    }

    public String getRunDescription()
    {
      return description.getText();
    }

    public String getRunLength()
    {
      return length.getText();
    }

    public Date getRunStart()
    {
      return startTime.getSelection();
    }

    public String getRunTimezone()
    {
      return timezone.getItem(timezone.getSelectionIndex());
    }

    @Override
    public boolean isPageComplete()
    {
      return isPageComplete;
    }
  }

  class VisualizerWizard extends Wizard
  {
    protected VisualizerWizardPage mainPage;
    protected Visualizer visual;
    private boolean allProperties = true;

    public VisualizerWizard(Visualizer visual, boolean full)
    {
      this.visual = visual;
      this.allProperties = full;
    }

    public VisualizerWizard()
    {
    }

    @Override
    public void addPages()
    {
      super.addPages();

      setWindowTitle("Visualizer properties");
      mainPage = new VisualizerWizardPage("mainPage", allProperties, visual);
      mainPage.setTitle("New visualizer");
      addPage(mainPage);
    }

    public Visualizer getVisualizer()
    {
      return visual;
    }

    @Override
    public boolean performFinish()
    {
      if(visual == null)
      {
        visual = GexperimentFactoryImpl.eINSTANCE.createVisualizer();
      }

      if(allProperties)
      {
        visual.setName(mainPage.getVisualName());
        visual.setImplementation(mainPage.getVisualImplementation());
        visual.getParameters().clear();
        visual.getParameters().addAll(mainPage.getParameters());
      }

      visual.setStartTime(mainPage.getStartTime());
      visual.setStopTime(mainPage.getStopTime());

      return true;
    }
  }

  class VisualizerWizardPage extends WizardPage
  {
    class VisualizerParamInfo
    {
      private String name;
      private String description;
      private String value;

      public VisualizerParamInfo(String name, String description)
      {
        this(name, description, null);
      }

      public VisualizerParamInfo(String name, String description, String value)
      {
        this.name = name;
        this.description = description;
        this.value = value;
      }

      public String getName()
      {
        return name;
      }

      public String getDescription()
      {
        return description;
      }

      public String toString()
      {
        return getName();
      }

      public void setValue(String value)
      {
        this.value = value;
      }

      public String getValue()
      {
        return value;
      }

      @Override
      public boolean equals(Object obj)
      {
        return ((VisualizerParamInfo) obj).getName().equals(getName());
      }
    }

    class AddVparAction extends Action
    {
      @Override
      public String getText()
      {
        return "Add";
      }

      @Override
      public void run()
      {
        ElementListSelectionDialog dlg = new ElementListSelectionDialog(
            getShell(), new LabelProvider());

        dlg.setFilter("*");
        dlg.setIgnoreCase(true);
        dlg.setElements(getAvailableParams().toArray());
        dlg.setValidator(new ISelectionStatusValidator()
        {
          @Override
          public IStatus validate(Object[] selection)
          {
            return new StatusInfo(IStatus.INFO,
                ((VisualizerParamInfo) selection[0]).getDescription());
          }
        });

        if(dlg.open() == Window.OK && dlg.getFirstResult() != null)
        {
          VisualizerParamInfo selParam = (VisualizerParamInfo) dlg
              .getFirstResult();
          addParamIntoTab(selParam);
        }
      }

      private List<VisualizerParamInfo> getAvailableParams()
      {
        List<VisualizerParamInfo> result = new ArrayList<VisualizerParamInfo>();

        for(VisualizerParamInfo info : allParams)
        {
          boolean contains = false;
          for(TableItem t : paramList.getItems())
          {
            if(((VisualizerParamInfo) t.getData()).getName().equals(
                info.getName()))
            {
              contains = true;
              break;
            }
          }

          if(!contains)
            result.add(info);
        }

        return result;
      }
    }

    class DelVparAction extends Action
    {
      @Override
      public String getText()
      {
        return "Delete";
      }

      @Override
      public void run()
      {
        clearParamTabEditor();
        paramList.remove(paramList.getSelectionIndex());
      }
    }

    private boolean full;
    private boolean isPageComplete;
    private Text name;
    private ControlDecoration nameDeco;
    private Combo implementation;
    private ControlDecoration implementationDeco;
    private Text startTime;
    private ControlDecoration startTimeDeco;
    private Text stopTime;
    private ControlDecoration stopTimeDeco;
    private HashSet<String> existingNames;
    private Visualizer visual;
    private Table paramList;
    private TableEditor paramListEditor;
    private List<VisualizerParamInfo> allParams;
    private Label statusMessage;

    protected VisualizerWizardPage(String pageName, Visualizer visual)
    {
      this(pageName, true, visual);
    }

    private void addParamIntoTab(VisualizerParamInfo par)
    {
      TableItem item = new TableItem(paramList, SWT.NONE);
      item.setData(par);
      item.setText(new String[] { par.getName(), par.getValue() });

      if(!isAdmissibleParameter(par))
      {
        item.setForeground(getShell().getDisplay()
            .getSystemColor(SWT.COLOR_RED));
      }
    }

    private boolean isAdmissibleParameter(VisualizerParamInfo par)
    {
      return allParams.contains(par);
    }

    public List<gmodel.Parameter> getParameters()
    {
      List<gmodel.Parameter> result = new ArrayList<gmodel.Parameter>();

      for(TableItem t : paramList.getItems())
      {
        VisualizerParamInfo ptab = (VisualizerParamInfo) t.getData();

        gmodel.Parameter pnode = GmodelFactoryImpl.eINSTANCE.createParameter();
        pnode.setName(ptab.getName());
        pnode.setValue(ptab.getValue());
        result.add(pnode);
      }

      return result;
    }

    private List<VisualizerParamInfo> getAvailableVisualizerParameters()
    {
      List<VisualizerParamInfo> result = new ArrayList<VisualizerParamInfo>();

      try
      {
        //        Class cls = Class.forName(implementation.getText());
        Class cls = ImplScanner.getInstance().getClassLoader().loadClass(
            implementation.getText());

        Method[] mtds = cls.getMethods();
        for(Method method : mtds)
        {
          Parameter param = method.getAnnotation(Parameter.class);
          if(param != null)
          {
            result.add(new VisualizerParamInfo(RuntimeUtils
                .getParameterName(method.getName()), param.description()));
          }
        }
      }
      catch(Exception e)
      {
      }

      return result;
    }

    public String getStopTime()
    {
      return stopTime.getText();
    }

    public String getStartTime()
    {
      return startTime.getText();
    }

    public String getVisualImplementation()
    {
      return implementation.getText();
    }

    public String getVisualName()
    {
      return name.getText();
    }

    protected VisualizerWizardPage(String pageName, boolean full,
        Visualizer visual)
    {
      super(pageName);
      this.full = full;
      isPageComplete = false;
      this.visual = visual;

      existingNames = new HashSet<String>();
      for(Visualizer vs : modelRoot.getVisualizers())
      {
        if(visual != vs)
        {
          existingNames.add(vs.getName());
        }
      }
    }

    @Override
    public void createControl(Composite parent)
    {
      Composite panel = new Composite(parent, SWT.NONE);
      setControl(panel);
      panel.setLayout(new GridLayout(2, true));

      ModifyListener checker = new ModifyListener()
      {
        public void modifyText(ModifyEvent e)
        {
          validatePage();
        }
      };

      Label lab = new Label(panel, SWT.WRAP);
      GridData dta = new GridData(GridData.FILL_HORIZONTAL);
      lab.setLayoutData(dta);
      lab.setText("Name:");
      name = new Text(panel, SWT.BORDER);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      nameDeco = createErrorDecorator(name, "Enter a new value");
      name.setLayoutData(dta);
      name.addModifyListener(checker);

      lab = new Label(panel, SWT.WRAP);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      lab.setLayoutData(dta);
      lab.setText("Implementation:");
      implementation = new Combo(panel, SWT.DROP_DOWN);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      implementationDeco = createErrorDecorator(implementation,
          "Enter a valid implementation");
      implementation.setLayoutData(dta);
      implementation.addModifyListener(new ModifyListener()
      {
        public void modifyText(ModifyEvent e)
        {
          validatePage();
          buildParamList();
        }
      });

      implementation.add(VisualizerLineCharts.class.getCanonicalName());
      implementation.add(VisualizerBarCharts.class.getCanonicalName());

      lab = new Label(panel, SWT.WRAP);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      lab.setLayoutData(dta);
      lab.setText("Start time:");
      startTime = new Text(panel, SWT.BORDER);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      startTimeDeco = createErrorDecorator(startTime, "Enter start time");
      startTime.setLayoutData(dta);
      startTime.addModifyListener(checker);

      lab = new Label(panel, SWT.WRAP);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      lab.setLayoutData(dta);
      lab.setText("Stop time:");
      stopTime = new Text(panel, SWT.BORDER);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      stopTimeDeco = createErrorDecorator(stopTime, "Enter stop time");
      stopTime.setLayoutData(dta);
      stopTime.addModifyListener(checker);

      paramList = new Table(panel, SWT.BORDER | SWT.MULTI);
      paramList.setLinesVisible(true);
      paramList.setHeaderVisible(true);
      dta = new GridData(GridData.FILL_BOTH);
      dta.horizontalSpan = 2;
      dta.heightHint = 200;
      paramList.setLayoutData(dta);

      statusMessage = new Label(panel, SWT.WRAP);
      dta = new GridData(GridData.FILL_HORIZONTAL);
      dta.horizontalSpan = 2;
      statusMessage.setLayoutData(dta);
      statusMessage.setText("");

      TableColumn pName = new TableColumn(paramList, SWT.NONE);
      pName.setWidth(200);
      pName.setText("Parameter name");
      TableColumn pValue = new TableColumn(paramList, SWT.NONE);
      pValue.setWidth(200);
      pValue.setText("Value");

      paramListEditor = new TableEditor(paramList);
      //The editor must have the same size as the cell and must
      //not be any smaller than 50 pixels.
      paramListEditor.horizontalAlignment = SWT.LEFT;
      paramListEditor.grabHorizontal = true;
      paramListEditor.minimumWidth = 50;
      // editing the second column
      final int EDITABLECOLUMN = 1;

      paramList.addSelectionListener(new SelectionAdapter()
      {
        public void widgetSelected(SelectionEvent e)
        {
          // Clean up any previous editor control
          clearParamTabEditor();

          // Identify the selected row
          TableItem item = (TableItem) e.item;
          if(item == null)
            return;

          statusMessage.setText((((VisualizerParamInfo) item.getData())
              .getDescription()));

          // The control that will be the editor must be a child of the Table
          Text newEditor = new Text(paramList, SWT.NONE);
          newEditor.setText(item.getText(EDITABLECOLUMN));
          newEditor.addModifyListener(new ModifyListener()
          {
            public void modifyText(ModifyEvent me)
            {
              Text text = (Text) paramListEditor.getEditor();
              String value = text.getText();
              paramListEditor.getItem().setText(EDITABLECOLUMN, value);
              ((VisualizerParamInfo) paramListEditor.getItem().getData())
                  .setValue(value);
            }
          });
          newEditor.selectAll();
          newEditor.setFocus();
          paramListEditor.setEditor(newEditor, item, EDITABLECOLUMN);
        }
      });

      MenuManager popupMenu = new MenuManager();
      popupMenu.setRemoveAllWhenShown(true);
      popupMenu.addMenuListener(new IMenuListener()
      {
        @Override
        public void menuAboutToShow(IMenuManager manager)
        {
          manager.add(new AddVparAction());
          Action del = new DelVparAction();
          manager.add(del);

          if(paramList.getSelectionIndex() == -1)
          {
            del.setEnabled(false);
          }
        }
      });
      paramList.setMenu(popupMenu.createContextMenu(paramList));

      if(!full)
      {
        name.setEnabled(false);
        implementation.setEnabled(false);
        paramList.setEnabled(false);
      }

      if(visual != null)
      {
        name.setText(visual.getName());
        implementation.setText(visual.getImplementation());
        startTime.setText(visual.getStartTime());
        stopTime.setText(visual.getStopTime());
        buildParamList();
      }
      
      validatePage();
    }

    private void buildParamList()
    {
      clearParamTabEditor();
      paramList.removeAll();

      allParams = getAvailableVisualizerParameters();

      if(visual != null)
      {
        for(gmodel.Parameter p : visual.getParameters())
        {
          addParamIntoTab(new VisualizerParamInfo(p.getName(), "", p.getValue()));
        }
      }
    }

    private void validatePage()
    {
      setErrorMessage(null);
      isPageComplete = validateInternal();
      setPageComplete(isPageComplete);
    }

    private boolean validateInternal()
    {
      if(name.getText().isEmpty())
      {
        setErrorMessage("Enter a valid name");
        nameDeco.show();
        return false;
      }
      if(existingNames.contains(name.getText()))
      {
        setErrorMessage("Visualizer with such name already exists");
        nameDeco.show();
        return false;
      }
      nameDeco.hide();

      try
      {
        //Class.forName(implementation.getText());
        ImplScanner.getInstance().getClassLoader().loadClass(
            implementation.getText());
      }
      catch(Exception e)
      {
        implementationDeco.show();
        setErrorMessage("Can not find implementation class");
        return false;
      }
      implementationDeco.hide();

      long startTimeLong = 0;
      try
      {
        startTimeLong = RuntimeUtils.parseTimeText(startTime.getText());
      }
      catch(NumberFormatException err)
      {
        setErrorMessage("Start time must be positive. "
            + "Format is: 'days hours minutes seconds'. For example '1d 10h 23m 9s'.");
        startTimeDeco.show();
        return false;
      }
      startTimeDeco.hide();
      try
      {
        long stopTimeLong = RuntimeUtils.parseTimeText(stopTime.getText());
        if(stopTimeLong <= startTimeLong)
        {
          throw new NumberFormatException();
        }
      }
      catch(NumberFormatException err)
      {
        setErrorMessage("Stop time must be greater than start time. "
            + "Format is: 'days hours minutes seconds'. For example '1d 10h 23m 9s'.");
        stopTimeDeco.show();
        return false;
      }
      stopTimeDeco.hide();

      return true;
    }

    @Override
    public boolean isPageComplete()
    {
      return isPageComplete;
    }

    private void clearParamTabEditor()
    {
      Control oldEditor = paramListEditor.getEditor();
      if(oldEditor != null)
        oldEditor.dispose();
      statusMessage.setText("");
    }
  }

  private Button debugMode;
  private Text descr;

  private ParameterTreeTable experimentParameters;
  private boolean expHasUserDefinedParams;
  private Text gmodel;
  private boolean isPageModified;
  private ControlDecoration modelDeco;
  private Experiment modelRoot;
  private IFile modelRootFile;
  private Table runList;
  private ParameterTreeTable runParameters;
  private Run selectedRun;
  private Table visualizersList;
  private Table visualizerElementsList;
  private Table visualizerInputList;
  private Visualizer selectedVisualizer;

  public ExperimentEditor()
  {
  }

  private List<String> getAvailableInputs()
  {
    ArrayList<String> result = new ArrayList<String>();

    for(Run run : modelRoot.getRuns())
    {
      String name = RuntimeUtils.getResultFileName(RuntimeUtils
          .removeExtension(modelRootFile.getName()), run.getName());

      if(findFile(name).exists() && notInInputList(name))
      {
        result.add(name);
      }
    }

    return result;
  }

  private boolean notInInputList(String name)
  {
    for(TableItem existing : visualizerInputList.getItems())
    {
      if(((RunResult) existing.getData()).getName().equals(name))
      {
        return false;
      }
    }
    return true;
  }

  private IFile findFile(String name)
  {
    return modelRootFile.getParent().getFile(new Path(name));
  }

  private void addVisualizerInput(RunResult input)
  {
    TableItem item = new TableItem(visualizerInputList, SWT.NONE);
    item.setData(input);
    item.setText(new String[] { input.getName() });

    if(!findFile(input.getName()).exists())
    {
      item.setForeground(item.getParent().getShell().getDisplay()
          .getSystemColor(SWT.COLOR_RED));
    }
  }

  private Collection<String> getAvailableMetrics(String element)
  {
    HashSet<String> metrics = new HashSet<String>();
    for(RunResult run : selectedVisualizer.getInput())
    {
      try
      {
        File resultsFile = findFile(run.getName()).getLocation().toFile();
        FileBasedLogAnalyser fa = new FileBasedLogAnalyser(resultsFile);
        metrics.addAll(fa.getMetrics(element));
      }
      catch(GRuntimeException e)
      {
        GridmeUIPlugin.logErrorMessage("Unable to get available metrics", e);
      }
    }

    return metrics;
  }

  private String getMetricsDescription(String name)
  {
    String result = null;
    for(RunResult run : selectedVisualizer.getInput())
    {
      if(result != null)
      {
        break;
      }

      try
      {
        File resultsFile = findFile(run.getName()).getLocation().toFile();
        FileBasedLogAnalyser fa = new FileBasedLogAnalyser(resultsFile);
        result = fa.getMetricsDescription(name);
      }
      catch(GRuntimeException e)
      {
        GridmeUIPlugin.logErrorMessage("Unable to get available metrics", e);
      }
    }
    return result;
  }

  private Collection<String> getAvailableElements()
  {
    HashSet<String> elements = new HashSet<String>();
    for(RunResult run : selectedVisualizer.getInput())
    {
      try
      {
        File resultsFile = findFile(run.getName()).getLocation().toFile();
        FileBasedLogAnalyser fa = new FileBasedLogAnalyser(resultsFile);
        elements.addAll(fa.getElements());
      }
      catch(GRuntimeException e)
      {
        GridmeUIPlugin.logErrorMessage("Unable to get available elements", e);
      }
    }
    return elements;
  }

  private ControlDecoration createErrorDecorator(Control field, String message)
  {
    ControlDecoration deco = new ControlDecoration(field, SWT.LEFT);
    deco.setImage(FieldDecorationRegistry.getDefault().getFieldDecoration(
        FieldDecorationRegistry.DEC_ERROR).getImage());
    deco.setDescriptionText(message);
    deco.hide();
    return deco;
  }

  private void addRun(Run run)
  {
    addRun(run, -1);
  }

  private void addRun(Run run, int index)
  {
    TableItem item = null;
    if(index >= 0)
    {
      item = new TableItem(runList, SWT.NONE, index);
    }
    else
    {
      item = new TableItem(runList, SWT.NONE);
    }
    item.setData(run);
    setTableItemRun(item, run);
  }

  private void setTableItemRun(TableItem item, Run run)
  {
    Calendar c = Calendar.getInstance();
    c.setTimeZone(TimeZone.getTimeZone(run.getTimezone()));
    c.setTimeInMillis(Long.parseLong(run.getStartRealTime()));
    item.setText(new String[] { run.getName(),
        DateFormat.getDateTimeInstance().format(c.getTime()), run.getLength(),
        RuntimeUtils.cutString(run.getDescription(), 50) });

    String name = RuntimeUtils.getResultFileName(RuntimeUtils
        .removeExtension(modelRootFile.getName()), run.getName());

    if(!findFile(name).exists())
    {
      item.setForeground(item.getParent().getShell().getDisplay()
          .getSystemColor(SWT.COLOR_RED));
    }
  }

  private void buildRunList(Table runList)
  {
    for(Run run : modelRoot.getRuns())
    {
      addRun(run);
    }
  }

  private void clearPage()
  {
    try
    {
      modelRootFile.deleteMarkers(IMarker.PROBLEM, true,
          IResource.DEPTH_INFINITE);
    }
    catch(CoreException markerErr)
    {
      GridmeUIPlugin.logException(markerErr);
    }
  }

  private com.googlecode.gridme.runtime.ModelElement createElementInstance(
      ModelElement elem) throws GRuntimeException
  {
    Class[] argsClass = new Class[] { String.class };
    Object[] args = new Object[] { elem.getName() };
    com.googlecode.gridme.runtime.ModelElement result;
    try
    {
      //      Constructor cons = Class.forName(elem.getImplementation())
      //          .getConstructor(argsClass);

      Constructor cons = ImplScanner.getInstance().getClassLoader().loadClass(
          elem.getImplementation()).getConstructor(argsClass);

      result = (com.googlecode.gridme.runtime.ModelElement) cons
          .newInstance(args);
    }
    catch(Exception e)
    {
      throw new GRuntimeException("Unable to load implementation class: "
          + elem.getImplementation());
    }
    return result;
  }

  private void createNewRun()
  {
    ExecutionScenarioWizard wizard = new ExecutionScenarioWizard();
    WizardDialog dialog = new WizardDialog(runList.getShell(), wizard);

    if(dialog.open() == Window.OK)
    {
      Run run = wizard.getRun();
      modelRoot.getRuns().add(run);
      addRun(run);
      pageModified();
    }
  }

  private void createPropertiesPage()
  {
    final Composite page = new Composite(getContainer(), SWT.NONE);
    page.setLayout(new GridLayout(4, false));
    GridData dta = null;

    new Label(page, SWT.NONE).setText("Description:");

    // Description
    descr = new Text(page, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.H_SCROLL
        | SWT.V_SCROLL);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 3;
    dta.heightHint = 150;

    descr.setLayoutData(dta);
    descr.setText(modelRoot.getDescription() != null ? modelRoot
        .getDescription() : "");
    descr.addModifyListener(this);

    // Run mode
    debugMode = new Button(page, SWT.CHECK);
    debugMode.setText("Run in debug mode");
    dta = new GridData();
    dta.horizontalSpan = 4;
    debugMode.setLayoutData(dta);
    if(modelRoot.getMode().equals(RunMode.DEBUG))
    {
      debugMode.setSelection(true);
    }
    debugMode.addSelectionListener(this);

    //Model
    new Label(page, SWT.NONE).setText("Grid model:");
    gmodel = new Text(page, SWT.BORDER | SWT.READ_ONLY);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 2;
    gmodel.setLayoutData(dta);
    Button browse = new Button(page, SWT.NONE);
    browse.setText("Select");
    modelDeco = createErrorDecorator(gmodel,
        "Unable to load specified Grid model");
    browse.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        FilteredResourcesSelectionDialog dlg = new FilteredResourcesSelectionDialog(
            page.getShell(), false, ResourcesPlugin.getWorkspace().getRoot(),
            IResource.FILE);
        dlg.setInitialPattern("*.gmm");
        dlg.setTitle("Select model");

        if(dlg.open() == Window.OK && dlg.getResult() != null)
        {
          boolean load = !experimentParameters.isModified()
              && !expHasUserDefinedParams;
          if(!load)
          {
            MessageBox messageBox = new MessageBox(page.getShell(),
                SWT.ICON_WARNING | SWT.YES | SWT.NO);
            messageBox.setText("Warning");
            messageBox.setMessage("Changes to experiment parameters "
                + "will be lost. Continue?");
            if(messageBox.open() == SWT.YES)
            {
              load = true;
            }
          }
          if(load)
          {
            gmodel.setText(((IFile) dlg.getResult()[0]).getFullPath()
                .makeRelative().toString());
            pageModified();
            expHasUserDefinedParams = false;
            experimentParameters.reloadInput(modelRoot.getModel(), modelRoot
                .getParameterValues());
          }
        }
      }
    });
    gmodel.setText(getModelName());

    // Parameters
    dta = new GridData(GridData.FILL_BOTH);
    dta.horizontalSpan = 4;

    experimentParameters = new ParameterTreeTable(page, dta, this, false,
        modelRootFile.getProject());
    experimentParameters.setInput(modelRoot.getModel(), modelRoot
        .getParameterValues());

    page.pack();
    validatePage();

    int index = addPage(page);
    setPageText(index, "Properties");
  }

  private void moveRun(boolean updown)
  {
    int index = runList.getSelectionIndex();
    int last = runList.getItemCount() - 1;
    int newIndex = 0;
    runList.remove(index);

    if(updown)
    {
      newIndex = index == 0 ? last : (index - 1);
    }
    else
    {
      newIndex = index == last ? 0 : (index + 1);
    }

    addRun(selectedRun, newIndex);
    runList.setSelection(newIndex);

    modelRoot.getRuns().remove(index);
    modelRoot.getRuns().add(newIndex, selectedRun);
  }

  private void moveVisual(boolean updown)
  {
    int index = visualizersList.getSelectionIndex();
    int last = visualizersList.getItemCount() - 1;
    int newIndex = 0;
    visualizersList.remove(index);

    if(updown)
    {
      newIndex = index == 0 ? last : (index - 1);
    }
    else
    {
      newIndex = index == last ? 0 : (index + 1);
    }

    addVisualizer(selectedVisualizer, newIndex);
    visualizersList.setSelection(newIndex);

    modelRoot.getVisualizers().remove(index);
    modelRoot.getVisualizers().add(newIndex, selectedVisualizer);
  }

  private void createScenariosPage()
  {
    final Composite page = new Composite(getContainer(), SWT.NONE);
    page.setLayout(new GridLayout(7, false));
    GridData dta = null;

    runList = new Table(page, SWT.BORDER | SWT.MULTI);
    runList.setLinesVisible(true);
    runList.setHeaderVisible(true);
    TableColumn nameCol = new TableColumn(runList, SWT.NONE);
    nameCol.setWidth(160);
    nameCol.setText("Name");
    TableColumn startCol = new TableColumn(runList, SWT.NONE);
    startCol.setWidth(210);
    startCol.setText("Start time");
    TableColumn lengthCol = new TableColumn(runList, SWT.NONE);
    lengthCol.setWidth(160);
    lengthCol.setText("Length");
    TableColumn descrCol = new TableColumn(runList, SWT.NONE);
    descrCol.setWidth(300);
    descrCol.setText("Description");

    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 7;
    dta.heightHint = 300;
    runList.setLayoutData(dta);
    buildRunList(runList);
    runList.addSelectionListener(new SelectionListener()
    {
      @Override
      public void widgetDefaultSelected(SelectionEvent e)
      {
        widgetSelected(e);
      }

      @Override
      public void widgetSelected(SelectionEvent e)
      {
        saveRunParameters();
        selectedRun = getSelectedRun();
        if(selectedRun != null)
        {
          runParameters.reloadInput(modelRoot.getModel(), selectedRun
              .getParameterValues());
          runParameters.setEnabled(true);
          updateRunInTable(selectedRun);
        }
        else
        {
          runParameters.setEnabled(false);
        }
      }
    });

    Button addButton = new Button(page, SWT.NONE);
    addButton.setText("Add");

    addButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        createNewRun();
      }
    });

    Button delButton = new Button(page, SWT.NONE);
    delButton.setText("Delete");

    delButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        if(isRunSelected()
            && PlatformUtils.askYesNo("Confirm delete", "Delete "
                + runList.getSelectionCount() + " scenarios?"))
        {
          for(TableItem item : runList.getSelection())
          {
            removeRun((Run) item.getData());
          }
          runList.remove(runList.getSelectionIndices());
          runParameters.clean();
        }
      }
    });

    Button editButton = new Button(page, SWT.NONE);
    editButton.setText("Edit");

    editButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        modifyRun();
      }
    });

    Button cloneButton = new Button(page, SWT.NONE);
    cloneButton.setText("Clone");

    cloneButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        cloneRun();
      }
    });
    
    Button execButton = new Button(page, SWT.NONE);
    execButton.setText("Execute");

    execButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        saveRunParameters();
        try
        {
          if(isRunSelected())
          {
            if(runList.getSelectionCount() > 1)
            {
              if(PlatformUtils.askYesNo("Confirm launch", "Launch "
                  + runList.getSelectionCount() + " scenarios?"))
              {
                for(TableItem item : runList.getSelection())
                {
                  executeRun((Run) item.getData());
                  updateRunInTable((Run) item.getData());
                }
              }
            }
            else
            {
              executeRun(selectedRun);
              updateRunInTable(selectedRun);
            }
          }
        }
        catch(GRuntimeException e)
        {
          PlatformUtils.showErrorMessage("Execution failed", e
              .getUserMessageString());
          GridmeUIPlugin.logException(e);
        }
      }
    });

    Button upButton = new Button(page, SWT.NONE);
    upButton.setText("Up");

    upButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        if(isRunSelected())
        {
          moveRun(true);
          pageModified();
        }
      }
    });

    Button downButton = new Button(page, SWT.NONE);
    downButton.setText("Down");

    downButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        if(isRunSelected())
        {
          moveRun(false);
          pageModified();
        }
      }
    });

    dta = new GridData(GridData.FILL_BOTH);
    dta.horizontalSpan = 7;

    // Add parameters editor
    runParameters = new ParameterTreeTable(page, dta, this, false,
        modelRootFile.getProject());
    runParameters.setEnabled(false);

    page.pack();
    int index = addPage(page);
    setPageText(index, "Execution Scenarios");
  }

  protected void cloneRun()
  {
    if(isRunSelected())
    {
      Run run = GexperimentFactoryImpl.eINSTANCE.createRun();
      copyRun(run, selectedRun);
      ExecutionScenarioWizard wizard = new ExecutionScenarioWizard(run, true);
      WizardDialog dialog = new WizardDialog(runList.getShell(), wizard);
      if(dialog.open() == Window.OK)
      {
        modelRoot.getRuns().add(run);
        addRun(run);
        pageModified();
      }
    }
  }

  /**
   * Copy all parameters recursively
   */
  private void copyRun(Run run, Run source)
  {
    run.setName("Copy of " + source.getName()); 
    run.setDescription(source.getDescription());
    run.setLength(source.getLength());
    run.setStartRealTime(source.getStartRealTime());
    run.setTimezone(source.getTimezone());
    
    for(ParameterValue par : source.getParameterValues())
    {
      ParameterValue npar = GexperimentFactoryImpl.eINSTANCE.createParameterValue();
      run.getParameterValues().add(npar);
      npar.getElements().addAll(par.getElements());
      copyParameters(npar.getParameters(), par.getParameters());
    }
  }

  private void copyParameters(EList<RParameter> dstList, EList<RParameter> srcList)
  {
    for(RParameter psrc : srcList)
    {
      RParameter pdst = GmodelFactoryImpl.eINSTANCE.createRParameter();
      dstList.add(pdst);
      
      pdst.setName(psrc.getName());
      pdst.setValue(psrc.getValue());
      
      copyParameters(pdst.getParameters(), psrc.getParameters());
    }
  }

  private void executeRun(final Run run) throws GRuntimeException
  {
    if(modelRoot.getModel() == null)
    {
      // Show message
      return;
    }

    File base = new File(modelRootFile.getProject().getLocation()
        .toPortableString());

    String resultsName = RuntimeUtils.removeExtension(modelRootFile.getName())
        + "." + run.getName();

    Calendar startTime = Calendar.getInstance();
    startTime.setTimeZone(TimeZone.getTimeZone(run.getTimezone()));
    startTime.setTimeInMillis(Long.parseLong(run.getStartRealTime()));

    final ModelErrorLogger errors = new UIErrorLogger();
    final FastLogger stats = new FastLogger(new File(base, resultsName
        + ".gstats"), true);
    stats.setManifest(new LogManifest(modelRoot.getDescription(), startTime));

    final FastGanttLogger gantt = new FastGanttLogger(startTime, new File(base,
        resultsName + ".gantt"), true);

    ModelProfileLogger profile = null;
    if(modelRoot.getMode().equals(RunMode.DEBUG))
    {
      try
      {
        profile = new XMLProfileLogger(new File(base, resultsName + ".debug"));
      }
      catch(IOException e)
      {
        throw new GRuntimeException("Unable to create model profile logger", e);
      }
    }

    try
    {
      final GridRuntimeModel model = new GridRuntimeModel(0, RuntimeUtils
          .parseTimeText(run.getLength()), startTime, null, errors, profile, 0,
          false);
      model.addWorkingPath(base);

      PlatformUI.getWorkbench().getProgressService().run(true, true,
          new IRunnableWithProgress()
          {
            public void run(final IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException
            {
              ModelProgressMonitor progress = new UIProgressMonitor(
                  "Running experiment " + run.getName(), monitor);
              model.setProgressMonitor(progress);

              try
              {
                model.setModel(makeModelRoot(stats, gantt, run, model));
                model.runModel();

                while(!model.finished() && !monitor.isCanceled())
                {
                  Thread.sleep(500);
                }

                // Check that model has finished without errors. 
                if(errors.getLastError() != null)
                {
                  throw new GRuntimeException(errors.getLastError());
                }
              }
              catch(GRuntimeException e)
              {
                throw new InvocationTargetException(e);
              }
              finally
              {
                model.stopModel();
              }
            }
          });
    }
    catch(InvocationTargetException e)
    {
      throw new GExecException(e.getTargetException());
    }
    catch(InterruptedException e)
    {
      throw new GExecException(e);
    }
    finally
    {
      if(stats != null)
      {
        stats.close();
      }

      if(gantt != null)
      {
        gantt.close();
      }
    }
  }

  private String getModelName()
  {
    if(modelRoot.getModel() != null && modelRoot.getModel().eResource() != null)
    {
      return modelRoot.getModel().eResource().getURI().toPlatformString(true);
    }
    return "";
  }

  private Method getParamSetMethod(Object melem, gmodel.Parameter param)
  {
    String mname = RuntimeUtils.getParameterMethodName(param.getName());
    Method[] allMethods = melem.getClass().getMethods();

    for(Method method : allMethods)
    {
      if(method.getName().equals(mname)
          && method.getAnnotation(Parameter.class) != null
          && method.getParameterTypes().length == 1)
      {
        return method;
      }
    }
    return null;
  }

  private Run getSelectedRun()
  {
    int i = runList.getSelectionIndex();

    if(i == -1)
    {
      return null;
    }

    return (Run) runList.getItem(i).getData();
  }

  /**
   * Check if run is selected and shows error message.
   * @return
   */
  private boolean isRunSelected()
  {
    if(selectedRun == null || runList.getSelectionIndex() == -1)
    {
      PlatformUtils.showErrorMessage("Select execution scenario");
      return false;
    }
    return true;
  }

  /**
   * Check if visualizer is selected and shows error message.
   * @return
   */
  private boolean isVisualizerSelected()
  {
    if(selectedVisualizer == null)
    {
      PlatformUtils.showErrorMessage("Select visualizer");
      return false;
    }
    return true;
  }

  private ActiveElement makeModelRoot(MetricsLogger stats, GanttLogger gantt,
      Run run, RuntimeModel model) throws GRuntimeException
  {
    ActiveElement root = new ActiveContainer("root", model);
    HashMap<String, com.googlecode.gridme.runtime.ModelElement> elems = new HashMap<String, com.googlecode.gridme.runtime.ModelElement>();

    for(GenericModelElement elem : modelRoot.getModel().getElements())
    {
      if(elem instanceof ModelElement)
      {
        com.googlecode.gridme.runtime.ModelElement melem = createElementInstance((ModelElement) elem);
        root.addChild(melem);
        melem.setMetricsLogger(stats);
        melem.setGanttLogger(gantt);
        setElementParameters(melem, (ModelElement) elem, run);
        elems.put(melem.getId(), melem);
      }
    }

    // Connect elements
    for(GenericModelElement elem : modelRoot.getModel().getElements())
    {
      if(elem instanceof Link)
      {
        GConnection from = (GConnection) elems.get(((Link) elem).getFrom()
            .getName());
        GElement to = (GElement) elems.get(((Link) elem).getTo().getName());

        from.connectElementSendReceive(to);
      }
    }

    return root;
  }

  private void modifyRun()
  {
    saveRunParameters();
    if(isRunSelected())
    {
      ExecutionScenarioWizard wizard = new ExecutionScenarioWizard(selectedRun,
          runList.getSelectionCount() == 1);
      WizardDialog dialog = new WizardDialog(runList.getShell(), wizard);

      if(dialog.open() == Window.OK)
      {
        if(runList.getSelectionCount() > 1)
        {
          for(TableItem item : runList.getSelection())
          {
            Run run = (Run) item.getData();
            run.setLength(selectedRun.getLength());
            run.setStartRealTime(selectedRun.getStartRealTime());
            run.setTimezone(selectedRun.getTimezone());
            updateRunInTable(run);
          }
        }
        else
        {
          updateRunInTable(selectedRun);
        }
        pageModified();
      }
    }
  }

  private void updateRunInTable(Run run)
  {
    TableItem[] items = runList.getItems();
    for(int i = 0; i < items.length; i++)
    {
      if(items[i].getData() == run)
      {
        setTableItemRun(items[i], run);
      }
    }
  }

  private void updateVisualizerInTable(Visualizer visual)
  {
    TableItem[] items = visualizersList.getItems();
    for(int i = 0; i < items.length; i++)
    {
      if(items[i].getData() == visual)
      {
        setTableItemVisual(items[i], visual);
      }
    }
  }

  private void pageModified()
  {
    isPageModified = true;
    validatePage();
    firePropertyChange(IEditorPart.PROP_DIRTY);
  }

  private void removeRun(Run target)
  {
    if(target != null)
    {
      int i = 0;
      for(Run run : modelRoot.getRuns())
      {
        if(run == target)
        {
          modelRoot.getRuns().remove(i);
          pageModified();
          break;
        }
        i++;
      }
    }
  }

  private void saveExperimentParameters()
  {
    modelRoot.getParameterValues().clear();
    experimentParameters.saveParameters(modelRoot.getParameterValues());
  }

  private void saveRunParameters()
  {
    if(selectedRun != null)
    {
      selectedRun.getParameterValues().clear();
      runParameters.saveParameters(selectedRun.getParameterValues());
    }
  }

  /**
   * Recursively calls parameter setter methods for the element 
   * instance. 
   * 
   * @param elem
   * @param param
   */
  private void setChildParameter(Object elem, RParameter param)
      throws GRuntimeException
  {
    // Get argument type info
    Method setParam = getParamSetMethod(elem, param);
    if(setParam == null)
    {
      throw new GRuntimeException("Unknown parameter: " + param.getName()
          + " in " + elem.getClass().getName());
    }

    Object argValue = param.getValue(); // String by default
    if(setParam.getAnnotation(Parameter.class).hasParams())
    {
      try
      {
        //argValue = Class.forName(param.getValue()).newInstance();
        argValue = ImplScanner.getInstance().getClassLoader().loadClass(
            param.getValue()).newInstance();
      }
      catch(Exception e)
      {
        throw new GRuntimeException("Unable to load implementation class: "
            + param.getValue());
      }
      // Set child parameters for the object
      for(RParameter cpar : param.getParameters())
      {
        setChildParameter(argValue, cpar);
      }
    }

    try
    {
      setParam.invoke(elem, argValue);
    }
    catch(Exception e)
    {
      throw new GRuntimeException("Error occured while setting parameter: "
          + param.getName() + ", value=" + argValue);
    }

    //    System.out.println("" + elem + "." + setParam.getName() + "( " + argValue
    //        + " )");
  }

  /**
   * Initializes parameters for the given model element instance.
   * 
   * @param melem Runtime model element instance
   * @param elem EMF model element instance
   * @param run Current run scenario EMF instance
   */
  private void setElementParameters(
      com.googlecode.gridme.runtime.ModelElement melem, ModelElement elem,
      Run run) throws GRuntimeException
  {
    HashMap<String, RParameter> mparams = new HashMap<String, RParameter>();

    // Model parameters
    for(RParameter param : elem.getParameters())
    {
      mparams.put(param.getName(), param);
    }

    // Experiment parameters
    for(ParameterValue param : modelRoot.getParameterValues())
    {
      for(ModelElement e : param.getElements())
      {
        if(e.getName().equals(elem.getName()))
        {
          for(RParameter pc : param.getParameters())
          {
            mparams.put(pc.getName(), pc);
          }
        }
      }
    }

    // Run parameters
    for(ParameterValue param : run.getParameterValues())
    {
      for(ModelElement e : param.getElements())
      {
        if(e.getName().equals(elem.getName()))
        {
          for(RParameter pc : param.getParameters())
          {
            mparams.put(pc.getName(), pc);
          }
        }
      }
    }

    // Check that all required parameters are set
    checkRequiredParameters(melem, elem.getName(), mparams.values());

    for(RParameter param : mparams.values())
    {
      setChildParameter(melem, param);
    }
  }

  /**
   * Recursively checks that all required parameters are 
   * present in the collection.
   * 
   * @param impl object instance
   * @param values collection of parameters to set
   */
  private void checkRequiredParameters(Object impl, String hrname,
      Collection<RParameter> values) throws GRuntimeException
  {
    HashMap<String, RParameter> pset = new HashMap<String, RParameter>();
    for(RParameter par : values)
    {
      pset.put(par.getName(), par);
    }

    Method[] allMethods = impl.getClass().getMethods();

    for(int i = 0; i < allMethods.length; i++)
    {
      Parameter psetMethod = allMethods[i].getAnnotation(Parameter.class);

      if(psetMethod != null)
      {
        // Name of the implementation parameter
        String implParName = RuntimeUtils.getParameterName(allMethods[i]
            .getName());
        // Parameter initialization value that we have
        RParameter parExisting = pset.get(implParName);

        if(psetMethod.required() && parExisting == null)
        {
          throw new GRuntimeException("Required parameter " + implParName
              + " is not set for the " + hrname + " - "
              + impl.getClass().getSimpleName());
        }

        // If we have the value and it has childs - check deeper
        if(psetMethod.hasParams() && parExisting != null)
        {
          Object child;
          try
          {
            //child = Class.forName(parExisting.getValue()).newInstance();
            child = ImplScanner.getInstance().getClassLoader().loadClass(
                parExisting.getValue()).newInstance();
          }
          catch(Exception e)
          {
            throw new GRuntimeException("Unable to load implementation class: "
                + parExisting.getValue(), e);
          }
          checkRequiredParameters(child, implParName, parExisting
              .getParameters());
        }
      }
    }
  }

  private boolean validateModel()
  {
    modelRoot.setModel(null);

    // Load model instance
    try
    {
      modelRoot.setModel((Model) PlatformUtils.loadModelFromFile(new Path(
          gmodel.getText())));
    }
    catch(Exception e)
    {
      return false;
    }
    return true;
  }

  private void validatePage()
  {
    try
    {
      clearPage();

      if(validateModel())
      {
        modelDeco.hide();
      }
      else
      {
        modelDeco.show();
        IMarker marker = modelRootFile.createMarker(IMarker.PROBLEM);
        marker.setAttribute(IMarker.MESSAGE, "Unable to load Grid model");
        marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
      }
    }
    catch(CoreException markerErr)
    {
      GridmeUIPlugin.logException(markerErr);
    }
  }

  @Override
  protected void createPages()
  {
    createPropertiesPage();
    createScenariosPage();
    createVisualizersPage();
  }

  private void createVisualizersPage()
  {
    final Composite page = new Composite(getContainer(), SWT.NONE);
    page.setLayout(new GridLayout(7, false));
    GridData dta = null;

    visualizersList = new Table(page, SWT.BORDER | SWT.MULTI);
    visualizersList.setLinesVisible(true);
    visualizersList.setHeaderVisible(true);
    TableColumn nameCol = new TableColumn(visualizersList, SWT.NONE);
    nameCol.setWidth(160);
    nameCol.setText("Name");
    TableColumn startCol = new TableColumn(visualizersList, SWT.NONE);
    startCol.setWidth(210);
    startCol.setText("Start time");
    TableColumn stopCol = new TableColumn(visualizersList, SWT.NONE);
    stopCol.setWidth(160);
    stopCol.setText("Stop time");
    TableColumn implCol = new TableColumn(visualizersList, SWT.NONE);
    implCol.setWidth(160);
    implCol.setText("Implementation");

    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 7;
    dta.heightHint = 300;
    visualizersList.setLayoutData(dta);
    buildVisList(visualizersList);
    visualizersList.addSelectionListener(new SelectionListener()
    {
      @Override
      public void widgetDefaultSelected(SelectionEvent e)
      {
        widgetSelected(e);
      }

      @Override
      public void widgetSelected(SelectionEvent e)
      {
        saveVisualizerChartsAndInput();
        selectedVisualizer = getSelectedVisualizer();

        if(selectedVisualizer != null)
        {
          fillVisualizerElements();
          fillVisualizerInput();

          visualizerElementsList.setEnabled(true);
          visualizerInputList.setEnabled(true);
        }
        else
        {
          visualizerElementsList.setEnabled(false);
          visualizerInputList.setEnabled(false);
        }
      }
    });

    Button addButton = new Button(page, SWT.NONE);
    addButton.setText("Add");

    addButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        createNewVisualizer();
      }
    });

    Button delButton = new Button(page, SWT.NONE);
    delButton.setText("Delete");

    delButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        if(isVisualizerSelected()
            && PlatformUtils.askYesNo("Confirm delete", "Delete "
                + visualizersList.getSelectionCount() + " visualizers?"))
        {
          for(TableItem item : visualizersList.getSelection())
          {
            removeVisualizer((Visualizer) item.getData());
          }
          visualizersList.remove(visualizersList.getSelectionIndices());
          unsetVisualizerPanels();
        }
      }
    });

    Button editButton = new Button(page, SWT.NONE);
    editButton.setText("Edit");

    editButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        editVisualizerProperties();
      }
    });

    
    Button cloneButton = new Button(page, SWT.NONE);
    cloneButton.setText("Clone");

    cloneButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        cloneVisual();
      }
    });
    
    Button execButton = new Button(page, SWT.NONE);
    execButton.setText("Execute");

    execButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        saveVisualizerChartsAndInput();
        IFile vres = null;
        try
        {
          int eCount = 0;
          if(isVisualizerSelected())
          {
            for(TableItem item : visualizersList.getSelection())
            {
              vres = executeVisualizer((Visualizer) item.getData());
              eCount++;
            }
          }
          // Show results
          if(eCount == 1)
          {
            Preferences prefs = GridmeUIPlugin.getInstance()
                .getPluginPreferences();
            if(prefs.getBoolean(GridmePreferencePage.SHOW_VISUAL))
            {
              IEditorInput editorInput = new FileEditorInput(vres);
              String editorID = "com.googlecode.gridme.ui.visualizerEditor";
              PlatformUtils.openEditor(editorInput, editorID);
            }
          }
        }
        catch(GRuntimeException e)
        {
          //GridmeUIPlugin.logException(e);
          PlatformUtils.showErrorMessage("Execution of visualizer failed", e
              .getUserMessageString());
        }
      }
    });

    Button upButton = new Button(page, SWT.NONE);
    upButton.setText("Up");

    upButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        if(isVisualizerSelected())
        {
          moveVisual(true);
          pageModified();
        }
      }
    });

    Button downButton = new Button(page, SWT.NONE);
    downButton.setText("Down");

    downButton.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent event)
      {
        if(isVisualizerSelected())
        {
          moveVisual(false);
          pageModified();
        }
      }
    });

    // Add elements and Scenarios tables on one panel
    Composite tpan = new Composite(page, SWT.NONE);
    tpan.setLayout(new FillLayout());

    dta = new GridData(GridData.FILL_BOTH);
    dta.horizontalSpan = 7;
    tpan.setLayoutData(dta);

    visualizerInputList = new Table(tpan, SWT.BORDER | SWT.MULTI);
    visualizerInputList.setLinesVisible(true);
    visualizerInputList.setHeaderVisible(true);
    visualizerInputList.setEnabled(false);
    TableColumn scenarioName = new TableColumn(visualizerInputList, SWT.NONE);
    scenarioName.setWidth(200);
    scenarioName.setText("Run scenario name");

    MenuManager popupMenu = new MenuManager();
    popupMenu.setRemoveAllWhenShown(true);
    popupMenu.addMenuListener(new IMenuListener()
    {
      @Override
      public void menuAboutToShow(IMenuManager manager)
      {
        manager.add(new AddInputAction());
        Action del = new DelInputAction();
        manager.add(del);

        if(visualizerInputList.getSelectionIndex() == -1)
        {
          del.setEnabled(false);
        }
      }
    });
    visualizerInputList.setMenu(popupMenu
        .createContextMenu(visualizerInputList));

    visualizerElementsList = new Table(tpan, SWT.BORDER | SWT.MULTI);
    visualizerElementsList.setLinesVisible(true);
    visualizerElementsList.setHeaderVisible(true);
    visualizerElementsList.setEnabled(false);
    TableColumn elemName = new TableColumn(visualizerElementsList, SWT.NONE);
    elemName.setWidth(100);
    elemName.setText("Element name");
    TableColumn elemValue = new TableColumn(visualizerElementsList, SWT.NONE);
    elemValue.setWidth(100);
    elemValue.setText("Metric name");

    popupMenu = new MenuManager();
    popupMenu.setRemoveAllWhenShown(true);
    popupMenu.addMenuListener(new IMenuListener()
    {
      @Override
      public void menuAboutToShow(IMenuManager manager)
      {
        manager.add(new AddChartAction());
        Action del = new DelChartAction();
        manager.add(del);

        if(visualizerElementsList.getSelectionIndex() == -1)
        {
          del.setEnabled(false);
        }
      }
    });
    visualizerElementsList.setMenu(popupMenu
        .createContextMenu(visualizerElementsList));

    page.pack();
    int index = addPage(page);
    setPageText(index, "Visualizers");
  }

  protected void cloneVisual()
  {
    if(isVisualizerSelected())
    {
      Visualizer visual = GexperimentFactoryImpl.eINSTANCE.createVisualizer();
      copyVisual(visual, selectedVisualizer);

      VisualizerWizard wizard = new VisualizerWizard(visual, true);
      WizardDialog dialog = new WizardDialog(visualizerElementsList.getShell(),
          wizard);

      if(dialog.open() == Window.OK)
      {
        modelRoot.getVisualizers().add(visual);
        addVisualizer(visual);
        pageModified();
      }
    }
  }

  private void copyVisual(Visualizer target, Visualizer source)
  {
    target.setName("Copy of " + source.getName());
    target.setImplementation(source.getImplementation());
    target.setStartTime(source.getStartTime());
    target.setStopTime(source.getStopTime());
    
    for(gmodel.Parameter par : source.getParameters())
    {
      gmodel.Parameter newPar = GmodelFactoryImpl.eINSTANCE.createParameter();
      target.getParameters().add(newPar);
      newPar.setName(par.getName());
      newPar.setValue(par.getValue());
    }
    
    for(Chart chart : source.getCharts())
    {
      Chart newChart = GexperimentFactoryImpl.eINSTANCE.createChart();
      target.getCharts().add(newChart);
      newChart.setElement(chart.getElement());
      newChart.setMetric(chart.getMetric());
    }
    
    for(RunResult input : source.getInput())
    {
      RunResult newInput = GexperimentFactoryImpl.eINSTANCE.createRunResult();
      target.getInput().add(newInput);
      newInput.setName(input.getName());
    }
  }

  protected void editVisualizerProperties()
  {
    if(isVisualizerSelected())
    {
      VisualizerWizard wizard = new VisualizerWizard(selectedVisualizer,
          visualizersList.getSelectionCount() == 1);
      WizardDialog dialog = new WizardDialog(visualizerElementsList.getShell(),
          wizard);

      if(dialog.open() == Window.OK)
      {
        if(visualizersList.getSelectionCount() > 1)
        {
          for(TableItem item : visualizersList.getSelection())
          {
            Visualizer visual = (Visualizer) item.getData();
            visual.setStartTime(selectedVisualizer.getStartTime());
            visual.setStopTime(selectedVisualizer.getStopTime());
            updateVisualizerInTable(visual);
          }
        }
        else
        {
          updateVisualizerInTable(selectedVisualizer);
        }

        pageModified();
      }
    }
  }

  private IFile executeVisualizer(final Visualizer visual)
      throws GRuntimeException
  {
    File base = new File(modelRootFile.getProject().getLocation()
        .toPortableString());

    String vname = RuntimeUtils.removeExtension(modelRootFile.getName()) + "."
        + visual.getName();

    final String vresultsPath = new File(base, vname).getAbsolutePath();

    IFile rf = modelRootFile.getProject().getFile(vname);

    final com.googlecode.gridme.runtime.visual.Visualizer runtimeVisual;
    Class[] argsClass = new Class[] { String.class };
    Object[] args = new Object[] { visual.getName() };
    try
    {
      //      Constructor cons = Class.forName(visual.getImplementation())
      //          .getConstructor(argsClass);
      Constructor cons = ImplScanner.getInstance().getClassLoader().loadClass(
          visual.getImplementation()).getConstructor(argsClass);

      runtimeVisual = (com.googlecode.gridme.runtime.visual.Visualizer) cons
          .newInstance(args);
    }
    catch(Exception e)
    {
      throw new GRuntimeException(
          "Unable to load visualizer implementation class: "
              + visual.getImplementation());
    }

    for(gmodel.Parameter par : visual.getParameters())
    {
      Method setParam = getParamSetMethod(runtimeVisual, par);
      if(setParam == null)
      {
        throw new GRuntimeException("Unknown parameter: " + par.getName()
            + " in " + visual.getImplementation());
      }
      try
      {
        setParam.invoke(runtimeVisual, par.getValue());
      }
      catch(Exception e)
      {
        throw new GRuntimeException("Error occured while setting parameter: "
            + par.getName() + ", value=" + par.getValue());
      }
    }

    final ArrayList<VChart> charts = new ArrayList<VChart>();
    for(Chart chart : visual.getCharts())
    {
      charts.add(new VChart(chart.getElement(), chart.getMetric()));
    }

    final ArrayList<LogEntry> logList = new ArrayList<LogEntry>();
    for(RunResult res : visual.getInput())
    {
      logList.add(new LogEntry(res.getName(), new FileBasedLogAnalyser(
          new File(base, res.getName()))));
    }

    try
    {
      PlatformUI.getWorkbench().getProgressService().run(true, true,
          new IRunnableWithProgress()
          {
            public void run(final IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException
            {
              ModelProgressMonitor progress = new UIProgressMonitor(
                  "Running visualizer " + visual.getName(), monitor);
              long start = RuntimeUtils.parseTimeText(visual.getStartTime());
              long stop = RuntimeUtils.parseTimeText(visual.getStopTime());

              try
              {
                runtimeVisual.execute(vresultsPath, start, stop, charts,
                    logList, progress);
              }
              catch(Exception e)
              {
                throw new InvocationTargetException(e,
                    "Visualizer execution failed: " + e.getMessage());
              }
            }
          });
    }
    catch(InvocationTargetException e)
    {
      throw new GRuntimeException(e.getCause());
    }
    catch(InterruptedException e)
    {
      throw new GRuntimeException("Visualizer execution failed: "
          + e.getMessage());
    }

    return rf;
  }

  private void removeVisualizer(Visualizer target)
  {
    int i = 0;
    for(Visualizer visual : modelRoot.getVisualizers())
    {
      if(visual == target)
      {
        modelRoot.getVisualizers().remove(i);
        pageModified();
        break;
      }
      i++;
    }
  }

  private void unsetVisualizerPanels()
  {
    visualizerElementsList.setEnabled(false);
    visualizerElementsList.removeAll();

    visualizerInputList.setEnabled(false);
    visualizerInputList.removeAll();
  }

  private void fillVisualizerInput()
  {
    visualizerInputList.removeAll();
    if(selectedVisualizer != null)
    {
      for(RunResult result : selectedVisualizer.getInput())
      {
        addVisualizerInput(result);
      }
    }
  }

  private void fillVisualizerElements()
  {
    visualizerElementsList.removeAll();
    if(selectedVisualizer != null)
    {
      for(Chart chart : selectedVisualizer.getCharts())
      {
        addVisualizerChart(chart);
      }
    }
  }

  private void addVisualizerChart(Chart chart)
  {
    TableItem item = new TableItem(visualizerElementsList, SWT.NONE);
    item.setData(chart);
    item.setText(new String[] { chart.getElement(), chart.getMetric() });
  }

  private Visualizer getSelectedVisualizer()
  {
    int i = visualizersList.getSelectionIndex();

    if(i == -1)
    {
      return null;
    }

    return (Visualizer) visualizersList.getItem(i).getData();
  }

  private void createNewVisualizer()
  {
    VisualizerWizard wizard = new VisualizerWizard();
    WizardDialog dialog = new WizardDialog(visualizerElementsList.getShell(),
        wizard);

    if(dialog.open() == Window.OK)
    {
      Visualizer visual = wizard.getVisualizer();
      modelRoot.getVisualizers().add(visual);
      addVisualizer(visual);
      pageModified();
    }
  }

  private void buildVisList(Table vList)
  {
    for(Visualizer visual : modelRoot.getVisualizers())
    {
      addVisualizer(visual);
    }
  }

  private void addVisualizer(Visualizer visual)
  {
    addVisualizer(visual, -1);
  }

  private void addVisualizer(Visualizer visual, int index)
  {
    TableItem item = null;
    if(index >= 0)
    {
      item = new TableItem(visualizersList, SWT.NONE, index);
    }
    else
    {
      item = new TableItem(visualizersList, SWT.NONE);
    }
    item.setData(visual);
    setTableItemVisual(item, visual);
  }

  private void setTableItemVisual(TableItem item, Visualizer visual)
  {
    item.setText(new String[] { visual.getName(), visual.getStartTime(),
        visual.getStopTime(), visual.getImplementation() });
  }

  @Override
  public void doSave(IProgressMonitor monitor)
  {
    if(isPageModified)
    {
      modelRoot.setDescription(descr.getText());
      if(debugMode.getSelection())
      {
        modelRoot.setMode(RunMode.DEBUG);
      }
      else
      {
        modelRoot.setMode(RunMode.RELEASE);
      }

      saveExperimentParameters();
      saveRunParameters();
      saveVisualizerChartsAndInput();

      try
      {
        PlatformUtils.saveModelToFile(modelRootFile.getFullPath(), modelRoot);
      }
      catch(Exception e)
      {
        GridmeUIPlugin.logErrorMessage("Unable to save experiment model", e);
      }
      isPageModified = false;
      firePropertyChange(IEditorPart.PROP_DIRTY);
    }
  }

  private void saveVisualizerChartsAndInput()
  {
    if(selectedVisualizer != null)
    {
      selectedVisualizer.getInput().clear();
      selectedVisualizer.getCharts().clear();

      for(TableItem item : visualizerElementsList.getItems())
      {
        selectedVisualizer.getCharts().add((Chart) item.getData());
      }
      for(TableItem item : visualizerInputList.getItems())
      {
        selectedVisualizer.getInput().add((RunResult) item.getData());
      }
    }
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

    try
    {
      modelRoot = (Experiment) PlatformUtils.loadModelFromFile(modelRootFile
          .getFullPath());
    }
    catch(Exception e)
    {
      GridmeUIPlugin.logErrorMessage("Bad experiment editor input", e);
    }

    isPageModified = false;
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
  public void modifyText(ModifyEvent e)
  {
    pageModified();
  }

  @Override
  public void setFocus()
  {
  }

  @Override
  public void widgetDefaultSelected(SelectionEvent e)
  {
    widgetSelected(e);
  }

  @Override
  public void widgetSelected(SelectionEvent e)
  {
    pageModified();
  }
}
