package com.murlov.entity;

public abstract class Predator extends Creature {
    final int damage;

    public Predator(int health, int speed, int satiety, int damage, Class<? extends Entity> food) {
        super(health, speed, satiety, food);
        this.damage = damage;
    }
}
