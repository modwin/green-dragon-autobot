package org.bunke.branch;

import org.bunke.leaf.AntiBanLeaf;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.wrappers.interactive.Player;

import static org.dreambot.api.methods.Calculations.random;
import static org.dreambot.api.utilities.Logger.log;

public class AntiBanBranch extends Branch {

    public AntiBanBranch(){
        addLeaves(new AntiBanLeaf());

    }
    @Override
    public boolean isValid() {
        log("AntiBanBranch.isValid()" + !(Players.getLocal().getCharacterInteractingWithMe() instanceof Player));
        return (Players.getLocal().getCharacterInteractingWithMe() instanceof Player) ;
    }

    @Override
    public int onLoop(){
        return random(600, 1200);

    }
}
