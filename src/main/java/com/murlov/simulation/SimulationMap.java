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

    public Entity getEntity(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    public void setEntity(Entity entity, Coordinates coordinates) {
        validateCoordinate(coordinates);

        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);
    }

    public void removeEntity(Entity entity) {
        validateCoordinate(entity.getCoordinates());

        entities.remove(entity.getCoordinates());
    }

    public void moveEntity(Entity entity, Coordinates coordinates) {
        validateCoordinate(entity.getCoordinates());
        validateCoordinate(coordinates);

        entities.remove(entity.getCoordinates());
        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);
    }

    public Size getSize() {
        return size;
    }

    public boolean isInside(Coordinates coordinates) {
        return coordinates.x() >= 0 && coordinates.x() < size.width()
                && coordinates.y() >= 0 && coordinates.y() < size.height();
    }

    private void validateCoordinate(Coordinates coordinates) {
        if (!isInside(coordinates)) {
            throw new IllegalArgumentException("Coordinates out of bounds:" + coordinates);
        }
    }
}