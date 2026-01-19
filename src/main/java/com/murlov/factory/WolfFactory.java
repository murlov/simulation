package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Wolf;

public class WolfFactory implements EntityFactory {

    private final int health;
    private final int speed;
    private final int satiety;
    private final int damage;

    public WolfFactory(int health, int speed, int satiety, int damage) {
        this.health = health;
        this.speed = speed;
        this.satiety = satiety;
        this.damage = damage;
    }

    @Override
    public Entity create() {
        return new Wolf(health, speed, satiety, damage);
    }
}
