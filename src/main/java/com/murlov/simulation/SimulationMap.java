package com.murlov.simulation;

import com.murlov.entity.Creature;
import com.murlov.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class SimulationMap {

    private final Map<Coordinates, Entity> entities;
    private final Size size;

    public SimulationMap(Size size) {
        this(new HashMap<>(), size);
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

        if (entity instanceof Creature creature) {
            creature.setCoordinates(coordinates);
        }
        entities.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates) {
        validateCoordinate(coordinates);

        entities.remove(coordinates);
    }

    public void moveCreature(Creature creature, Coordinates coordinates) {
        validateCoordinate(creature.getCoordinates());
        validateCoordinate(coordinates);

        entities.remove(creature.getCoordinates());
        creature.setCoordinates(coordinates);
        entities.put(coordinates, creature);
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