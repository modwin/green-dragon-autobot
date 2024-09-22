package org.bunke.branch;

import org.bunke.leaf.EscapeAttackingPlayerLeaf;
import org.bunke.util.UtilityMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import java.util.stream.Collectors;

public class EscapeHostilePlayerBranch extends Branch implements UtilityMethods {

    public EscapeHostilePlayerBranch(){
        addLeaves(
                new EscapeAttackingPlayerLeaf()
        );
    }
    @Override
    public boolean isValid() {
        return Players.all().stream().noneMatch(p -> getLevelDifference(p) && isPlayerKiller(p));
    }

    @Override
    public int onLoop() {
        return Calculations.random(500, 1000);
    }
}
