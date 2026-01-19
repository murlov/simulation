package com.murlov.factory;

import com.murlov.model.*;
import com.murlov.settings.SimulationSettings;

import java.util.HashMap;
import java.util.Map;

public class DefaultSimulationSettingsFactory implements SimulationSettingsFactory {
    private final static int MAP_WIDTH = 10;
    private final static int MAP_LENGTH = 10;
    private final static int FILL_PERCENTAGE = 40;
    private final static int HERBIVORE_HEALTH = 10;
    private final static int HERBIVORE_SPEED = 1;
    private final static int HERBIVORE_SATIETY = 10;
    private final static int PREDATOR_HEALTH = 10;
    private final static int PREDATOR_SPEED = 1;
    private final static int PREDATOR_SATIETY = 10;
    private final static int PREDATOR_DAMAGE = 1;
    private final static Map<Class<? extends Entity>, Integer> MIN_NUMBERS_FOR_CLASSES = new HashMap<>() {{
        put(Wolf.class, 8);
        put(Rabbit.class, 8);
        put(Grass.class, 8);
    }};

    @Override
    public SimulationSettings get () {
        return SimulationSettings.getInstance(MAP_WIDTH, MAP_LENGTH, FILL_PERCENTAGE, MIN_NUMBERS_FOR_CLASSES, HERBIVORE_HEALTH, HERBIVORE_SPEED, HERBIVORE_SATIETY,
                PREDATOR_HEALTH, PREDATOR_SPEED, PREDATOR_SATIETY, PREDATOR_DAMAGE);
    }
}
