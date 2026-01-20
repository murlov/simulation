package com.murlov.model.factory;

import com.murlov.model.Entity;
import com.murlov.model.Rock;

public class RockFactory implements EntityFactory {

    @Override
    public Entity create() {
        return new Rock();
    }
}
