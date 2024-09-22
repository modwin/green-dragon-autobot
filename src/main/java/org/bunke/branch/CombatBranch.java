package org.bunke.branch;

import org.bunke.leaf.AttackDragonLeaf;
import org.bunke.leaf.EatFoodLeaf;
import org.bunke.leaf.BankLeaf;
import org.bunke.leaf.WalkToTrainingAreaLeaf;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class CombatBranch extends Branch {

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
        return false;
    }
}
