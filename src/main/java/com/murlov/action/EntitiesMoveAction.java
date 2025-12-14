package com.murlov.action;

import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;
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
    public void execute(Map map) {
        boolean hasMoved = false;
        List<Coordinates> keys = new ArrayList<>(map.getEntities().keySet());
        PathFinder pathFinder = new BfsPathFinder(map.getSize(), map.getNumberOfCells());
        for (Coordinates coordinates : keys) {
            pauseCallback.waitIfPaused();
            if (exitCallback.shouldExit()) {
                return;
            }
            Entity entity = map.getEntities().get(coordinates);
            if (entity instanceof Creature creature) {
                creature.notifyMoveStart();
                if (creature.makeMove(map, coordinates, pathFinder)) {
                    hasMoved = true;

                    if (creature.isDead) {
                        creature.notifyDeath(creature.getType(), creature.getCoordinates());
                        map.getEntities().remove(creature.getCoordinates());
                        map.countInGroupDecrement(creature.getGroup());
                    }
                    creature.notifyMoveEnd(map);
                }
            }
        }
        if (!hasMoved) {
            Renderer renderer = new Renderer();
            renderer.entitiesCannotMoveMessage();
        }
    }
}
