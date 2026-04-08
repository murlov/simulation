package com.murlov.entity.factory;

import com.murlov.entity.Entity;
import com.murlov.entity.Rabbit;

public class RabbitFactory implements EntityFactory {

    private final int health;
    private final int speed;
    private final int satiety;
    private final Class<? extends Entity> food;

    public RabbitFactory(int health, int speed, int satiety, Class<? extends Entity> food) {
        this.health = health;
        this.speed = speed;
        this.satiety = satiety;
        this.food = food;
    }

    @Override
    public Entity create() {
        return new Rabbit(health, speed, satiety, food);
    }
}
