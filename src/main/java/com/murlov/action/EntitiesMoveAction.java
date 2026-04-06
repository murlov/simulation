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

    private PauseCallback pauseCallback;
    private ExitCallback exitCallback;
    private SpawnCallback spawnCallback;
    private final EventBus eventBus;

    public EntitiesMoveAction(EventBus eventBus, Renderer renderer) {
        this.eventBus = eventBus;
        initEventBus(renderer);
    }

    public interface PauseCallback {
        void waitIfPaused();
    }

    public interface ExitCallback {
        boolean shouldExit();
    }

    public interface SpawnCallback {
        void spawn(SimulationMap simulationMap);
    }

    public void setPauseCallback(PauseCallback pauseCallback) {
        this.pauseCallback = pauseCallback;
    }

    public void setExitCallback(ExitCallback exitCallback) {
        this.exitCallback = exitCallback;
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
        boolean hasMoved = false;
        List<Coordinates> keys = new ArrayList<>(simulationMap.getEntities().keySet());

        PathFinder pathFinder = new BfsPathFinder();

        for (Coordinates coordinates : keys) {
            pauseCallback.waitIfPaused();
            if (exitCallback.shouldExit()) {
                return;
            }
            Entity entity = simulationMap.getEntity(coordinates);
            if (entity instanceof Creature creature) {
                if (creature.makeMove(simulationMap, pathFinder, eventBus)) {
                    hasMoved = true;
                }
            }
            spawnCallback.spawn(simulationMap);
        }
        if (!hasMoved) {
            throw new RuntimeException("Creatures cannot move");
        }
    }
}
