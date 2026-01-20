package com.murlov.model.factory;

import com.murlov.model.Entity;
import com.murlov.model.Tree;

public class TreeFactory implements EntityFactory {

    @Override
    public Entity create() {
        return new Tree();
    }
}
