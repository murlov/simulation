package com.murlov.simulation;

import com.murlov.model.Entity;
import com.murlov.util.RandomProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SimulationMap {

    private final Map<Coordinates, Entity> entities;
    private int wolfsCount;
    private int rabbitsCount;
    private int grassCount;
    private final Size size;

    public SimulationMap(Size size) {
        this(new HashMap<>(), 0, 0, 0, size);
    }

    public SimulationMap(SimulationMap simulationMap) {
        this(new HashMap<>(simulationMap.entities), simulationMap.wolfsCount, simulationMap.rabbitsCount, simulationMap.grassCount, simulationMap.size);
    }

    private SimulationMap(Map<Coordinates, Entity> entities, int wolfsCount, int rabbitsCount, int grassCount, Size size) {
        this.entities = entities;
        this.wolfsCount = wolfsCount;
        this.rabbitsCount = rabbitsCount;
        this.grassCount = grassCount;
        this.size = size;
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

    private Coordinates getRandomCoordinates(Size size) {
        Random random = RandomProvider.getInstance();
        int x = random.nextInt(size.width());
        int y = random.nextInt(size.height());
        return new Coordinates(x, y);
    }

    public Coordinates getFreeCellCoordinates() {

        Coordinates coordinates = getRandomCoordinates(getSize());

        while (getEntities().containsKey(coordinates)) {
            coordinates = getRandomCoordinates(getSize());
        }

        return coordinates;
    }

}