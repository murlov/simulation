package com.murlov.action;

import com.murlov.action.listener.MoveEventListener;
import com.murlov.action.listener.MoveListenerRegistry;
import com.murlov.entity.Entity;
import com.murlov.entity.Grass;
import com.murlov.entity.Rabbit;
import com.murlov.entity.Wolf;
import com.murlov.simulation.SimulationMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntitiesSpawnAction implements Action {

    private final Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes;
    private final MoveListenerRegistry listenerRegistry;
    private final MoveEventListener listener;
    private final Map<Class<? extends Entity>, Integer> entityCounts;

    public EntitiesSpawnAction(Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes,
                               MoveListenerRegistry listenerRegistry, MoveEventListener listener) {
        this.minNumbersForEntityTypes = minNumbersForEntityTypes;
        this.listenerRegistry = listenerRegistry;
        this.listener = listener;
        entityCounts = new HashMap<>();
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        initEntityCounts(simulationMap);
        
        List<Class<? extends Entity>> entityTypes = List.of(Wolf.class, Rabbit.class, Grass.class);
        for (Class<? extends Entity> entityType : entityTypes) {
            int count = Math.max(0, getMinNumberBy(entityType) - getCountByType(entityType));
            Spawner.execute(simulationMap, entityType.getSimpleName(), listenerRegistry, count, true, listener);
        }

        entityCounts.clear();
    }

    private int getMinNumberBy(Class<? extends Entity> entityType) {
        return minNumbersForEntityTypes.get(entityType);
    }

    private void initEntityCounts (SimulationMap simulationMap) {
        Collection<Entity> entities = simulationMap.getEntities().values();
        for (Entity entity : entities) {
            entityCounts.merge(entity.getClass(), 1, Integer::sum);
        }
    }

    private int getCountByType(Class<? extends Entity> entityType) {
        return entityCounts.getOrDefault(entityType, 0);
    }
}
