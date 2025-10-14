package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Leopard;

public class LeopardFactory implements EntityFactory {

    @Override
    public Entity createEntity() {
        return new Leopard(1, 10, 1);
    }
}
