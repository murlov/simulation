package com.murlov.model;

public class Elephant extends Herbivore {

    public Elephant() {
        super("\uD83D\uDC18");
    }

    public Elephant(Integer speed, Integer health) {
        super(speed, health,"\uD83D\uDC18");
    }

    public EntityType getType() {
        return EntityType.ELEPHANT;
    }
}
