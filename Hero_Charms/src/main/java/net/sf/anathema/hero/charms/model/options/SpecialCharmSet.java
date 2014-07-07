package net.sf.anathema.hero.charms.model.options;

import net.sf.anathema.hero.charms.model.special.ISpecialCharm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpecialCharmSet {

  private final List<ISpecialCharm> list = new ArrayList<>();

  public void add(ISpecialCharm identificate) {
    for (ISpecialCharm existing : new ArrayList<>(list)) {
      if (existing.getCharmName().equals(identificate.getCharmName())) {
        list.remove(existing);
      }
    }
    list.add(identificate);
  }

  public ISpecialCharm[] toArray(ISpecialCharm[] array) {
    return list.toArray(array);
  }

  public int size() {
    return list.size();
  }

  public void addAll(Collection<ISpecialCharm> identificates) {
    for (ISpecialCharm identificate : identificates) {
      add(identificate);
    }
  }
}
