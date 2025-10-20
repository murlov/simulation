package com.murlov.model;

public class Leopard extends Predator{

    public Leopard() {
        super("\uD83D\uDC06");
    }

    public Leopard(Integer speed, Integer health, Integer damage) {
        super(speed, health, damage,"\uD83D\uDC06");
    }
}
