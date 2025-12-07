package com.murlov.simulation;

import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;

import java.util.HashMap;

public class Map {

    private final java.util.Map<Coordinates, Entity> entities;
    private int predatorsCount;
    private int herbivoresCount;
    private int grassCount;
    private final Size size;

    public Map(Size size) {
        this.size = size;
        entities = new HashMap<>();
        predatorsCount = 0;
        herbivoresCount = 0;
        grassCount = 0;
    }

    public Map(Map map) {
        this.entities = new HashMap<>(map.entities);
        this.predatorsCount = map.predatorsCount;
        this.herbivoresCount = map.herbivoresCount;
        this.grassCount = map.grassCount;
        this.size = map.size;
    }

    public java.util.Map<Coordinates, Entity> getEntities() {
        return entities;
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);
    }

    public Size getSize() {
        return size;
    }

    public int getNumberOfCells() {
        return size.getArea();
    }

    public int getCountInGroup(EntityGroup group) {
        return switch(group) {
            case EntityGroup.PREDATOR -> predatorsCount;
            case EntityGroup.HERBIVORE -> herbivoresCount;
            case EntityGroup.GRASS -> grassCount;
            case EntityGroup.STATIC -> 0;
        };
    }

    public void countInGroupDecrement(EntityGroup group) {
        switch(group) {
            case EntityGroup.PREDATOR -> predatorsCount -= 1;
            case EntityGroup.HERBIVORE -> herbivoresCount -= 1;
            case EntityGroup.GRASS -> grassCount -= 1;
        }
    }

    public void countInGroupIncrement(EntityGroup group) {
        switch(group) {
            case EntityGroup.PREDATOR -> predatorsCount += 1;
            case EntityGroup.HERBIVORE -> herbivoresCount += 1;
            case EntityGroup.GRASS -> grassCount += 1;
        }
    }

    public Coordinates getFreeCellCoordinates() {
        Coordinates coordinates = Coordinates.getRandom(getSize());

        while (getEntities().containsKey(coordinates)) {
            coordinates = Coordinates.getRandom(getSize());
        }

        return coordinates;
    }

}