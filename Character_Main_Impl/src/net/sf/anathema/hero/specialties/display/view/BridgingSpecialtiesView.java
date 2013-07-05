package net.sf.anathema.hero.specialties.display.view;

import net.sf.anathema.character.generic.framework.ITraitReference;
import net.sf.anathema.character.presenter.ExtensibleTraitView;
import net.sf.anathema.framework.swing.IView;
import net.sf.anathema.hero.specialties.display.presenter.SpecialtiesConfigurationView;
import net.sf.anathema.hero.specialties.display.presenter.SpecialtyCreationView;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.lib.gui.AgnosticUIConfiguration;
import net.sf.anathema.platform.fx.BridgingPanel;

import javax.swing.JComponent;

public class BridgingSpecialtiesView implements SpecialtiesConfigurationView, IView {
  private final FxSpecialtiesView fxView;
  private final BridgingPanel panel = new BridgingPanel();

  public BridgingSpecialtiesView(FxSpecialtiesView fxView) {
    this.fxView = fxView;
    panel.init(fxView);
  }

  @Override
  public JComponent getComponent() {
    return panel.getComponent();
  }

  @Override
  public ExtensibleTraitView addSpecialtyView(String abilityName, String specialtyName, RelativePath deleteIcon, int value, int maxValue) {
    return fxView.addSpecialtyView(abilityName, specialtyName, deleteIcon, value, maxValue);
  }

  @Override
  public SpecialtyCreationView addSpecialtySelectionView(AgnosticUIConfiguration<ITraitReference> configuration, RelativePath addIcon) {
    return fxView.addSpecialtySelectionView(configuration, addIcon);
  }
}