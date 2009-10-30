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
package gmodel.diagram.part;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
* @generated
*/
public class GmodelCreationWizardPage extends WizardNewFileCreationPage
{

  /**
  * @generated
  */
  private final String fileExtension;

  /**
  * @generated
  */
  public GmodelCreationWizardPage(String pageName,
      IStructuredSelection selection, String fileExtension)
  {
    super(pageName, selection);
    this.fileExtension = fileExtension;
  }

  /**
   * Override to create files with this extension.
   * 
   * @generated
   */
  protected String getExtension()
  {
    return fileExtension;
  }

  /**
  * @generated
  */
  public URI getURI()
  {
    return URI.createPlatformResourceURI(getFilePath().toString(), false);
  }

  /**
  * @generated
  */
  protected IPath getFilePath()
  {
    IPath path = getContainerFullPath();
    if(path == null)
    {
      path = new Path(""); //$NON-NLS-1$
    }
    String fileName = getFileName();
    if(fileName != null)
    {
      path = path.append(fileName);
    }
    return path;
  }

  /**
  * @generated
  */
  public void createControl(Composite parent)
  {
    super.createControl(parent);
    setFileName(GmodelDiagramEditorUtil.getUniqueFileName(
        getContainerFullPath(), getFileName(), getExtension()));
    setPageComplete(validatePage());
  }

  /**
  * @generated
  */
  protected boolean validatePage()
  {
    if(!super.validatePage())
    {
      return false;
    }
    String extension = getExtension();
    if(extension != null && !getFilePath().toString().endsWith("." + extension))
    {
      setErrorMessage(NLS.bind(Messages.GmodelCreationWizardPageExtensionError,
          extension));
      return false;
    }
    return true;
  }
}
