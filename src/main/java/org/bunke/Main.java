package org.bunke;


import com.google.common.base.Supplier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bunke.branch.RootBranch;
import org.bunke.gui.GreenDragonBotGUI;
import org.bunke.leaf.LootLeaf;
import org.bunke.util.ScriptPaint;
import org.bunke.util.cfg.Config;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.combat.CombatStyle;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.event.impl.GroundItemSpawnEvent;
import org.dreambot.api.script.event.impl.InventoryItemEvent;
import org.dreambot.api.script.event.impl.SpawnEvent;
import org.dreambot.api.script.frameworks.treebranch.Root;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.script.listener.ItemContainerListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@ScriptManifest(name = "Bunkebot Green dragon slayer", version = 1.0, category = Category.COMBAT,
        description = "Kills Green dragons and banks loot", author = "Bunke")
@Data
public class Main extends TreeScript implements PaintListener {


    @Override
    public void onPaint(Graphics graphics) {
        String branch = getCurrentBranchName();
        String leaf = getCurrentLeafName();
        String thread;
        Skill skillTrained = getSkillTrained();
        if(skillTrained != null && getSkillTrained() == skillTrained){
            graphics.drawString(getCurrentLeafName(), 25 , 36);
//            new ScriptPaint().onPaint(graphics);
            graphics.drawString(skillTrained.name() + " XP/HR  =  " + SkillTracker.getGainedExperiencePerHour(getSkillTrained()), 27, 55);
        }
    }

    @Override
    public void onStart(){
        try {
            SkillTracker.start(Skill.STRENGTH, Skill.RANGED, Skill.ATTACK, Skill.DEFENCE, Skill.HITPOINTS);
            SwingUtilities.invokeAndWait(GreenDragonBotGUI::initialize);
            Root root = getRoot().addBranches(new RootBranch());
        } catch (InterruptedException | InvocationTargetException e) {
            log(e.getCause());
            throw new RuntimeException(e);
        }
    }

    private void setCombatStyle() {
        CombatStyle current = Combat.getCombatStyle();
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

    public void onPause(){

    }

    @Override
    public int onLoop(){

        if(Config.INSTANCE.getFoodAmount() == 0) Config.INSTANCE.setFoodAmount(23);
        CombatStyle cs = Combat.getCombatStyle();
        if(Config.INSTANCE.getAttackStyle() != null){
            setCombatStyle();
        }
        String leaf = getCurrentLeafName();
//        if(leaf != null && leaf.equals("LootLeaf"))
//            trackLoot();
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

//    private void trackLoot(){
//        HashSet<GroundItem> items = new HashSet<>();
//        GroundItems.all().stream().filter(i -> (((Players.getLocal().getSurroundingArea(10).contains(i.getTile())) && LivePrices.get(i.getItem()) > 1300 || i.getName().equals("Looting bag"))
//                        && (Players.getLocal().getSurroundingArea(10).contains(i.getTile()))))
//                .distinct()
//                .forEach(i -> profitTracker += LivePrices.get(i.getItem()));
//    }

}