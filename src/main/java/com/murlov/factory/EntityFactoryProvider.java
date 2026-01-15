package com.murlov.factory;

public class EntityFactoryProvider {
    public static EntityFactory getFactory(String name) {
        return switch (name) {
            case "Wolf" -> new WolfFactory();
            case "Rock" -> new RockFactory();
            case "Tree" -> new TreeFactory();
            case "Grass" -> new GrassFactory();
            case "Rabbit" -> new RabbitFactory();
            default -> throw new IllegalStateException("Unexpected entity: " + name);
        };
    }
}
