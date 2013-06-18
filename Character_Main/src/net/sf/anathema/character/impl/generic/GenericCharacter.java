package net.sf.anathema.character.impl.generic;

import com.google.common.collect.Lists;
import net.sf.anathema.character.generic.additionaltemplate.IAdditionalModel;
import net.sf.anathema.character.generic.caste.ICasteType;
import net.sf.anathema.character.generic.character.IConcept;
import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.generic.framework.ITraitReference;
import net.sf.anathema.character.generic.health.HealthLevelType;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.IGenericCombo;
import net.sf.anathema.character.generic.magic.IMagic;
import net.sf.anathema.character.generic.magic.IMagicVisitor;
import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.generic.magic.charms.special.IMultiLearnableCharm;
import net.sf.anathema.character.generic.magic.charms.special.ISpecialCharmConfiguration;
import net.sf.anathema.character.generic.magic.charms.special.ISubeffect;
import net.sf.anathema.character.generic.template.HeroTemplate;
import net.sf.anathema.character.generic.template.ITraitLimitation;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.traits.groups.IIdentifiedTraitTypeGroup;
import net.sf.anathema.character.generic.traits.types.OtherTraitType;
import net.sf.anathema.character.impl.model.advance.ExperiencePointManagement;
import net.sf.anathema.character.library.trait.specialties.ISpecialtiesConfiguration;
import net.sf.anathema.character.library.trait.specialties.Specialty;
import net.sf.anathema.character.library.trait.subtrait.ISpecialtyListener;
import net.sf.anathema.character.main.model.abilities.AbilityModelFetcher;
import net.sf.anathema.character.main.model.attributes.AttributeModelFetcher;
import net.sf.anathema.character.main.model.concept.CharacterConcept;
import net.sf.anathema.character.main.model.concept.CharacterConceptFetcher;
import net.sf.anathema.character.main.model.description.HeroDescriptionFetcher;
import net.sf.anathema.character.main.model.essencepool.EssencePoolModelFetcher;
import net.sf.anathema.character.main.model.experience.ExperienceModelFetcher;
import net.sf.anathema.character.main.model.health.HealthModelFetcher;
import net.sf.anathema.character.main.model.othertraits.OtherTraitModelFetcher;
import net.sf.anathema.character.main.model.traits.GenericTraitCollectionFacade;
import net.sf.anathema.character.main.model.traits.TraitMap;
import net.sf.anathema.character.main.model.traits.TraitModelFetcher;
import net.sf.anathema.character.model.ICharacter;
import net.sf.anathema.character.model.ITypedDescription;
import net.sf.anathema.character.model.charm.ICharmConfiguration;
import net.sf.anathema.character.model.charm.ICombo;
import net.sf.anathema.character.model.charm.ILearningCharmGroup;
import net.sf.anathema.character.model.charm.special.IMultiLearnableCharmConfiguration;
import net.sf.anathema.character.model.charm.special.IMultipleEffectCharmConfiguration;
import net.sf.anathema.character.model.charm.special.ISubeffectCharmConfiguration;
import net.sf.anathema.lib.control.IChangeListener;
import net.sf.anathema.lib.exception.ContractFailedException;
import net.sf.anathema.lib.util.IdentifiedInteger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericCharacter implements IGenericCharacter {

  private final ICharacter character;

  public GenericCharacter(ICharacter character) {
    this.character = character;
  }

  @Override
  public IGenericTraitCollection getTraitCollection() {
    return new GenericTraitCollectionFacade(getTraitMap());
  }

  @Override
  public int getLearnCount(IMultiLearnableCharm charm) {
    return getLearnCount(charm.getCharmId());
  }

  @Override
  public int getLearnCount(String charmName) {
    ICharmConfiguration charms = character.getCharms();
    try {
      IMultiLearnableCharmConfiguration configuration = (IMultiLearnableCharmConfiguration) charms.getSpecialCharmConfiguration(charmName);
      return configuration.getCurrentLearnCount();
    } catch (IllegalArgumentException e) {
      return 0;
    }
  }

  @Override
  public void setLearnCount(IMultiLearnableCharm multiLearnableCharm, int newValue) {
    setLearnCount(multiLearnableCharm.getCharmId(), newValue);
  }

  @Override
  public void setLearnCount(String charmName, int newValue) {
    ICharmConfiguration charms = character.getCharms();
    IMultiLearnableCharmConfiguration configuration = (IMultiLearnableCharmConfiguration) charms.getSpecialCharmConfiguration(charmName);
    configuration.setCurrentLearnCount(newValue);
  }

  @Override
  public boolean isLearned(IMagic magic) {
    final boolean[] isLearned = new boolean[1];
    magic.accept(new IMagicVisitor() {
      @Override
      public void visitSpell(ISpell spell) {
        isLearned[0] = character.getSpells().isLearned(spell);
      }

      @Override
      public void visitCharm(ICharm charm) {
        isLearned[0] = character.getCharms().isLearned(charm);
      }
    });
    return isLearned[0];
  }

  @Override
  public IGenericDescription getDescription() {
    return new GenericDescription(HeroDescriptionFetcher.fetch(character));
  }

  @Override
  public boolean isAlienCharm(ICharm charm) {
    return character.getCharms().isAlienCharm(charm);
  }

  @Override
  public HeroTemplate getTemplate() {
    return character.getTemplate();
  }

  @Override
  public Specialty[] getSpecialties(TraitType traitType) {
    ISpecialtiesConfiguration specialtyConfiguration = AbilityModelFetcher.fetch(character).getSpecialtyConfiguration();
    return specialtyConfiguration.getSpecialtiesContainer(traitType).getSubTraits();
  }

  @Override
  public ICasteType getCasteType() {
    CharacterConcept characterConcept = CharacterConceptFetcher.fetch(character);
    ITypedDescription<ICasteType> caste = characterConcept.getCaste();
    return caste.getType();
  }

  @Override
  public int getHealthLevelTypeCount(HealthLevelType type) {
    return HealthModelFetcher.fetch(character).getHealthLevelTypeCount(type);
  }

  @Override
  public String getPeripheralPool() {
    try {
      return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(character).getPeripheralPool() : null;
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  @Override
  public int getPeripheralPoolValue() {
    return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(character).getPeripheralPoolValue() : 0;
  }

  @Override
  public String getPersonalPool() {
    try {
      return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(character).getPersonalPool() : null;
    } catch (ContractFailedException e) {
      return null;
    }
  }

  @Override
  public int getPersonalPoolValue() {
    return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(character).getPersonalPoolValue() : 0;
  }

  @Override
  public int getOverdrivePoolValue() {
    return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(character).getOverdrivePoolValue() : 0;
  }

  @Override
  public IdentifiedInteger[] getComplexPools() {
    if (getTemplate().getEssenceTemplate().isEssenceUser()) {
      return EssencePoolModelFetcher.fetch(character).getComplexPools();
    } else {
      return new IdentifiedInteger[0];
    }
  }

  @Override
  public int getAttunedPoolValue() {
    return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(character).getAttunedPoolValue() : 0;
  }

  @Override
  public <T> List<T> getAllRegistered(Class<T> interfaceClass) {
    IAdditionalModel[] additionalModels = character.getExtendedConfiguration().getAdditionalModels();
    List<T> registeredModels = Lists.newArrayList();
    for (IAdditionalModel additionalModel : additionalModels) {
      if (interfaceClass.isInstance(additionalModel)) {
        registeredModels.add((T) additionalModel);
      }
    }
    return registeredModels;
  }

  @Override
  public IAdditionalModel getAdditionalModel(String id) {
    return character.getExtendedConfiguration().getAdditionalModel(id);
  }

  @Override
  public IConcept getConcept() {
    return new GenericConcept(CharacterConceptFetcher.fetch(character));
  }

  @Override
  public List<IMagic> getAllLearnedMagic() {
    List<IMagic> magicList = new ArrayList<>();
    magicList.addAll(Arrays.asList(getLearnedCharms()));
    magicList.addAll(Arrays.asList(character.getSpells().getLearnedSpells(ExperienceModelFetcher.fetch(character).isExperienced())));
    return magicList;
  }

  @Override
  public int getLearnCount(ICharm charm) {
    return getLearnCount(charm, character.getCharms());
  }

  private int getLearnCount(ICharm charm, ICharmConfiguration configuration) {
    ISpecialCharmConfiguration specialCharmConfiguration = configuration.getSpecialCharmConfiguration(charm.getId());
    if (specialCharmConfiguration != null) {
      return specialCharmConfiguration.getCurrentLearnCount();
    }
    return configuration.isLearned(charm) ? 1 : 0;
  }

  @Override
  public IGenericCombo[] getCombos() {
    List<IGenericCombo> genericCombos = new ArrayList<>();
    for (ICombo combo : character.getCombos().getAllCombos()) {
      genericCombos.add(new GenericCombo(combo));
    }
    return genericCombos.toArray(new IGenericCombo[genericCombos.size()]);
  }

  @Override
  public boolean isExperienced() {
    return ExperienceModelFetcher.fetch(character).isExperienced();
  }

  @Override
  public int getPainTolerance() {
    return HealthModelFetcher.fetch(character).getPainToleranceLevel();
  }

  @Override
  public ITraitLimitation getEssenceLimitation() {
    return getTemplate().getTraitTemplateCollection().getTraitTemplate(OtherTraitType.Essence).getLimitation();
  }

  @Override
  public int getEssenceCap(boolean modified) {
    return OtherTraitModelFetcher.fetch(character).getEssenceCap(modified);
  }

  @Override
  public int getAge() {
    return CharacterConceptFetcher.fetch(character).getAge().getValue();
  }

  @Override
  public IIdentifiedTraitTypeGroup[] getAbilityTypeGroups() {
    return AbilityModelFetcher.fetch(character).getAbilityTypeGroups();
  }

  @Override
  public IIdentifiedTraitTypeGroup[] getAttributeTypeGroups() {
    return AttributeModelFetcher.fetch(character).getAttributeTypeGroups();
  }

  @Override
  public int getSpentExperiencePoints() {
    return new ExperiencePointManagement(character).getTotalCosts();
  }

  @Override
  public int getTotalExperiencePoints() {
    return ExperienceModelFetcher.fetch(character).getExperiencePoints().getTotalExperiencePoints();
  }

  @Override
  public boolean isSubeffectCharm(ICharm charm) {
    ISpecialCharmConfiguration charmConfiguration = character.getCharms().getSpecialCharmConfiguration(charm);
    return charmConfiguration instanceof ISubeffectCharmConfiguration;
  }

  @Override
  public boolean isMultipleEffectCharm(ICharm charm) {
    ISpecialCharmConfiguration charmConfiguration = character.getCharms().getSpecialCharmConfiguration(charm);
    return charmConfiguration instanceof IMultipleEffectCharmConfiguration && !(charmConfiguration instanceof ISubeffectCharmConfiguration);
  }

  @Override
  public String[] getLearnedEffects(ICharm charm) {
    ISpecialCharmConfiguration charmConfiguration = character.getCharms().getSpecialCharmConfiguration(charm);
    if (!(charmConfiguration instanceof IMultipleEffectCharmConfiguration)) {
      return new String[0];
    }
    IMultipleEffectCharmConfiguration configuration = (IMultipleEffectCharmConfiguration) charmConfiguration;
    List<String> learnedEffectIds = new ArrayList<>();
    for (ISubeffect effect : configuration.getEffects()) {
      if (effect.isLearned()) {
        learnedEffectIds.add(effect.getId());
      }
    }
    return learnedEffectIds.toArray(new String[learnedEffectIds.size()]);
  }

  @Override
  public ICharm[] getGenericCharms() {
    List<ICharm> genericCharms = new ArrayList<>();
    for (ILearningCharmGroup group : character.getCharms().getAllGroups()) {
      for (ICharm charm : group.getAllCharms()) {
        if (charm.isInstanceOfGenericCharm() &&
            charm.getCharacterType().equals(character.getTemplate().getTemplateType().getCharacterType())) {
          genericCharms.add(charm);
        }
      }
    }
    return genericCharms.toArray(new ICharm[genericCharms.size()]);
  }

  @Override
  public ICharm[] getLearnedCharms() {
    return character.getCharms().getLearnedCharms(ExperienceModelFetcher.fetch(character).isExperienced());
  }

  @Override
  public void addSpecialtyListChangeListener(final IChangeListener listener) {
    ISpecialtiesConfiguration config = AbilityModelFetcher.fetch(character).getSpecialtyConfiguration();
    for (ITraitReference trait : config.getAllTraits()) {
      config.getSpecialtiesContainer(trait).addSubTraitListener(new ISpecialtyListener() {
        @Override
        public void subTraitValueChanged() {
        }

        @Override
        public void subTraitAdded(Specialty subTrait) {
          listener.changeOccurred();
        }

        @Override
        public void subTraitRemoved(Specialty subTrait) {
          listener.changeOccurred();
        }
      });
    }
  }

  private TraitMap getTraitMap() {
    return TraitModelFetcher.fetch(character);
  }
}