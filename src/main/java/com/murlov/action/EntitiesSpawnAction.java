package com.murlov.action;

import com.murlov.action.listener.MoveEventListener;
import com.murlov.action.listener.MoveListenerRegistry;
import com.murlov.model.Entity;
import com.murlov.model.Grass;
import com.murlov.model.Rabbit;
import com.murlov.model.Wolf;
import com.murlov.simulation.SimulationMap;

import java.util.List;
import java.util.Map;

public class EntitiesSpawnAction implements Action {

    private final Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes;
    private final MoveListenerRegistry listenerRegistry;
    private final MoveEventListener listener;

    public EntitiesSpawnAction(Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes, MoveListenerRegistry listenerRegistry, MoveEventListener listener) {
        this.minNumbersForEntityTypes = minNumbersForEntityTypes;
        this.listenerRegistry = listenerRegistry;
        this.listener = listener;
    }

    @Override
    public void execute(SimulationMap simulationMap) {

        List<Class<? extends Entity>> entityTypes = List.of(Wolf.class, Rabbit.class, Grass.class);
        for (Class<? extends Entity> entityType : entityTypes) {
            int count = Math.max(0, getMinNumberBy(entityType) - simulationMap.getCountByType(entityType));
            Spawner.execute(simulationMap, entityType.getSimpleName(), listenerRegistry, count, true, listener);
        }
    }

    private int getMinNumberBy(Class<? extends Entity> entityType) {
        return minNumbersForEntityTypes.get(entityType);
    }
}
