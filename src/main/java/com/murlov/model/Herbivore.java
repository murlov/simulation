package com.murlov.model;

public abstract class Herbivore extends Creature {

    public Herbivore(String icon) {
        super(icon);
    }
    public Herbivore(Integer speed, Integer health, String icon) {
        super(speed, health, icon);
    }

    public EntityGroup getGroup() {
        return EntityGroup.HERBIVORE;
    }
}
