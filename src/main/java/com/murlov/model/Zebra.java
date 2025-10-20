package com.murlov.model;

public class Zebra extends Herbivore {

    public Zebra() {
        super( "\uD83E\uDD93");
    }

    public Zebra(Integer speed, Integer health) {
        super(speed, health,"\uD83E\uDD93");
    }
}
