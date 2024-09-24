package org.bunke.util;

import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.interactive.Character;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.log;
import static org.dreambot.api.methods.Calculations.random;
import static org.dreambot.api.utilities.Sleep.sleep;

public interface UtilityMethods {
    default boolean hasFood() {
        return Inventory.all().stream().filter(Objects::nonNull).anyMatch(i -> i.hasAction("Eat"));
    }

    default int computeLootValue() {
        int[] lootValue = new int[1];
        Inventory.all().stream().filter(Objects::nonNull).forEach(i -> lootValue[0] += LivePrices.get(i));
        return lootValue[0];
    }

    default boolean hasCorrectEquipment() {
        Set<String> equipment = new HashSet<>(Arrays.asList("Rune platebody", "Dragon sword", "Amulet of strength", "Climbing boots", "Adamant platelegs", "Anti-dragon shield", "Adamant full helm"));
        Set<String> actualEquipment = new HashSet<>();
        Players.getLocal().getEquipment().stream().filter(Objects::nonNull).forEach(i -> actualEquipment.add(i.getName()));
        return actualEquipment.containsAll(equipment);
    }

    default Character getCharacterInteracting() {
        return Players.getLocal().getCharacterInteractingWithMe();
    }

    default Set<String> getInventorySet() {
        Set<String> itemNames = new HashSet<>();
        Inventory.all().forEach(i -> {
            if (i != null) itemNames.add(i.getName());
        });
        return itemNames;
    }

    default NPC getDragonInteractedWith() {
        Character c = getCharacterInteracting();
        if (c instanceof NPC && c.isInCombat() && c.getName().equals("Green dragon")) return (NPC) c;
        return null;
    }

    default Player getLocal() {
        return Players.getLocal();
    }

    default boolean getLevelDifference(Player p) {
        int diff = (p.getLevel() - getLocal().getLevel());
        return diff >= -15 && diff <= 15;
    }

    default boolean isPlayerKiller(Player p) {
        return p.getEquipment().stream().anyMatch(i -> i.getName().matches("bow") || i.getName().matches("[rR]obes?") || i.getName().matches("[sS]taff"));
    }

    default List<NPC> getNearbyDragons() {
        List<NPC> dragons = NPCs.all("Green dragon");
        if (!dragons.isEmpty())
            dragons = dragons.stream().filter(n -> n.isInteractedWith() && n.getInteractingCharacter().equals(getLocal())).distinct().collect(Collectors.toList());
        return dragons;
    }

    default boolean attackClosestDragon() {
        Map<Double, NPC> distances = new HashMap<>();
        NPC dragon = NPCs.closest("Green dragon");
        if (dragon != null && !(dragon.getInteractingCharacter() instanceof Player) && !dragon.isInteracting(getLocal()))
            return dragon.interact("Attack");

        return false;

    }

    default Tile getMyTile() {
        return Players.getLocal().getTile();
    }

    default boolean openLootingBag() {
        Item lootingBag = Inventory.get("Looting bag");
        return lootingBag != null && lootingBag.hasAction("Open") && lootingBag.interact("Open");
    }

    default boolean closeLootingBag() {
        Bank.close();
        sleep(random(800, 1200));
        Item lootingBag = Inventory.get("Looting bag");
        return lootingBag != null && lootingBag.hasAction("Close") && lootingBag.interact("Close");
    }

    default boolean assertLootingBagClosed() {
        Item lootingBag = Inventory.get("Looting bag");
        return lootingBag != null && lootingBag.hasAction("Open");
    }

    default Item getLootingBag() {
        return Inventory.get("Looting bag");
    }

    default boolean hasCorrectInventorySetup() {
        int[] foodCount = new int[1];
        List<Item> inventoryItems = Inventory.all();
        inventoryItems.forEach(i -> {
            if (i != null) {
                if (i.getName().equals(Config.INSTANCE.getFood())) {
                    foodCount[0]++;
                }
                }});
        return foodCount[0] == 23 && Inventory.contains("Falador Teleport", "Looting bag", "Combat potion(4)");

    }

    default boolean hasNoLootInInventory() {
        return Inventory.all().stream().noneMatch(i -> i != null && (i.getName().equals("Dragon bones") || i.getName().equals("Green dragonhide") || i.getName().matches("Rune dagger")));
    }
}
