package com.murlov.entity;

public abstract class Predator extends Creature {
    private final int damage;

    public Predator(int health, int speed, int satiety, int damage, Class<? extends Entity> food) {
        super(health, speed, satiety, food);
        this.damage = damage;
    }

    @Override
    public int getDamage() {
        return damage;
    }
}
