package org.bunke.branch;

import org.bunke.leaf.WalkToTrainingAreaLeaf;
import org.bunke.util.SimulateHumanBehaviour;
import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static org.dreambot.api.methods.Randoms.random;
import static org.dreambot.api.utilities.Logger.log;
import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class WalkToTrainingAreaBranch extends Branch implements UtilityMethods, SimulateHumanBehaviour {
    public WalkToTrainingAreaBranch(){
        addLeaves(new WalkToTrainingAreaLeaf());
    }
    @Override
    public boolean isValid() {
        log("!Config.INSTANCE.getTrainingArea().contains(getMyTile()) && hasFood() && getNearbyDragons().isEmpty()");
        return !Config.INSTANCE.getTrainingArea().contains(getMyTile()) && hasCorrectInventorySetup() && getNearbyDragons().isEmpty();
    }

    @Override
    public int onLoop(){
        generateRandomBehaviour();
        sleepUntil(() -> Walking.walk(Config.INSTANCE.getTrainingArea()), () -> Config.INSTANCE.getTrainingArea().contains(getMyTile())
                ,random(1200, 25000)
                , random(2000, 4000));
        return random(800, 1550);
    }
}
