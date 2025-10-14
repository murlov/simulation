package com.murlov.simulation;

public class SimulationSettings {
    private static SimulationSettings instance;
    private final static int COUNT_OF_GROUPS = 4;
    private static int density;
    private static int groupCount;
    private static Size sizeOfMap;
    //private static Map<EntityGroup, Integer> groupCounts = new HashMap<>(COUNT_OF_GROUPS);

    private SimulationSettings() {
        sizeOfMap = new Size(10, 10);
        density = 10;
        groupCount = sizeOfMap.getArea()/density;
//        for (EntityGroup group: EntityGroup.values()) {
//            groupCounts.put(group, groupCount);
//        }
    }
    
    private SimulationSettings(int width, int length, int density) {
        sizeOfMap = new Size(width, length);
        SimulationSettings.density = density;
        groupCount = sizeOfMap.getArea()/SimulationSettings.density;
//        for (EntityGroup group: EntityGroup.values()) {
//            groupCounts.put(group, groupCount);
//        }
    }

    public static SimulationSettings getInstance() {
        if (instance == null) {
            instance = new SimulationSettings();
        }
        return instance;
    }

    public static SimulationSettings getInstance(int width, int length, int density) {
        if (instance == null) {
            instance = new SimulationSettings(width, length, density);
        }
        return instance;
    }

    public static int getDensity() {
        return density;
    }

    public static Size getSizeOfMap() {
        return sizeOfMap;
    }

    public static int getGroupCount() {
        return groupCount;
    }

//    public static Map<EntityGroup, Integer> getEntitiesNumbers() {
//        return groupCounts;
//    }

    public static int getCountOfGroups() {
        return COUNT_OF_GROUPS;
    }

    public int getCountOfEntities() {
        return groupCount*COUNT_OF_GROUPS;
    }
}
