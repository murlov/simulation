package com.murlov.factory;

import com.murlov.model.Elephant;
import com.murlov.model.Entity;

public class ElephantFactory implements EntityFactory {

    @Override
    public Entity createEntity() {
        return new Elephant(1, 20);
    }
}
