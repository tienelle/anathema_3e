package net.sf.anathema.hero.languages.display;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import net.miginfocom.layout.CC;
import net.sf.anathema.character.main.library.overview.OverviewCategory;
import net.sf.anathema.hero.languages.display.overview.FxOverviewCategory;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.lib.gui.AgnosticUIConfiguration;
import net.sf.anathema.platform.fx.NodeHolder;
import org.tbee.javafx.scene.layout.MigPane;

import static net.sf.anathema.lib.gui.layout.LayoutUtils.withoutInsets;

public class FxLanguagesView implements LanguagesView, NodeHolder {

  private final MigPane selectionPanel = new MigPane(withoutInsets().fillX());
  private final MigPane entryPanel = new MigPane(withoutInsets().wrapAfter(2).fillX());
  private final MigPane overviewPanel = new MigPane(withoutInsets());
  private final MigPane panel = new MigPane(withoutInsets());

  public FxLanguagesView() {
    MigPane mainPanel = new MigPane(withoutInsets().wrapAfter(1));
    mainPanel.add(selectionPanel);
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(entryPanel);
    mainPanel.add(scrollPane, new CC().grow().pushY());
    panel.add(mainPanel, new CC().grow().pushY());
    panel.add(overviewPanel, new CC().alignY("top"));
  }

  @Override
  public Node getNode() {
    return panel;
  }

  @Override
  public ObjectSelectionViewWithTool<Object> addSelectionView(String labelText, AgnosticUIConfiguration<Object> renderer) {
    FxSelectionViewWithTool<Object> view = new FxSelectionViewWithTool<>(renderer, labelText);
    view.addTo(selectionPanel);
    return view;
  }

  @Override
  public OverviewCategory addOverview(String label) {
    return new FxOverviewCategory(overviewPanel, label);
  }

  @Override
  public RemovableEntryView addEntryView(RelativePath removeIcon, String label) {
    FxRemovableStringView view = new FxRemovableStringView(removeIcon, label);
    view.addTo(entryPanel);
    return view;
  }
}