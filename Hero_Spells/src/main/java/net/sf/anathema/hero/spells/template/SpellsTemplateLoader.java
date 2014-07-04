package net.sf.anathema.hero.spells.template;

import net.sf.anathema.hero.template.GenericTemplateLoader;
import net.sf.anathema.hero.template.TemplateFactory;
import net.sf.anathema.hero.template.TemplateLoader;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;

public class SpellsTemplateLoader {

  public static SpellsTemplate loadTemplate(TemplateFactory templateFactory, String templateName) {
    Identifier templateId = new SimpleIdentifier(templateName);
    TemplateLoader<SpellsTemplate> loader = new GenericTemplateLoader<>(SpellsTemplate.class);
    return templateFactory.loadModelTemplate(templateId, loader);
  }
}