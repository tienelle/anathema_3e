package net.sf.anathema.hero.merits.model;

import java.util.Collection;
import java.util.List;

import net.sf.anathema.hero.individual.model.HeroModel;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;
import net.sf.anathema.library.model.OptionalEntryReference;
import net.sf.anathema.library.model.RemovableEntryModel;
import net.sf.anathema.library.model.trait.OptionalTraitsModel;

public interface MeritsModel extends RemovableEntryModel<Merit>, HeroModel,
	OptionalTraitsModel<MeritCategory, MeritOption, Merit>{

  Identifier ID = new SimpleIdentifier("Merits");

  boolean hasMeritsMatchingReference(OptionalEntryReference reference);

  List<Merit> getMeritsMatchingReference(OptionalEntryReference option);

  boolean isEntryAllowed();

  void addSuggestions(OptionalEntryReference merit, Collection<String> suggestions);
}