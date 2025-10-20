package com.murlov.settings;

import com.murlov.simulation.Size;

public class SimulationSettings {
    private final  int COUNT_OF_GROUPS = 4;

    private static SimulationSettings instance;
    private double fillPercentage;
    private int perGroup;
    private Size sizeOfMap;
    private int remainingEntities;

    @Override
    public String toString() {
        return "SimulationSettings {fillPercentage=" + fillPercentage +
                ", groupCount=" + perGroup +
                ", sizeOfMap=" + sizeOfMap +
                "}";
    }

    private SimulationSettings() {
        sizeOfMap = new Size();
        fillPercentage = 0;
        perGroup = 0;
        remainingEntities = 0;
    }

    private SimulationSettings(int width, int length, int fillPercentage) {
        sizeOfMap = new Size(width, length);
        this.fillPercentage = fillPercentage * 0.01;
        perGroup = (int)(sizeOfMap.getArea()*this.fillPercentage)/COUNT_OF_GROUPS;
        remainingEntities = (int)(sizeOfMap.getArea()*this.fillPercentage) - perGroup*COUNT_OF_GROUPS;
    }

    public static SimulationSettings getInstance(int width, int length, int fillPercentage) {
        if (instance == null) {
            instance = new SimulationSettings(width, length, fillPercentage);
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
        perGroup = (int)(sizeOfMap.getArea()*this.fillPercentage)/COUNT_OF_GROUPS;
        remainingEntities = (int)(sizeOfMap.getArea()*this.fillPercentage) - perGroup*COUNT_OF_GROUPS;
    }

    public int getPerGroup() {
        return perGroup;
    }

    public Size getSizeOfMap() {
        return sizeOfMap;
    }

    public void setSizeOfMap(int x, int y) {
        sizeOfMap = new Size(x, y);
        perGroup = (int)(sizeOfMap.getArea()*this.fillPercentage)/COUNT_OF_GROUPS;
        remainingEntities = (int)(sizeOfMap.getArea()*this.fillPercentage) - perGroup*COUNT_OF_GROUPS;
    }

    public int getCountOfGroups() {
        return COUNT_OF_GROUPS;
    }

    public int getCountOfEntities() {
        return perGroup*COUNT_OF_GROUPS;
    }

    public int getRemainingEntities() {
        return remainingEntities;
    }
}
