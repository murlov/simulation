package com.murlov.action;

import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;
import com.murlov.view.Renderer;

import java.util.ArrayList;
import java.util.List;

public class EntitiesMoveAction implements Action {

    public interface PauseCallback {
        void waitIfPaused();
    }

    public interface  ExitCallback {
        boolean shouldExit();
    }

    private PauseCallback pauseCallback;
    private ExitCallback exitCallback;

    public void setPauseCallback(PauseCallback pauseCallback) {
        this.pauseCallback = pauseCallback;
    }

    public void setExitCallback(ExitCallback exitCallback) {
        this.exitCallback = exitCallback;
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
                        creature.notifyDeath(creature.getType(), creature.getCoordinates());
                        simulationMap.getEntities().remove(creature.getCoordinates());
                        simulationMap.countInGroupDecrement(creature.getGroup());
                    }
                    creature.notifyMoveEnd(simulationMap);
                }
            }
        }
        if (!hasMoved) {
            Renderer renderer = new Renderer();
            renderer.entitiesCannotMoveMessage();
        }
    }
}
