package com.murlov.model;

import com.murlov.action.MoveEventListener;
import com.murlov.action.PathFinder;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public abstract class Creature extends Entity {
    private int speed;
    private int health;
    private MoveEventListener listener;

    public abstract boolean makeMove(Map map, Coordinates oldCoordinates, PathFinder pathFinder);

    public Creature(String icon) {
        super(icon);
        this.speed = 1;
        this.health = 10;
    }

    public Creature(int speed, int health, String icon) {
        super(icon);
        this.speed = speed;
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMoveEventListener(MoveEventListener listener) {
        this.listener = listener;
    }

    public void notifyMove(EntityType creatureType, Coordinates from, Coordinates to) {
        if (listener != null) {
            listener.onMove(creatureType, from, to);
        }
    }

    public void notifyAttack(EntityType attackerType, Coordinates from, EntityType victimType, Coordinates to) {
        if (listener != null) {
            listener.onAttack(attackerType, from, victimType, to);
        }
    }

    public void notifyEat(EntityType creatureType, Coordinates from, EntityType victimType, Coordinates to) {
        if (listener != null) {
            listener.onEat(creatureType, from, victimType, to);
        }
    }

    public void notifyMoveEnd(Map map) {
        if (listener != null) {
            listener.onMoveEnd(map);
        }
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }
}
