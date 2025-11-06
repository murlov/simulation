package com.murlov.model;

public class Wolf extends Predator {

    public Wolf() {
        super("\uD83D\uDC3A");
    }

    public Wolf(Integer speed, Integer health, Integer damage) {
        super(speed, health, damage, "\uD83D\uDC3A");
    }

    public EntityType getType() {
        return EntityType.WOLF;
    }
}
