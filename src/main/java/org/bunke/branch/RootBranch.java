package org.bunke.branch;

import org.bunke.gui.GreenDragonBotGUI;
import org.bunke.leaf.AntiBanLeaf;
import org.bunke.leaf.BankLeaf;
import org.bunke.leaf.EatFoodLeaf;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static org.dreambot.api.utilities.Logger.log;

public class RootBranch extends Branch {

    public RootBranch(){
        addLeaves(
                new EatFoodLeaf(),
                new CombatBranch(),
                new WalkToTrainingAreaBranch(),
                new     BankLeaf(),
                new AntiBanLeaf(),
                new EscapeHostilePlayerBranch()
        );

    }

    @Override
    public boolean isValid() {
        log(GreenDragonBotGUI.isInitialized());
        return GreenDragonBotGUI.isInitialized();
    }
//    public int onLoop(){
//
//    }

}
