package net.sf.anathema.character.impl.model.advance.models;

import net.sf.anathema.character.generic.template.abilities.GroupedTraitType;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.impl.model.advance.IPointCostCalculator;
import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.main.model.traits.TraitMap;
import net.sf.anathema.character.model.ICharacter;

import java.util.ArrayList;
import java.util.List;

public class AttributeExperienceModel extends AbstractIntegerValueModel {

  private final TraitMap traitMap;
  private final IPointCostCalculator calculator;
  private final ICharacter character;

  public AttributeExperienceModel(TraitMap traitMap, IPointCostCalculator calculator, ICharacter character) {
    super("Experience", "Attributes");
    this.traitMap = traitMap;
    this.calculator = calculator;
    this.character = character;
  }

  @Override
  public Integer getValue() {
    return getAttributeCosts();
  }

  private int getAttributeCosts() {
    int experienceCosts = 0;
    for (Trait attribute : getAllAttributes()) {
      experienceCosts += calculator.getAttributeCosts(attribute, attribute.getFavorization().isCaste() || attribute.getFavorization().isFavored());
    }
    return experienceCosts;
  }

  private Trait[] getAllAttributes() {
    List<TraitType> attributeTypes = new ArrayList<>();
    for (GroupedTraitType type : character.getTemplate().getAttributeGroups()) {
      attributeTypes.add(type.getTraitType());
    }
    return traitMap.getTraits(attributeTypes.toArray(new TraitType[attributeTypes.size()]));
  }
}