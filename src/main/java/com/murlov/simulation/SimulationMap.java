package com.murlov.simulation;

import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;

import java.util.HashMap;

public class SimulationMap {

    private final java.util.Map<Coordinates, Entity> entities;
    private int predatorsCount;
    private int herbivoresCount;
    private int grassCount;
    private final Size size;

    public SimulationMap(Size size) {
        this.size = size;
        entities = new HashMap<>();
        predatorsCount = 0;
        herbivoresCount = 0;
        grassCount = 0;
    }

    public SimulationMap(SimulationMap simulationMap) {
        this.entities = new HashMap<>(simulationMap.entities);
        this.predatorsCount = simulationMap.predatorsCount;
        this.herbivoresCount = simulationMap.herbivoresCount;
        this.grassCount = simulationMap.grassCount;
        this.size = simulationMap.size;
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
            case EntityGroup.PREDATOR -> predatorsCount--;
            case EntityGroup.HERBIVORE -> herbivoresCount--;
            case EntityGroup.GRASS -> grassCount--;
        }
    }

    public void countInGroupIncrement(EntityGroup group) {
        switch(group) {
            case EntityGroup.PREDATOR -> predatorsCount++;
            case EntityGroup.HERBIVORE -> herbivoresCount++;
            case EntityGroup.GRASS -> grassCount++;
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