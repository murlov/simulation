package com.murlov.model;

public class Rock extends Entity {

    public Rock() {
        super("\uD83E\uDEA8");
    }

    public EntityType getType() {
        return EntityType.ROCK;
    }

    public EntityGroup getGroup() {
        return EntityGroup.STATIC;
    }
}
