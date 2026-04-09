package com.murlov.action;

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
    private final Map<Class<? extends Entity>, Integer> entityCounts;

    public EntitiesSpawnAction(Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes) {
        this.minNumbersForEntityTypes = minNumbersForEntityTypes;
        entityCounts = new HashMap<>();
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        initEntityCounts(simulationMap);
        
        List<Class<? extends Entity>> entityTypes = List.of(Wolf.class, Rabbit.class, Grass.class);
        for (Class<? extends Entity> entityType : entityTypes) {
            int count = Math.max(0, getMinNumberBy(entityType) - getCountByType(entityType));
            Spawner.execute(simulationMap, entityType, count);
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
