package com.murlov.model;

public class Elephant extends Herbivore {

    public Elephant() {
        super("\uD83D\uDC18");
    }

    public Elephant(int speed, int health) {
        super(speed, health,"\uD83D\uDC18");
    }

    public EntityType getType() {
        return EntityType.ELEPHANT;
    }
}
