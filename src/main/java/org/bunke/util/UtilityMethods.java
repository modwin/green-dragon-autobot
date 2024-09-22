package org.bunke.util;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.Character;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;

import java.util.*;
import java.util.stream.Collectors;

public interface UtilityMethods {
    default boolean hasFood(){
        return Inventory.all().stream().filter(Objects::nonNull).anyMatch(i -> i.hasAction("Eat"));
    }
    default int computeLootValue(){
        int[] lootValue = new int[1];
        Inventory.all().stream().filter(Objects::nonNull).forEach(i -> lootValue[0] += LivePrices.get(i));
        return lootValue[0];
    }
    default boolean hasCorrectEquipment(){
        boolean[] b = new boolean[1];
        b[0] = true;
        Set<String> equipment = new HashSet<>(Arrays.asList("Rune platebody", "Dragon sword", "Amulet of strength", "Climbing boots", "Adamant platelegs", "Anti-dragon shield", "Adamant full helm"));
        Players.getLocal().getEquipment().stream().filter(Objects::nonNull).forEach(item -> {
            if(!equipment.contains(item.getName()))
                b[0] = false;
        });
        return b[0];
    }
    default Character getCharacterInteracting(){
        return Players.getLocal().getCharacterInteractingWithMe();
    }
    default NPC getDragonInteractedWith(){
        Character c = getCharacterInteracting();
        if(c instanceof NPC && c.isInCombat() && c.getName().equals("Green dragon")) return (NPC) c;
        return null;
    }
    default Player getLocal(){
        return Players.getLocal();
    }
    default boolean getLevelDifference(Player p){
        int diff = (p.getLevel() - getLocal().getLevel());
        return  diff >= -15 && diff <= 15 ;
    }
    default boolean isPlayerKiller(Player p){
        return p.getEquipment().stream()
                .anyMatch(i -> i.getName().matches("bow") || i.getName().matches("[rR]obes?") || i.getName().matches("[sS]taff"));
    }

    default List<NPC> getNearbyDragons(){
        return NPCs.all()
                .stream()
                .filter(npc ->
                        npc.getName().equals("Green dragon")
                                && !npc.isInteractedWith()
                                && !npc.getInteractingCharacter().equals(Players.getLocal()))
                .collect(Collectors.toList());
    }

    default boolean attackClosestDragon(){
        Map<Double, NPC> distances = new HashMap<>();
        getNearbyDragons().forEach(d -> distances.put(d.getTile().distance(), d));
        distances.get(Collections.min(distances.keySet())).interact("Attack");
        return true;
    }
    default Tile getMyTile(){
        return Players.getLocal().getTile();
    }
}
