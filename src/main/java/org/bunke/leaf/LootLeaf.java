package org.bunke.leaf;

import org.bunke.Main;
import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

import java.util.HashSet;
import java.util.Objects;

import static org.dreambot.api.methods.Calculations.random;
import static org.dreambot.api.utilities.Logger.log;
import static org.dreambot.api.utilities.Sleep.sleep;
import static org.dreambot.api.utilities.Sleep.sleepUntil;
public class LootLeaf extends Leaf implements UtilityMethods , PaintListener {

    private int profitTracker = 0;

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
        if (!items.isEmpty()) {
            dropIfFullInventory();
            items.forEach(i -> {
                i.interact("Take");
                sleepUntil(() -> !i.exists(), random(5000, 7000),random(800, 1200));
                profitTracker += LivePrices.get(i.getItem());
            });
        }
        Object o = getTree();
        if(o instanceof Main)
            ((Main) o).setLootTracker(profitTracker);

        items.clear();
        return true;
    }
    private void dropIfFullInventory() {
        Item food = Inventory.get(Config.INSTANCE.getFood());
        if (Inventory.isFull() && !items.isEmpty() && Inventory.contains(Config.INSTANCE.getFood()) && food != null) {
            Inventory.all().stream().filter(Objects::nonNull).forEach(item -> {
                if (!item.getName().equals(Config.INSTANCE.getFood()) && !item.hasAction("Drink") && !item.hasAction("Rub") && LivePrices.get(item) <= 100) {
                    log("Dropping unwanted item = " + Objects.requireNonNull(item).getName());
                    item.interact("Drop");
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
