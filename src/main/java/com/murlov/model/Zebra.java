package com.murlov.model;

public class Zebra extends Herbivore {

    public Zebra() {
        super( "\uD83E\uDD93");
    }

    public Zebra(int health) {
        super(health,"\uD83E\uDD93");
    }

    public EntityType getType() {
        return EntityType.ZEBRA;
    }
}
