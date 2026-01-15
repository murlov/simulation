package com.murlov.action;

import com.murlov.model.*;
import com.murlov.simulation.SimulationMap;
import com.murlov.util.RandomProvider;

import java.util.List;
import java.util.Random;

public class EntitiesInitAction implements Action {

    private final int numberOfEntitiesPerEntityType;
    private final int numberOfRemainingEntities;

    public EntitiesInitAction(int numberOfEntitiesPerEntityType, int numberOfRemainingEntities) {
        this.numberOfEntitiesPerEntityType = numberOfEntitiesPerEntityType;
        this.numberOfRemainingEntities = numberOfRemainingEntities;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        throw new IllegalArgumentException("EntitiesInitAction requires listenerRegistry. Use execute(Map, MoveListenerRegistry) instead.");
    }

    @Override
    public void execute(SimulationMap simulationMap, MoveListenerRegistry listenerRegistry) {

        List<Class<? extends Entity>> types = List.of(Wolf.class, Rabbit.class, Grass.class, Tree.class, Rock.class);
        for (Class<? extends Entity> type : types) {
            Spawner.execute(simulationMap, type.getSimpleName(), listenerRegistry, numberOfEntitiesPerEntityType);
        }

        for (int i = 0; i < numberOfRemainingEntities; i++) {
            Random random = RandomProvider.getInstance();
            Class<? extends Entity> type = types.get(random.nextInt(types.size()));
            Spawner.execute(simulationMap, type.getSimpleName(), listenerRegistry, 1);
        }
    }
}
