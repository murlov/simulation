package com.murlov.action;

import com.murlov.factory.EntityFactory;
import com.murlov.factory.EntityFactoryProvider;
import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.model.EntityType;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public class Spawner {
    public static void execute(Map map, EntityGroup entityGroup, Coordinates coordinates, MoveListenerRegistry listenerRegistry) {
        EntityType entityType = EntityType.getRandom(entityGroup);
        EntityFactory entityFactory = EntityFactoryProvider.getFactory(entityType);
        Entity entity = entityFactory.create();
        map.setEntity(coordinates, entity);
        map.countInGroupIncrement(entityGroup);
        if (entity instanceof Creature creature) {
            listenerRegistry.attachListener(creature);
        }
    }
}
