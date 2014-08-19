package net.sf.anathema.hero.merits.display;

import javafx.scene.Node;
import net.sf.anathema.library.event.ObjectChangedListener;
import net.sf.anathema.library.fx.configurableview.FxConfigurableSingleLineView;
import net.sf.anathema.library.fx.selection.ComboBoxSelectionView;
import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.presenter.AgnosticUIConfiguration;
import net.sf.anathema.library.text.ITextView;
import net.sf.anathema.library.view.ObjectSelectionView;

public class FxMeritsEntryView implements MeritEntryView {
  private final FxConfigurableSingleLineView view = new FxConfigurableSingleLineView();
  private ComboBoxSelectionView<String> meritSelectionView;
  private ITextView textView;

  @Override
  public void addTextChangeListener(ObjectChangedListener<String> listener) {
    textView.addTextChangedListener(listener);
  }

  public Tool addTool() {
    return view.addEditAction();
  }

  @Override
  public void clear() {
    textView.setText(null);
    meritSelectionView.clearSelection();
  }
  
  @Override
  public <T> ObjectSelectionView<T> addMeritSelection(AgnosticUIConfiguration<T> uiConfiguration) {
    meritSelectionView = (ComboBoxSelectionView<String>) view.addSelectionView("", uiConfiguration);
    meritSelectionView.makeEditable();
    return (ObjectSelectionView<T>) meritSelectionView;
  }

  @Override
  public <T> ObjectSelectionView<T> addSelection(AgnosticUIConfiguration<T> uiConfiguration) {
    return view.addSelectionView("", uiConfiguration);
  }

  public Node getNode() {
    return view.getNode();
  }

  @Override
  public void addDescriptionBox(String label) {
	  this.textView = view.addLineView(label);
  }
}