package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Grass;

public class GrassFactory implements EntityFactory{
    @Override
    public Entity createEntity() {
        return new Grass();
    }
}
