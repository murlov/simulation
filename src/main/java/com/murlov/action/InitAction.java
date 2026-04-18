package com.murlov.action;

import com.murlov.entity.*;
import com.murlov.simulation.SimulationMap;

import java.util.List;
import java.util.Random;

public class InitAction implements Action {

    private final int numberOfEntitiesPerEntityType;
    private final int numberOfRemainingEntities;
    private final Spawner spawner;

    public InitAction(int numberOfEntitiesPerEntityType, int numberOfRemainingEntities, Spawner spawner) {
        this.numberOfEntitiesPerEntityType = numberOfEntitiesPerEntityType;
        this.numberOfRemainingEntities = numberOfRemainingEntities;
        this.spawner = spawner;
    }

    @Override
    public void execute(SimulationMap simulationMap) {

        List<Class<? extends Entity>> types = List.of(Wolf.class, Rabbit.class, Grass.class, Tree.class, Rock.class);
        for (Class<? extends Entity> type : types) {
            spawner.execute(simulationMap, type, numberOfEntitiesPerEntityType);
        }

        for (int i = 0; i < numberOfRemainingEntities; i++) {
            Random random = new Random();
            Class<? extends Entity> type = types.get(random.nextInt(types.size()));
            spawner.execute(simulationMap, type, 1);
        }
    }
}
