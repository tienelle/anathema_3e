package net.sf.anathema.hero.charms.model.learn.prerequisites;

import net.sf.anathema.hero.magic.charm.ICharmLearnArbitrator;
import net.sf.anathema.hero.magic.charm.prerequisite.CharmPrerequisite;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class AllSatisfied implements Consumer<CharmPrerequisite> {
  private final ICharmLearnArbitrator learnArbitrator;
  private Predicate<CharmPrerequisite> predicate;
  public boolean isFulfilled;

  public AllSatisfied(ICharmLearnArbitrator learnArbitrator, Predicate<CharmPrerequisite> predicate) {
    this.learnArbitrator = learnArbitrator;
    this.predicate = predicate;
    isFulfilled = true;
  }

  @Override
  public void accept(CharmPrerequisite prerequisite) {
    if (!isFulfilled || !predicate.test(prerequisite)) {
      return;
    }
    IsSatisfied satisfied = new IsSatisfied(learnArbitrator);
    prerequisite.process(satisfied);
    isFulfilled = satisfied.satisfied;
  }
}