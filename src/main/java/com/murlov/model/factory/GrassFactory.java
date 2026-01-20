package com.murlov.model.factory;

import com.murlov.model.Entity;
import com.murlov.model.Grass;

public class GrassFactory implements EntityFactory{

    @Override
    public Entity create() {
        return new Grass();
    }
}
