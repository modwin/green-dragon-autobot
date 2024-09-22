package org.bunke.leaf;

import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.bunke.util.UtilityMethods;

public class WalkToTrainingAreaLeaf extends Leaf implements UtilityMethods {
    @Override
    public boolean isValid() {
        return (Config.INSTANCE.getTrainingArea().contains(Players.getLocal().getTile()) && hasFood() && computeLootValue() < 50000);
    }

    @Override
    public int onLoop() {
        Walking.walk(Config.INSTANCE.getTrainingArea().getRandomTile());
        return Calculations.random(400, 1000);
    }


}
