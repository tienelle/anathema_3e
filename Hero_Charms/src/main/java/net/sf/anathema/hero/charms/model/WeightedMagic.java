package net.sf.anathema.hero.charms.model;

import net.sf.anathema.library.sort.WeightedObject;
import net.sf.anathema.magic.data.Magic;

public class WeightedMagic extends WeightedObject<Magic> {

  public WeightedMagic(Magic magic, int weight) {
    super(magic, weight);
  }
}