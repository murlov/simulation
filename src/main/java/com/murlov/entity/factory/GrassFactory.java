package com.murlov.entity.factory;

import com.murlov.entity.Entity;
import com.murlov.entity.Grass;

public class GrassFactory implements EntityFactory{

    @Override
    public Entity create() {
        return new Grass();
    }
}
