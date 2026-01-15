package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Rabbit;

public class RabbitFactory implements EntityFactory {

    @Override
    public Entity create() {
        return new Rabbit();
    }
}
