package com.murlov.action;

import com.murlov.factory.EntityFactory;
import com.murlov.factory.EntityFactoryProvider;
import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.model.EntityType;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public class InitEntitiesAction {
    public static void execute(Map map) {
        SimulationSettings settings = SimulationSettings.getInstance();
        Coordinates coordinates = Coordinates.getRandom(settings.getSizeOfMap());
        Entity entity;
        for (EntityGroup entityGroup : EntityGroup.values()) {
            for (int i = 0; i < settings.getPerGroup(); i++) {
                while (map.getEntities().containsKey(coordinates)) {
                    coordinates = Coordinates.getRandom(settings.getSizeOfMap());
                }
                EntityType entityType = EntityType.getRandom(entityGroup);
                EntityFactory entityFactory = EntityFactoryProvider.getFactory(entityType);
                entity = entityFactory.create();
                map.setEntity(coordinates, entity);
            }
        }

        if (settings.getRemainingEntities() != 0) {
            for (int i = 0; i < settings.getRemainingEntities(); i++) {
                while (map.getEntities().containsKey(coordinates)) {
                    coordinates = Coordinates.getRandom(settings.getSizeOfMap());
                }
                EntityType entityType = EntityType.getRandom(EntityGroup.getRandom());
                EntityFactory entityFactory = EntityFactoryProvider.getFactory(entityType);
                entity = entityFactory.create();
                map.setEntity(coordinates, entity);
            }
        }
    }
}
