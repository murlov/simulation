package com.murlov.action;

import com.murlov.action.listener.MoveListenerRegistry;
import com.murlov.model.*;
import com.murlov.simulation.SimulationMap;
import com.murlov.util.RandomProvider;

import java.util.List;
import java.util.Random;

public class EntitiesInitAction implements Action {

    private final int numberOfEntitiesPerEntityType;
    private final int numberOfRemainingEntities;
    private final MoveListenerRegistry listenerRegistry;

    public EntitiesInitAction(int numberOfEntitiesPerEntityType, int numberOfRemainingEntities, MoveListenerRegistry listenerRegistry) {
        this.numberOfEntitiesPerEntityType = numberOfEntitiesPerEntityType;
        this.numberOfRemainingEntities = numberOfRemainingEntities;
        this.listenerRegistry = listenerRegistry;
    }

    @Override
    public void execute(SimulationMap simulationMap) {

        List<Class<? extends Entity>> types = List.of(Wolf.class, Rabbit.class, Grass.class, Tree.class, Rock.class);
        for (Class<? extends Entity> type : types) {
            Spawner.execute(simulationMap, type.getSimpleName(), listenerRegistry, numberOfEntitiesPerEntityType, false);
        }

        for (int i = 0; i < numberOfRemainingEntities; i++) {
            Random random = RandomProvider.getInstance();
            Class<? extends Entity> type = types.get(random.nextInt(types.size()));
            Spawner.execute(simulationMap, type.getSimpleName(), listenerRegistry, 1, false);
        }
    }
}
