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
package com.googlecode.gridme.ui.editors;

import gexperiment.ParameterValue;
import gexperiment.impl.GexperimentFactoryImpl;
import gmodel.GenericModelElement;
import gmodel.Model;
import gmodel.ModelElement;
import gmodel.RParameter;
import gmodel.impl.GmodelFactoryImpl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.internal.ui.dialogs.StatusInfo;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalListener2;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.googlecode.gridme.runtime.Parameter;
import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.ui.GridmeUIPlugin;
import com.googlecode.gridme.ui.ImplScanner;
import com.googlecode.gridme.ui.ImplementationInfo;

public class ParameterTreeTable implements IMenuListener
{
  // Menu actions
  class AddElementAction extends Action
  {
    @Override
    public String getText()
    {
      return "Add model element";
    }

    @Override
    public void run()
    {
      SelectDialog dlg = new SelectElementsDialog(getContents());

      if(dlg.open() == Window.OK && dlg.getFirstResult() != null)
      {
        getContents().addChild((TreeNode) dlg.getFirstResult());
        // Update tree
        refreshfTree();
      }
    }
  }

  /**
   * All elements from group (element item)
   * must have this parameter.
   */
  class AddParameterAction extends Action
  {
    @Override
    public String getText()
    {
      return "Add parameter";
    }

    @Override
    public void run()
    {
      ViewerCell cell = focusCellManager.getFocusCell();

      if(cell != null && cell.getColumnIndex() == 0)
      {
        SelectDialog dlg = new SelectParametersDialog(
            (NamedNodeWithParams) cell.getElement());

        if(dlg.open() == Window.OK && dlg.getFirstResult() != null)
        {
          ParameterNode result = (ParameterNode) dlg.getFirstResult();
          ((TreeNode) cell.getElement()).addChild(result);
          // Update tree
          refreshfTree();
        }
      }
    }
  }

  class DeleteAction extends Action
  {
    @Override
    public String getText()
    {
      return "Delete";
    }

    @Override
    public void run()
    {
      ViewerCell cell = focusCellManager.getFocusCell();

      if(cell != null && cell.getColumnIndex() == 0)
      {
        ((NamedNodeWithParams) cell.getElement()).hide();
        paramsModified();
        refreshfTree();
      }
    }
  }

  class EditAction extends Action
  {
    @Override
    public String getText()
    {
      return "Change";
    }

    @Override
    public void run()
    {
      ViewerCell cell = focusCellManager.getFocusCell();

      if(cell != null && cell.getColumnIndex() == 0)
      {
        SelectDialog dlg = null;
        if(cell.getElement() instanceof ElementNode)
        {
          dlg = new SelectElementsDialog((TreeRoot) tree.getInput());
        }
        else
        // Parameter node
        {
          NamedNodeWithParams parent = (NamedNodeWithParams) ((TreeNode) cell
              .getElement()).getParent();
          dlg = new SelectParametersDialog(parent);
        }

        if(dlg.open() == Window.OK && dlg.getFirstResult() != null)
        {
          ((NamedNodeWithParams) cell.getElement())
              .swapNode((NamedNodeWithParams) dlg.getFirstResult());
          // Update tree
          refreshfTree();
        }
      }
    }
  }

  /**
   * A dialog for selecting model element or parameter
   */
  class SelectDialog
  {
    protected ElementListSelectionDialog dlg;

    public SelectDialog()
    {
      dlg = new ElementListSelectionDialog(tree.getControl().getShell(),
          new LabelProvider());
      dlg.setFilter("*");
      dlg.setIgnoreCase(true);
    }

    public int open()
    {
      return dlg.open();
    }

    public Object getFirstResult()
    {
      return dlg.getFirstResult();
    }
  }

  class SelectElementsDialog extends SelectDialog
  {
    public SelectElementsDialog(TreeRoot root)
    {
      dlg.setTitle("Model elements selection");
      dlg.setMessage("Select Grid model elements");
      dlg.setMultipleSelection(true);
      dlg.setElements(root.getAvailableElements().toArray());
    }
  }

  class SelectParametersDialog extends SelectDialog
  {
    public SelectParametersDialog(NamedNodeWithParams node)
    {
      dlg.setTitle("Parameters selection");
      dlg.setMessage("Select element parameter.");
      dlg.setMultipleSelection(false);
      dlg.setElements(node.getAvailableParameters().toArray());
      dlg.setSize(30, 20);

      dlg.setValidator(new ISelectionStatusValidator()
      {
        @Override
        public IStatus validate(Object[] selection)
        {
          ParameterNode pnode = (ParameterNode) selection[0];
          return new StatusInfo(IStatus.INFO,
              (pnode.isRequired() ? "(*) " : "") + pnode.getDescription());
        }
      });
    }
  }

  // Adapted from eclipse snippet.
  class EditorWithContentProposal extends TextCellEditor
  {
    private ContentProposalAdapter contentProposalAdapter;
    private boolean isPopupOpen = false;

    public EditorWithContentProposal(Composite parent,
        IContentProposalProvider contentProposalProvider, KeyStroke keyStroke,
        char[] autoActivationCharacters)
    {
      super(parent);
      enableContentProposal(contentProposalProvider, keyStroke,
          autoActivationCharacters);
    }

    @Override
    protected boolean dependsOnExternalFocusListener()
    {
      // Always return false;
      // Otherwise, the ColumnViewerEditor will install an additional focus listener
      // that cancels cell editing on focus lost, even if focus gets lost due to
      // activation of the completion proposal popup. See also bug 58777.
      return false;
    }

    private void enableContentProposal(
        IContentProposalProvider contentProposalProvider, KeyStroke keyStroke,
        char[] autoActivationCharacters)
    {
      contentProposalAdapter = new ContentProposalAdapter(text,
          new TextContentAdapter(), contentProposalProvider, keyStroke,
          autoActivationCharacters);

      contentProposalAdapter
          .setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

      // Listen for popup open/close events to be able to handle focus events correctly
      contentProposalAdapter
          .addContentProposalListener(new IContentProposalListener2()
          {

            public void proposalPopupClosed(ContentProposalAdapter adapter)
            {
              isPopupOpen = false;
            }

            public void proposalPopupOpened(ContentProposalAdapter adapter)
            {
              isPopupOpen = true;
            }
          });
    }

    @Override
    protected void focusLost()
    {
      if(!isPopupOpen)
      {
        // Focus lost deactivates the cell editor.
        // This must not happen if focus lost was caused by activating
        // the completion proposal popup.
        super.focusLost();
      }
    }

    /**
     * Return the {@link ContentProposalAdapter} of this cell editor.
     * 
     * @return the {@link ContentProposalAdapter}
     */
    public ContentProposalAdapter getContentProposalAdapter()
    {
      return contentProposalAdapter;
    }
  }

  private class MyContentProvider implements ITreeContentProvider
  {
    public void dispose()
    {
    }

    public Object[] getChildren(Object parentElement)
    {
      return getElements(parentElement);
    }

    public Object[] getElements(Object inputElement)
    {
      return ((TreeNode) inputElement).getChilds();
    }

    public Object getParent(Object element)
    {
      if(element == null)
      {
        return null;
      }
      return ((TreeNode) element).getParent();
    }

    public boolean hasChildren(Object element)
    {
      return ((TreeNode) element).hasChilds();
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    {
    }
  }

  /**
   * Base class for all tree nodes
   */
  abstract class TreeNode
  {
    private ArrayList<TreeNode> childs;
    private TreeNode parent;

    public TreeNode()
    {
      childs = new ArrayList<TreeNode>();
    }

    public void addChild(TreeNode child)
    {
      childs.add(child);
      child.parent = this;
    }

    public Object[] getChilds()
    {
      return childs.toArray();
    }

    public TreeNode getParent()
    {
      return parent;
    }

    public void setParent(TreeNode parent)
    {
      this.parent = parent;
    }

    public boolean hasChilds()
    {
      return !childs.isEmpty();
    }

    public void hide()
    {
      ArrayList<TreeNode> nodes = getParent().childs;
      nodes.remove(this);
    }

    public void swapNode(TreeNode newNode)
    {
      ArrayList<TreeNode> nodes = getParent().childs;
      for(int i = 0; i < nodes.size(); i++)
      {
        if(nodes.get(i) == this)
        {
          nodes.remove(i);
          nodes.add(i, newNode);
          break;
        }
      }
    }

    public boolean containsChild(TreeNode node)
    {
      boolean result = false;
      for(TreeNode child : childs)
      {
        if(child.equals(node))
        {
          result = true;
          break;
        }
      }
      return result;
    }

    public void clear()
    {
      childs.clear();
    }

    public abstract boolean hasAvailableSiblings();
  }

  /**
   * Named tree node
   */
  abstract class NamedNodeWithParams extends TreeNode
  {
    // All available parameters
    private ArrayList<ParameterNode> allParameters;
    private String name;

    public NamedNodeWithParams(String name)
    {
      this.name = name;
      allParameters = new ArrayList<ParameterNode>();
    }

    public void setName(String name)
    {
      this.name = name;
    }

    public String getName()
    {
      return name;
    }

    public String toString()
    {
      return name;
    }

    public List<ParameterNode> getAvailableParameters()
    {
      ArrayList<ParameterNode> result = new ArrayList<ParameterNode>();

      for(ParameterNode param : allParameters)
      {
        if(!containsChild(param))
        {
          result.add(param);
        }
      }

      return result;
    }

    public void addAvailableParameter(ParameterNode param)
    {
      allParameters.add(param);
      param.setParent(this);
    }

    public void clear()
    {
      clearProposals();
      super.clear();
    }

    private void clearProposals()
    {
      allParameters.clear();
    }

    public ParameterNode findAvailableParameter(String name)
    {
      for(ParameterNode par : allParameters)
      {
        if(par.getName().equals(name))
        {
          return par;
        }
      }
      return null;
    }

    public boolean paramAdmissible(ParameterNode param)
    {
      return findAvailableParameter(param.getName()) != null;
    }

    @Override
    public boolean hasAvailableSiblings()
    {
      return false;
    }

    @Override
    public boolean equals(Object obj)
    {
      return (obj instanceof NamedNodeWithParams)
          && ((NamedNodeWithParams) obj).getName().equals(getName());
    }
  }

  /**
   * Root of the tree.
   */
  class TreeRoot extends TreeNode
  {
    // All elements available in model
    private ArrayList<ElementNode> allElements;

    public TreeRoot()
    {
      allElements = new ArrayList<ElementNode>();
    }

    /**
     * Add element to the list of proposals
     */
    public void addAvailableElement(ElementNode elem)
    {
      allElements.add(elem);
      elem.setParent(this);
    }

    public List<ElementNode> getAvailableElements()
    {
      ArrayList<ElementNode> result = new ArrayList<ElementNode>();

      for(ElementNode elem : allElements)
      {
        if(!containsChild(elem))
        {
          result.add(elem);
        }
      }

      return result;
    }

    @Override
    public boolean hasAvailableSiblings()
    {
      return false;
    }

    public ElementNode findAvailableElement(String name)
    {
      for(ElementNode elem : allElements)
      {
        if(elem.getName().equals(name))
        {
          return elem;
        }
      }
      return null;
    }
  }

  /**
   * Parameter item. All elements from group (element item)
   * must have this parameter.
   */
  class ParameterNode extends NamedNodeWithParams
  {
    // All available parameters
    private ArrayList<String> proposals;
    private String value;
    private final boolean isImplementation;
    private String description;
    private final int category;
    private boolean isRequired;

    public ParameterNode(String name)
    {
      this(name, false, 0);
    }

    public String getDescription()
    {
      return description;
    }

    public ParameterNode(String name, boolean impl, int category)
    {
      super(name);
      value = "";
      proposals = new ArrayList<String>();
      isImplementation = impl;
      this.category = category;
      isRequired = false;
    }

    public boolean isImplementation()
    {
      return isImplementation;
    }

    public String getValue()
    {
      return value;
    }

    public void setValue(String value)
    {
      this.value = value;
    }

    public List<String> getProposals()
    {
      return proposals;
    }

    public void addProposal(String prop)
    {
      proposals.add(prop);
    }

    @Override
    public boolean hasAvailableSiblings()
    {
      return !((NamedNodeWithParams) getParent()).getAvailableParameters()
          .isEmpty();
    }

    @Override
    public void clear()
    {
      super.clear();
      proposals.clear();
    }

    public boolean isAdmissible()
    {
      return ((NamedNodeWithParams) getParent()).paramAdmissible(this);
    }

    public void setDescription(String description)
    {
      this.description = description;
    }

    public int getCategory()
    {
      return category;
    }

    public boolean isRequired()
    {
      return isRequired;
    }

    public void setRequired(boolean isRequired)
    {
      this.isRequired = isRequired;
    }
  }

  /**
   * Model element item which references a group 
   * of model elements.  
   */
  class ElementNode extends NamedNodeWithParams
  {
    private String implClass;
    private Object elementData;

    public ElementNode(String name, String impl)
    {
      super(name);
      this.implClass = impl;
    }

    @Override
    public boolean hasAvailableSiblings()
    {
      return !((TreeRoot) getParent()).getAvailableElements().isEmpty();
    }

    public String getImplClass()
    {
      return implClass;
    }

    public Object getElementData()
    {
      return elementData;
    }

    public void setElementData(Object elementData)
    {
      this.elementData = elementData;
    }

    public void setImplClass(String implClass)
    {
      this.implClass = implClass;
    }
  }

  class ValueEditingSupport extends EditingSupport
  {
    private EditorWithContentProposal cellEditor;
    private boolean editImpl;

    private void setImplementationProposals(final String[] values)
    {
      KeyStroke stroke = null;
      try
      {
        stroke = KeyStroke.getInstance("Ctrl+Space");
      }
      catch(ParseException e)
      {
        GridmeUIPlugin.logErrorMessage(
            "Parameter content assistant key stroke error", e);
      }

      IContentProposalProvider contentProposalProvider = new IContentProposalProvider()
      {
        public IContentProposal[] getProposals(String contents, int position)
        {
          ArrayList<IContentProposal> proposals = new ArrayList<IContentProposal>();
          for(int i = 0; i < values.length; i++)
          {
            final String val = values[i];
            final ImplementationInfo info = ImplScanner.getInstance()
                .getImplementationInfo(val);

            if(info == null)
            {
              GridmeUIPlugin.logErrorMessage("Invalid implementation class: "
                  + val);
              continue;
            }

            proposals.add(new IContentProposal()
            {
              public String getContent()
              {
                return val;
              }

              public String getLabel()
              {
                return info.getName();
              }

              public String getDescription()
              {
                return info.getDescription();
              }

              public int getCursorPosition()
              {
                return val.length();
              }
            });
          }
          return proposals.toArray(new IContentProposal[proposals.size()]);
        }
      };

      cellEditor = new EditorWithContentProposal(tree.getTree(),
          contentProposalProvider, stroke, null);
    }

    private void setProposals(final String[] values)
    {
      KeyStroke stroke = null;
      try
      {
        stroke = KeyStroke.getInstance("Ctrl+Space");
      }
      catch(ParseException e)
      {
        GridmeUIPlugin.logErrorMessage(
            "Parameter content assistant key stroke error", e);
      }

      IContentProposalProvider contentProposalProvider = new SimpleContentProposalProvider(
          values);

      cellEditor = new EditorWithContentProposal(tree.getTree(),
          contentProposalProvider, stroke, null);
    }

    public ValueEditingSupport(TreeViewer viewer, boolean editImpl)
    {
      super(viewer);
      this.editImpl = editImpl;
    }

    protected boolean canEdit(Object element)
    {
      if(element instanceof ParameterNode)
      {
        // Set proposals list for the value editor
        if(((ParameterNode) element).isImplementation())
        {
          Set<String> imList = ImplScanner.getInstance().getImplementations(
              ((ParameterNode) element).getCategory());

          if(imList != null)
          {
            setImplementationProposals(imList
                .toArray(new String[imList.size()]));
          }
        }
        else
        {
          setProposals(((ParameterNode) element).getProposals().toArray(
              new String[((ParameterNode) element).getProposals().size()]));
        }
        return true;
      }
      else if(editImpl)
      {
        ViewerCell cell = focusCellManager.getFocusCell();

        boolean isImpl = cell != null && cell.getColumnIndex() == 1;

        if(isImpl)
        {
          Set<String> imList = ImplScanner.getInstance().getModelElements();

          if(imList != null)
          {
            setImplementationProposals(imList
                .toArray(new String[imList.size()]));
          }
        }

        return isImpl;
      }
      return false;
    }

    @Override
    protected CellEditor getCellEditor(Object element)
    {
      return cellEditor;
    }

    protected Object getValue(Object element)
    {
      if(element instanceof ElementNode)
      {
        return ((ElementNode) element).getImplClass();
      }
      else
      {
        return ((ParameterNode) element).value;
      }
    }

    protected void setValue(Object element, Object value)
    {
      if(element instanceof ParameterNode)
      {
        ParameterNode param = (ParameterNode) element;
        param.setValue(value.toString().trim());

        if(param.isImplementation())
        {
          buildParamsProposals(param, value.toString());
          refreshfTree();
        }
        else
        {
          tree.update(element, null);
        }
      }
      else if(element instanceof ElementNode)
      {
        ElementNode elem = (ElementNode) element;
        elem.setImplClass(value.toString().trim());
        buildParamsProposals(elem, value.toString());
        refreshfTree();
      }
      paramsModified();
    }
  }

  private void paramsModified()
  {
    parentEditor.modifyText(null);
    isModified = true;
  }

  private TreeViewer tree;
  private TreeViewerFocusCellManager focusCellManager;
  private boolean isModified = false;
  private ModifyListener parentEditor;
  private IProject project;

  public ParameterTreeTable(final Composite parent, GridData dta,
      ModifyListener changeModify, boolean editImpl, IProject project)
  {
    this.project = project;

    parentEditor = changeModify;

    tree = new TreeViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
    tree.getTree().setLinesVisible(true);
    tree.getTree().setHeaderVisible(true);
    tree.getTree().setLayoutData(dta);

    MenuManager popupMenu = new MenuManager();
    popupMenu.setRemoveAllWhenShown(true);
    popupMenu.addMenuListener(this);
    tree.getControl().setMenu(popupMenu.createContextMenu(tree.getControl()));

    focusCellManager = new TreeViewerFocusCellManager(tree,
        new FocusCellOwnerDrawHighlighter(tree));
    ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(
        tree)
    {
      protected boolean isEditorActivationEvent(
          ColumnViewerEditorActivationEvent event)
      {
        return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
            || event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
            || (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
            || event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
      }
    };

    TreeViewerEditor.create(tree, focusCellManager, actSupport,
        ColumnViewerEditor.TABBING_HORIZONTAL
            | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
            | ColumnViewerEditor.TABBING_VERTICAL
            | ColumnViewerEditor.KEYBOARD_ACTIVATION);

    TreeViewerColumn column = new TreeViewerColumn(tree, SWT.NONE);
    column.getColumn().setWidth(200);
    column.getColumn().setMoveable(false);
    column.getColumn().setText("Element/Parameter Name");
    column.setLabelProvider(new ColumnLabelProvider()
    {
      public String getText(Object element)
      {
        // Name in 1 column
        return element.toString();
      }

      @Override
      public Color getForeground(Object element)
      {
        if(element instanceof ParameterNode
            && !((ParameterNode) element).isAdmissible())
        {
          return tree.getControl().getShell().getDisplay().getSystemColor(
              SWT.COLOR_RED);
        }
        return super.getForeground(element);
      }
    });

    column = new TreeViewerColumn(tree, SWT.NONE);
    column.getColumn().setWidth(200);
    column.getColumn().setMoveable(false);
    column.getColumn().setText("Value");
    column.setLabelProvider(new ColumnLabelProvider()
    {
      public String getText(Object element)
      {
        // 2 column empty for element and contains value for parameter
        if(element instanceof ElementNode)
        {
          return ((ElementNode) element).getImplClass();
        }
        else
        {
          return ((ParameterNode) element).value;
        }
      }
    });
    column.setEditingSupport(new ValueEditingSupport(tree, editImpl));
    tree.setContentProvider(new MyContentProvider());
  }

  /**
   * Builds a list of proposals for the given class name.
   * @param elem element with parameters
   * @param className class name that have annotated parameters
   */
  public void buildParamsProposals(NamedNodeWithParams elem, String className)
  {
    elem.clearProposals();
    if(className != null && !className.isEmpty())
    {
      try
      {
        //      Class cls = Class.forName(className);
        Class cls = ImplScanner.getInstance().getClassLoader().loadClass(
            className);
        Method[] mtds = cls.getMethods();
        for(Method method : mtds)
        {
          Parameter param = method.getAnnotation(Parameter.class);
          if(param != null)
          {
            ParameterNode pnode = new ParameterNode(RuntimeUtils
                .getParameterName(method.getName()), param.hasParams(), param
                .category());
            elem.addAvailableParameter(pnode);
            pnode.setDescription(param.description());
            pnode.setRequired(param.required());

            if(!param.hasParams())
            {
              switch(param.category())
              {
                case Parameter.WORKLOAD:
                  if(project != null)
                  {
                    for(IResource file : project.members())
                    {
                      if(file instanceof IFile
                          && ((IFile) file).getFileExtension().equals("swfz"))
                      {
                        pnode.addProposal(file.getName());
                      }
                    }
                  }
                  break;
                default:
              }
            }
          }
        }
      }
      catch(Exception e)
      {
        GridmeUIPlugin.logWarningMessage(
            "Unable to find element implementation "
                + "class. No proposals will be available.", e);
      }
    }
  }

  public TreeRoot getContents()
  {
    return (TreeRoot) tree.getInput();
  }

  @Override
  public void menuAboutToShow(IMenuManager manager)
  {
    Action addElem = new AddElementAction();
    Action addPar = new AddParameterAction();
    Action edit = new EditAction();
    Action del = new DeleteAction();

    manager.add(addElem);
    manager.add(addPar);
    manager.add(edit);
    manager.add(del);

    ViewerCell cell = focusCellManager.getFocusCell();

    addElem.setEnabled(false);
    addPar.setEnabled(false);
    edit.setEnabled(false);
    del.setEnabled(false);

    if(!getContents().getAvailableElements().isEmpty())
    {
      addElem.setEnabled(true);
    }

    if(cell != null)
    {
      del.setEnabled(true);

      if(!((NamedNodeWithParams) cell.getElement()).getAvailableParameters()
          .isEmpty())
      {
        addPar.setEnabled(true);
      }

      if(((TreeNode) cell.getElement()).hasAvailableSiblings())
      {
        edit.setEnabled(true);
      }
    }
  }

  public void setInput(Model model, List<ParameterValue> params)
  {
    tree.getTree().removeAll();
    tree.setInput(createModel(model, params));
    if(getContents().getChilds().length > 0)
    {
      isModified = true;
    }
    tree.expandAll();
  }

  public void setInput(Model model)
  {
    tree.getTree().removeAll();
    tree.setInput(createModel(model));
    if(getContents().getChilds().length > 0)
    {
      isModified = true;
    }
    tree.expandAll();
  }

  public boolean isModified()
  {
    return isModified;
  }

  public void reloadInput(Model model, List<ParameterValue> eparams)
  {
    tree.setInput(createModel(model, eparams));
    refreshfTree();
  }

  private TreeRoot createModel(Model model)
  {
    TreeRoot root = new TreeRoot();

    if(model != null)
    {
      for(GenericModelElement elemG : model.getElements())
      {
        if(elemG instanceof ModelElement)
        {
          ModelElement elem = (ModelElement) elemG;
          ElementNode enode = new ElementNode(elem.getName(), elem
              .getImplementation() != null ? elem.getImplementation() : "");
          enode.setElementData(elem);
          root.addAvailableElement(enode);
          root.addChild(enode);

          if(elem.getImplementation() != null)
          {
            buildParamsProposals(enode, elem.getImplementation());
          }

          for(RParameter param : elem.getParameters())
          {
            createParameterTree(enode, param);
          }
        }
      }
    }

    return root;
  }

  private TreeRoot createModel(Model model, List<ParameterValue> eparams)
  {
    TreeRoot root = new TreeRoot();

    // Create available parameters

    if(model != null)
    {
      for(GenericModelElement elemG : model.getElements())
      {
        if(elemG instanceof ModelElement)
        {
          ModelElement elem = (ModelElement) elemG;
          ElementNode enode = new ElementNode(elem.getName(), elem
              .getImplementation());
          enode.setElementData(elem);
          root.addAvailableElement(enode);

          buildParamsProposals(enode, elem.getImplementation());
        }
      }
    }

    // Load parameters from experiment

    for(ParameterValue pvalue : eparams)
    {
      ModelElement elem = pvalue.getElements().get(0);
      ElementNode elemNode = root.findAvailableElement(elem.getName());
      if(elemNode != null)
      {
        root.addChild(elemNode);
        for(RParameter nodeParam : pvalue.getParameters())
        {
          createParameterTree(elemNode, nodeParam);
        }
      }
      else
      {
        // Error: model has no such element
      }
    }

    return root;
  }

  private void createParameterTree(NamedNodeWithParams node, RParameter param)
  {
    ParameterNode pnode = node.findAvailableParameter(param.getName());
    if(pnode != null)
    {
      pnode.setValue(param.getValue());
      if(pnode.isImplementation())
      {
        buildParamsProposals(pnode, param.getValue());
      }
      node.addChild(pnode);

      for(RParameter childParam : param.getParameters())
      {
        createParameterTree(pnode, childParam);
      }
    }
    else
    {
      // Error: parameter can not be applied to the element
    }
  }

  public void saveModel(Model model)
  {
    for(Object elem : getContents().getChilds())
    {
      ElementNode enode = (ElementNode) elem;
      ModelElement melem = (ModelElement) enode.getElementData();
      melem.setImplementation(enode.getImplClass());
      melem.getParameters().clear();

      for(Object pNode : enode.getChilds())
      {
        ParameterNode paramTreeNode = (ParameterNode) pNode;
        RParameter modelParam = GmodelFactoryImpl.eINSTANCE.createRParameter();
        melem.getParameters().add(modelParam);
        saveParametersRecursive(paramTreeNode, modelParam);
      }
    }
  }

  public void saveParameters(List<ParameterValue> params)
  {
    for(Object elem : getContents().getChilds())
    {
      ElementNode enode = (ElementNode) elem;
      ParameterValue pModValue = GexperimentFactoryImpl.eINSTANCE
          .createParameterValue();
      //pModValue.getElements().add(findElement(enode.getName(), model));
      pModValue.getElements().add((ModelElement) enode.getElementData());
      for(Object pNode : enode.getChilds())
      {
        ParameterNode paramTreeNode = (ParameterNode) pNode;
        RParameter modelParam = GmodelFactoryImpl.eINSTANCE.createRParameter();
        pModValue.getParameters().add(modelParam);
        saveParametersRecursive(paramTreeNode, modelParam);
      }
      params.add(pModValue);
    }
  }

  private void saveParametersRecursive(ParameterNode nodep, RParameter modp)
  {
    modp.setName(nodep.getName());
    modp.setValue(nodep.getValue());

    for(Object pNode : nodep.getChilds())
    {
      ParameterNode paramTreeNode = (ParameterNode) pNode;
      RParameter modelParam = GmodelFactoryImpl.eINSTANCE.createRParameter();
      modelParam.setName(paramTreeNode.getName());
      modelParam.setValue(paramTreeNode.getValue());
      modp.getParameters().add(modelParam);
      saveParametersRecursive(paramTreeNode, modelParam);
    }
  }

  /**
   * Disables or tree
   */
  public void setEnabled(boolean b)
  {
    tree.getTree().setEnabled(b);
  }

  /*
  private ModelElement findElement(String name, Model model)
  {
    ModelElement melem = null;

    for(GenericModelElement elem : model.getElements())
    {
      if(elem instanceof ModelElement
          && name.equals(((ModelElement) elem).getName()))
      {
        melem = (ModelElement) elem;
        break;
      }
    }

    return melem;
  }*/

  private void refreshfTree()
  {
    tree.refresh();
    tree.expandAll();
  }

  /**
   * Removes all data from tree and disables it.
   */
  public void clean()
  {
    getContents().clear();
    tree.refresh();
    setEnabled(false);
  }
}
