package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;

import java.util.*;
import java.util.stream.Collectors;

public class BankLeaf extends Leaf implements UtilityMethods {
    @Override
    public boolean isValid() {
        return !hasFood() || !hasCorrectEquipment();
    }

    @Override
    public int onLoop() {
//        if(!BankLocation.getNearest().getArea(8).contains(getMyTile()))
        if(Bank.isOpen()){
            Bank.depositAllExcept("Looting bag");
            if(!hasCorrectEquipment()) withdrawLoadout();

            if(!hasFood()){
                Bank.withdraw(i -> i.getName().matches("^Combat potion\\W[3|4]\\W$"), 2);
                Bank.withdraw(i -> i != null && i.getName().matches("^Burning amulet\\W[1|2|3|4]\\W$"));
                Bank.withdrawAll(Config.INSTANCE.getFood());
            }
        }

        return Calculations.random(500, 1000);
    }


    public void equipItems(){
        Inventory.all().stream()
                .filter(Objects::nonNull)
                .filter(i ->
                        i.hasAction("Equip"))
                .forEach(i ->{
                    Sleep.sleepUntil(() -> i.interact("Equip"), Calculations.random(400, 800));
                        });
    }
    private void withdrawLoadout(){
        Set<String> equipment = new HashSet<>(Arrays.asList("Rune platebody", "Dragon sword", "Amulet of strength", "Climbing boots", "Adamant platelegs", "Anti-dragon shield", "Adamant full helm"));
        List<Item> items = new LinkedList<>();
        Bank.all().stream()
                .filter(Objects::nonNull)
                .filter(i -> equipment.contains(i.getName()) || i.getName()
                        .matches("^Ring of wealth\\W[1|2|3|4]\\W")).distinct().collect(Collectors.toList())
                .forEach(i -> Sleep.sleepUntil(() -> Bank.withdraw(i != null ? i.getName() : null), Calculations.random(400, 800)));
        equipItems();
    }
    private void withdrawEquipmentThenEquip(){


    }

//    public void deposit
}
