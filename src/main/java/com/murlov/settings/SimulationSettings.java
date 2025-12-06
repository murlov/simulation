package com.murlov.settings;

import com.murlov.model.EntityGroup;
import com.murlov.simulation.Size;

import java.util.HashMap;

public class SimulationSettings {
    private final int NUMBER_OF_GROUPS = 4;

    private static SimulationSettings instance;
    private double fillPercentage;
    private int numberOfEntitiesPerGroup;
    private Size sizeOfMap;
    private java.util.Map<EntityGroup, Integer> minNumbersInGroups;
    private int numberOfRemainingEntities;

    private SimulationSettings() {
        sizeOfMap = new Size();
        fillPercentage = 0;
        numberOfEntitiesPerGroup = 0;
        minNumbersInGroups = new HashMap<EntityGroup, Integer>(NUMBER_OF_GROUPS);
        numberOfRemainingEntities = 0;
    }

    private SimulationSettings(int width, int length, int fillPercentage, java.util.Map<EntityGroup, Integer> minNumbersInGroups) {
        sizeOfMap = new Size(width, length);
        this.fillPercentage = fillPercentage * 0.01;
        numberOfEntitiesPerGroup = (int)(getNumberOfCells()*this.fillPercentage)/ NUMBER_OF_GROUPS;
        numberOfRemainingEntities = (int)(getNumberOfCells()*this.fillPercentage) - numberOfEntitiesPerGroup * NUMBER_OF_GROUPS;
        this.minNumbersInGroups = new HashMap<>(minNumbersInGroups);
    }

    public static SimulationSettings getInstance(int width, int length, int fillPercentage, java.util.Map<EntityGroup, Integer> minNumbersInGroups) {
        if (instance == null) {
            instance = new SimulationSettings(width, length, fillPercentage, minNumbersInGroups);
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
        numberOfEntitiesPerGroup = (int)(getNumberOfCells()*this.fillPercentage)/ NUMBER_OF_GROUPS;
        numberOfRemainingEntities = (int)(getNumberOfCells()*this.fillPercentage) - numberOfEntitiesPerGroup * NUMBER_OF_GROUPS;
    }

    public int getNumberOfEntitiesPerGroup() {
        return numberOfEntitiesPerGroup;
    }

    public Size getSizeOfMap() {
        return sizeOfMap;
    }

    public void setSizeOfMap(int x, int y) {
        sizeOfMap = new Size(x, y);
        numberOfEntitiesPerGroup = (int)(getNumberOfCells()*this.fillPercentage)/ NUMBER_OF_GROUPS;
        numberOfRemainingEntities = (int)(getNumberOfCells()*this.fillPercentage) - numberOfEntitiesPerGroup * NUMBER_OF_GROUPS;
    }

    public int getNumberOfGroups() {
        return NUMBER_OF_GROUPS;
    }

    public int getNumberOfEntities() {
        return numberOfEntitiesPerGroup * NUMBER_OF_GROUPS;
    }

    public int getNumberOfRemainingEntities() {
        return numberOfRemainingEntities;
    }

    private int getNumberOfCells() {
        return sizeOfMap.getArea();
    }

    public java.util.Map<EntityGroup, Integer> getMinNumbersInGroups() {
        return minNumbersInGroups;
    }

    public void setMinNumbersInGroups(java.util.Map<EntityGroup, Integer> minNumbersInGroups) {
        this.minNumbersInGroups = new HashMap<>(minNumbersInGroups);
    }



}
