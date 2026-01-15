package com.murlov.action;

import com.murlov.model.Entity;
import com.murlov.model.Grass;
import com.murlov.model.Rabbit;
import com.murlov.model.Wolf;
import com.murlov.simulation.SimulationMap;

import java.util.List;

public class EntitiesSpawnAction implements Action {

    private final java.util.Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes;

    public EntitiesSpawnAction(java.util.Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes) {
        this.minNumbersForEntityTypes = minNumbersForEntityTypes;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        throw new IllegalArgumentException("EntitiesMoveAction requires listenerRegistry. Use execute(Map, MoveListenerRegistry) instead.");
    }

    @Override
    public void execute(SimulationMap simulationMap, MoveListenerRegistry listenerRegistry) {

        List<Class<? extends Entity>> entityTypes = List.of(Wolf.class, Rabbit.class, Grass.class);
        for (Class<? extends Entity> entityType : entityTypes) {
            int count = Math.max(0, getMinNumberBy(entityType) - simulationMap.getCountByType(entityType));
            Spawner.execute(simulationMap, entityType.getSimpleName(), listenerRegistry, count);
        }
    }

    private int getMinNumberBy(Class<? extends Entity> entityType) {
        return minNumbersForEntityTypes.get(entityType);
    }
}
