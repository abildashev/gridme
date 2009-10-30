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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class WorkloadAnalysisDialog extends Dialog
{
  private String width = "";
  private Text widthField;

  public WorkloadAnalysisDialog(Shell parentShell)
  {
    super(parentShell);
  }
  
  public int getWidth()
  {
    int result = 0;
    
    try
    {
      result = Integer.parseInt(width);
    }
    catch(NumberFormatException e)
    {
      // Do nothing, return 0
    }
    
    return result; 
  }

  @Override
  protected Control createDialogArea(Composite parent)
  {
    Composite container = (Composite) super.createDialogArea(parent);
    final GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    container.setLayout(gridLayout);

    final Label nameLabel = new Label(container, SWT.NONE);
    nameLabel.setLayoutData(new GridData(GridData.END,
          GridData.CENTER, false, false));
    nameLabel.setText("Total system width:");

    widthField = new Text(container, SWT.BORDER);
    widthField.setLayoutData(new GridData(GridData.FILL,
          GridData.CENTER, true, false));

    return container;
  }

  @Override
  protected void okPressed()
  {
    width = widthField.getText();
    super.okPressed();
  }
}
