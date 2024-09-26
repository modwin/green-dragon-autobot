package org.bunke.branch;

import org.bunke.gui.GreenDragonBotGUI;
import org.bunke.leaf.BankLeaf;
import org.bunke.leaf.EatFoodLeaf;
import org.dreambot.api.script.frameworks.treebranch.Branch;


public class RootBranch extends Branch {

    public RootBranch(){
        addLeaves(
                new EatFoodLeaf(),
                new WalkToTrainingAreaBranch(),
                new CombatBranch(),
                new     BankLeaf(),
                new EscapeHostilePlayerBranch()
        );

    }

    @Override
    public boolean isValid() {
        return GreenDragonBotGUI.isInitialized();
    }

    public int onLoop(){
        return super.onLoop();
    }
//    public int onLoop(){
//
//    }

}
