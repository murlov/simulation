package com.murlov.factory;

import com.murlov.settings.SimulationSettings;

public class DefaultSimulationSettingsFactory implements SimulationSettingsFactory {
    private final static int MAP_WIDTH = 10;
    private final static int MAP_LENGTH = 10;
    private final static int FILL_PERCENTAGE = 40;
    private final static int PREDATORS_MIN_COUNT = 10;
    private final static int HERBIVORE_MIN_COUNT = 10;
    private final static int GRASS_MIN_COUNT = 10;


    @Override
    public SimulationSettings get () {
        return SimulationSettings.getInstance(MAP_WIDTH, MAP_LENGTH, FILL_PERCENTAGE, PREDATORS_MIN_COUNT, HERBIVORE_MIN_COUNT, GRASS_MIN_COUNT);
    }
}
