package com.murlov.factory;

import com.murlov.model.EntityType;

public class EntityFactoryProvider {
    public static EntityFactory getFactory(EntityType entityType) {
        return switch (entityType) {
            case GOAT -> new GoatFactory();
            case ELEPHANT -> new ElephantFactory();
            case ZEBRA -> new ZebraFactory();
            case WOLF -> new WolfFactory();
            case TIGER -> new TigerFactory();
            case LEOPARD -> new LeopardFactory();
            case ROCK -> new RockFactory();
            case TREE -> new TreeFactory();
            case GRASS -> new GrassFactory();
        };
    }
}
