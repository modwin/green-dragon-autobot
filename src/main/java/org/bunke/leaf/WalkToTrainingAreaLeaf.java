package org.bunke.leaf;

import org.bunke.util.SimulateHumanBehaviour;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.bunke.util.UtilityMethods;

import static org.dreambot.api.methods.Randoms.random;
import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class WalkToTrainingAreaLeaf extends Leaf implements UtilityMethods, SimulateHumanBehaviour {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop(){
        if(Dialogues.canContinue())
            Dialogues.spaceToContinue();
        if(!getMyTile().canReach(BankLocation.getNearest().getTile()))
            generateRandomBehaviour();
        sleepUntil(() -> Walking.walk(Config.INSTANCE.getTrainingArea()), () -> getMyTile().canReach(Config.INSTANCE.getTrainingArea().getRandomTile()),  random(1000, 1500), random(1000, 1500));
        return random(800, 1550);
    }
}
