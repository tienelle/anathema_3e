package net.sf.anathema.hero.specialties.display.view;

import net.sf.anathema.character.main.library.util.CssSkinner;
import net.sf.anathema.character.main.type.CharacterType;
import net.sf.anathema.hero.display.ExtensibleTraitView;
import net.sf.anathema.character.main.traits.TraitType;
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

  public BridgingSpecialtiesView(FxSpecialtiesView fxView, CharacterType type) {
    this.fxView = fxView;
    String[] skins = new CssSkinner().getSkins(type);
    panel.init(fxView, skins);
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
  public SpecialtyCreationView addSpecialtyCreationView(AgnosticUIConfiguration<TraitType> configuration, RelativePath addIcon) {
    return fxView.addSpecialtyCreationView(configuration, addIcon);
  }
}
