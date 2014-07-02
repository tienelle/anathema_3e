package net.sf.anathema.hero.magic.charm.prerequisite;

import com.google.common.base.Preconditions;
import net.sf.anathema.charm.data.reference.CharmName;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.magic.charm.ICharmLearnArbitrator;
import net.sf.anathema.hero.magic.charm.ICharmLearnableArbitrator;
import net.sf.anathema.hero.magic.charm.UnlinkedCharmMap;

import java.util.HashSet;
import java.util.Set;

public class SimpleCharmPrerequisite implements DirectCharmPrerequisite {
  private static final Charm PREREQUISITE_NOT_SET = null;
  private final CharmName prerequisiteId;
  private Charm prerequisite;

  public SimpleCharmPrerequisite(CharmName charm) {
    this.prerequisiteId = charm;
  }

  public SimpleCharmPrerequisite(Charm charm) {
    this.prerequisite = charm;
    this.prerequisiteId = charm.getName();
  }

  @Override
  public Charm[] getDirectPredecessors() {
    return new Charm[]{prerequisite};
  }

  @Override
  public boolean isSatisfied(ICharmLearnArbitrator arbitrator) {
    return arbitrator.isLearned(prerequisite);
  }

  @Override
  public boolean isAutoSatisfiable(ICharmLearnableArbitrator arbitrator) {
    return arbitrator.isLearnable(prerequisite);
  }

  @Override
  public Charm[] getLearnPrerequisites(ICharmLearnArbitrator arbitrator) {
    if (prerequisite == PREREQUISITE_NOT_SET) {
      throw new IllegalStateException(
              "The prerequisite Charm isn't linked yet. Please call ``link(Map)`` prior to using this object.");
    }
    Set<Charm> prerequisiteCharms = new HashSet<>();
    prerequisiteCharms.addAll(prerequisite.getLearnPrerequisitesCharms(arbitrator));
    prerequisiteCharms.add(prerequisite);
    return prerequisiteCharms.toArray(new Charm[prerequisiteCharms.size()]);
  }

  @Override
  public void accept(CharmPrerequisiteVisitor visitor) {
    visitor.requiresCharm(prerequisite);
  }

  @Override
  public void link(UnlinkedCharmMap charmsById) {
    if (prerequisite != PREREQUISITE_NOT_SET) {
      return;
    }
    prerequisite = charmsById.get(prerequisiteId);
    Preconditions.checkNotNull(prerequisite, "Parent Charm " + prerequisiteId + " not defined.");
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof SimpleCharmPrerequisite) {
      SimpleCharmPrerequisite prerequisite = (SimpleCharmPrerequisite) obj;
      return prerequisite.prerequisite.equals(prerequisite);
    }
    return false;
  }
}