package com.murlov.action;

import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

import java.util.ArrayList;
import java.util.List;

public class EntitiesMoveAction implements Action {

    private PauseCallback pauseCallback;
    private ExitCallback exitCallback;
    private SpawnCallback spawnCallback;
    private final MoveEventListener listener;

    public EntitiesMoveAction(MoveEventListener listener) {
        this.listener = listener;
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

    @Override
    public void execute(SimulationMap simulationMap) {
        boolean hasMoved = false;
        List<Coordinates> keys = new ArrayList<>(simulationMap.getEntities().keySet());
        PathFinder pathFinder = new BfsPathFinder(simulationMap.getSize(), simulationMap.getNumberOfCells());
        for (Coordinates coordinates : keys) {
            pauseCallback.waitIfPaused();
            if (exitCallback.shouldExit()) {
                return;
            }
            Entity entity = simulationMap.getEntities().get(coordinates);
            if (entity instanceof Creature creature) {
                creature.notifyMoveStart();
                if (creature.makeMove(simulationMap, coordinates, pathFinder)) {
                    hasMoved = true;

                    if (creature.isDead) {
                        creature.notifyDeath(creature.getClass(), creature.getCoordinates());
                        simulationMap.getEntities().remove(creature.getCoordinates());
                        simulationMap.countForEntityTypeDecrement(creature.getClass());
                    }
                    listener.onMoveEnd(simulationMap);
                }
            }
            spawnCallback.spawn(simulationMap);
        }
        if (!hasMoved) {
            throw new RuntimeException("Creatures cannot move");
        }
    }
}
