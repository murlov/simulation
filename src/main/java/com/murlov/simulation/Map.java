package com.murlov.simulation;

import com.murlov.factory.*;
import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.model.EntityType;
import com.murlov.settings.SimulationSettings;

import java.util.HashMap;

public class Map {

    public java.util.Map<Coordinates, Entity> entities;

    public Map() {
        entities = new HashMap<>();
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);
    }

    public void setRandomEntitiesPositions () {
        SimulationSettings settings = SimulationSettings.getInstance();
        Coordinates coordinates = Coordinates.getRandom(settings.getSizeOfMap());
        Entity entity;
        for (EntityGroup entityGroup : EntityGroup.values()) {
            for (int i = 0; i < settings.getPerGroup(); i++) {
                while (entities.containsKey(coordinates)) {
                    coordinates = Coordinates.getRandom(settings.getSizeOfMap());
                }
                EntityType entityType = EntityType.getRandom(entityGroup);
                EntityFactory entityFactory = getFactoryByName(entityType);
                entity = entityFactory.create();
                setEntity(coordinates, entity);
            }
        }

        if (settings.getRemainingEntities() != 0) {
            for (int i = 0; i < settings.getRemainingEntities(); i++) {
                while (entities.containsKey(coordinates)) {
                    coordinates = Coordinates.getRandom(settings.getSizeOfMap());
                }
                EntityType entityType = EntityType.getRandom(EntityGroup.getRandom());
                EntityFactory entityFactory = getFactoryByName(entityType);
                entity = entityFactory.create();
                setEntity(coordinates, entity);
            }
        }
    }

    private EntityFactory getFactoryByName (EntityType entityType) {
        switch (entityType) {
            case GOAT:
                return new GoatFactory();
            case ELEPHANT:
                return new ElephantFactory();
            case ZEBRA:
                return new ZebraFactory();
            case WOLF:
                return new WolfFactory();
            case TIGER:
                return new TigerFactory();
            case LEOPARD:
                return new LeopardFactory();
            case ROCK:
                return new RockFactory();
            case TREE:
                return new TreeFactory();
            case GRASS:
                return new GrassFactory();
            default:
                System.out.println("Invalid entity type");
                return null;
        }
    }
}
