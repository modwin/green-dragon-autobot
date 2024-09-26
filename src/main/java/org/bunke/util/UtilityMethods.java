package org.bunke.util;

import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.wrappers.interactive.Character;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
        Set<String> equipment = new HashSet<>(Arrays.asList("Black cape", "Obsidian platebody", "Abyssal whip", "Amulet of strength", "Climbing boots", "Adamant platelegs", "Anti-dragon shield", "Obsidian helmet"));
        Set<String> actualEquipment = new HashSet<>();
        if(!getLocal().getEquipment().isEmpty() && getLocal().getEquipment() != null) {
            for (Item i : getLocal().getEquipment()) {
                if (i != null) {
                    actualEquipment.add(i.getName());
                }
            }
        }
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
    default int getHealthPercent(){
        return getLocal().getHealthPercent();
    }

    default boolean getLevelDifference(Player p) {

        int diff = (p.getLevel() - getLocal().getLevel());
        if(Combat.getWildernessLevel() == 0) return false;
        return diff >= -Combat.getWildernessLevel() && diff <= Combat.getWildernessLevel();
    }

    default boolean isPlayerKiller(Player p) {
        return p != null && (p.getEquipment().stream().anyMatch(i -> i.getName().matches("(^\\w*\\W*bow$)|(^\\w*\\W*[rR]obes?$)|(^(\\w*\\W)*[sS]taff$)")));
    }

    default List<NPC> getNearbyDragons() {
        List<NPC> dragons = NPCs.all("Green dragon");
        if (!dragons.isEmpty())
            dragons = dragons.stream().filter(npc -> npc != null &&
                    !npc.isInteractedWith())
                    .collect(Collectors.toList());
        return dragons;
    }

    default boolean attackClosestDragon() {
        Map<Double, NPC> distances = new HashMap<>();
        NPC dragon = NPCs.closest("Green dragon");
        if (dragon != null && !(dragon.getInteractingCharacter() instanceof Player) && !dragon.isInteracting(getLocal()) && !dragon.isInCombat())
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

    default boolean isPlayerKillerNearby(){
        return Players.all().stream().anyMatch(p -> getLevelDifference(p) && isPlayerKiller(p)) || getCharacterInteracting() instanceof Player;

    }

    default boolean closeLootingBag() {
        sleep(random(800, 1200));
        Item lootingBag = Inventory.get("Looting bag");
        return Bank.close() && lootingBag != null && lootingBag.hasAction("Close") && lootingBag.interact("Close");
    }

    default boolean assertLootingBagIsClosed() {
        Item lootingBag = Inventory.get("Looting bag");
        return lootingBag != null && lootingBag.getID() == 11941;
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
        return foodCount[0] == Config.INSTANCE.getFoodAmount() && Inventory.contains("Falador Teleport", "Looting bag", "Combat potion(4)");

    }

    default boolean hasUndesiredItems() {
        return !Inventory.all().stream().allMatch(i -> i != null && (i.getName().equals(Config.INSTANCE.getFood()) || i.getName().matches(("(^(\\w*\\W)*potion\\W[1234]\\W$)|^(\\w*\\W*Teleport$)|(^\\w*\\W*amulet\\W[12345]\\W$)|(^Looting bag$)"))));
    }
    default boolean hasLootInLootingBag(){
        Item lootingBag = getLootingBag();
        return lootingBag.interact("View");
    }
    default int getSharedXp(Skill... skills){
        int[] xp = new int[1];
        for(Skill s : skills){
            xp[0] += SkillTracker.getGainedExperiencePerHour(s);
        }
        return xp[0];

    }
}
