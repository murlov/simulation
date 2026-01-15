package com.murlov.model;

public abstract class Predator extends Creature {
    private final static int DEFAULT_DAMAGE = 1;
    private final int damage;

    public Predator() {
        super();
        this.damage = DEFAULT_DAMAGE;
    }

    @Override
    public int getDamage() {
        return damage;
    }
}
