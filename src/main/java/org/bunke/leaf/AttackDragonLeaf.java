package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.wrappers.interactive.NPC;

import java.util.Objects;

import static org.dreambot.api.methods.Calculations.random;
import static org.dreambot.api.utilities.Logger.log;
import static org.dreambot.api.utilities.Sleep.sleep;
import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class AttackDragonLeaf extends Leaf implements UtilityMethods {
    @Override
    public boolean isValid() {
        boolean lootAvailable = GroundItems.all().stream().anyMatch(i -> (((getLocal().getSurroundingArea(7).contains(i.getTile())) && LivePrices.get(i.getItem()) > 1300 || i.getName().equals("Looting bag"))));

        return getLocal().getHealthPercent() >= 40 && !lootAvailable;
    }

    @Override
    public int onLoop() {
        drinkPotion();
        NPC npc = NPCs.closest("Green dragon");;
        if (npc != null && !npc.isInteractedWith() && !Players.getLocal().isInCombat() && npc.getName().equalsIgnoreCase("Green dragon")) {
            sleepUntil(this::attackClosestDragon, Calculations.random(300, 500), 300);
        }
//        if(dragon != null && dragon.getHealthPercent() == 0) attackSelectedNpc(dragon);
        return random(500, 1000);
    }
    private boolean attackSelectedNpc(NPC npc) {
        Camera.rotateToEntity(npc);
        if (random(1, 2) == 1){
            sleepUntil(() -> npc.interactForceRight("Attack"), random(300, 600), 300);
            return true;
        }
        else {
            npc.interact("Attack");
        }
        if (Players.getLocal().isInteracting(npc)) {
            Players.getLocal().canAttack();
            return false;
        }
        return true;
    }

    public void drinkPotion() {
        if ((Skills.getBoostedLevel(Skill.STRENGTH) - Skill.STRENGTH.getLevel()) <= 4) {
            if (Inventory.all().stream().anyMatch(item -> (item != null && item.getName().matches("^(\\w+\\s)*(\\s)*potion[(][1-4][)]$")))) {
                Inventory.all().stream()
                        .filter(item -> (item != null && item.hasAction("Drink")))
                        .distinct()
                        .findFirst()
                        .ifPresent(item-> item.interact("Drink"));

            }
        }
        if (Inventory.contains("Vial")) Objects.requireNonNull(Inventory.get("Vial")).interact("Drop");
    }

}
