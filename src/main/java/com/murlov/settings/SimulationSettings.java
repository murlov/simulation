package com.murlov.settings;

import com.murlov.model.EntityGroup;
import com.murlov.simulation.Size;

public class SimulationSettings {
    private final  int COUNT_OF_GROUPS = 4;

    private static SimulationSettings instance;
    private double fillPercentage;
    private int perGroup;
    private Size sizeOfMap;
    private int remainingEntities;
    private int predatorsMinCount;
    private int herbivoresMinCount;
    private int grassMinCount;

    @Override
    public String toString() {
        return "SimulationSettings {fillPercentage=" + fillPercentage +
                ", perGroup=" + perGroup +
                ", sizeOfMap=" + sizeOfMap +
                "}";
    }

    private SimulationSettings() {
        sizeOfMap = new Size();
        fillPercentage = 0;
        perGroup = 0;
        remainingEntities = 0;
        predatorsMinCount = 0;
        herbivoresMinCount = 0;
        grassMinCount = 0;
    }

    private SimulationSettings(int width, int length, int fillPercentage, int predatorsMinCount, int herbivoresMinCount, int grassMinCount) {
        sizeOfMap = new Size(width, length);
        this.fillPercentage = fillPercentage * 0.01;
        perGroup = (int)(getCountOfCells()*this.fillPercentage)/ COUNT_OF_GROUPS;
        remainingEntities = (int)(getCountOfCells()*this.fillPercentage) - perGroup* COUNT_OF_GROUPS;
        this.predatorsMinCount = predatorsMinCount;
        this.herbivoresMinCount = herbivoresMinCount;
        this.grassMinCount = grassMinCount;
    }

    public static SimulationSettings getInstance(int width, int length, int fillPercentage, int predatorsMinCount, int herbivoresMinCount, int grassMinCount) {
        if (instance == null) {
            instance = new SimulationSettings(width, length, fillPercentage, predatorsMinCount, herbivoresMinCount, grassMinCount);
        }
        return instance;
    }

    public static SimulationSettings getInstance() {
        if (instance == null) {
            instance = new SimulationSettings();
        }
        return instance;
    }

    public double getFillPercentage() {
        return fillPercentage;
    }

    public void setFillPercentage(int fillPercentage) {
        this.fillPercentage = fillPercentage*0.01;
        perGroup = (int)(getCountOfCells()*this.fillPercentage)/ COUNT_OF_GROUPS;
        remainingEntities = (int)(getCountOfCells()*this.fillPercentage) - perGroup* COUNT_OF_GROUPS;
    }

    public int getPerGroup() {
        return perGroup;
    }

    public Size getSizeOfMap() {
        return sizeOfMap;
    }

    public void setSizeOfMap(int x, int y) {
        sizeOfMap = new Size(x, y);
        perGroup = (int)(getCountOfCells()*this.fillPercentage)/ COUNT_OF_GROUPS;
        remainingEntities = (int)(getCountOfCells()*this.fillPercentage) - perGroup* COUNT_OF_GROUPS;
    }

    public int getCountOfGroups() {
        return COUNT_OF_GROUPS;
    }

    public int getCountOfEntities() {
        return perGroup* COUNT_OF_GROUPS;
    }

    public int getRemainingEntities() {
        return remainingEntities;
    }

    public int getCountOfCells() {
        return sizeOfMap.getArea();
    }

    public int getMinCountInGroup(EntityGroup group) {
        return switch(group) {
            case EntityGroup.PREDATOR -> predatorsMinCount;
            case EntityGroup.HERBIVORE -> herbivoresMinCount;
            case EntityGroup.GRASS -> grassMinCount;
            case EntityGroup.STATIC -> 0;
        };
    }

    public void setMinCountInGroup(EntityGroup group, int count) {
        switch(group) {
            case EntityGroup.PREDATOR -> predatorsMinCount = count;
            case EntityGroup.HERBIVORE -> herbivoresMinCount = count;
            case EntityGroup.GRASS -> grassMinCount = count;
        }
    }
}
