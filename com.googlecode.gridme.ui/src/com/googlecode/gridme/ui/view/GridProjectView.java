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
package com.googlecode.gridme.ui.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.internal.ui.refactoring.reorg.NewNameQueries;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

import com.googlecode.gridme.runtime.RuntimeUtils;
import com.googlecode.gridme.ui.GridmeUIPlugin;
import com.googlecode.gridme.ui.PlatformUtils;

public class GridProjectView extends ViewPart
{
  private TreeViewer viewer;
  private Action doubleClickAction;
  private GTreeItem root;
  private Action collapse;
  private Action expand;
  private Action deleteAction;

  private class MyContentProvider implements ITreeContentProvider,
      IResourceChangeListener
  {
    public Object[] getElements(Object inputElement)
    {
      return ((GTreeItem) inputElement).childs.toArray();
    }

    public void dispose()
    {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    {
      ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
          IResourceChangeEvent.POST_CHANGE);
    }

    public Object[] getChildren(Object parentElement)
    {
      return getElements(parentElement);
    }

    public Object getParent(Object element)
    {
      if(element == null)
      {
        return null;
      }
      return ((GTreeItem) element).parent;
    }

    public boolean hasChildren(Object element)
    {
      return ((GTreeItem) element).childs.size() > 0;
    }

    @Override
    public void resourceChanged(IResourceChangeEvent event)
    {
      if(event.getType() == IResourceChangeEvent.POST_CHANGE)
      {
        //TreePath[] state = viewer.getExpandedTreePaths();
        createModel();
        if(viewer.getControl().getDisplay().getThread() == Thread
            .currentThread())
        {
          viewer.refresh();
          viewer.expandAll();
        }
        else
        {
          viewer.getControl().getDisplay().asyncExec(new Runnable()
          {
            public void run()
            {
              Control ctrl = viewer.getControl();
              if(ctrl == null || ctrl.isDisposed())
              {
                return;
              }
              viewer.refresh();
              viewer.expandAll();
            }
          });
        }
        //viewer.setExpandedTreePaths(state);
      }
    }
  }

  private class MyLabelProvider implements ILabelProvider
  {
    private HashMap<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>();

    @Override
    public Image getImage(Object element)
    {
      ImageDescriptor descriptor = null;

      if(element instanceof GTreeItemProject)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("gridme.png");
      }
      else if(element instanceof GTreeItemModels)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("gmodel.png");
      }
      else if(element instanceof GTreeItemWorkloads)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("workload.png");
      }
      else if(element instanceof GTreeItemExperiments)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("experiment.png");
      }
      else if(element instanceof GTreeItemCharts)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("gantt.png");
      }
      else if(element instanceof GTreeItemResults)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("visual.png");
      }
      else if(element instanceof GTreeChart)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("igantt.png");
      }
      else if(element instanceof GTreeVisual)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("iresult.png");
      }
      else if(element instanceof GTreeExperiment)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("iexperiment.png");
      }
      else if(element instanceof GTreeItemWorkload)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("iworkload.png");
      }
      else if(element instanceof GTreeItemModel)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("imodel.png");
      }
      else if(element instanceof GTreeItemModelDiagram)
      {
        descriptor = GridmeUIPlugin.getImageDescriptor("imodel_dia.png");
      }
      else
      {
        return null;
      }

      Image image = (Image) imageCache.get(descriptor);
      if(image == null)
      {
        image = descriptor.createImage();
        imageCache.put(descriptor, image);
      }

      return image;
    }

    @Override
    public String getText(Object element)
    {
      return ((GTreeItem) element).getName();
    }

    @Override
    public void addListener(ILabelProviderListener listener)
    {
    }

    @Override
    public void dispose()
    {
      for(Iterator i = imageCache.values().iterator(); i.hasNext();)
      {
        ((Image) i.next()).dispose();
      }
      imageCache.clear();
    }

    @Override
    public boolean isLabelProperty(Object element, String property)
    {
      return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener)
    {
    }
  }

  class GTreeItem
  {
    private GTreeItem parent;
    private ArrayList<GTreeItem> childs = new ArrayList<GTreeItem>();
    private String name;

    public GTreeItem(String name)
    {
      this.name = name;
    }

    public void addChild(GTreeItem child)
    {
      childs.add(child);
      child.parent = this;
    }

    public GTreeItem getParent()
    {
      return parent;
    }

    public ArrayList<GTreeItem> getChilds()
    {
      return childs;
    }

    public String getName()
    {
      return name;
    }
  }

  class GTreeResourceItem extends GTreeItem
  {
    private IResource resource;

    public GTreeResourceItem(String name, IResource resource)
    {
      super(name);
      this.resource = resource;
    }

    public IResource getResource()
    {
      return resource;
    }

    public void setResource(IResource resource)
    {
      this.resource = resource;
    }
  }

  class GTreeItemProject extends GTreeItem
  {
    public GTreeItemProject(String name)
    {
      super(name);
    }
  }

  class GTreeItemModels extends GTreeItem
  {
    public GTreeItemModels()
    {
      super("Models");
    }
  }

  class GTreeItemWorkloads extends GTreeItem
  {
    public GTreeItemWorkloads()
    {
      super("Workloads");
    }
  }

  class GTreeItemExperiments extends GTreeItem
  {
    public GTreeItemExperiments()
    {
      super("Experiments");
    }
  }

  class GTreeItemCharts extends GTreeItem
  {
    public GTreeItemCharts()
    {
      super("Gantt charts");
    }
  }

  class GTreeItemResults extends GTreeItem
  {
    public GTreeItemResults()
    {
      super("Visual results");
    }
  }

  class GTreeItemModel extends GTreeResourceItem
  {
    public GTreeItemModel(String name, IResource resource)
    {
      super(name, resource);
    }
  }

  class GTreeItemModelDiagram extends GTreeResourceItem
  {
    public GTreeItemModelDiagram(String name, IResource resource)
    {
      super(name + "(diagram)", resource);
    }
  }

  class GTreeItemWorkload extends GTreeResourceItem
  {
    public GTreeItemWorkload(String name, IResource resource)
    {
      super(name, resource);
    }
  }

  class GTreeExperiment extends GTreeResourceItem
  {
    private GTreeItem visuals;
    private GTreeItem charts;

    public GTreeExperiment(String name, IResource resource)
    {
      super(name, resource);
    }

    public void addVisual(String name, IResource res)
    {
      if(visuals == null)
      {
        visuals = new GTreeItemResults();
        addChild(visuals);
      }

      visuals.addChild(new GTreeVisual(name, res));
    }

    public void addChart(String name, IResource res)
    {
      if(charts == null)
      {
        charts = new GTreeItemCharts();
        addChild(charts);
      }

      charts.addChild(new GTreeChart(name, res));
    }
  }

  class GTreeChart extends GTreeResourceItem
  {
    public GTreeChart(String name, IResource resource)
    {
      super(name, resource);
    }
  }

  class GTreeVisual extends GTreeResourceItem
  {
    public GTreeVisual(String name, IResource resource)
    {
      super(name, resource);
    }
  }

  private void createModel()
  {
    root.getChilds().clear();
    try
    {
      for(IProject project : ResourcesPlugin.getWorkspace().getRoot()
          .getProjects())
      {
        if(project.isOpen()
            && project.hasNature("com.googlecode.gridme.ui.GridmeNature"))
        {
          GTreeItemProject tpro = new GTreeItemProject(project.getName());
          root.addChild(tpro);

          HashMap<String, GTreeItemModel> models = new HashMap<String, GTreeItemModel>();
          ArrayList<GTreeItem> workloads = new ArrayList<GTreeItem>();
          HashMap<String, GTreeExperiment> experiments = new HashMap<String, GTreeExperiment>();

          for(IResource child : project.members())
          {
            if(child.getType() == IResource.FILE)
            {
              String name = RuntimeUtils.removeExtension(child.getName());

              if(child.getFileExtension().equals("gmm"))
              {
                models.put(name, new GTreeItemModel(name, child));
              }
              else if(child.getFileExtension().equals("swfz"))
              {
                workloads.add(new GTreeItemWorkload(name, child));
              }
              else if(child.getFileExtension().equals("gme"))
              {
                experiments.put(name, new GTreeExperiment(name, child));
              }
            }
          }

          // Second pass to fill experiment and model child nodes
          for(IResource child : project.members())
          {
            if(child.getType() == IResource.FILE)
            {
              String name = RuntimeUtils.removeExtension(child.getName());

              if(child.getFileExtension().equals("gantt"))
              {
                addExpChild(name, child, GTreeChart.class, experiments);
              }
              else if(child.getFileExtension().equals("png"))
              {
                addExpChild(name, child, GTreeVisual.class, experiments);
              }
              else if(child.getFileExtension().equals("gmm_dia"))
              {
                GTreeItemModel m = models.get(name);
                if(m != null)
                {
                  m.addChild(new GTreeItemModelDiagram(name, child));
                }
              }
            }
          }

          if(!models.isEmpty())
          {
            GTreeItem ml = new GTreeItemModels();
            tpro.addChild(ml);

            for(GTreeItem treeItem : models.values())
            {
              ml.addChild(treeItem);
            }
          }

          if(!workloads.isEmpty())
          {
            GTreeItem vl = new GTreeItemWorkloads();
            tpro.addChild(vl);

            for(GTreeItem treeItem : workloads)
            {
              vl.addChild(treeItem);
            }
          }

          if(!experiments.isEmpty())
          {
            GTreeItem ex = new GTreeItemExperiments();
            tpro.addChild(ex);

            for(GTreeItem treeItem : experiments.values())
            {
              ex.addChild(treeItem);
            }
          }
        }
      }
    }
    catch(Exception e)
    {
      GridmeUIPlugin.logException(e);
    }
  }

  private void addExpChild(String name, IResource child,
      Class<? extends GTreeItem> c, HashMap<String, GTreeExperiment> experiments)
  {
    String[] s = name.split("\\.");
    
    if(s.length >= 2 && s.length <= 4)
    {
      Pattern p = Pattern.compile("\\w+\\.(.+)");
      Matcher m = p.matcher(name);
      
      if(m.matches())
      {
        String xname = m.group(1);
      
        GTreeExperiment exp = experiments.get(s[0]);
        if(exp != null)
        {
          if(c.equals(GTreeVisual.class))
          {
            exp.addVisual(xname.toString(), child);
          }
          else if(c.equals(GTreeChart.class))
          {
            exp.addChart(xname.toString(), child);
          }
        }
      }
    }
  }

  /**
   * The constructor.
   */
  public GridProjectView()
  {
  }

  /**
   * This is a callback that will allow us
   * to create the viewer and initialize it.
   */
  public void createPartControl(Composite parent)
  {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    viewer.setContentProvider(new MyContentProvider());
    viewer.setLabelProvider(new MyLabelProvider());
    root = new GTreeItem("root");
    createModel();
    viewer.setInput(root);
    makeActions();
    hookDoubleClickAction();
    contributeToActionBars();

    getSite().setSelectionProvider(viewer);
  }

  private void makeActions()
  {
    collapse = new Action()
    {
      public void run()
      {
        viewer.collapseAll();
      }
    };
    collapse.setText("Collapse");
    collapse.setToolTipText("Collapse all nodes");
    collapse.setImageDescriptor(GridmeUIPlugin
        .getImageDescriptor("collapse.png"));

    expand = new Action()
    {
      public void run()
      {
        viewer.expandAll();
      }
    };
    expand.setText("Expand");
    expand.setToolTipText("Expand all nodes");
    expand.setImageDescriptor(GridmeUIPlugin.getImageDescriptor("expand.png"));

    doubleClickAction = new Action()
    {
      public void run()
      {
        ISelection selection = viewer.getSelection();
        Object obj = ((IStructuredSelection) selection).getFirstElement();

        if(obj instanceof GTreeResourceItem)
        {
          IFile file = (IFile) ((GTreeResourceItem) obj).getResource();
          String editorID = null;
          IEditorInput editorInput = new FileEditorInput(file);

          if(obj instanceof GTreeVisual)
          {
            editorID = "com.googlecode.gridme.ui.visualizerEditor";
          }
          else
          {
            IEditorDescriptor ed = GridmeUIPlugin.getInstance().getWorkbench()
                .getEditorRegistry().getDefaultEditor(
                    "." + file.getFileExtension());
            if(ed != null)
            {
              editorID = ed.getId();
            }
          }

          PlatformUtils.openEditor(editorInput, editorID);
        }
      }
    };

    deleteAction = new Action()
    {
      public void run()
      {
        ISelection selection = viewer.getSelection();
        if(!selection.isEmpty() && PlatformUtils.askYesNo("Confirm delete", "Delete selected elements?"))
        {
          for(Iterator iterator = ((IStructuredSelection) selection).iterator(); iterator.hasNext();)
          {
            final GTreeResourceItem item = (GTreeResourceItem) iterator.next();
            deleteResource(item);
          }
        }
      }
    };
    deleteAction.setText("Delete");
    deleteAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
        .getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));

    hookContextMenu();
  }

  private void deleteResource(final GTreeResourceItem item)
  {
    viewer.remove(item);
    try
    {
      PlatformUI.getWorkbench().getProgressService().run(true, true,
          new IRunnableWithProgress()
          {
            public void run(final IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException
            {
              try
              {
                item.getResource().delete(true, monitor);
              }
              catch(CoreException e)
              {
                GridmeUIPlugin.logErrorMessage("Unable to remove resource "
                    + item.getResource().getName(), e);
              }
            }
          });
    }
    catch(Exception e)
    {
    }
  }

  private void hookDoubleClickAction()
  {
    viewer.addDoubleClickListener(new IDoubleClickListener()
    {
      public void doubleClick(DoubleClickEvent event)
      {
        doubleClickAction.run();
      }
    });
  }

  private void hookContextMenu()
  {
    MenuManager menuMgr = new MenuManager("#PopupMenu");
    menuMgr.setRemoveAllWhenShown(true);
    menuMgr.addMenuListener(new IMenuListener()
    {
      public void menuAboutToShow(IMenuManager manager)
      {
        ISelection selection = viewer.getSelection();
        Object obj = ((IStructuredSelection) selection).getFirstElement();

        if(obj != null && obj instanceof GTreeResourceItem)
        {
          fillContextMenu(manager);
        }
      }
    });
    Menu menu = menuMgr.createContextMenu(viewer.getControl());
    viewer.getControl().setMenu(menu);
    getSite().registerContextMenu(menuMgr, viewer);
  }

  private void fillContextMenu(IMenuManager manager)
  {
    manager.add(deleteAction);
    // Other plug-ins can contribute there actions here
    manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
  }

  private void contributeToActionBars()
  {
    IActionBars bars = getViewSite().getActionBars();
    fillLocalToolBar(bars.getToolBarManager());
  }

  private void fillLocalToolBar(IToolBarManager manager)
  {
    manager.add(expand);
    manager.add(collapse);
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  public void setFocus()
  {
    viewer.getControl().setFocus();
  }
}
