package com.murlov.model;

public class Tree extends Entity {
    public Tree() {
        super("\uD83C\uDF33");
    }

    public EntityType getType() {
        return EntityType.TREE;
    }

    public EntityGroup getGroup() {
        return EntityGroup.STATIC;
    }
}
