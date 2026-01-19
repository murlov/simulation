package com.murlov.model;

public abstract class Predator extends Creature {
    private final int damage;

    public Predator(int health, int speed, int satiety, int damage) {
        super(health, speed, satiety);
        this.damage = damage;
    }

    @Override
    public int getDamage() {
        return damage;
    }
}
