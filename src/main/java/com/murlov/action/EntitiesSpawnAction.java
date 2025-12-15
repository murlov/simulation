package com.murlov.action;

import com.murlov.factory.EntityFactory;
import com.murlov.factory.EntityFactoryProvider;
import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.model.EntityType;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public class EntitiesSpawnAction implements Action {

    private final java.util.Map<EntityGroup, Integer> minNumbersInGroups;

    public EntitiesSpawnAction(java.util.Map<EntityGroup, Integer> minNumbersInGroups) {
        this.minNumbersInGroups = minNumbersInGroups;
    }

    @Override
    public void execute(Map map) {
        throw new IllegalArgumentException("EntitiesMoveAction requires listenerRegistry. Use execute(Map, MoveListenerRegistry) instead.");
    }

    @Override
    public void execute(Map map, MoveListenerRegistry listenerRegistry) {

        for (EntityGroup entityGroup : EntityGroup.values()) {
            if (!entityGroup.equals(EntityGroup.STATIC))
            {
                while (map.getCountInGroup(entityGroup) < getMinNumberInGroup(entityGroup)) {
                    Coordinates coordinates = map.getFreeCellCoordinates();
                    Spawner.execute(map, entityGroup, coordinates, listenerRegistry);
                }
            }
        }
    }

    private int getMinNumberInGroup(EntityGroup entityGroup) {
        return  minNumbersInGroups.get(entityGroup);
    }
}
