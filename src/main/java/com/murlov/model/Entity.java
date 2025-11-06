package com.murlov.model;

import com.murlov.simulation.Coordinates;

public abstract class Entity {
    private Coordinates coordinates;
    private final String icon;

    protected Entity(String icon) {
        this.icon = icon;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getIcon () {
        return icon;
    }

    public abstract EntityType getType();

    public abstract EntityGroup getGroup();
}
