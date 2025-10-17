package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Tiger;

public class TigerFactory implements EntityFactory {

    @Override
    public Entity create() {
        return new Tiger(1, 10, 1);
    }
}
