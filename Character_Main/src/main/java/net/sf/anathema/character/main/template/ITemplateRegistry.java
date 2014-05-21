package net.sf.anathema.character.main.template;

import net.sf.anathema.character.main.type.CharacterType;

public interface ITemplateRegistry {

  HeroTemplate[] getAllSupportedTemplates(CharacterType type);

  void register(HeroTemplate template);

  HeroTemplate getTemplate(TemplateType type);
}