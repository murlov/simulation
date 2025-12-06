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
    public boolean execute(Map map) {
        throw new IllegalArgumentException("EntitiesInitAction requires listenerRegistery. Use execute(Map, MoveListenerRegistry) instead.");
    }

    @Override
    public boolean execute(Map map, MoveListenerRegistry listenerRegistry) {
        Coordinates coordinates;
        Entity entity;

        for (EntityGroup entityGroup : EntityGroup.values()) {
            for (int i = 0; i < numberOfEntitiesPerGroup; i++) {
                coordinates = map.getFreeCellCoordinates();
                EntityType entityType = EntityType.getRandom(entityGroup);
                EntityFactory entityFactory = EntityFactoryProvider.getFactory(entityType);
                entity = entityFactory.create();
                map.setEntity(coordinates, entity);
                map.countInGroupIncrement(entityGroup);
                if (entity instanceof Creature creature) {
                    listenerRegistry.attachListener(creature);
                }
            }
        }

        if (numberOfRemainingEntities != 0) {
            for (int i = 0; i < numberOfRemainingEntities; i++) {
                coordinates = map.getFreeCellCoordinates();
                EntityGroup entityGroup = EntityGroup.getRandom();
                EntityType entityType = EntityType.getRandom(entityGroup);
                EntityFactory entityFactory = EntityFactoryProvider.getFactory(entityType);
                entity = entityFactory.create();
                map.setEntity(coordinates, entity);
                map.countInGroupIncrement(entityGroup);
                if (entity instanceof Creature creature) {
                    listenerRegistry.attachListener(creature);
                }
            }
        }

        return true;
    }
}
