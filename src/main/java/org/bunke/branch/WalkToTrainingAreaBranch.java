package org.bunke.branch;

import org.bunke.leaf.WalkToTrainingAreaLeaf;
import org.bunke.util.SimulateHumanBehaviour;
import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.script.frameworks.treebranch.Branch;


public class WalkToTrainingAreaBranch extends Branch implements UtilityMethods, SimulateHumanBehaviour {
    public WalkToTrainingAreaBranch(){
        addLeaves(new WalkToTrainingAreaLeaf());
    }

    @Override
    public boolean isValid() {

        return hasFood() && !isPlayerKillerNearby() && !Config.INSTANCE.getTrainingArea().contains(getMyTile())  && getNearbyDragons().isEmpty();
    }

    @Override
    public int onLoop(){
        return super.onLoop();
    }
}
