package org.bunke;


import org.bunke.branch.AntiBanBranch;
import org.bunke.branch.RootBranch;
import org.bunke.gui.GreenDragonBotGUI;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.Root;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
@ScriptManifest(name = "Bunkebot Green dragon slayer", version = 1.0, category = Category.COMBAT,
        description = "Kills Green dragons and banks loot", author = "Bunke")
public class Main extends TreeScript {

    @Override
    public void onStart(){
        try {
            SwingUtilities.invokeAndWait(GreenDragonBotGUI::initialize);
            Root root = getRoot().addBranches(new RootBranch());
        } catch (InterruptedException | InvocationTargetException e) {
            log(e.getCause());
            throw new RuntimeException(e);
        }
    }

    @Override
    public int onLoop(){

        return getRoot().onLoop();
    }


}