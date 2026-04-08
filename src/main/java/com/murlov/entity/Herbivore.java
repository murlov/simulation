package com.murlov.entity;

public abstract class Herbivore extends Creature {


    public Herbivore(int health, int speed, int satiety, Class<? extends Entity> food) {
        super(health, speed, satiety, food);
    }
}
