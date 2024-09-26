package org.bunke.branch;

import org.bunke.leaf.EscapeAttackingPlayerLeaf;
import org.bunke.util.UtilityMethods;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class EscapeHostilePlayerBranch extends Branch implements UtilityMethods {
    private static final Robot ROBOT;

    static{
        try {
            ROBOT = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
    public EscapeHostilePlayerBranch(){
        addLeaves(
                new EscapeAttackingPlayerLeaf()
        );
    }
    @Override
    public boolean isValid() {
        return isPlayerKillerNearby();
    }

    @Override
    public int onLoop() {

        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screen = ROBOT.createScreenCapture(rectangle);

        try {
            ImageIO.write(screen, "jpg", new File("C:\\Users\\komvu\\DreamBot\\Green Dragon Screenshots\\",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss")) + "jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return super.onLoop();
    }
}
