package com.murlov.factory;

import com.murlov.model.*;
import com.murlov.settings.SimulationSettings;

import java.util.HashMap;

public class DefaultSimulationSettingsFactory implements SimulationSettingsFactory {
    private final static int MAP_WIDTH = 10;
    private final static int MAP_LENGTH = 10;
    private final static int FILL_PERCENTAGE = 40;
    private final static java.util.Map<Class<? extends Entity>, Integer> MIN_NUMBERS_FOR_CLASSES = new HashMap<>() {{
        put(Wolf.class, 8);
        put(Rabbit.class, 8);
        put(Grass.class, 8);
    }};

    @Override
    public SimulationSettings get () {
        return SimulationSettings.getInstance(MAP_WIDTH, MAP_LENGTH, FILL_PERCENTAGE, MIN_NUMBERS_FOR_CLASSES);
    }
}
