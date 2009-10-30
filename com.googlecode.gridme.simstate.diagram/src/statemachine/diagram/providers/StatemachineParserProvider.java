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
package statemachine.diagram.providers;

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

import statemachine.StatemachinePackage;
import statemachine.diagram.edit.parts.GStateNameEditPart;
import statemachine.diagram.edit.parts.TransitionNameEditPart;
import statemachine.diagram.parsers.MessageFormatParser;
import statemachine.diagram.part.StatemachineVisualIDRegistry;

/**
* @generated
*/
public class StatemachineParserProvider extends AbstractProvider implements
    IParserProvider
{

  /**
  * @generated
  */
  private IParser gStateName_4001Parser;

  /**
  * @generated
  */
  private IParser getGStateName_4001Parser()
  {
    if(gStateName_4001Parser == null)
    {
      gStateName_4001Parser = createGStateName_4001Parser();
    }
    return gStateName_4001Parser;
  }

  /**
  * @generated
  */
  protected IParser createGStateName_4001Parser()
  {
    EAttribute[] features = new EAttribute[] { StatemachinePackage.eINSTANCE
        .getNamed_Name(), };
    MessageFormatParser parser = new MessageFormatParser(features);
    return parser;
  }

  /**
  * @generated
  */
  private IParser transitionName_4002Parser;

  /**
  * @generated
  */
  private IParser getTransitionName_4002Parser()
  {
    if(transitionName_4002Parser == null)
    {
      transitionName_4002Parser = createTransitionName_4002Parser();
    }
    return transitionName_4002Parser;
  }

  /**
  * @generated
  */
  protected IParser createTransitionName_4002Parser()
  {
    EAttribute[] features = new EAttribute[] { StatemachinePackage.eINSTANCE
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
      case GStateNameEditPart.VISUAL_ID:
        return getGStateName_4001Parser();
      case TransitionNameEditPart.VISUAL_ID:
        return getTransitionName_4002Parser();
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
      return getParser(StatemachineVisualIDRegistry.getVisualID(vid));
    }
    View view = (View) hint.getAdapter(View.class);
    if(view != null)
    {
      return getParser(StatemachineVisualIDRegistry.getVisualID(view));
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
      if(StatemachineElementTypes.getElement(hint) == null)
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
