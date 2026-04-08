package com.murlov.entity.factory;

import com.murlov.entity.Grass;
import com.murlov.entity.Rabbit;
import com.murlov.settings.SimulationSettings;

public class EntityFactoryProvider {

    public static EntityFactory getFactory(String name, SimulationSettings settings) {
        return switch (name) {
            case "Wolf" -> new WolfFactory(settings.getPredatorHealth(), settings.getPredatorSpeed(),
                    settings.getPredatorSatiety(),  settings.getPredatorDamage(), Rabbit.class);
            case "Rabbit" -> new RabbitFactory(settings.getHerbivoreHealth(), settings.getHerbivoreSpeed(),
                    settings.getHerbivoreSatiety(), Grass.class);
            case "Rock" -> new RockFactory();
            case "Tree" -> new TreeFactory();
            case "Grass" -> new GrassFactory();
            default -> throw new IllegalStateException("Unexpected entity: " + name);
        };
    }
}
