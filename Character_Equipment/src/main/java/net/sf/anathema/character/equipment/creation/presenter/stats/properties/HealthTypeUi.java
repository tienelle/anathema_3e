package net.sf.anathema.character.equipment.creation.presenter.stats.properties;

import net.sf.anathema.hero.health.model.HealthType;
import net.sf.anathema.library.presenter.AbstractUIConfiguration;
import net.sf.anathema.library.resources.Resources;

public class HealthTypeUi extends AbstractUIConfiguration<HealthType> {

  private final Resources resources;

  public HealthTypeUi(Resources resources) {
    this.resources = resources;
  }

  @Override
  public String getLabel(HealthType value) {
    if (value == null) {
      return resources.getString("ComboBox.SelectLabel");
    }
    return resources.getString("HealthType." + value.name() + ".Capitalized");
  }
}