package net.sf.anathema.character.impl.model.advance;

import net.sf.anathema.character.generic.IBasicCharacterData;
import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.library.trait.IDefaultTrait;

public interface IPointCostCalculator {

  int getAbilityCosts(IDefaultTrait ability, boolean favored);

  int getAttributeCosts(IDefaultTrait attribute, boolean favored);

  int getEssenceCosts(IDefaultTrait essence);

  int getVirtueCosts(IDefaultTrait virtue);

  int getWillpowerCosts(IDefaultTrait willpower);

  double getSpecialtyCosts(boolean favored);

  int getSpellCosts(ISpell spell, IBasicCharacterData basicCharacter, IGenericTraitCollection traitCollection);

  int getCharmCosts(ICharm charm, IBasicCharacterData basicCharacter, IGenericTraitCollection traitCollection);
}