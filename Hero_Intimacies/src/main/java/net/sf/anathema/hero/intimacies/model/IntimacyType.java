package net.sf.anathema.hero.intimacies.model;

import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.types.ITraitTypeVisitor;
import net.sf.anathema.lib.exception.NotYetImplementedException;
import net.sf.anathema.lib.util.SimpleIdentifier;

public class IntimacyType extends SimpleIdentifier implements TraitType {

  public IntimacyType(String name) {
    super(name);
  }

  @Override
  public void accept(ITraitTypeVisitor visitor) {
    throw new NotYetImplementedException();
  }
}