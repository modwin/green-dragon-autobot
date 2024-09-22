package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.wrappers.interactive.Character;
import org.dreambot.api.wrappers.interactive.Player;

import static org.dreambot.api.methods.Calculations.random;

public class EscapeAttackingPlayerLeaf extends Leaf implements UtilityMethods {

    @Override
    public boolean isValid() {
        Character c = getCharacterInteracting();
        return c instanceof Player;
    }

    @Override
    public int onLoop() {

        return random(500, 1000);
    }
}
