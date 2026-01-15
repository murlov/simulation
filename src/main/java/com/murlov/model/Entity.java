package com.murlov.model;

import com.murlov.simulation.Coordinates;

public abstract class Entity {
    private Coordinates coordinates;

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
