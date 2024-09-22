package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.wrappers.interactive.NPC;

import java.util.List;

public class AttackDragonLeaf extends Leaf implements UtilityMethods {
    @Override
    public boolean isValid() {
        if(Config.INSTANCE.getTrainingArea().contains(Players.getLocal().getTile())){
//            List<NPC> dragons = getNearbyDragons();
            NPC dragon = getDragonInteractedWith();
            if(dragon == null || dragon.getHealthPercent() == 0) return attackClosestDragon();
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        return Calculations.random(500, 1000);
    }
}
