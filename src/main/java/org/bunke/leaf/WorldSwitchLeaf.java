package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.frameworks.treebranch.Leaf;

import static org.dreambot.api.methods.Calculations.random;

public class WorldSwitchLeaf extends Leaf implements UtilityMethods {
    @Override
    public boolean isValid() {
        return getMyTile().canReach(Config.INSTANCE.getTrainingArea().getRandomTile()) && Players.all().size() >= 3 || Players.all().stream().anyMatch(this::isPlayerKiller);
    }

    @Override
    public int onLoop() {
        if(new Area(2976, 3592, 2985, 3585).contains(getMyTile()) && !getLocal().isInCombat()){

        }
        Walking.walk(BankLocation.getNearest());
        WorldHopper.hopWorld(random(505, 525));

        return 0;
    }
}
