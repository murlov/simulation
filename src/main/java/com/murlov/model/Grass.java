package com.murlov.model;

public class Grass extends Entity {
    public Grass() {
        super("\uD83C\uDF31");
    }

    public EntityType getType() {
        return EntityType.GRASS;
    }

    public EntityGroup getGroup() {
        return EntityGroup.GRASS;
    }
}
