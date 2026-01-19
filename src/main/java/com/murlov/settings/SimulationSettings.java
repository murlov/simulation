package com.murlov.settings;

import com.murlov.model.Entity;
import com.murlov.simulation.Size;

import java.util.HashMap;
import java.util.Map;

public class SimulationSettings {
    private final int NUMBER_OF_ENTITY_TYPES = 5;

    private static SimulationSettings instance;
    private double fillPercentage;
    private int numberOfEntitiesPerEntityType;
    private Size sizeOfMap;
    private Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes;
    private int numberOfRemainingEntities;
    private int herbivoreHealth;
    private int herbivoreSpeed;
    private int herbivoreSatiety;
    private int predatorHealth;
    private int predatorSpeed;
    private int predatorSatiety;
    private int predatorDamage;

    private SimulationSettings() {
        sizeOfMap = new Size();
        fillPercentage = 0;
        numberOfEntitiesPerEntityType = 0;
        minNumbersForEntityTypes = new HashMap<>(NUMBER_OF_ENTITY_TYPES);
        numberOfRemainingEntities = 0;
        herbivoreHealth = 0;
        herbivoreSpeed = 0;
        herbivoreSatiety = 0;
        predatorHealth = 0;
        predatorSpeed = 0;
        predatorSatiety = 0;
        predatorDamage = 0;
    }

    private SimulationSettings(int width, int length, int fillPercentage, Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes, int herbivoreHealth,
                               int herbivoreSpeed, int herbivoreSatiety, int predatorHealth, int predatorSpeed, int predatorSatiety, int predatorDamage) {
        sizeOfMap = new Size(width, length);
        this.fillPercentage = fillPercentage * 0.01;
        numberOfEntitiesPerEntityType = (int)(getNumberOfCells()*this.fillPercentage)/ NUMBER_OF_ENTITY_TYPES;
        numberOfRemainingEntities = (int)(getNumberOfCells()*this.fillPercentage) - numberOfEntitiesPerEntityType * NUMBER_OF_ENTITY_TYPES;
        this.minNumbersForEntityTypes = new HashMap<>(minNumbersForEntityTypes);
        this.herbivoreHealth = herbivoreHealth;
        this.herbivoreSpeed = herbivoreSpeed;
        this.herbivoreSatiety = herbivoreSatiety;
        this.predatorHealth = predatorHealth;
        this.predatorSpeed = predatorSpeed;
        this.predatorSatiety = predatorSatiety;
        this.predatorDamage = predatorDamage;
    }

    public static SimulationSettings getInstance(int width, int length, int fillPercentage, Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes,
                                                 int herbivoreHealth, int herbivoreSpeed, int herbivoreSatiety, int predatorHealth, int predatorSpeed,
                                                 int predatorSatiety, int predatorDamage) {
        if (instance == null) {
            instance = new SimulationSettings(width, length, fillPercentage, minNumbersForEntityTypes, herbivoreHealth, herbivoreSpeed, herbivoreSatiety,
                    predatorHealth, predatorSpeed, predatorSatiety, predatorDamage);
        }
        return instance;
    }

    public static SimulationSettings getInstance() {
        if (instance == null) {
            instance = new SimulationSettings();
        }
        return instance;
    }

    public void setFillPercentage(int fillPercentage) {
        this.fillPercentage = fillPercentage*0.01;
        numberOfEntitiesPerEntityType = (int)(getNumberOfCells()*this.fillPercentage)/ NUMBER_OF_ENTITY_TYPES;
        numberOfRemainingEntities = (int)(getNumberOfCells()*this.fillPercentage) - numberOfEntitiesPerEntityType * NUMBER_OF_ENTITY_TYPES;
    }

    public int getNumberOfEntitiesPerEntityType() {
        return numberOfEntitiesPerEntityType;
    }

    public Size getSizeOfMap() {
        return sizeOfMap;
    }

    public void setSizeOfMap(int x, int y) {
        sizeOfMap = new Size(x, y);
        numberOfEntitiesPerEntityType = (int)(getNumberOfCells()*this.fillPercentage)/ NUMBER_OF_ENTITY_TYPES;
        numberOfRemainingEntities = (int)(getNumberOfCells()*this.fillPercentage) - numberOfEntitiesPerEntityType * NUMBER_OF_ENTITY_TYPES;
    }

    public int getNumberOfEntityTypes() {
        return NUMBER_OF_ENTITY_TYPES;
    }

    public int getNumberOfRemainingEntities() {
        return numberOfRemainingEntities;
    }

    private int getNumberOfCells() {
        return sizeOfMap.getArea();
    }

    public Map<Class<? extends Entity>, Integer> getMinNumbersForEntityTypes() {
        return minNumbersForEntityTypes;
    }

    public void setMinNumbersForEntityTypes(Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes) {
        this.minNumbersForEntityTypes = new HashMap<>(minNumbersForEntityTypes);
    }

    public void setHerbivoreHealth(int herbivoreHealth) {
        this.herbivoreHealth = herbivoreHealth;
    }

    public int getHerbivoreHealth() {
        return herbivoreHealth;
    }

    public void setHerbivoreSpeed(int herbivoreSpeed) {
        this.herbivoreSpeed = herbivoreSpeed;
    }

    public int getHerbivoreSpeed() {
        return herbivoreSpeed;
    }

    public void setHerbivoreSatiety(int herbivoreSatiety) {
        this.herbivoreSatiety = herbivoreSatiety;
    }

    public int getHerbivoreSatiety() {
        return herbivoreSatiety;
    }

    public void setPredatorHealth(int predatorHealth) {
        this.predatorHealth = predatorHealth;
    }

    public int getPredatorHealth() {
        return predatorHealth;
    }

    public void setPredatorSpeed(int predatorSpeed) {
        this.predatorSpeed = predatorSpeed;
    }

    public int getPredatorSpeed() {
        return predatorSpeed;
    }

    public void setPredatorSatiety(int predatorSatiety) {
        this.predatorSatiety = predatorSatiety;
    }

    public int getPredatorSatiety() {
        return predatorSatiety;
    }

    public void setPredatorDamage(int predatorDamage) {
        this.predatorDamage = predatorDamage;
    }

    public int getPredatorDamage() {
        return predatorDamage;
    }
}
