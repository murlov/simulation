package com.murlov.simulation;

import com.murlov.model.Entity;

import java.util.HashMap;
import java.util.Map;

public class SimulationMap {

    private final Map<Coordinates, Entity> entities;
    private final Size size;

    public SimulationMap(Size size) {
        this(new HashMap<>(), size);
    }

    public SimulationMap(SimulationMap simulationMap) {
        this(simulationMap.entities,simulationMap.size);
    }

    private SimulationMap(Map<Coordinates, Entity> entities, Size size) {
        this.entities = new HashMap<>(entities);
        this.size = size;
    }

    public Map<Coordinates, Entity> getEntities() {
        return new HashMap<>(entities);
    }

    public void setEntity(Entity entity, Coordinates coordinates) {
        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.getCoordinates());
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
}