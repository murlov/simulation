package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Rabbit;

public class RabbitFactory implements EntityFactory {

    private final int health;
    private final int speed;
    private final int satiety;

    public RabbitFactory(int health, int speed, int satiety) {
        this.health = health;
        this.speed = speed;
        this.satiety = satiety;
    }

    @Override
    public Entity create() {
        return new Rabbit(health, speed, satiety);
    }
}
