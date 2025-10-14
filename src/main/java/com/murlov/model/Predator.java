package com.murlov.model;

public class Predator extends Creature {
    Integer damage;

    public Predator(String icon) {
        super(icon);
        this.damage = 1;
    }

    public Predator(Integer speed, Integer health, Integer damage, String icon) {
        super(speed, health, icon);
        this.damage = damage;
    }
}
