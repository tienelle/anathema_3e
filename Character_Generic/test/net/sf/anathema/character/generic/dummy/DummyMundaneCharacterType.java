package net.sf.anathema.character.generic.dummy;

import net.sf.anathema.character.generic.template.magic.FavoringTraitType;
import net.sf.anathema.character.generic.type.ICharacterType;

public class DummyMundaneCharacterType implements ICharacterType {

  @Override
  public boolean isExaltType() {
    return false;
  }

  @Override
  public boolean isEssenceUser() {
    return false;
  }

  @Override
  public FavoringTraitType getFavoringTraitType() {
    return FavoringTraitType.AbilityType;
  }

  @Override
  public String getId() {
    return "Dummy";
  }
}