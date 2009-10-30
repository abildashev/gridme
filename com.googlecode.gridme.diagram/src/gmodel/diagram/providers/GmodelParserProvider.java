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
package gmodel.diagram.providers;

import gmodel.GmodelPackage;
import gmodel.diagram.edit.parts.ConnectionNameEditPart;
import gmodel.diagram.edit.parts.GridElementNameEditPart;
import gmodel.diagram.parsers.MessageFormatParser;
import gmodel.diagram.part.GmodelVisualIDRegistry;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

/**
* @generated
*/
public class GmodelParserProvider extends AbstractProvider implements
    IParserProvider
{

  /**
  * @generated
  */
  private IParser connectionName_5001Parser;

  /**
  * @generated
  */
  private IParser getConnectionName_5001Parser()
  {
    if(connectionName_5001Parser == null)
    {
      connectionName_5001Parser = createConnectionName_5001Parser();
    }
    return connectionName_5001Parser;
  }

  /**
  * @generated
  */
  protected IParser createConnectionName_5001Parser()
  {
    EAttribute[] features = new EAttribute[] { GmodelPackage.eINSTANCE
        .getNamed_Name(), };
    MessageFormatParser parser = new MessageFormatParser(features);
    return parser;
  }

  /**
  * @generated
  */
  private IParser gridElementName_5002Parser;

  /**
  * @generated
  */
  private IParser getGridElementName_5002Parser()
  {
    if(gridElementName_5002Parser == null)
    {
      gridElementName_5002Parser = createGridElementName_5002Parser();
    }
    return gridElementName_5002Parser;
  }

  /**
  * @generated
  */
  protected IParser createGridElementName_5002Parser()
  {
    EAttribute[] features = new EAttribute[] { GmodelPackage.eINSTANCE
        .getNamed_Name(), };
    MessageFormatParser parser = new MessageFormatParser(features);
    return parser;
  }

  /**
  * @generated
  */
  protected IParser getParser(int visualID)
  {
    switch(visualID)
    {
      case ConnectionNameEditPart.VISUAL_ID:
        return getConnectionName_5001Parser();
      case GridElementNameEditPart.VISUAL_ID:
        return getGridElementName_5002Parser();
    }
    return null;
  }

  /**
  * @generated
  */
  public IParser getParser(IAdaptable hint)
  {
    String vid = (String) hint.getAdapter(String.class);
    if(vid != null)
    {
      return getParser(GmodelVisualIDRegistry.getVisualID(vid));
    }
    View view = (View) hint.getAdapter(View.class);
    if(view != null)
    {
      return getParser(GmodelVisualIDRegistry.getVisualID(view));
    }
    return null;
  }

  /**
  * @generated
  */
  public boolean provides(IOperation operation)
  {
    if(operation instanceof GetParserOperation)
    {
      IAdaptable hint = ((GetParserOperation) operation).getHint();
      if(GmodelElementTypes.getElement(hint) == null)
      {
        return false;
      }
      return getParser(hint) != null;
    }
    return false;
  }

  /**
  * @generated
  */
  public static class HintAdapter extends ParserHintAdapter
  {

    /**
    * @generated
    */
    private final IElementType elementType;

    /**
    * @generated
    */
    public HintAdapter(IElementType type, EObject object, String parserHint)
    {
      super(object, parserHint);
      assert type != null;
      elementType = type;
    }

    /**
    * @generated
    */
    public Object getAdapter(Class adapter)
    {
      if(IElementType.class.equals(adapter))
      {
        return elementType;
      }
      return super.getAdapter(adapter);
    }
  }

}
