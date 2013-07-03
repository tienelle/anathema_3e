package net.sf.anathema.character.library.trait.view.fx;

import javafx.scene.Node;
import javafx.scene.control.Label;
import net.miginfocom.layout.CC;
import net.sf.anathema.character.view.ColumnCount;
import net.sf.anathema.lib.gui.layout.LayoutUtils;
import net.sf.anathema.platform.fx.FxThreading;
import org.tbee.javafx.scene.layout.MigPane;

public class FxGroupedColumnPanel {
  private final MigPane[] columns;
  private int columnIndex = -1;

  public FxGroupedColumnPanel(MigPane pane, ColumnCount columnCount) {
    this.columns = new MigPane[columnCount.getColumnCount()];
    for (int i = 0; i < columns.length; i++) {
      columns[i] = new MigPane(LayoutUtils.withoutInsets().wrapAfter(3));
    }
    addColumnsToContainer(pane);
  }

  private void increaseColumnIndex() {
    columnIndex++;
    if (columnIndex >= columns.length) {
      columnIndex = 0;
    }
  }

  private void addColumnsToContainer(MigPane container) {
    container.setLayoutConstraints(LayoutUtils.withoutInsets().wrapAfter(columns.length).gridGapX("15"));
    for (MigPane column : columns) {
      container.add(column, new CC().alignY("top"));
    }
  }

  public void startNewGroup(final String groupLabel) {
    FxThreading.runOnCorrectThread(new Runnable() {
      @Override
      public void run() {
        increaseColumnIndex();
        if (groupLabel != null) {
          getCurrentColumn().add(new Label(groupLabel), new CC().gapTop("5").spanX().growX().pushX());
        }
      }
    });
  }

  public MigPane getCurrentColumn() {
    return columns[columnIndex];
  }

  public void add(final Node node) {
    FxThreading.runOnCorrectThread(new Runnable() {
      @Override
      public void run() {
        getCurrentColumn().add(node);
      }
    });
  }
}