package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Wolf;

public class WolfFactory implements EntityFactory {

    @Override
    public Entity create() {
        return new Wolf(1, 10, 1);
    }
}
