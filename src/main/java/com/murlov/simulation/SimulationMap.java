package com.murlov.simulation;

import com.murlov.model.Entity;
import com.murlov.util.RandomProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SimulationMap {

    private final Map<Coordinates, Entity> entities;
    private final Map<Class<? extends Entity>, Integer> entityCounts;
    private final Size size;

    public SimulationMap(Size size) {
        this(new HashMap<>(), new HashMap<>(), size);
    }

    public SimulationMap(SimulationMap simulationMap) {
        this(simulationMap.entities, simulationMap.entityCounts, simulationMap.size);
    }

    private SimulationMap(Map<Coordinates, Entity> entities, Map<Class<? extends Entity>, Integer> entityCounts, Size size) {
        this.entities = new HashMap<>(entities);
        this.entityCounts = new HashMap<>(entityCounts);
        this.size = size;
    }

    public Map<Coordinates, Entity> getEntities() {
        return entities;
    }

    public void setEntity(Entity entity, Coordinates coordinates) {
        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);

        entityCounts.merge(entity.getClass(), 1, Integer::sum);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.getCoordinates());

        entityCounts.merge(entity.getClass(), -1, Integer::sum);
    }

    public void moveEntity(Entity entity, Coordinates coordinates) {
        entities.remove(entity.getCoordinates());

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
        return entityCounts.getOrDefault(entityType, 0);
    }
}