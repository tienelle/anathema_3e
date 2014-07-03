package net.sf.anathema.magic;

import net.sf.anathema.charm.data.reference.MagicName;
import net.sf.anathema.charm.old.cost.CostList;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.magic.attribute.MagicAttribute;
import net.sf.anathema.magic.source.SourceBook;

public interface Magic {

  MagicName getName();

  MagicAttribute[] getAttributes();

  SourceBook[] getSources();

  CostList getTemporaryCost();

  boolean hasAttribute(Identifier attribute);
}