package net.sf.anathema.character.main.magic.model.charmtree.builder.stringbuilder;

import net.sf.anathema.character.main.magic.model.charm.Charm;
import net.sf.anathema.character.main.magic.model.magic.Magic;
import net.sf.anathema.hero.charmtree.martial.MartialArtsLevel;
import net.sf.anathema.charms.MartialArtsUtilities;
import net.sf.anathema.lib.gui.ConfigurableTooltip;
import net.sf.anathema.lib.resources.Resources;

public class MartialArtsCharmContributor implements MagicTooltipContributor {
  private final Resources resources;

  public MartialArtsCharmContributor(Resources resources) {
    this.resources = resources;
  }

  @Override
  public void buildStringForMagic(ConfigurableTooltip tooltip, Magic magic, Object details) {
    if (magic instanceof Charm && MartialArtsUtilities.isMartialArts(magic)) {
      MartialArtsLevel level = MartialArtsUtilities.getLevel(magic);
      String label = resources.getString("CharmTreeView.ToolTip.MartialArtsLevel");
      String levelName = resources.getString(level.getId());
      tooltip.appendLine(label, levelName);
    }
  }
}
