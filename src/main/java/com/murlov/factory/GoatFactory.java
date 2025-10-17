package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Goat;

public class GoatFactory implements EntityFactory {

    @Override
    public Entity create() {
        return new Goat(1, 10);
    }
}
