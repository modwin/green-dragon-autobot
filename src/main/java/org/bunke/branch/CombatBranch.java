package org.bunke.branch;

import org.bunke.leaf.*;
import org.bunke.util.SimulateHumanBehaviour;
import org.bunke.util.UtilityMethods;
import org.dreambot.api.script.frameworks.treebranch.Branch;


public class CombatBranch extends Branch implements UtilityMethods, SimulateHumanBehaviour {

    public CombatBranch() {
        addLeaves(
                new AttackDragonLeaf(),
                new WalkToTrainingAreaBranch(),
                new EatFoodLeaf(),
                new WorldSwitchLeaf(),
                new LootLeaf());
    }



    @Override
    public int onLoop() {
        return super.onLoop();
    }

    @Override
    public boolean isValid() {
        if(isPlayerKillerNearby()) return false;
        return !getNearbyDragons().isEmpty() && hasFood();
    }
}
