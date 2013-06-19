package net.sf.anathema.hero.othertraits.sheet.essence.content;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.character.reporting.pdf.content.RegisteredReportContent;
import net.sf.anathema.character.reporting.pdf.content.ReportContentFactory;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.lib.resources.Resources;

@RegisteredReportContent(produces = ExtendedEssenceContent.class)
public class ExtendedEssenceContentFactory implements ReportContentFactory<ExtendedEssenceContent> {

  private Resources resources;

  public ExtendedEssenceContentFactory(Resources resources)  {
    this.resources = resources;
  }

  @Override
  public ExtendedEssenceContent create(ReportSession session, IGenericCharacter character, Hero hero) {
    return new ExtendedEssenceContent(resources, character, hero);
  }
}
