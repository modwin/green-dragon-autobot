package org.bunke.util.cfg;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.Category;

import java.util.*;

@Data
public class Config {

    public static final Config INSTANCE = new Config();
    private HashMap<String, Category> activityType = new HashMap<>();
    private Category category;
    private String potion;
    private int potionAmount;
    private String food;
    private int foodAmount;
    private String combatStyle;
    private Area trainingArea;
    ObjectMapper mapper = new ObjectMapper();

    private Config() {
        setTrainingArea(new Area(2966, 3623, 2988, 3606));
    }
}
