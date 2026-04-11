package com.murlov.settings;

import com.murlov.entity.Entity;
import com.murlov.simulation.Size;

import java.util.HashMap;
import java.util.Map;

public class SimulationSettings {

    private final int numberOfEntitiesPerEntityType;
    private final Size mapSize;
    private final Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes;
    private final int numberOfRemainingEntities;
    private final int herbivoreHealth;
    private final int herbivoreSpeed;
    private final int herbivoreSatiety;
    private final int predatorHealth;
    private final int predatorSpeed;
    private final int predatorSatiety;
    private final int predatorDamage;

    public static class Parameters {
        public static final int NUMBER_OF_ENTITY_TYPES = 5;

        public int numberOfEntitiesPerEntityType;
        public Size mapSize;
        public Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes;
        public int numberOfRemainingEntities;
        public int herbivoreHealth;
        public int herbivoreSpeed;
        public int herbivoreSatiety;
        public int predatorHealth;
        public int predatorSpeed;
        public int predatorSatiety;
        public int predatorDamage;
    }

    public SimulationSettings(Parameters parameters) {
        mapSize = new Size(parameters.mapSize);
        this.numberOfEntitiesPerEntityType = parameters.numberOfEntitiesPerEntityType;
        this.numberOfRemainingEntities = parameters.numberOfRemainingEntities;
        this.minNumbersForEntityTypes = new HashMap<>(parameters.minNumbersForEntityTypes);
        this.herbivoreHealth = parameters.herbivoreHealth;
        this.herbivoreSpeed = parameters.herbivoreSpeed;
        this.herbivoreSatiety = parameters.herbivoreSatiety;
        this.predatorHealth = parameters.predatorHealth;
        this.predatorSpeed = parameters.predatorSpeed;
        this.predatorSatiety = parameters.predatorSatiety;
        this.predatorDamage = parameters.predatorDamage;
    }

    public int getNumberOfEntitiesPerEntityType() {
        return numberOfEntitiesPerEntityType;
    }

    public Size getMapSize() {
        return mapSize;
    }

    public int getNumberOfRemainingEntities() {
        return numberOfRemainingEntities;
    }

    public Map<Class<? extends Entity>, Integer> getMinNumbersForEntityTypes() {
        return minNumbersForEntityTypes;
    }
    public int getHerbivoreHealth() {
        return herbivoreHealth;
    }

    public int getHerbivoreSpeed() {
        return herbivoreSpeed;
    }

    public int getHerbivoreSatiety() {
        return herbivoreSatiety;
    }

    public int getPredatorHealth() {
        return predatorHealth;
    }

    public int getPredatorSpeed() {
        return predatorSpeed;
    }

    public int getPredatorSatiety() {
        return predatorSatiety;
    }

    public int getPredatorDamage() {
        return predatorDamage;
    }
}
