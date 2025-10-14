package com.murlov.simulation;

import com.murlov.factory.*;
import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.model.EntityType;

import java.util.HashMap;
import java.util.Random;

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
        Coordinates coordinates = Coordinates.getRandom(SimulationSettings.getSizeOfMap());
        Entity entity;
        for (EntityGroup entityGroup : EntityGroup.values()) {
            for (int i = 0; i < SimulationSettings.getGroupCount(); i++) {
                while (entities.containsKey(coordinates)) {
                    coordinates = Coordinates.getRandom(SimulationSettings.getSizeOfMap());
                }
                EntityFactory entityFactory = getFactoryByName(EntityType.getRandom(entityGroup));
                entity = entityFactory.createEntity();
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
