package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Zebra;

public class ZebraFactory implements EntityFactory{

    @Override
    public Entity create() {
        return new Zebra(10);
    }
}
