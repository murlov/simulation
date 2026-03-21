package com.murlov.entity.factory;

import com.murlov.entity.Entity;
import com.murlov.entity.Tree;

public class TreeFactory implements EntityFactory {

    @Override
    public Entity create() {
        return new Tree();
    }
}
