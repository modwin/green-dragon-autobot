package org.bunke.util;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

import java.util.LinkedList;

import static org.dreambot.api.utilities.Sleep.sleep;


public interface SimulateHumanBehaviour {

    default void moveCamera() {
        switch (Calculations.random(1, 4)) {
            case 1:
                GameObjects.all().stream().findAny().ifPresent(Camera::keyboardRotateToEntity);
                break;
            case 2:
                Camera.mouseRotateTo(Calculations.random(200, 400), Calculations.random(200, 400));
                break;
            case 3:
                Camera.fireZoomEvent(Calculations.random(20, 40));
            case 4:
                NPCs.all().stream().findAny().ifPresent(Camera::rotateToEntity);
                break;
        }
    }

    default void rightClickPlayer() {
        LinkedList<Player> players = new LinkedList<>(Players.all());
        Player player = players.pollFirst();
        sleep(Calculations.random(400, 600));
        if (player != null && Players.getLocal().canReach(player.getTile()) && player.isOnScreen()) Mouse.click(player, true);
    }

    default void rightClickGameObject() {
        LinkedList<GameObject> objects = new LinkedList<>(GameObjects.all());
        GameObject object = objects.pollFirst();
        sleep(Calculations.random(400, 600));
        if (object != null && Players.getLocal().canReach(object.getTile()) && object.isOnScreen()) {
            sleep(Calculations.random(200, 400));
            Mouse.click(object, true);
            sleep(Calculations.random(200, 400));
            Mouse.move();
        }
    }

    default void examineGameObject() {
        LinkedList<GameObject> objects = new LinkedList<>(GameObjects.all());
        GameObject object = objects.pollFirst();
        sleep(Calculations.random(400, 600));
        if (object != null && object.isOnScreen() && object.hasAction("Examine") && Players.getLocal().canReach(object.getTile()))
            object.interact("Examine");

    }

    default void switchInterfaceTabs() {
        Tab tab = null;
        switch (Calculations.random(1, 5)) {
            case 1:
                tab = Tab.SKILLS;
                break;
            case 2:
                tab = Tab.COMBAT;
                break;
            case 3:
                tab = Tab.EMOTES;
                break;
            case 4:
                tab = Tab.MAGIC;
                break;
            case 5:
                tab = Tab.MUSIC;
                break;
            case 6:
                tab = Tab.EQUIPMENT;
                break;
            case 7: {
                tab = Tab.CLAN;
                break;
            }
            case 8: {
                tab = Tab.INVENTORY;
                break;
            }
            case 9:
                tab = Tab.QUEST;
                break;

        }
        if (tab != null && !tab.isOpen()) {
            sleep(Calculations.random(300, 600));
            tab.open();
        }
        sleep(Calculations.random(400, 700));
        tab = Tab.INVENTORY;
        tab.open();
        sleep(Calculations.random(400, 700));
    }

    default void examineInventoryItem() {
        if (Calculations.random(1, 4) == 4) {
            LinkedList<Item> items = new LinkedList<>(Inventory.all());
            Item item = items.get(Calculations.random(0, items.size()));
            sleep(Calculations.random(300, 700));
            if (item != null) item.interact("Examine");
        }
    }

    default void examineGroundItem() {
        if (Calculations.random(1, 3) == 3) {
            Player player = Players.getLocal();
            LinkedList<GroundItem> groundItems = new LinkedList<>(GroundItems.all());
            if (!groundItems.isEmpty()) {
                GroundItem groundItem = groundItems.get(Calculations.random(0, groundItems.size()));
                sleep(Calculations.random(600, 800));
                if (groundItem != null && groundItem.isOnScreen() && Players.getLocal().canReach(groundItem.getTile()) && groundItem.hasAction("Examine"))
                    groundItem.interact("Examine");
            }
        }
    }

    default void hoverOverSkill() {
        if (!Tab.SKILLS.isOpen()) {
            Tab.SKILLS.open();
            Sleep.sleep(Calculations.random(500, 900));
            switch (Calculations.random(1, 6)) {
                case 1:
                    Skills.hoverSkill(Skill.HITPOINTS);
                    break;
                case 2:
                    Skills.hoverSkill(Skill.STRENGTH);
                    break;
                case 3:
                    Skills.hoverSkill(Skill.RANGED);
                    break;
                case 4:
                    Skills.hoverSkill(Skill.ATTACK);
                    break;
                case 5:
                    Skills.hoverSkill(Skill.AGILITY);
                    break;
                case 6:
                    Skills.hoverSkill(Skill.MAGIC);
                    break;
            }
        }
    }

    default void generateRandomBehaviour() {

        switch (Calculations.random(1, 9)) {
            case 1:
                moveCamera();
                break;
            case 2:
                rightClickPlayer();
                break;
            case 3:
                rightClickGameObject();
                break;
            case 4:
                examineGameObject();
                break;
            case 5:
                switchInterfaceTabs();
                break;
            case 6:
                examineInventoryItem();
                break;
            case 7:
                examineGroundItem();
                break;
            case 8:
                hoverOverSkill();
                break;
        }
    }


}

