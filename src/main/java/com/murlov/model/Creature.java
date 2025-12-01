package com.murlov.model;

import com.murlov.action.MoveEventListener;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public abstract class Creature extends Entity {
    private int speed;
    private int health;
    private MoveEventListener listener;

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

    public void setMoveEventListener(MoveEventListener listener) {
        this.listener = listener;
    }

    public void notifyMove(EntityType creatureType, Coordinates from, Coordinates to) {
        if (listener != null) {
            listener.onMove(creatureType, from, to);
        }
    }

    public void notifyAttack(EntityType attackerType, EntityType victimType, Coordinates position) {
        if (listener != null) {
            listener.onAttack(attackerType, victimType, position);
        }
    }

    public void notifyEat(EntityType creatureType, EntityType victimType, Coordinates position) {
        if (listener != null) {
            listener.onEat(creatureType, victimType, position);
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
