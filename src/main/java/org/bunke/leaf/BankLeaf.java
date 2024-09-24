package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.*;
import java.util.stream.Collectors;

import static org.dreambot.api.methods.Calculations.random;
import static org.dreambot.api.utilities.Logger.log;
import static org.dreambot.api.utilities.Sleep.sleep;
import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class BankLeaf extends Leaf implements UtilityMethods {
    @Override
    public boolean isValid() {

        return !hasFood() && getHealthPercent() <= 85 || !hasCorrectEquipment() || !Config.INSTANCE.getTrainingArea().contains(getMyTile()) && !hasCorrectInventorySetup();
    }

    @Override
    public int onLoop() {

        Set<String> equipment = new HashSet<>(Arrays.asList("Rune platebody", "Dragon sword", "Amulet of strength", "Climbing boots", "Adamant platelegs", "Anti-dragon shield", "Adamant full helm"));
        if (!getInventorySet().containsAll(equipment) && !hasCorrectEquipment()) {
            withdrawLoadout();
            sleep(random(400, 1200));
            return random(400, 800);
        }

        if (getInventorySet().containsAll(equipment) && !hasCorrectEquipment()) {
            equipItems();
            sleep(random(400, 1200));
            return random(600, 1000);
        }


        if(!assertLootingBagClosed() || hasLootInInventory()){
            log("L0l condition 3, 1");
            if(Bank.open() &&  Bank.depositAllExcept("Looting bag") && depositAllLoot()){
                return random(600, 1000);
            }
            closeLootingBag();
        }




//        if (Bank.open()) {
//            if (depositAllLoot())
//                return random(600, 1000);
//            if(!assertLootingBagClosed() && closeLootingBag()){
//                sleep(random(800, 1500));
//                return random(600, 1000);
//            }
//        }
//        log("condition 4 = " + (!hasCorrectInventorySetup()));
        if (Bank.open() && !hasCorrectInventorySetup() && assertLootingBagClosed()) {
            sleep(random(400, 800));
            if (!Inventory.contains("Combat potion(4)"))
                Bank.withdraw(i -> i.getName().matches("^Combat potion\\W4\\W$"), Config.INSTANCE.getPotionAmount());

            sleep(random(400, 800));

            if (!Inventory.contains(i -> i.getName().matches("^Burning amulet\\W[1-5]\\W$")))
                Bank.withdraw(i -> i != null && i.getName().matches("^Burning amulet\\W[1-5]\\W$"));

            if(!Inventory.contains("("+Config.INSTANCE.getFood() + ")|^Falador Teleport$")){
                Bank.withdraw("Falador Teleport");
                sleep(random(400, 800));
                Bank.withdraw(Config.INSTANCE.getFood(), Config.INSTANCE.getFoodAmount());
                sleep(random(400, 800));

            }

            if(!Inventory.contains("Looting bag")) {
                Bank.withdraw("Looting bag");
                sleep(random(400, 800));
            }
            closeLootingBag();
        }
        return random(500, 1000);
    }

    //    private boolean lootingBagIsEmpty(){
//        if()
//
//    }
    private List<WidgetChild> getLootingBagWidgets() {
        Item lootingBag = Inventory.get("Looting bag");
        if (lootingBag != null) lootingBag.interact("View");
        sleep(600, 1000);
        Widget widget = Widgets.getWidget(15);
        WidgetChild depositWidget = null;
        WidgetChild closeWidget = null;
        if (widget != null) {
            depositWidget = widget.getChild(6);
            closeWidget = widget.getChild(8);
        }
        return Arrays.asList(depositWidget, closeWidget);
    }


    private boolean depositAllLoot() {
        if (!Inventory.contains("Looting bag") && !Bank.contains("Looting bag")) return true;
        Item lootingBag = getLootingBag();
        return lootingBag.interact("View") && getLootingBagWidgets().get(0).interact("Deposit loot");
    }


    public void equipItems() {
        if (Bank.isOpen()) Bank.close();
        Inventory.all().stream().filter(Objects::nonNull).filter(i -> i.hasAction("Wear", "Wield")).forEach(i -> {
            sleep(random(400, 800));
            if (i.hasAction("Wield")) i.interact("Wield");
            else i.interact("Wear");
        });
    }

    private void withdrawLoadout() {
        if (!Bank.isOpen()) Bank.open();
        Set<String> equipment = new HashSet<>(Arrays.asList("Rune platebody", "Dragon sword", "Amulet of strength", "Climbing boots", "Adamant platelegs", "Anti-dragon shield", "Adamant full helm"));
        Bank.all().stream().filter(Objects::nonNull).filter(i -> !Inventory.contains(i) && equipment.contains(i.getName()) || i.getName().matches("^Ring of wealth\\W[1234]\\W")).distinct().collect(Collectors.toList()).forEach(i -> sleepUntil(() -> Bank.withdraw(i != null ? i.getName() : null), random(400, 800)));
        equipItems();
    }
}
