package com.murlov.model;

public class Tiger extends Predator{

    public Tiger() {
        super("\uD83D\uDC05");
    }

    public Tiger(Integer speed, Integer health, Integer damage) {
        super(speed, health, damage,"\uD83D\uDC05");
    }

    public EntityType getType() {
        return EntityType.TIGER;
    }
}
