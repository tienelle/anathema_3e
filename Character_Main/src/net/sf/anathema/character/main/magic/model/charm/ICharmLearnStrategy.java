package net.sf.anathema.character.main.magic.model.charm;

import net.sf.anathema.character.main.magic.model.charm.ICharm;
import net.sf.anathema.character.main.magic.model.charms.IBasicLearnCharmGroup;

public interface ICharmLearnStrategy {

  boolean isUnlearnable(IBasicLearnCharmGroup group, ICharm charm);

  boolean isLearned(IBasicLearnCharmGroup group, ICharm charm);

  void toggleLearned(IBasicLearnCharmGroup group, ICharm charm);
}