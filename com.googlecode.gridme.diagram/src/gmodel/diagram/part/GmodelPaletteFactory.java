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

import gmodel.diagram.providers.GmodelElementTypes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

/**
* @generated
*/
public class GmodelPaletteFactory
{

  /**
  * @generated
  */
  public void fillPalette(PaletteRoot paletteRoot)
  {
    paletteRoot.add(createGmodel1Group());
  }

  /**
  * Creates "gmodel" palette tool group
  * @generated
  */
  private PaletteContainer createGmodel1Group()
  {
    PaletteGroup paletteContainer = new PaletteGroup(
        Messages.Gmodel1Group_title);
    paletteContainer.add(createConnection1CreationTool());
    paletteContainer.add(createGridElement2CreationTool());
    paletteContainer.add(createLink3CreationTool());
    return paletteContainer;
  }

  /**
  * @generated
  */
  private ToolEntry createConnection1CreationTool()
  {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
    types.add(GmodelElementTypes.Connection_2001);
    NodeToolEntry entry = new NodeToolEntry(
        Messages.Connection1CreationTool_title,
        Messages.Connection1CreationTool_desc, types);
    entry.setSmallIcon(GmodelElementTypes
        .getImageDescriptor(GmodelElementTypes.Connection_2001));
    entry.setLargeIcon(entry.getSmallIcon());
    return entry;
  }

  /**
  * @generated
  */
  private ToolEntry createGridElement2CreationTool()
  {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
    types.add(GmodelElementTypes.GridElement_2002);
    NodeToolEntry entry = new NodeToolEntry(
        Messages.GridElement2CreationTool_title,
        Messages.GridElement2CreationTool_desc, types);
    entry.setSmallIcon(GmodelElementTypes
        .getImageDescriptor(GmodelElementTypes.GridElement_2002));
    entry.setLargeIcon(entry.getSmallIcon());
    return entry;
  }

  /**
  * @generated
  */
  private ToolEntry createLink3CreationTool()
  {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
    types.add(GmodelElementTypes.Link_4002);
    LinkToolEntry entry = new LinkToolEntry(Messages.Link3CreationTool_title,
        Messages.Link3CreationTool_desc, types);
    entry.setSmallIcon(GmodelElementTypes
        .getImageDescriptor(GmodelElementTypes.Link_4002));
    entry.setLargeIcon(entry.getSmallIcon());
    return entry;
  }

  /**
  * @generated
  */
  private static class NodeToolEntry extends ToolEntry
  {

    /**
    * @generated
    */
    private final List elementTypes;

    /**
    * @generated
    */
    private NodeToolEntry(String title, String description, List elementTypes)
    {
      super(title, description, null, null);
      this.elementTypes = elementTypes;
    }

    /**
    * @generated
    */
    public Tool createTool()
    {
      Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
      tool.setProperties(getToolProperties());
      return tool;
    }
  }

  /**
  * @generated
  */
  private static class LinkToolEntry extends ToolEntry
  {

    /**
    * @generated
    */
    private final List relationshipTypes;

    /**
    * @generated
    */
    private LinkToolEntry(String title, String description,
        List relationshipTypes)
    {
      super(title, description, null, null);
      this.relationshipTypes = relationshipTypes;
    }

    /**
    * @generated
    */
    public Tool createTool()
    {
      Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
      tool.setProperties(getToolProperties());
      return tool;
    }
  }
}
