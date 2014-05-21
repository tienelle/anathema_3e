package net.sf.anathema.hero.framework;

import net.sf.anathema.character.framework.ICharacterTemplateExtensionResourceCache;
import net.sf.anathema.character.framework.ICharacterTemplateRegistryCollection;
import net.sf.anathema.character.framework.data.ExtensibleDataSet;
import net.sf.anathema.character.framework.data.IExtensibleDataSetProvider;
import net.sf.anathema.hero.template.ITemplateRegistry;
import net.sf.anathema.hero.template.TemplateRegistry;
import net.sf.anathema.character.framework.type.CharacterTypes;
import net.sf.anathema.character.framework.type.ReflectionCharacterTypes;
import net.sf.anathema.character.framework.xml.registry.CharacterTemplateRegistryCollection;
import net.sf.anathema.framework.environment.ObjectFactory;
import net.sf.anathema.initialization.repository.DataFileProvider;

public class HeroEnvironmentImpl implements HeroEnvironment {

  private final ITemplateRegistry templateRegistry = new TemplateRegistry();
  private final ICharacterTemplateRegistryCollection templateRegistries;
  private final DataFileProvider dataFileProvider;
  private final IExtensibleDataSetProvider dataSetProvider;
  private final ObjectFactory objectFactory;
  private final CharacterTypes characterTypes;

  public HeroEnvironmentImpl(DataFileProvider dataFileProvider, ObjectFactory objectFactory, IExtensibleDataSetProvider dataSetProvider) {
    this.objectFactory = objectFactory;
    this.dataFileProvider = dataFileProvider;
    this.dataSetProvider = dataSetProvider;
    this.templateRegistries = new CharacterTemplateRegistryCollection(getDataSet(ICharacterTemplateExtensionResourceCache.class));
    this.characterTypes = new ReflectionCharacterTypes(objectFactory);
  }

  @Override
  public ITemplateRegistry getTemplateRegistry() {
    return templateRegistry;
  }

  @Override
  public ICharacterTemplateRegistryCollection getCharacterTemplateRegistries() {
    return templateRegistries;
  }

  @Override
  public ObjectFactory getObjectFactory() {
    return objectFactory;
  }

  @Override
  public CharacterTypes getCharacterTypes() {
    return characterTypes;
  }

  @Override
  public DataFileProvider getDataFileProvider() {
    return dataFileProvider;
  }

  @Override
  public <T extends ExtensibleDataSet> T getDataSet(Class<T> set) {
    return dataSetProvider.getDataSet(set);
  }
}
