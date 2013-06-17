package net.sf.anathema.character.main.abilities;

import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.traits.groups.IIdentifiedTraitTypeGroup;
import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.library.trait.TraitGroup;
import net.sf.anathema.character.library.trait.specialties.ISpecialtiesConfiguration;
import net.sf.anathema.character.main.traits.model.TraitMap;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public interface AbilityModel extends TraitMap{

  Identifier ID = new SimpleIdentifier("Abilities");

  Trait[] getAll();

  TraitGroup[] getTraitGroups();

  Trait getTrait(TraitType type);

  IIdentifiedTraitTypeGroup[] getAbilityTypeGroups();

  ISpecialtiesConfiguration getSpecialtyConfiguration();
}