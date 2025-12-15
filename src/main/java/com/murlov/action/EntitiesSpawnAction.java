package com.murlov.action;

import com.murlov.model.EntityGroup;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public class EntitiesSpawnAction implements Action {

    private final java.util.Map<EntityGroup, Integer> minNumbersInGroups;

    public EntitiesSpawnAction(java.util.Map<EntityGroup, Integer> minNumbersInGroups) {
        this.minNumbersInGroups = minNumbersInGroups;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        throw new IllegalArgumentException("EntitiesMoveAction requires listenerRegistry. Use execute(Map, MoveListenerRegistry) instead.");
    }

    @Override
    public void execute(SimulationMap simulationMap, MoveListenerRegistry listenerRegistry) {

        for (EntityGroup entityGroup : EntityGroup.values()) {
            if (!entityGroup.equals(EntityGroup.STATIC))
            {
                while (simulationMap.getCountInGroup(entityGroup) < getMinNumberInGroup(entityGroup)) {
                    Coordinates coordinates = simulationMap.getFreeCellCoordinates();
                    Spawner.execute(simulationMap, entityGroup, coordinates, listenerRegistry);
                }
            }
        }
    }

    private int getMinNumberInGroup(EntityGroup entityGroup) {
        return  minNumbersInGroups.get(entityGroup);
    }
}
