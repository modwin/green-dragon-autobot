package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

import java.util.HashSet;
import java.util.Objects;

import static org.dreambot.api.methods.Calculations.random;
import static org.dreambot.api.utilities.Logger.log;
import static org.dreambot.api.utilities.Sleep.sleep;
import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class LootLeaf extends Leaf implements UtilityMethods {

    private final HashSet<GroundItem> items = new HashSet<>();
    @Override
    public boolean isValid() {
        GroundItems.all().stream().filter(i -> (((Players.getLocal().getSurroundingArea(10).contains(i.getTile())) && LivePrices.get(i.getItem()) > 1300 || i.getName().equals("Looting bag"))
                && (Players.getLocal().getSurroundingArea(10).contains(i.getTile()))))
                .distinct()
                .forEach(items::add);
        return !items.isEmpty() &&
                getLocal().getHealthPercent() > 50;
    }

    @Override
    public int onLoop() {
        if(openLootingBag()){
                return random(600, 1000);
        }
        Sleep.sleepUntil(this::lootItems, Calculations.random(500, 800), 600);
        sleep(random(600, 1000));
        return random(600, 1000);
    }
    private boolean lootItems() {
        int rand = Calculations.random(1, 4);
        GroundItem[] temp = new GroundItem[1];
        if (!items.isEmpty()) {
            dropIfFullInventory();
            items.forEach(i -> {
                i.interact("Take");
                sleep(random(800, 1500));
            });

        }
        items.clear();
        return true;
    }
    private void dropIfFullInventory() {
        Item food = Inventory.get("Lobster");
        if (Inventory.isFull() && !items.isEmpty() && Inventory.contains("Lobster") && food != null) {
            Inventory.all().stream().filter(i -> i != null && LivePrices.get(Inventory.get(i.getName())) < 100).forEach(item -> {
                if (!item.getName().equals("Lobster") && !item.hasAction("Drink") && !item.hasAction("Rub")) {
                    log("Dropping unwanted item = " + Objects.requireNonNull(item).getName());
                    Objects.requireNonNull(item).interact("Drop");
                    sleep(Calculations.random(800, 1300));
                }
            });
            if (Inventory.isFull()) {
                log("Eating or dropping food to pick up loot...");
                food.interact("Eat");

            }
        }
    }
}
