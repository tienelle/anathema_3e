package net.sf.anathema.character.presenter;

import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.library.trait.presenter.TraitPresenter;
import net.sf.anathema.character.view.IBasicAdvantageView;
import net.sf.anathema.framework.value.IIntValueView;
import net.sf.anathema.lib.gui.Presenter;
import net.sf.anathema.lib.resources.Resources;

public class WillpowerConfigurationPresenter implements Presenter {

  private final Trait willpower;
  private final IBasicAdvantageView view;
  private final Resources resources;

  public WillpowerConfigurationPresenter(Resources resources, Trait willpower, IBasicAdvantageView view) {
    this.resources = resources;
    this.willpower = willpower;
    this.view = view;
  }

  @Override
  public void initPresentation() {
    String labelText = resources.getString("WillpowerType.Name");
    IIntValueView willpowerView = view.addWillpower(labelText, willpower.getCurrentValue(), willpower.getMaximalValue());
    new TraitPresenter(willpower, willpowerView).initPresentation();
  }
}
