package com.murlov.simulation;

import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.settings.SimulationSettings;

import java.util.HashMap;

public class Map {

    private java.util.Map<Coordinates, Entity> entities;
    private int predatorsCount;
    private int herbivoresCount;
    private int grassCount;

    public Map() {
        entities = new HashMap<>();
        predatorsCount = 0;
        herbivoresCount = 0;
        grassCount = 0;
    }

    public Map(Map map) {
        this.entities = map.entities;
        this.predatorsCount = map.predatorsCount;
        this.herbivoresCount = map.herbivoresCount;
        this.grassCount = map.grassCount;
    }

    public java.util.Map<Coordinates, Entity> getEntities() {
        return entities;
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);
    }

    public int getCountInGroup(EntityGroup group) {
        return switch(group) {
            case EntityGroup.PREDATOR -> predatorsCount;
            case EntityGroup.HERBIVORE -> herbivoresCount;
            case EntityGroup.GRASS -> grassCount;
            case EntityGroup.STATIC -> 0;
        };
    }

    public void setCountInGroup(EntityGroup group, int count) {
        switch(group) {
            case EntityGroup.PREDATOR -> predatorsCount = count;
            case EntityGroup.HERBIVORE -> herbivoresCount = count;
            case EntityGroup.GRASS -> grassCount = count;
        }
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

    public Coordinates getFreeCellCoordinates(SimulationSettings settings) {
        Coordinates coordinates = Coordinates.getRandom(settings.getSizeOfMap());

        while (getEntities().containsKey(coordinates)) {
            coordinates = Coordinates.getRandom(settings.getSizeOfMap());
        }

        return coordinates;
    }

}