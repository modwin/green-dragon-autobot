package org.bunke;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bunke.branch.RootBranch;
import org.bunke.gui.GreenDragonBotGUI;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.combat.CombatStyle;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.Root;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@ScriptManifest(name = "Bunkebot Green dragon slayer", version = 1.0, category = Category.COMBAT,
        description = "Kills Green dragons and banks loot", author = "Bunke")
@Data
public class Main extends TreeScript implements PaintListener {

    private GreenDragonBotGUI gui;
    private int lootTracker;
    private Timer timer = new Timer();

    @Override
    public void onPaint(Graphics graphics) {
        graphics.drawString(getCurrentLeafName(), 25 , 36);
        Skill skillTrained = getSkillTrained();
        int profitTracker = getLootTracker();
        if(skillTrained != null && getSkillTrained() == skillTrained){
            graphics.drawString(skillTrained.name() + " XP/HR  =  " + SkillTracker.getGainedExperiencePerHour(getSkillTrained()), 27, 55);
            graphics.drawString("TOTAL PROFIT = " + profitTracker, 27, 95);
            graphics.drawString("GP/HR = " + timer.getHourlyRate(profitTracker), 27, 75);
//            super.onPaint(graphics);
        }
        else graphics.drawString("No data yet", 27, 55);
    }

    @Override
    public void onStart(){
        try {
            timer.start();
            SkillTracker.start(Skill.STRENGTH, Skill.RANGED, Skill.ATTACK, Skill.DEFENCE, Skill.HITPOINTS);
            SwingUtilities.invokeAndWait(GreenDragonBotGUI::new);
            Root root = getRoot().addBranches(new RootBranch());
        } catch (InterruptedException | InvocationTargetException e) {
            log(e.getCause());
            throw new RuntimeException(e);
        }
    }

    private void setCombatStyle() {
        if (Config.INSTANCE.getAttackStyle() != null) {
            switch (Config.INSTANCE.getAttackStyle()) {
                case "Attack": {
                    Combat.setCombatStyle(CombatStyle.ATTACK);
                    break;
                }
                case "Strength": {
                    Combat.setCombatStyle(CombatStyle.STRENGTH);
                    break;
                }
                case "Shared":{
                    Combat.setCombatStyle(CombatStyle.SHARED);
                    break;
                }
                case "Defensive": {
                    Combat.setCombatStyle(CombatStyle.DEFENCE);
                    break;
                }

            }
        }
    }

    @Override
    public int onLoop(){

        if(Config.INSTANCE.getFoodAmount() == 0) Config.INSTANCE.setFoodAmount(23);
        if(Config.INSTANCE.getAttackStyle() != null){
            setCombatStyle();
        }

        return getRoot().onLoop();
    }

    private Skill getSkillTrained(){

        Skill[] skills = new Skill[1];
        Arrays.asList(Skill.values()).forEach(s -> {
            if(s != null && SkillTracker.getGainedExperiencePerHour(s) > 0 && !s.equals(Skill.HITPOINTS))
                skills[0] = s;
        });
        return skills[0];
    }


    public void onPause(){

    }

    public void onExit(){
        gui.close();
    }

}