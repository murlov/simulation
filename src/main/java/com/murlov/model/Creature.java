package com.murlov.model;

import com.murlov.action.MoveEventListener;
import com.murlov.action.PathFinder;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public abstract class Creature extends Entity {
    private int health;
    private int satiety;
    private MoveEventListener listener;
    public boolean isDead;
    private final static int DEAFAULT_HEALTH = 10;
    private final static int DEFAULT_SATIETY = 10;
    private final static int DAMAGE_FROM_HUNGER = 1;

    public abstract boolean makeMove(Map map, Coordinates oldCoordinates, PathFinder pathFinder);

    public Creature(String icon) {
        super(icon);
        this.health = DEAFAULT_HEALTH;
        this.satiety = DEFAULT_SATIETY;
    }

    public Creature(int health, String icon) {
        super(icon);
        this.health = health;
        satiety = DEFAULT_SATIETY;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
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

    public void notifyDeath(EntityType creatureType, Coordinates coordinates) {
        if (listener != null) {
            listener.onDeath(creatureType, coordinates);
        }
    }

    public void notifyMoveEnd(Map map) {
        if (listener != null) {
            listener.onMoveEnd(map);
        }
    }

    public void notifyMoveStart() {
        if (listener != null) {
            listener.onMoveStart();
        }
    }

    public void takeDamage (){
        health = Math.max(0, health - DAMAGE_FROM_HUNGER);
        if (health == 0) {
            die();
        }
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    private void die() {
        isDead = true;
    }

    public void satietyIncrement() {
        satiety = Math.min(10, ++satiety);
    }

    public void satietyDecrement() {
        if (satiety <= 0) {
            takeDamage();
        }
        satiety--;
    }
}
