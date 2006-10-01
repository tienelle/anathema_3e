package net.sf.anathema.character.equipment.impl.character.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.anathema.character.equipment.character.model.IEquipmentItem;
import net.sf.anathema.character.equipment.item.model.IEquipmentTemplateProvider;
import net.sf.anathema.character.equipment.template.IEquipmentTemplate;
import net.sf.anathema.character.generic.equipment.weapon.IArmourStats;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.character.generic.equipment.weapon.IWeaponStats;
import net.sf.anathema.character.generic.rules.IExaltedRuleSet;

public class EquipmentAdditionalModel extends AbstractEquipmentAdditionalModel {
  private final IArmourStats naturalArmour;
  private final IEquipmentTemplateProvider equipmentTemplateProvider;
  private IEquipmentItem naturalWeaponsItem;

  public EquipmentAdditionalModel(
      IArmourStats naturalArmour,
      IEquipmentTemplate naturalWeapons,
      IEquipmentTemplateProvider equipmentTemplateProvider,
      IExaltedRuleSet ruleSet) {
    super(ruleSet);
    this.naturalArmour = naturalArmour;
    this.equipmentTemplateProvider = equipmentTemplateProvider;
    if (naturalWeapons != null) {
      naturalWeaponsItem = addEquipmentObjectFor(naturalWeapons);
    }
  }

  public boolean canBeRemoved(IEquipmentItem item) {
    return item != naturalWeaponsItem;
  }

  public IArmourStats[] getPrintArmours() {
    List<IArmourStats> printStats = new ArrayList<IArmourStats>();
    printStats.add(naturalArmour);
    fillPrintEquipmentList(printStats, IArmourStats.class);
    return printStats.toArray(new IArmourStats[printStats.size()]);
  }

  public IWeaponStats[] getPrintWeapons() {
    List<IWeaponStats> printStats = new ArrayList<IWeaponStats>();
    fillPrintEquipmentList(printStats, IWeaponStats.class);
    return printStats.toArray(new IWeaponStats[printStats.size()]);
  }

  @SuppressWarnings("unchecked")
  private <K extends IEquipmentStats> void fillPrintEquipmentList(List<K> printStats, Class<K> printedClass) {
    for (IEquipmentItem item : getEquipmentItems()) {
      if (item == naturalWeaponsItem) {
        for (IEquipmentStats stats : naturalWeaponsItem.getStats()) {
          if (doPrint(naturalWeaponsItem, stats, printedClass)) {
            printStats.add((K) stats);
          }
        }
      }
      else {
        IEquipmentStats[] statsArray = item.getStats();
        for (IEquipmentStats stats : statsArray) {
          if (doPrint(item, stats, printedClass)) {
            String itemName = item.getName();
            if (statsArray.length > 1) {
              itemName += " - " + stats.getName();
            }
            printStats.add((K) EquipmentCloneUtilities.getRenamedPrintClone(stats, itemName));
          }
        }
      }
    }
  }

  private <K> boolean doPrint(IEquipmentItem item, IEquipmentStats stats, Class<K> printedClass) {
    return (printedClass.isInstance(stats) && item.isPrintEnabled(stats));
  }

  public String[] getAvailableTemplateIds() {
    return equipmentTemplateProvider.getAllAvailableTemplateIds();
  }

  @Override
  protected IEquipmentTemplate loadEquipmentTemplate(String templateId) {
    return equipmentTemplateProvider.loadTemplate(templateId);
  }
}