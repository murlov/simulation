package com.murlov.action;

import com.murlov.factory.EntityFactory;
import com.murlov.factory.EntityFactoryProvider;
import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.model.EntityType;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public class EntitiesInitAction implements Action {

    private final int numberOfEntitiesPerGroup;
    private final int numberOfRemainingEntities;

    public EntitiesInitAction(int numberOfEntitiesPerGroup, int numberOfRemainingEntities) {
        this.numberOfEntitiesPerGroup = numberOfEntitiesPerGroup;
        this.numberOfRemainingEntities = numberOfRemainingEntities;
    }

    @Override
    public void execute(Map map) {
        throw new IllegalArgumentException("EntitiesInitAction requires listenerRegistry. Use execute(Map, MoveListenerRegistry) instead.");
    }

    @Override
    public void execute(Map map, MoveListenerRegistry listenerRegistry) {
        Coordinates coordinates;

        for (EntityGroup entityGroup : EntityGroup.values()) {
            for (int i = 0; i < numberOfEntitiesPerGroup; i++) {
                coordinates = map.getFreeCellCoordinates();
                Spawner.execute(map, entityGroup, coordinates, listenerRegistry);
            }
        }

        if (numberOfRemainingEntities != 0) {
            for (int i = 0; i < numberOfRemainingEntities; i++) {
                coordinates = map.getFreeCellCoordinates();
                EntityGroup entityGroup = EntityGroup.getRandom();
                Spawner.execute(map, entityGroup, coordinates, listenerRegistry);
            }
        }
    }
}
