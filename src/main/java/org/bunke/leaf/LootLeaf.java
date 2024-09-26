package org.bunke.leaf;

import org.bunke.Main;
import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
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

import static org.dreambot.api.methods.Calculations.random;
import static org.dreambot.api.utilities.Logger.log;
import static org.dreambot.api.utilities.Sleep.sleepUntil;
public class LootLeaf extends Leaf implements UtilityMethods , PaintListener {

    private int profitTracker = 0;

    private final HashSet<GroundItem> items = new HashSet<>();
    @Override
    public boolean isValid() {

        if(isPlayerKillerNearby())
            return false;

        GroundItems.all().stream().filter(i -> (((Players.getLocal().getSurroundingArea(15).contains(i.getTile())) && LivePrices.get(i.getItem()) > 1300 || i.getName().equals("Looting bag"))
                && (Players.getLocal().getSurroundingArea(15).contains(i.getTile()))))
                .distinct()
                .forEach(items::add);

        if(getHealthPercent() < 50) return false;
        return !items.isEmpty();
    }

    @Override
    public int onLoop() {
        if(assertLootingBagIsClosed() && openLootingBag()){
                return random(300, 600);
        }
        Sleep.sleepUntil(this::lootItems, 300, 300);
        return random(200, 400);
    }

    private boolean lootItems() {
        if (!items.isEmpty()) {

            if(Inventory.isFull())
                dropIfFullInventory();

            items.forEach(i -> {
                i.interact("Take");

                sleepUntil(() ->{
                    items.removeIf(item -> !item.exists());
                    return !i.exists();
                } , random(300, 600),random(600, 800));
                if(!i.exists())
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
        String regexPattern = "(^\\w*\\W*potion\\W[1234]\\W$)|(^\\w*\\W*Teleport$)|(^\\w*\\W*amulet\\W[12345]\\W$)|(^Looting bag$)|(^";
        Inventory.all().stream().filter(item -> item != null &&
                !item.getName().matches(regexPattern + Config.INSTANCE.getFood()+")$") && LivePrices.get(item) < 200)
                .distinct()
                .findAny()
                .ifPresent(i -> i.interact("Drop"));
        if (food != null) {
            log("Eating or dropping inventory item to pick up loot...");
            food.interact("Eat");

            Inventory.all(i -> i != null && i.getName().equals(Config.INSTANCE.getFood())).get(0).interact("Eat");
        }

    }

}
