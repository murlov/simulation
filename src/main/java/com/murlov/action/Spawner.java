package com.murlov.action;

import com.murlov.factory.entityfactory.EntityFactory;
import com.murlov.entity.Entity;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;
import com.murlov.simulation.Size;
import com.murlov.util.RandomProvider;

import java.util.Random;

public class Spawner {

    public static void execute(SimulationMap simulationMap, Class<? extends Entity> type, int count) {
        SimulationSettings settings = SimulationSettings.getInstance();
        EntityFactory entityFactory = new EntityFactory(settings);
        for (int i = 0; i < count; i++) {
            Entity entity = entityFactory.get(type);
            Coordinates coordinates = getFreeCellCoordinates(simulationMap);
            simulationMap.setEntity(entity, coordinates);
        }
    }

    private static Coordinates getRandomCoordinates(Size size) {
        Random random = RandomProvider.getInstance();
        int x = random.nextInt(size.width());
        int y = random.nextInt(size.height());
        return new Coordinates(x, y);
    }

    private static Coordinates getFreeCellCoordinates(SimulationMap simulationMap) {

        Coordinates coordinates = getRandomCoordinates(simulationMap.getSize());

        while (simulationMap.getEntities().containsKey(coordinates)) {
            coordinates = getRandomCoordinates(simulationMap.getSize());
        }

        return coordinates;
    }
}
