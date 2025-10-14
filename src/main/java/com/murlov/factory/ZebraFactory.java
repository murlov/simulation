package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Zebra;

public class ZebraFactory implements EntityFactory{
    @Override

    public Entity createEntity() {
        return new Zebra(1, 10);
    }
}
