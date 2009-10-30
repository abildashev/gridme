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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.schedule.workload.WaveGenerator;

public class WaveGeneratorUI extends WaveGenerator implements WorkloadGeneratorUI
{
  private WizardPage page;
  private Text pTotalTimeTxt;
  private Text pMaxWidthText;
  private Text serialTxt;
  private Text pDensityText;
  private Text pMaxLengthText;
  private Text pMinLengthText;

  @Override
  public void createControls(Composite panel, WizardPage page)
  {
    this.page = page;
    panel.setLayout(new GridLayout(3, true));

    // Description
    Label lab = new Label(panel, SWT.WRAP);
    GridData dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 3;
    lab.setLayoutData(dta);
    lab.setText(getDescription());

    // Length
    lab = new Label(panel, SWT.WRAP);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 1;
    dta.verticalIndent = 20;
    lab.setLayoutData(dta);
    lab.setText("Total workload length (start time of the last task):");

    pTotalTimeTxt = new Text(panel, SWT.BORDER);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.verticalIndent = 20;
    dta.horizontalSpan = 2;
    pTotalTimeTxt.setLayoutData(dta);
    ModifyListener checker = new ModifyListener()
    {
      public void modifyText(ModifyEvent e)
      {
        checkPageComplete();
      }
    };
    pTotalTimeTxt.addModifyListener(checker);

    // Density
    lab = new Label(panel, SWT.WRAP);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 1;
    lab.setLayoutData(dta);
    lab.setText("Workload density:");

    pDensityText = new Text(panel, SWT.BORDER);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 2;
    pDensityText.setLayoutData(dta);
    pDensityText.addModifyListener(checker);
    
    // Serial tasks fraction
    lab = new Label(panel, SWT.WRAP);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 1;
    lab.setLayoutData(dta);
    lab.setText("Fraction of serial (1-cpu) tasks:");

    serialTxt = new Text(panel, SWT.BORDER);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 2;
    serialTxt.setLayoutData(dta);
    serialTxt.addModifyListener(checker);

    // Width
    lab = new Label(panel, SWT.WRAP);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 1;
    lab.setLayoutData(dta);
    lab.setText("Total cluster width:");

    pMaxWidthText = new Text(panel, SWT.BORDER);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 2;
    pMaxWidthText.setLayoutData(dta);
    pMaxWidthText.addModifyListener(checker);

    // Length
    lab = new Label(panel, SWT.WRAP);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 1;
    lab.setLayoutData(dta);
    lab.setText("Maximum execution time:");

    pMaxLengthText = new Text(panel, SWT.BORDER);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 2;
    pMaxLengthText.setLayoutData(dta);
    pMaxLengthText.addModifyListener(checker);

    // Min Length
    lab = new Label(panel, SWT.WRAP);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 1;
    lab.setLayoutData(dta);
    lab.setText("Minimum execution time:");

    pMinLengthText = new Text(panel, SWT.BORDER);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 2;
    pMinLengthText.setLayoutData(dta);
    pMinLengthText.addModifyListener(checker);
}

  @Override
  public void checkPageComplete()
  {
    page.setPageComplete(checkParams());
  }
  
  private boolean checkParams()
  {
    try
    {
      pTotalTime = RuntimeUtils.parseTimeText(pTotalTimeTxt.getText());
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Workload length must be positive. "
              + "Format is: 'days hours minutes seconds'. For example '1d 10h 23m 9s'. "
              + "You can enter hours, minutes and seconds in any combination. For example "
              + "'10h 5s' '11m' '5h 10m' are all valid. \n");
      return false;
    }

    try
    {
      pDensity = Float.parseFloat(pDensityText.getText());
      if(pDensity < 0)
      {
        throw new NumberFormatException();
      }
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Density must be a non negative "
          + "float value.");
      return false;
    }
    
    
    try
    {
      pNonparalelRate = Float.parseFloat(serialTxt.getText());
      if(pNonparalelRate > 1 || pNonparalelRate < 0)
      {
        throw new NumberFormatException();
      }
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Fraction must be a non negative "
          + "float value less or equal 1.");
      return false;
    }

    try
    {
      pMaxWidth = Integer.parseInt(pMaxWidthText.getText());
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Set positive size of cluster.");
      return false;
    }

    try
    {
      pMaxLength = RuntimeUtils.parseTimeText(pMaxLengthText.getText());
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Set valid maximum execution time. Format is 'days hours minutes seconds'");
      return false;
    }
    
    try
    {
      pMinLength = RuntimeUtils.parseTimeText(pMinLengthText.getText());
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Set valid minimum execution time. Format is 'days hours minutes seconds'");
      return false;
    }
    
   page.setErrorMessage(null);
   return true; 
  }
}
