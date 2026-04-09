package com.murlov.action;

import com.murlov.action.listener.*;
import com.murlov.entity.Creature;
import com.murlov.entity.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;
import com.murlov.view.Renderer;

import java.util.ArrayList;
import java.util.List;

public class MoveAction implements Action {

    private final EventBus eventBus;
    private final SpawnAction spawnAction;
    private final PathFinder pathFinder;

    public MoveAction(EventBus eventBus, Renderer renderer, SpawnAction spawnAction,
                      PathFinder pathFinder) {
        this.spawnAction = spawnAction;
        this.eventBus = eventBus;
        initEventBus(renderer);
        this.pathFinder = pathFinder;
    }

    private void initEventBus(Renderer renderer) {
        eventBus.subscribe(DeathEvent.class, new DeathEventLogger(renderer));
        eventBus.subscribe(EatEvent.class, new EatEventLogger(renderer));
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        List<Coordinates> keys = new ArrayList<>(simulationMap.getEntities().keySet());

        for (Coordinates coordinates : keys) {
            Entity entity = simulationMap.getEntity(coordinates);
            if (entity instanceof Creature creature) {
                creature.makeMove(simulationMap, pathFinder, eventBus);
            }
            spawnAction.execute(simulationMap);
        }
    }
}
