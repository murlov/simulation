package com.murlov.entity.factory;

import com.murlov.entity.Entity;
import com.murlov.entity.Wolf;

public class WolfFactory implements EntityFactory {

    private final int health;
    private final int speed;
    private final int satiety;
    private final int damage;
    private final Class<? extends Entity> food;

    public WolfFactory(int health, int speed, int satiety, int damage, Class<? extends Entity> food) {
        this.health = health;
        this.speed = speed;
        this.satiety = satiety;
        this.damage = damage;
        this.food = food;
    }

    @Override
    public Entity create() {
        return new Wolf(health, speed, satiety, damage, food);
    }
}
