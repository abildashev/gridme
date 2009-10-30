package com.googlecode.gridme.ui.editors;

import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class WorkloadTextEditor extends TextEditor
{

  @Override
  public IDocumentProvider getDocumentProvider()
  {
    return new WorkloadDocumentProvider();
  }
}
