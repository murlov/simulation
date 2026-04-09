package com.murlov.action;

import com.murlov.action.listener.*;
import com.murlov.entity.Creature;
import com.murlov.entity.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;
import com.murlov.view.Renderer;

import java.util.ArrayList;
import java.util.List;

public class EntitiesMoveAction implements Action {

    private SpawnCallback spawnCallback;
    private final EventBus eventBus;

    public EntitiesMoveAction(EventBus eventBus, Renderer renderer) {
        this.eventBus = eventBus;
        initEventBus(renderer);
    }

    public interface SpawnCallback {
        void spawn(SimulationMap simulationMap);
    }

    public void setSpawnCallback(SpawnCallback spawnCallback) {
        this.spawnCallback = spawnCallback;
    }

    private void initEventBus(Renderer renderer) {
        eventBus.subscribe(DeathEvent.class, new DeathEventLogger(renderer));
        eventBus.subscribe(EatEvent.class, new EatEventLogger(renderer));
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        List<Coordinates> keys = new ArrayList<>(simulationMap.getEntities().keySet());

        PathFinder pathFinder = new BfsPathFinder();

        for (Coordinates coordinates : keys) {
            Entity entity = simulationMap.getEntity(coordinates);
            if (entity instanceof Creature creature) {
                creature.makeMove(simulationMap, pathFinder, eventBus);
            }
            spawnCallback.spawn(simulationMap);
        }
    }
}
