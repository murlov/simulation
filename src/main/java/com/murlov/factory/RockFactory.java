package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Rock;

public class RockFactory implements EntityFactory {

    @Override
    public Entity createEntity() {
        return new Rock();
    }
}
