package net.sf.anathema.hero.spells.model;

import net.sf.anathema.hero.charms.model.learn.MagicLearner;
import net.sf.anathema.hero.spells.data.SpellImpl;
import net.sf.anathema.magic.data.Magic;

import java.util.Arrays;
import java.util.Collection;

public class SpellsLearner implements MagicLearner {
  private SpellsModelImpl spellsModel;

  public SpellsLearner(SpellsModelImpl spellsModel) {
    this.spellsModel = spellsModel;
  }

  @Override
  public boolean handlesMagic(Magic magic) {
    return magic instanceof SpellImpl;
  }

  @Override
  public int getAdditionalBonusPoints(Magic magic) {
    return 0;
  }

  @Override
  public int getCreationLearnCount(Magic magic) {
    return 1;
  }

  @Override
  public Collection<? extends Magic> getLearnedMagic(boolean experienced) {
    return spellsModel.getLearnedSpells(experienced).asList();
  }
}
