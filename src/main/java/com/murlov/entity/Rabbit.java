package com.murlov.entity;

public class Rabbit extends Herbivore{

    public Rabbit(int health, int speed, int satiety, Class<? extends Entity> food) {
        super(health, speed, satiety, food);
    }
}
