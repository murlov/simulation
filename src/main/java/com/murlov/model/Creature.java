package com.murlov.model;

import com.murlov.action.PathFindAction;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public abstract class Creature extends Entity {
    private Integer speed;
    private Integer health;

    public boolean makeMove(Map map, Coordinates oldCoordinates){
        Coordinates newCoordinates = PathFindAction.execute(map, this);

        if (this != null && newCoordinates != null){
            map.setEntity(newCoordinates, this);
            map.getEntities().remove(oldCoordinates);
            return true;
        }
        return false;
    };

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
}
