package com.murlov.action;

import com.murlov.model.EntityGroup;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public class EntitiesInitAction implements Action {

    private final int numberOfEntitiesPerGroup;
    private final int numberOfRemainingEntities;

    public EntitiesInitAction(int numberOfEntitiesPerGroup, int numberOfRemainingEntities) {
        this.numberOfEntitiesPerGroup = numberOfEntitiesPerGroup;
        this.numberOfRemainingEntities = numberOfRemainingEntities;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        throw new IllegalArgumentException("EntitiesInitAction requires listenerRegistry. Use execute(Map, MoveListenerRegistry) instead.");
    }

    @Override
    public void execute(SimulationMap simulationMap, MoveListenerRegistry listenerRegistry) {
        Coordinates coordinates;

        for (EntityGroup entityGroup : EntityGroup.values()) {
            for (int i = 0; i < numberOfEntitiesPerGroup; i++) {
                coordinates = simulationMap.getFreeCellCoordinates();
                Spawner.execute(simulationMap, entityGroup, coordinates, listenerRegistry);
            }
        }

        if (numberOfRemainingEntities != 0) {
            for (int i = 0; i < numberOfRemainingEntities; i++) {
                coordinates = simulationMap.getFreeCellCoordinates();
                EntityGroup entityGroup = EntityGroup.getRandom();
                Spawner.execute(simulationMap, entityGroup, coordinates, listenerRegistry);
            }
        }
    }
}
