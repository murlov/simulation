package com.murlov.action;

import com.murlov.factory.EntityFactory;
import com.murlov.factory.EntityFactoryProvider;
import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.model.EntityType;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public class EntitiesInitAction implements Action {

    @Override
    public boolean execute(Map map) {
        SimulationSettings settings = SimulationSettings.getInstance();
        Coordinates coordinates;
        Entity entity;

        for (EntityGroup entityGroup : EntityGroup.values()) {
            for (int i = 0; i < settings.getPerGroup(); i++) {
                coordinates = map.getFreeCellCoordinates(settings);
                EntityType entityType = EntityType.getRandom(entityGroup);
                EntityFactory entityFactory = EntityFactoryProvider.getFactory(entityType);
                entity = entityFactory.create();
                map.setEntity(coordinates, entity);
                map.countInGroupIncrement(entityGroup);
            }
        }

        if (settings.getRemainingEntities() != 0) {
            for (int i = 0; i < settings.getRemainingEntities(); i++) {
                coordinates = map.getFreeCellCoordinates(settings);
                EntityGroup entityGroup = EntityGroup.getRandom();
                EntityType entityType = EntityType.getRandom(entityGroup);
                EntityFactory entityFactory = EntityFactoryProvider.getFactory(entityType);
                entity = entityFactory.create();
                map.setEntity(coordinates, entity);
                map.countInGroupIncrement(entityGroup);
            }
        }

        return true;
    }
}
