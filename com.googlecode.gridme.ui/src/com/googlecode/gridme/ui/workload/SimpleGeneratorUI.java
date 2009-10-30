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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.runtime.schedule.workload.SimpleGenerator;

public class SimpleGeneratorUI extends SimpleGenerator implements WorkloadGeneratorUI
{
  private WizardPage page;
  private Text lengthTxt;
  private Text pMinWidthText;
  private Text pMaxWidthText;
  private Text minLengthText;
  private Text maxLengthText;
  private Text rectLenText;
  private Text rectWidthText;
  private Text serialTxt;

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

    lengthTxt = new Text(panel, SWT.BORDER);
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.verticalIndent = 20;
    dta.horizontalSpan = 2;
    lengthTxt.setLayoutData(dta);
    ModifyListener checker = new ModifyListener()
    {
      public void modifyText(ModifyEvent e)
      {
        checkPageComplete();
      }
    };
    lengthTxt.addModifyListener(checker);

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
    lab.setText("Minimum and maximum width of parallel tasks:");
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 1;
    lab.setLayoutData(dta);

    Composite subPan = new Composite(panel, SWT.NONE);
    subPan.setLayout(new FillLayout());
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 2;
    subPan.setLayoutData(dta);

    pMinWidthText = new Text(subPan, SWT.BORDER);
    pMinWidthText.addModifyListener(checker);

    pMaxWidthText = new Text(subPan, SWT.BORDER);
    pMaxWidthText.addModifyListener(checker);

    // Height

    lab = new Label(panel, SWT.WRAP);
    lab.setText("Minimum and maximum real execution time of tasks:");
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 1;
    lab.setLayoutData(dta);

    subPan = new Composite(panel, SWT.NONE);
    subPan.setLayout(new FillLayout());
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 2;
    subPan.setLayoutData(dta);

    minLengthText = new Text(subPan, SWT.BORDER);
    minLengthText.addModifyListener(checker);

    maxLengthText = new Text(subPan, SWT.BORDER);
    maxLengthText.addModifyListener(checker);

    // Total square

    lab = new Label(panel, SWT.WRAP);
    lab.setText("Total square of tasks - length (real execution time) and "
        + "width (number of required processors):");
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 1;
    lab.setLayoutData(dta);

    subPan = new Composite(panel, SWT.NONE);
    subPan.setLayout(new FillLayout());
    dta = new GridData(GridData.FILL_HORIZONTAL);
    dta.horizontalSpan = 2;
    subPan.setLayoutData(dta);

    rectLenText = new Text(subPan, SWT.BORDER);
    rectLenText.addModifyListener(checker);

    rectWidthText = new Text(subPan, SWT.BORDER);
    rectWidthText.addModifyListener(checker);
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
      length = RuntimeUtils.parseTimeText(lengthTxt.getText());
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
      serial = Float.parseFloat(serialTxt.getText());
      if(serial > 1 || serial < 0)
      {
        throw new NumberFormatException();
      }

      if(serial == 1)
      {
        pMinWidthText.setEnabled(false);
        pMaxWidthText.setEnabled(false);
      }
      else
      {
        pMinWidthText.setEnabled(true);
        pMaxWidthText.setEnabled(true);
      }
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Fraction must be a non negative "
          + "float value less or equal 1.");
      return false;
    }
    
    if(serial != 1)
    {
      try
      {
        pMinWidth = Integer.parseInt(pMinWidthText.getText());
        if(pMinWidth <= 1)
        {
          throw new NumberFormatException();
        }
      }
      catch(NumberFormatException err)
      {
        page.setErrorMessage("Minimal width of parallel tasks must be greater then 1.");
        return false;
      }
  
      try
      {
        pMaxWidth = Integer.parseInt(pMaxWidthText.getText());
        if(pMinWidth > pMaxWidth)
        {
          throw new NumberFormatException();
        }
      }
      catch(NumberFormatException err)
      {
        page.setErrorMessage("Maximum width of parallel tasks must be not less then minimum.");
        return false;
      }
    }

    try
    {
      minLength = RuntimeUtils.parseTimeText(minLengthText.getText());
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Minimim real execution time must be positive. " +
          "Format is 'days hours minutes seconds'.");
      return false;
    }

    try
    {
      maxLength = RuntimeUtils.parseTimeText(maxLengthText.getText());

      if(minLength >= maxLength)
      {
        throw new NumberFormatException();
      }
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Maximum real execution time must be greater then minimum. " +
          "Format is 'days hours minutes seconds'.");
      return false;
    }

    try
    {
      rectLen = RuntimeUtils.parseTimeText(rectLenText.getText());
      if(rectLen <= 0)
      {
        throw new NumberFormatException();
      }
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Set positive length for total square of tasks. "
          + "Format is 'days hours minutes seconds'.");
      return false;
    }

    try
    {
      rectWidth = Integer.parseInt(rectWidthText.getText());
    }
    catch(NumberFormatException err)
    {
      page.setErrorMessage("Set positive width for total square of tasks.");
      return false;
    }
    
   page.setErrorMessage(null);
   return true; 
  }

}
