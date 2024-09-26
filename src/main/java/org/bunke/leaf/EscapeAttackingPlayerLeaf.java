package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.frameworks.treebranch.Leaf;

import static org.dreambot.api.methods.Calculations.random;

public class EscapeAttackingPlayerLeaf extends Leaf implements UtilityMethods {


    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {


        super.getTree().setState(ScriptManager.State.RUNNING);
        Walking.walk(BankLocation.getNearest());
        WorldHopper.hopWorld(random(505, 525));
        return random(500, 1000);
    }
}
