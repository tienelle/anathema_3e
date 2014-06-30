package net.sf.anathema.cascades.presenter;

import net.sf.anathema.hero.charms.model.options.CharmTreeCategory;
import net.sf.anathema.hero.magic.charm.martial.MartialArtsLevel;
import net.sf.anathema.hero.magic.charm.martial.MartialArtsUtilities;
import net.sf.anathema.hero.framework.type.CharacterType;
import net.sf.anathema.hero.framework.type.CharacterTypes;
import net.sf.anathema.hero.charms.compiler.CharmProvider;
import net.sf.anathema.hero.charms.model.CharmGroupCollection;
import net.sf.anathema.hero.charms.model.ICharmGroup;
import net.sf.anathema.hero.charms.model.options.CharmTreeCategoryImpl;
import net.sf.anathema.hero.charms.model.options.MartialArtsCharmTreeCategory;
import net.sf.anathema.lib.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CascadeGroupCollection implements CharmGroupCollection {
  private final CharacterTypes characterTypes;
  private CharmProvider charmProvider;
  private CharmTreeIdentifierMap treeIdentifierMap;

  public CascadeGroupCollection(CharmProvider charmProvider, CharacterTypes characterTypes, CharmTreeIdentifierMap treeIdentifierMap) {
    this.charmProvider = charmProvider;
    this.treeIdentifierMap = treeIdentifierMap;
    this.characterTypes = characterTypes;
  }

  @Override
  public ICharmGroup[] getCharmGroups() {
    List<ICharmGroup> allCharmGroups = new ArrayList<>();
    initCharacterTypeCharms(allCharmGroups);
    initMartialArtsCharms(allCharmGroups);
    return allCharmGroups.toArray(new ICharmGroup[allCharmGroups.size()]);
  }

  private void initCharacterTypeCharms(List<ICharmGroup> allCharmGroups) {
    for (CharacterType type : characterTypes) {
       if (charmProvider.getCharms(type).length > 0) {
        registerTypeCharms(allCharmGroups, type);
      }
    }
  }

  private void initMartialArtsCharms(List<ICharmGroup> allCharmGroups) {
    CharmTreeCategory martialArtsTree = new MartialArtsCharmTreeCategory(charmProvider, MartialArtsLevel.Sidereal);
    treeIdentifierMap.put(MartialArtsUtilities.MARTIAL_ARTS, martialArtsTree);
    allCharmGroups.addAll(Arrays.asList(martialArtsTree.getAllCharmGroups()));
  }

  private void registerTypeCharms(List<ICharmGroup> allCharmGroups, CharacterType type) {
    CharmTreeCategory typeTree = new CharmTreeCategoryImpl(charmProvider.getCharms(type));
    registerGroups(allCharmGroups, type, typeTree);
  }

  private void registerGroups(List<ICharmGroup> allCharmGroups, Identifier typeId, CharmTreeCategory charmTreeCategory) {
    ICharmGroup[] groups = charmTreeCategory.getAllCharmGroups();
    if (groups.length != 0) {
      treeIdentifierMap.put(typeId, charmTreeCategory);
      allCharmGroups.addAll(Arrays.asList(groups));
    }
  }
}