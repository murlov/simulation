package com.murlov.factory;

import com.murlov.model.EntityGroup;
import com.murlov.settings.SimulationSettings;

import java.util.HashMap;

public class DefaultSimulationSettingsFactory implements SimulationSettingsFactory {
    private final static int MAP_WIDTH = 10;
    private final static int MAP_LENGTH = 10;
    private final static int FILL_PERCENTAGE = 40;
    private final static java.util.Map<EntityGroup, Integer> MIN_NUMBERS_IN_GROUPS = new HashMap<>() {{
        put(EntityGroup.PREDATOR, 10);
        put(EntityGroup.HERBIVORE, 10);
        put(EntityGroup.GRASS, 10);
    }};


    @Override
    public SimulationSettings get () {
        return SimulationSettings.getInstance(MAP_WIDTH, MAP_LENGTH, FILL_PERCENTAGE, MIN_NUMBERS_IN_GROUPS);
    }
}
