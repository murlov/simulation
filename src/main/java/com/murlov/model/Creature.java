package com.murlov.model;

import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public abstract class Creature extends Entity {
    private int speed;
    private int health;

    public abstract boolean makeMove(Map map, Coordinates oldCoordinates);

    public Creature(String icon) {
        super(icon);
        this.speed = 1;
        this.health = 10;
    }

    public Creature(Integer speed, Integer health, String icon) {
        super(icon);
        this.speed = speed;
        this.health = health;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }
}
