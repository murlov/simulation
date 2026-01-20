package com.murlov.model.factory;

import com.murlov.settings.SimulationSettings;

public class EntityFactoryProvider {

    public static EntityFactory getFactory(String name, SimulationSettings settings) {
        return switch (name) {
            case "Wolf" -> new WolfFactory(settings.getPredatorHealth(), settings.getPredatorSpeed(), settings.getPredatorSatiety(),  settings.getPredatorDamage());
            case "Rabbit" -> new RabbitFactory(settings.getHerbivoreHealth(), settings.getHerbivoreSpeed(), settings.getHerbivoreSatiety());
            case "Rock" -> new RockFactory();
            case "Tree" -> new TreeFactory();
            case "Grass" -> new GrassFactory();
            default -> throw new IllegalStateException("Unexpected entity: " + name);
        };
    }
}
