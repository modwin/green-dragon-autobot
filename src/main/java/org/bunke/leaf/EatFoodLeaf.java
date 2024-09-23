package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;

import java.util.Objects;

import static org.dreambot.api.methods.Calculations.random;
import static org.dreambot.api.utilities.Logger.log;
import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class EatFoodLeaf extends Leaf implements UtilityMethods {
    @Override
    public boolean isValid() {
        log("L0l EatFoodLeaf.isValid()" + (Players.getLocal().getHealthPercent() < random(45, 65)));
        return Players.getLocal().getHealthPercent() <= random( 45, 65);
    }

    @Override
    public int onLoop() {
        eatFood();
        log("L0l eatFood!!!");
        return random(500, 1000);
    }


    public static boolean eatFood() {
        sleepUntil(() -> {
            if (Inventory.contains("Lobster")){
                Objects.requireNonNull(Inventory.get("Lobster")).interact("Eat");
                return true;
            }
            return false;
        }, () -> Players.getLocal().getHealthPercent() >= Calculations.random(80, 100), Calculations.random(800, 1500), Calculations.random(600, 900));
        return true;
    }
}

