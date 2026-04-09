package com.murlov.factory.entityfactory;

import com.murlov.entity.*;
import com.murlov.settings.SimulationSettings;

public class EntityFactory {
    private final SimulationSettings simulationSettings;

    public EntityFactory(SimulationSettings simulationSettings) {
        this.simulationSettings = simulationSettings;
    }

    public Entity get(Class<? extends Entity> type) {
        String name = type.getSimpleName();
        return switch (name) {
            case "Grass" -> new Grass();
            case "Rabbit" -> new Rabbit(
                    simulationSettings.getHerbivoreHealth(),
                    simulationSettings.getHerbivoreSpeed(),
                    simulationSettings.getHerbivoreSatiety(),
                    Grass.class);
            case "Rock" -> new Rock();
            case "Tree" -> new Tree();
            case "Wolf" -> new Wolf(
                    simulationSettings.getPredatorHealth(),
                    simulationSettings.getPredatorSpeed(),
                    simulationSettings.getPredatorSatiety(),
                    simulationSettings.getPredatorDamage(),
                    Rabbit.class
                    );

            default -> throw new IllegalStateException("Unexpected type: " + name);
        };
    }
}
