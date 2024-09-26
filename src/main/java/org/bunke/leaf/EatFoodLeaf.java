package org.bunke.leaf;

import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;

import java.util.Objects;

import static org.dreambot.api.methods.Calculations.random;
import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class EatFoodLeaf extends Leaf implements UtilityMethods {
    @Override
    public boolean isValid() {
        return getHealthPercent() <= random( 60, 75) && Inventory.all().stream().anyMatch(item -> item != null && item.hasAction("Eat")) || getLocal().canReach(BankLocation.getNearest().getTile()) && getHealthPercent() <= 95 && Inventory.contains(Config.INSTANCE.getFood()) ;
    }

    @Override
    public int onLoop() {
        if(getMyTile().canReach(BankLocation.getNearest().getTile()) && Bank.open())
            Bank.withdraw(Config.INSTANCE.getFood());
        eatFood();
        return random(300, 600);
    }


    public static void eatFood() {
        sleepUntil(() -> {
            if (Inventory.contains(Config.INSTANCE.getFood())){
                Objects.requireNonNull(Inventory.get(Config.INSTANCE.getFood())).interact("Eat");
                return true;
            }
            return false;
        }, () -> Players.getLocal().getHealthPercent() >= Calculations.random(80, 100), Calculations.random(800, 1500), Calculations.random(600, 900));
    }
}

