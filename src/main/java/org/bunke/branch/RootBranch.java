package org.bunke.branch;

import org.bunke.leaf.BankLeaf;
import org.bunke.leaf.EatFoodLeaf;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class RootBranch extends Branch {

    public RootBranch(){
        addLeaves(
                new EatFoodLeaf(),
                new CombatBranch(),
                new WalkToTrainingAreaBranch(),
                new BankLeaf(),
                new AntiBanBranch(),
                new EscapeHostilePlayerBranch()
        );

    }

    @Override
    public boolean isValid() {
        return false;
    }

}
