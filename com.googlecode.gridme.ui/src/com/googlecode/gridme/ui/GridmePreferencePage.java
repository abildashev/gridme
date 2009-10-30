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
package com.googlecode.gridme.ui;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class GridmePreferencePage extends FieldEditorPreferencePage implements
    IWorkbenchPreferencePage
{
  public static final String CLASSPATH = "gclasspath";
  public static final String SHOW_VISUAL = "vshow";

  private PathEditor path;

  @Override
  protected void createFieldEditors()
  {
    path = new PathEditor(CLASSPATH, "Classpath folder list:",
        "Select folder containing implementation classes",
        getFieldEditorParent());

    addField(path);

    addField(new BooleanFieldEditor(SHOW_VISUAL,
        "Open visualizer result after execution", getFieldEditorParent()));
  }

  @Override
  public void init(IWorkbench workbench)
  {
    setPreferenceStore(GridmeUIPlugin.getInstance().getPreferenceStore());
  }
}
