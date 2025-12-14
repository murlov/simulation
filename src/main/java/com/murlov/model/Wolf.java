package com.murlov.model;

public class Wolf extends Predator {

    public Wolf() {
        super("\uD83D\uDC3A");
    }

    public Wolf(int health, int damage) {
        super(health, damage, "\uD83D\uDC3A");
    }

    public EntityType getType() {
        return EntityType.WOLF;
    }
}
