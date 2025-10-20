package com.murlov.simulation;

import com.murlov.model.Entity;

import java.util.HashMap;

public class Map {

    public java.util.Map<Coordinates, Entity> entities;

    public Map() {
        entities = new HashMap<>();
    }

    public java.util.Map<Coordinates, Entity> getEntities() {
        return entities;
    }

    public void setEntity (Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);
    }
}