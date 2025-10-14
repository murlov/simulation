package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Tree;

public class TreeFactory implements EntityFactory {
    @Override
    public Entity createEntity() {
        return new Tree();
    }
}
