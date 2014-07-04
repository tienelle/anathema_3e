package net.sf.anathema.hero.spiritual.template;

import net.sf.anathema.hero.template.GenericTemplateLoader;
import net.sf.anathema.hero.template.TemplateFactory;
import net.sf.anathema.hero.template.TemplateLoader;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;

public class SpiritualTraitsTemplateLoader {

  public static SpiritualTraitsTemplate loadTemplate(TemplateFactory templateFactory, String templateName) {
    Identifier templateId = new SimpleIdentifier(templateName);
    TemplateLoader<SpiritualTraitsTemplate> loader = new GenericTemplateLoader<>(SpiritualTraitsTemplate.class);
    return templateFactory.loadModelTemplate(templateId, loader);
  }
}