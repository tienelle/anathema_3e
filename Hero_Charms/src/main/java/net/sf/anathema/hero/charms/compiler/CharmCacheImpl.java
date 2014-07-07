package net.sf.anathema.hero.charms.compiler;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.charm.data.reference.CategoryReference;
import net.sf.anathema.charm.data.reference.CharmName;
import net.sf.anathema.hero.charms.model.special.ISpecialCharm;
import net.sf.anathema.library.collection.MultiEntryMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class CharmCacheImpl implements CharmCache {

  private MultiEntryMap<CategoryReference, Charm> charmsByCategory = new MultiEntryMap<>();
  private Map<CategoryReference, List<ISpecialCharm>> specialCharmsByCategory = new HashMap<>();
  private Map<CharmName, Charm> charmsById = new HashMap<>();

  @Override
  public Charm getCharmById(CharmName charmName) {
    return charmsById.get(charmName);
  }

  @Override
  public List<Charm> getCharms(CategoryReference type) {
    if (!charmsByCategory.containsKey(type)) {
      return Collections.emptyList();
    }
    List<Charm> charmList = charmsByCategory.get(type);
    return new ArrayList<>(charmList);
  }

  @Override
  public List<CategoryReference> getAllCategories() {
    return new ArrayList<>(charmsByCategory.keySet());
  }

  @Override
  public List<ISpecialCharm> getSpecialCharms(CategoryReference type) {
    if (specialCharmsByCategory.containsKey(type)) {
      List<ISpecialCharm> specials = specialCharmsByCategory.get(type);
      return new ArrayList<>(specials);
    }
    return Collections.emptyList();
  }

  public Iterable<Charm> getCharms() {
    List<Charm> allCharms = new ArrayList<>();
    for (CategoryReference type : charmsByCategory.keySet()) {
      for (Charm charm : charmsByCategory.get(type)) {
        allCharms.add(charm);
      }
    }
    return allCharms;
  }

  public void addCharm(Charm charm) {
    charmsByCategory.replace(charm.getTreeReference().category, charm, charm);
    charmsById.put(charm.getName(), charm);
  }

  public void addSpecial(CategoryReference type, List<ISpecialCharm> specialCharms) {
    if (specialCharms == null) {
      return;
    }
    List<ISpecialCharm> cachedList = getCachedSpecialCharmList(type);
    cachedList.addAll(specialCharms);
  }

  private List<ISpecialCharm> getCachedSpecialCharmList(CategoryReference type) {
    if (!specialCharmsByCategory.containsKey(type)) {
      specialCharmsByCategory.put(type, new ArrayList<>());
    }
    return specialCharmsByCategory.get(type);
  }

  public void addSpecial(ISpecialCharm specialCharm) {
    Charm charm = getCharmById(specialCharm.getCharmName());
    addSpecial(charm.getTreeReference().category, singletonList(specialCharm));
  }
}