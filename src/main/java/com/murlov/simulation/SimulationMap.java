package com.murlov.simulation;

import com.murlov.model.Entity;

import java.util.HashMap;
import java.util.Map;

public class SimulationMap {

    private final Map<Coordinates, Entity> entities;
    private int wolfsCount;
    private int rabbitsCount;
    private int grassCount;
    private final Size size;

    public SimulationMap(Size size) {
        this.size = size;
        entities = new HashMap<>();
        wolfsCount = 0;
        rabbitsCount = 0;
        grassCount = 0;
    }

    public SimulationMap(SimulationMap simulationMap) {
        this.entities = new HashMap<>(simulationMap.entities);
        this.wolfsCount = simulationMap.wolfsCount;
        this.rabbitsCount = simulationMap.rabbitsCount;
        this.grassCount = simulationMap.grassCount;
        this.size = simulationMap.size;
    }

    public Map<Coordinates, Entity> getEntities() {
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

    public int getCountByType(Class<? extends Entity> entityType) {
        String name = entityType.getSimpleName();

        return switch(name) {
            case "Wolf" -> wolfsCount;
            case "Rabbit" -> rabbitsCount;
            case "Grass" -> grassCount;
            default -> throw new IllegalStateException("Not count for this entity: " + name);
        };
    }

    public void countForEntityTypeDecrement(Class<? extends Entity> entityType) {
        String name = entityType.getSimpleName();

        switch(name) {
            case "Wolf" -> wolfsCount--;
            case "Rabbit" -> rabbitsCount--;
            case "Grass" -> grassCount--;
        }
    }

    public void countForEntityTypeIncrement(Class<? extends Entity> entityType) {
        String name = entityType.getSimpleName();

        switch(name) {
            case "Wolf" -> wolfsCount++;
            case "Rabbit" -> rabbitsCount++;
            case "Grass" -> grassCount++;
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