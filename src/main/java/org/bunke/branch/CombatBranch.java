package org.bunke.branch;

import org.bunke.leaf.*;
import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import static org.dreambot.api.utilities.Logger.log;

public class CombatBranch extends Branch implements UtilityMethods {

    public CombatBranch() {
        addLeaves(new AttackDragonLeaf(), new WalkToTrainingAreaBranch(), new EatFoodLeaf(), new WorldSwitchLeaf(), new LootLeaf());
    }



    @Override
    public int onLoop() {

        return super.onLoop();
    }

    @Override
    public boolean isValid() {
//        Player p;
//        Players.all().stream().filter(this::isPlayerKiller).
        if (!Config.INSTANCE.getTrainingArea().contains(getMyTile())) {
            return false;
        }
        if (!hasFood()) {
            return false;
        }
        if (!hasCorrectEquipment()) {
            return false;
        }
        if ((Players.all().stream().anyMatch(this::isPlayerKiller))) return false;
        if (Config.INSTANCE.getTrainingArea().contains(getMyTile()) && Players.all().size() >= 3) return false;
        return (Config.INSTANCE.getTrainingArea().contains(getMyTile()) || !getNearbyDragons().isEmpty() && Players.all().stream().noneMatch(p -> getLevelDifference(p) && isPlayerKiller(p)));
    }
}
