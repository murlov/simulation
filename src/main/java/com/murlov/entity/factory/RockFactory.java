package com.murlov.entity.factory;

import com.murlov.entity.Entity;
import com.murlov.entity.Rock;

public class RockFactory implements EntityFactory {

    @Override
    public Entity create() {
        return new Rock();
    }
}
