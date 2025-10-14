package com.murlov.model;

abstract public class Creature extends Entity {
    private Integer speed;
    private Integer health;

    void makeMove() {
    }

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
