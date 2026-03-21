package com.murlov.action;

import com.murlov.action.listener.MoveEventListener;
import com.murlov.action.listener.MoveListenerRegistry;
import com.murlov.entity.factory.EntityFactory;
import com.murlov.entity.factory.EntityFactoryProvider;
import com.murlov.entity.Creature;
import com.murlov.entity.Entity;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;
import com.murlov.simulation.Size;
import com.murlov.util.RandomProvider;

import java.util.Random;

public class Spawner {

    public static void execute(SimulationMap simulationMap, String entityName, MoveListenerRegistry listenerRegistry,
                               int count, boolean isLoggingRequired, MoveEventListener listener) {
        SimulationSettings settings = SimulationSettings.getInstance();
        EntityFactory entityFactory = EntityFactoryProvider.getFactory(entityName, settings);
        for (int i = 0; i < count; i++) {
            Entity entity = entityFactory.create();
            Coordinates coordinates = getFreeCellCoordinates(simulationMap);
            simulationMap.setEntity(entity, coordinates);
            if (entity instanceof Creature creature) {
                listenerRegistry.attachListener(creature);
            }
            if (isLoggingRequired && listener != null) {
                listener.onSpawn(entity.getClass(), coordinates);
                listener.onMoveEnd(simulationMap);
            }
        }
    }

    public static void execute(SimulationMap simulationMap, String entityName, MoveListenerRegistry listenerRegistry,
                               int count, boolean isLoggingRequired) {
        execute(simulationMap, entityName, listenerRegistry, count, isLoggingRequired, null);
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
