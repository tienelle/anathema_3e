package net.sf.anathema.character.equipment.item;

import net.sf.anathema.equipment.core.MagicalMaterial;
import net.sf.anathema.library.presenter.AbstractUIConfiguration;
import net.sf.anathema.library.resources.Resources;

public class MaterialUi extends AbstractUIConfiguration<MagicalMaterial> {
  private Resources resources;

  public MaterialUi(Resources resources) {
    this.resources = resources;
  }

  @Override
  public String getLabel(MagicalMaterial value) {
    if (value == null) {
      return NO_LABEL;
    }
    return resources.getString("MagicMaterial." + value.getId());
  }
}