package com.murlov.model;

public class Goat extends Herbivore {

    public Goat() {
        super("\uD83D\uDC10");
    }

    public Goat(int speed, int health) {
        super(speed, health,"\uD83D\uDC10");
    }

    public EntityType getType() {
        return EntityType.GOAT;
    }
}
