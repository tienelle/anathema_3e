package net.sf.anathema.character.equipment.item.view;

import net.sf.anathema.framework.repository.tree.VetorFactory;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.gui.selection.VetoableObjectSelectionView;

public interface EquipmentNavigation extends VetorFactory {
  VetoableObjectSelectionView<String> getTemplateListView();

  Tool addEditTemplateTool();
}