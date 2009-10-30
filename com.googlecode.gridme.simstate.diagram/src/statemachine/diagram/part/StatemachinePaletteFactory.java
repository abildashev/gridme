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
package statemachine.diagram.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

import statemachine.diagram.providers.StatemachineElementTypes;

/**
* @generated
*/
public class StatemachinePaletteFactory
{

  /**
  * @generated
  */
  public void fillPalette(PaletteRoot paletteRoot)
  {
    paletteRoot.add(createGmecore1Group());
  }

  /**
  * Creates "gmecore" palette tool group
  * @generated
  */
  private PaletteContainer createGmecore1Group()
  {
    PaletteGroup paletteContainer = new PaletteGroup(
        Messages.Gmecore1Group_title);
    paletteContainer.add(createGStartState1CreationTool());
    paletteContainer.add(createGStopState2CreationTool());
    paletteContainer.add(createGState3CreationTool());
    paletteContainer.add(createTransition4CreationTool());
    return paletteContainer;
  }

  /**
  * @generated
  */
  private ToolEntry createGStartState1CreationTool()
  {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
    types.add(StatemachineElementTypes.GStartState_1002);
    NodeToolEntry entry = new NodeToolEntry(
        Messages.GStartState1CreationTool_title,
        Messages.GStartState1CreationTool_desc, types);
    entry
        .setSmallIcon(StatemachineDiagramEditorPlugin
            .findImageDescriptor("platform:/plugin/com.googlecode.gridme.simstate/icons/obj16/start.gif")); //$NON-NLS-1$
    entry
        .setLargeIcon(StatemachineDiagramEditorPlugin
            .findImageDescriptor("platform:/plugin/com.googlecode.gridme.simstate/icons/obj16/start.gif")); //$NON-NLS-1$
    return entry;
  }

  /**
  * @generated
  */
  private ToolEntry createGStopState2CreationTool()
  {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
    types.add(StatemachineElementTypes.GStopState_1003);
    NodeToolEntry entry = new NodeToolEntry(
        Messages.GStopState2CreationTool_title,
        Messages.GStopState2CreationTool_desc, types);
    entry
        .setSmallIcon(StatemachineDiagramEditorPlugin
            .findImageDescriptor("platform:/plugin/com.googlecode.gridme.simstate/icons/obj16/stop.gif")); //$NON-NLS-1$
    entry
        .setLargeIcon(StatemachineDiagramEditorPlugin
            .findImageDescriptor("platform:/plugin/com.googlecode.gridme.simstate/icons/obj16/stop.gif")); //$NON-NLS-1$
    return entry;
  }

  /**
  * @generated
  */
  private ToolEntry createGState3CreationTool()
  {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
    types.add(StatemachineElementTypes.GState_1001);
    NodeToolEntry entry = new NodeToolEntry(Messages.GState3CreationTool_title,
        Messages.GState3CreationTool_desc, types);
    entry
        .setSmallIcon(StatemachineDiagramEditorPlugin
            .findImageDescriptor("platform:/plugin/com.googlecode.gridme.simstate/icons/obj16/state.gif")); //$NON-NLS-1$
    entry
        .setLargeIcon(StatemachineDiagramEditorPlugin
            .findImageDescriptor("platform:/plugin/com.googlecode.gridme.simstate/icons/obj16/state.gif")); //$NON-NLS-1$
    return entry;
  }

  /**
  * @generated
  */
  private ToolEntry createTransition4CreationTool()
  {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
    types.add(StatemachineElementTypes.Transition_3001);
    LinkToolEntry entry = new LinkToolEntry(
        Messages.Transition4CreationTool_title,
        Messages.Transition4CreationTool_desc, types);
    entry
        .setSmallIcon(StatemachineDiagramEditorPlugin
            .findImageDescriptor("platform:/plugin/com.googlecode.gridme.simstate/icons/obj16/transition.gif")); //$NON-NLS-1$
    entry
        .setLargeIcon(StatemachineDiagramEditorPlugin
            .findImageDescriptor("platform:/plugin/com.googlecode.gridme.simstate/icons/obj16/transition.gif")); //$NON-NLS-1$
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
