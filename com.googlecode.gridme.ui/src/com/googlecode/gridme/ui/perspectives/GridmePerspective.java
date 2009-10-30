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
package com.googlecode.gridme.ui.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

import com.googlecode.gridme.ui.view.GridProjectView;


/**
 *  This class is meant to serve as an example for how various contributions 
 *  are made to a perspective. Note that some of the extension point id's are
 *  referred to as API constants while others are hardcoded and may be subject 
 *  to change. 
 */
public class GridmePerspective implements IPerspectiveFactory {

	private IPageLayout factory;

	public GridmePerspective() {
		super();
	}

	public void createInitialLayout(IPageLayout factory) {
		this.factory = factory;
		addViews();
		addActionSets();
		addNewWizardShortcuts();
		addPerspectiveShortcuts();
		addViewShortcuts();
	}

	private void addViews() {
		// Creates the overall folder layout. 
		// Note that each new Folder uses a percentage of the remaining EditorArea.
		
		IFolderLayout bottom =
			factory.createFolder(
				"bottomRight", //NON-NLS-1
				IPageLayout.BOTTOM,
				0.75f,
				factory.getEditorArea());
		
    bottom.addView(IPageLayout.ID_PROP_SHEET);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addView("org.eclipse.pde.runtime.LogView");

		IFolderLayout topLeft =
			factory.createFolder(
				"topLeft", //NON-NLS-1
				IPageLayout.LEFT,
				0.25f,
				factory.getEditorArea());
		
		topLeft.addView("com.googlecode.gridme.ui.view.project");
		topLeft.addView(IPageLayout.ID_RES_NAV);
	}

	private void addActionSets() {
//		factory.addActionSet("org.eclipse.debug.ui.launchActionSet"); //NON-NLS-1
	}

	private void addPerspectiveShortcuts() {
		factory.addPerspectiveShortcut("org.eclipse.ui.resourcePerspective"); //NON-NLS-1
	}

	private void addNewWizardShortcuts() {
	  factory.addNewWizardShortcut("com.googlecode.gridme.ui.ProjectWizard");//NON-NLS-1
	  //factory.addNewWizardShortcut("com.googlecode.gridme.ui.ModelWizard");//NON-NLS-1
	  factory.addNewWizardShortcut("gmodel.diagram.part.GmodelCreationWizardID");//NON-NLS-1
	  factory.addNewWizardShortcut("com.googlecode.gridme.ui.ExperimentWizard");//NON-NLS-1
	  factory.addNewWizardShortcut("com.googlecode.gridme.ui.WorkloadWizard");//NON-NLS-1
	}

	private void addViewShortcuts() {
		factory.addShowViewShortcut("com.googlecode.gridme.ui.view.project"); //NON-NLS-1
		factory.addShowViewShortcut(IPageLayout.ID_RES_NAV);
	}

}
