package net.sf.anathema.framework.repository.tree;

import net.sf.anathema.framework.swing.IView;

import javax.swing.Action;
import javax.swing.JTree;

public interface IRepositoryTreeView extends IView {

  void addActionButton(Action action);

  JTree addTree();
}