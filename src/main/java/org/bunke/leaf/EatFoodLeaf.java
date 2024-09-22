package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;

import java.util.Objects;

import static org.dreambot.api.methods.Calculations.random;

public class EatFoodLeaf extends Leaf implements UtilityMethods {
    @Override
    public boolean isValid() {
        if(Players.getLocal().getHealthPercent() <= random(25, 50))
            return eatFood();
        return false;
    }

    @Override
    public int onLoop() {
        if(getLocal().getHealthPercent() < random(60, 80))
            eatFood();
        return random(500, 1000);
    }


    public boolean eatFood(){
        return Inventory.all().stream().filter(Objects::nonNull).anyMatch(item -> item.interact("Eat"));
    }
}

