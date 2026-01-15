package com.murlov.action;

import com.murlov.factory.EntityFactory;
import com.murlov.factory.EntityFactoryProvider;
import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public class Spawner {
    public static void execute(SimulationMap simulationMap, String entityName, MoveListenerRegistry listenerRegistry, int count) {
        EntityFactory entityFactory = EntityFactoryProvider.getFactory(entityName);
        for (int i = 0; i < count; i++) {
            Entity entity = entityFactory.create();
            Coordinates coordinates = simulationMap.getFreeCellCoordinates();
            simulationMap.setEntity(coordinates, entity);
            simulationMap.countForEntityTypeIncrement(entity.getClass());
            if (entity instanceof Creature creature) {
                listenerRegistry.attachListener(creature);
            }
        }
    }
}
