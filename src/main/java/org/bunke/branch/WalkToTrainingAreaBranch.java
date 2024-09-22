package org.bunke.branch;

import org.bunke.leaf.WalkToTrainingAreaLeaf;
import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static org.dreambot.api.methods.Randoms.random;

public class WalkToTrainingAreaBranch extends Branch implements UtilityMethods {
    public WalkToTrainingAreaBranch(){
        addLeaves(new WalkToTrainingAreaLeaf());
    }
    @Override
    public boolean isValid() {
        return Config.INSTANCE.getTrainingArea().contains(getMyTile()) && hasFood() && getNearbyDragons().isEmpty();
    }

    @Override
    public int onLoop(){
        walkWithRandomMethod();
        return random(400, 1000);
    }

    private void walkWithRandomMethod() {
        if(Walking.getRunEnergy() >= random(20, 50) && !Walking.isRunEnabled())
            Walking.toggleRun();
    }
}
