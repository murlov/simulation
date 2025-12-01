package com.murlov.action;

import com.murlov.factory.EntityFactory;
import com.murlov.factory.EntityFactoryProvider;
import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.model.EntityType;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public class EntitiesSpawnAction implements Action {

    @Override
    public boolean execute(Map map) {
        throw new IllegalArgumentException("EntitiesMoveAction requires listenerRegistry. Use execute(Map, MoveListenerRegistry) instead.");
    }

    @Override
    public boolean execute(Map map, MoveListenerRegistry listenerRegistry) {
        SimulationSettings settings = SimulationSettings.getInstance();

        for (EntityGroup entityGroup : EntityGroup.values()) {
            while (map.getCountInGroup(entityGroup) < settings.getMinCountInGroup(entityGroup)) {
                Coordinates coordinates = map.getFreeCellCoordinates(settings);
                EntityType entityType = EntityType.getRandom(entityGroup);
                EntityFactory factory = EntityFactoryProvider.getFactory(entityType);
                Entity entity = factory.create();
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
