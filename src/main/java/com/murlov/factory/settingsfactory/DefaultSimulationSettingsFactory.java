package com.murlov.factory.settingsfactory;

import com.murlov.entity.*;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Size;

import java.util.HashMap;
import java.util.Map;

public class DefaultSimulationSettingsFactory implements SimulationSettingsFactory {
    private static final Size MAP_SIZE = new Size(10,10);
    private static final double FILL_PERCENTAGE = 0.4;
    private static final int HERBIVORE_HEALTH = 10;
    private static final int HERBIVORE_SPEED = 1;
    private static final int HERBIVORE_SATIETY = 10;
    private static final int PREDATOR_HEALTH = 10;
    private static final int PREDATOR_SPEED = 1;
    private static final int PREDATOR_SATIETY = 10;
    private static final int PREDATOR_DAMAGE = 1;
    private static final Map<Class<? extends Entity>, Integer> MIN_NUMBERS_FOR_CLASSES = new HashMap<>() {{
        put(Wolf.class, 8);
        put(Rabbit.class, 8);
        put(Grass.class, 8);
    }};

    @Override
    public SimulationSettings get () {
        SimulationSettings.Parameters parameters = new SimulationSettings.Parameters();
        parameters.mapSize = MAP_SIZE;
        parameters.numberOfEntitiesPerEntityType = (int) (MAP_SIZE.getArea()*FILL_PERCENTAGE) /
                SimulationSettings.Parameters.NUMBER_OF_ENTITY_TYPES;
        parameters.numberOfRemainingEntities = (int) (MAP_SIZE.getArea()*FILL_PERCENTAGE) -
                parameters.numberOfEntitiesPerEntityType * SimulationSettings.Parameters.NUMBER_OF_ENTITY_TYPES;
        parameters.minNumbersForEntityTypes = MIN_NUMBERS_FOR_CLASSES;
        parameters.herbivoreHealth = HERBIVORE_HEALTH;
        parameters.herbivoreSpeed = HERBIVORE_SPEED;
        parameters.herbivoreSatiety = HERBIVORE_SATIETY;
        parameters.predatorHealth = PREDATOR_HEALTH;
        parameters.predatorSpeed = PREDATOR_SPEED;
        parameters.predatorSatiety = PREDATOR_SATIETY;
        parameters.predatorDamage = PREDATOR_DAMAGE;
        return new SimulationSettings(parameters);
    }
}
