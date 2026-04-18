package com.murlov.factory.settingsfactory;

import com.murlov.entity.*;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Size;
import com.murlov.util.PropertiesProvider;

import java.util.HashMap;
import java.util.Map;

public class DefaultSimulationSettingsFactory implements SimulationSettingsFactory {
    private static final Size MAP_SIZE;
    private static final double FILL_PERCENTAGE;
    private static final int HERBIVORE_HEALTH;
    private static final int HERBIVORE_SPEED;
    private static final int HERBIVORE_SATIETY;
    private static final int PREDATOR_HEALTH;
    private static final int PREDATOR_SPEED;
    private static final int PREDATOR_SATIETY;
    private static final int PREDATOR_DAMAGE;
    private static final Map<Class<? extends Entity>, Integer> MIN_NUMBERS_FOR_CLASSES;

    static {
        PropertiesProvider propertiesProvider = new PropertiesProvider();
        int width = propertiesProvider.getInt("map.width");
        int height = propertiesProvider.getInt("map.height");
        MAP_SIZE = new Size(width, height);
        FILL_PERCENTAGE = propertiesProvider.getDouble("fillPercentage");
        HERBIVORE_HEALTH = propertiesProvider.getInt("herbivore.health");
        HERBIVORE_SPEED = propertiesProvider.getInt("herbivore.speed");
        HERBIVORE_SATIETY = propertiesProvider.getInt("herbivore.satiety");
        PREDATOR_HEALTH = propertiesProvider.getInt("predator.health");
        PREDATOR_SPEED = propertiesProvider.getInt("predator.speed");
        PREDATOR_SATIETY = propertiesProvider.getInt("predator.satiety");
        PREDATOR_DAMAGE = propertiesProvider.getInt("predator.damage");
        int wolfMinNumber = propertiesProvider.getInt("wolfsNumber");
        int rabbitNumber = propertiesProvider.getInt("rabbitsNumber");
        int grassNumber = propertiesProvider.getInt("grassNumber");
        MIN_NUMBERS_FOR_CLASSES = new HashMap<>() {{
            put(Wolf.class, wolfMinNumber);
            put(Rabbit.class, rabbitNumber);
            put(Grass.class, grassNumber);
        }};
    }

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
