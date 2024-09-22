package org.bunke.branch;

import org.bunke.leaf.AttackDragonLeaf;
import org.bunke.leaf.EatFoodLeaf;
import org.bunke.leaf.BankLeaf;
import org.bunke.util.UtilityMethods;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class CombatBranch extends Branch implements UtilityMethods {

    public CombatBranch(){
        addLeaves(
                new AttackDragonLeaf(),
                new WalkToTrainingAreaBranch(),
                new EatFoodLeaf(),
                new BankLeaf()
        );
    }
    @Override
    public int onLoop(){
        return super.onLoop();
    }
    @Override
    public boolean isValid() {
//        Player p;
//        Players.all().stream().filter(this::isPlayerKiller).
        if(!Config.INSTANCE.getTrainingArea().contains(getMyTile())){
            return false;
        }
        if(!hasFood()){
            return false;
        }
        if(!hasCorrectEquipment()){
            return false;
        }
        if((Players.all().stream().noneMatch(this::isPlayerKiller)))
                return false;

        return Config.INSTANCE.getTrainingArea().contains(getMyTile()) || !getNearbyDragons().isEmpty();
    }
}
