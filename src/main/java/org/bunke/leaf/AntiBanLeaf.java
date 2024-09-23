package org.bunke.leaf;

import org.bunke.util.SimulateHumanBehaviour;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.wrappers.interactive.Player;

import static org.dreambot.api.methods.Calculations.random;
import static org.dreambot.api.utilities.Logger.log;

public class AntiBanLeaf extends Leaf implements SimulateHumanBehaviour {
    @Override
    public boolean isValid() {
        log("AntiBanLeaf.isValid() = " + (Players.getLocal().getHealthPercent() > 30));
        return Players.getLocal().getHealthPercent() > 30;
    }

    @Override
    public int onLoop() {
        log("Generating random behaviour...");
        generateRandomBehaviour();
        return random(800, 2500);
    }
}
