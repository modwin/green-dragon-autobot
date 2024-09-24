package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.prayer.Prayer;
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

        return !hasFood() || !hasCorrectEquipment() || !Config.INSTANCE.getTrainingArea().contains(getMyTile()) &&  !hasCorrectInventorySetup() ;
    }

    @Override
    public int onLoop() {

        Item lootingBag = getLootingBag();
        Set<String> equipment = new HashSet<>(Arrays.asList("Rune platebody", "Dragon sword", "Amulet of strength", "Climbing boots", "Adamant platelegs", "Anti-dragon shield", "Adamant full helm"));
        if(!getInventorySet().containsAll(equipment) && !hasCorrectEquipment()){
            withdrawLoadout();
            sleep(random(400, 1200));
            return random(400, 800);
        }

        if(getInventorySet().containsAll(equipment) && !hasCorrectEquipment()){
            equipItems();
            sleep(random(400, 1200));
            return random(600, 1000);
        }

        if(!hasNoLootInInventory() && Bank.open() && !assertLootingBagClosed()){
            if (depositAllLoot()) {
                sleep(random(800, 1500));
            }
            return random(600, 1000);
        }

        if(Bank.open() && hasCorrectEquipment() && hasNoLootInInventory() || assertLootingBagClosed()){
                sleep(random(400, 800));

                if(!Inventory.contains("Combat potion(4)"))
                    Bank.withdraw(i -> i.getName().matches("^Combat potion\\W4\\W$"), 2);

                sleep(random(400, 800));

                if(!Inventory.contains(i -> i.getName().matches("^Burning amulet\\W[1-5]\\W$")))
                    Bank.withdraw(i -> i != null && i.getName().matches("^Burning amulet\\W[1-5]\\W$"));

                sleep(random(400, 800));

                Bank.withdraw("Falador Teleport");
                sleep(random(400, 800));
                Bank.withdraw("Looting bag");
                sleep(random(400, 800));
                Bank.withdrawAll(Config.INSTANCE.getFood());
        }
        return random(500, 1000);
    }
//    private boolean lootingBagIsEmpty(){
//        if()
//
//    }
    private WidgetChild getLootingBagDepositAllWidget(){
        Item lootingBag = Inventory.get("Looting bag");
        if (lootingBag != null)
            lootingBag.interact("View");
        sleep(600, 1000);
        Widget widget = Widgets.getWidget(15);;
        WidgetChild child = null;
        if(widget != null) {
            child = widget.getChild(6);
        }
        return child;
    }


    private boolean depositAllLoot() {
        if(!Inventory.contains("Looting bag") && !Bank.contains("Looting bag"))
            return true;
        if (Inventory.size() > 1) {
            Bank.depositAllExcept("Looting bag");
        }
        WidgetChild c = getLootingBagDepositAllWidget();
        Item lootingBag = getLootingBag();
        ;
        //        c.getChildItems().forEach(i -> {
//            if(i != null && i.hasAction("Deposit-All")) i.interact("Deposit-All");
//        } );
        return lootingBag != null && c.interact("Deposit loot") && closeLootingBag();

    }


    public void equipItems(){
        if(Bank.isOpen()) Bank.close();
        Inventory.all().stream()
                .filter(Objects::nonNull)
                .filter(i ->
                        i.hasAction("Wear", "Wield"))
                .forEach(i -> {
                    sleep(random(400, 800));
                    if(i.hasAction("Wield")) i.interact("Wield");
                    else i.interact("Wear");
                });
    }
    private void withdrawLoadout(){
        if(!Bank.isOpen()) Bank.open();
        Set<String> equipment = new HashSet<>(Arrays.asList("Rune platebody", "Dragon sword", "Amulet of strength", "Climbing boots", "Adamant platelegs", "Anti-dragon shield", "Adamant full helm"));
        List<Item> items = new LinkedList<>();
        Bank.all().stream()
                .filter(Objects::nonNull)
                .filter(i -> !Inventory.contains(i) && equipment.contains(i.getName()) || i.getName()
                        .matches("^Ring of wealth\\W[1|2|3|4]\\W")).distinct().collect(Collectors.toList())
                .forEach(i -> sleepUntil(() -> Bank.withdraw(i != null ? i.getName() : null), random(400, 800)));
        equipItems();
    }
    private void withdrawEquipmentThenEquip(){


    }




//    public void deposit
}
