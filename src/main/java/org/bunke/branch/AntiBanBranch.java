package org.bunke.branch;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class AntiBanBranch extends Branch {
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public int onLoop(){
        return Calculations.random(500, 1000);

    }
}
