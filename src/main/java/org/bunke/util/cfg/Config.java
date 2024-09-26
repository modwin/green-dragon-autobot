package org.bunke.util.cfg;

import lombok.Data;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.Category;

import java.util.*;

@Data
public class Config {

    public static final Config INSTANCE = new Config();
    private HashMap<String, Category> activityType = new HashMap<>();
    private int potionAmount;
    private int foodAmount;
    private Category category;
    private String potion;
    private String food;
    private String attackStyle;
    private Area trainingArea;

    private Config() {
        setTrainingArea(new Area(2971, 3621, 2983, 3611));
    }
}
