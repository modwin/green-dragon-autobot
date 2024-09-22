package org.bunke;


import org.bunke.branch.RootBranch;
import org.bunke.gui.ScriptGUI;
import org.dreambot.api.script.frameworks.treebranch.Root;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Main extends TreeScript {

    @Override
    public void onStart(){
        try {
            SwingUtilities.invokeAndWait(ScriptGUI::initialize);
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