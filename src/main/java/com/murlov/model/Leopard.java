package com.murlov.model;

public class Leopard extends Predator{

    public Leopard() {
        super("\uD83D\uDC06");
    }

    public Leopard(int speed, int health, int damage) {
        super(speed, health, damage,"\uD83D\uDC06");
    }

    public EntityType getType() {
        return EntityType.LEOPARD;
    }
}
