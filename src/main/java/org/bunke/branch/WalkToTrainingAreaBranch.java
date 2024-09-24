package org.bunke.branch;

import org.bunke.leaf.WalkToTrainingAreaLeaf;
import org.bunke.util.SimulateHumanBehaviour;
import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
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
//        return hasFood() && !super.getTree().getCurrentState().equals(ScriptManager.State.RUNNING) && !getNearbyDragons().isEmpty();
        return !Config.INSTANCE.getTrainingArea().contains(getMyTile()) && hasCorrectInventorySetup() && getNearbyDragons().isEmpty();
    }

    @Override
    public int onLoop(){
        generateRandomBehaviour();
        return super.onLoop();
    }
}
