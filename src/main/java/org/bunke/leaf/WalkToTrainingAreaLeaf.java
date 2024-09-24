package org.bunke.leaf;

import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.bunke.util.UtilityMethods;

import static org.dreambot.api.methods.Randoms.random;
import static org.dreambot.api.utilities.Logger.log;

public class WalkToTrainingAreaLeaf extends Leaf implements UtilityMethods {

    @Override
    public boolean isValid() {
        log("!Config.INSTANCE.getTrainingArea().contains(getMyTile()) && hasFood() && getNearbyDragons().isEmpty()");
        return true;
    }

    @Override
    public int onLoop(){
        Walking.walk(Config.INSTANCE.getTrainingArea());
        return random(800, 1550);
    }
}
