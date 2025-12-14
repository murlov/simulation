package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Leopard;

public class LeopardFactory implements EntityFactory {

    @Override
    public Entity create() {
        return new Leopard(10, 1);
    }
}
