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

    private SimulationSettings() {
        sizeOfMap = new Size();
        fillPercentage = 0;
        numberOfEntitiesPerEntityType = 0;
        minNumbersForEntityTypes = new HashMap<>(NUMBER_OF_ENTITY_TYPES);
        numberOfRemainingEntities = 0;
    }

    private SimulationSettings(int width, int length, int fillPercentage, Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes) {
        sizeOfMap = new Size(width, length);
        this.fillPercentage = fillPercentage * 0.01;
        numberOfEntitiesPerEntityType = (int)(getNumberOfCells()*this.fillPercentage)/ NUMBER_OF_ENTITY_TYPES;
        numberOfRemainingEntities = (int)(getNumberOfCells()*this.fillPercentage) - numberOfEntitiesPerEntityType * NUMBER_OF_ENTITY_TYPES;
        this.minNumbersForEntityTypes = new HashMap<>(minNumbersForEntityTypes);
    }

    public static SimulationSettings getInstance(int width, int length, int fillPercentage, Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes) {
        if (instance == null) {
            instance = new SimulationSettings(width, length, fillPercentage, minNumbersForEntityTypes);
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



}
