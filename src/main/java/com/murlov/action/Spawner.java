package com.murlov.action;

import com.murlov.action.listener.MoveEventListener;
import com.murlov.action.listener.MoveListenerRegistry;
import com.murlov.model.factory.EntityFactory;
import com.murlov.model.factory.EntityFactoryProvider;
import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public class Spawner {

    public static void execute(SimulationMap simulationMap, String entityName, MoveListenerRegistry listenerRegistry, int count, boolean isLoggingRequired, MoveEventListener listener) {
        SimulationSettings settings = SimulationSettings.getInstance();
        EntityFactory entityFactory = EntityFactoryProvider.getFactory(entityName, settings);
        for (int i = 0; i < count; i++) {
            Entity entity = entityFactory.create();
            Coordinates coordinates = simulationMap.getFreeCellCoordinates();
            simulationMap.setEntity(coordinates, entity);
            simulationMap.countForEntityTypeIncrement(entity.getClass());
            if (entity instanceof Creature creature) {
                listenerRegistry.attachListener(creature);
            }
            if (isLoggingRequired && listener != null) {
                listener.onSpawn(entity.getClass(), coordinates);
                listener.onMoveEnd(simulationMap);
            }
        }
    }

    public static void execute(SimulationMap simulationMap, String entityName, MoveListenerRegistry listenerRegistry, int count, boolean isLoggingRequired) {
        execute(simulationMap, entityName, listenerRegistry, count, isLoggingRequired, null);
    }
}
