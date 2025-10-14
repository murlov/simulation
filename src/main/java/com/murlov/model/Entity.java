package com.murlov.model;

import com.murlov.simulation.Coordinates;

abstract public class Entity {
    private Coordinates coordinates;
    private final String icon;

    protected Entity(String icon) {
        this.icon = icon;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getIcon () {
        return icon;
    }
}
