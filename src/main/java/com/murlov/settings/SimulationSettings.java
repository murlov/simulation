package com.murlov.settings;

import com.murlov.simulation.Size;

public class SimulationSettings {
    private final  int COUNT_OF_GROUPS = 4;

    private static SimulationSettings instance;
    private int density;
    private int groupCount;
    private Size sizeOfMap;

    @Override
    public String toString() {
        return "SimulationSettings {density=" + density +
                ", groupCount=" + groupCount +
                ", sizeOfMap=" + sizeOfMap +
                "}";
    }

    private SimulationSettings() {
        sizeOfMap = new Size();
        density = 0;
        groupCount = 0;
    }

    private SimulationSettings(int width, int length, int density) {
        sizeOfMap = new Size(width, length);
        this.density = density;
        groupCount = sizeOfMap.getArea()/ density;
    }

    public static SimulationSettings getInstance(int width, int length, int density) {
        if (instance == null) {
            instance = new SimulationSettings(width, length, density);
        }
        return instance;
    }

    public static SimulationSettings getInstance() {
        if (instance == null) {
            instance = new SimulationSettings();
        }
        return instance;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
        groupCount = sizeOfMap.getArea()/density;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public Size getSizeOfMap() {
        return sizeOfMap;
    }

    public void setSizeOfMap(int x, int y) {
        sizeOfMap = new Size(x, y);
        groupCount = sizeOfMap.getArea()/ density;
    }

    public int getCountOfGroups() {
        return COUNT_OF_GROUPS;
    }

    public int getCountOfEntities() {
        return groupCount*COUNT_OF_GROUPS;
    }
}
