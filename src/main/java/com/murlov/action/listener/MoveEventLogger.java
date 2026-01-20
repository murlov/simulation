package com.murlov.action.listener;

import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;
import com.murlov.view.Renderer;

public record MoveEventLogger(Renderer renderer) implements MoveEventListener {

    @Override
    public void onMove(Class<? extends Creature> creatureType, Coordinates from, Coordinates to) {
        renderer.logMove(creatureType, from, to);
    }

    @Override
    public void onAttack(Class<? extends Creature> attackerType, Coordinates from, Class<? extends Creature> victimType, Coordinates to) {
        renderer.logAttack(attackerType, from, victimType, to);
    }

    @Override
    public void onEat(Class<? extends Creature> creatureType, Coordinates from, Class<? extends Entity> victimType, Coordinates to) {
        renderer.logEat(creatureType, from, victimType, to);
    }

    @Override
    public void onSpawn(Class<? extends Entity> entityType, Coordinates coordinates) {
        renderer.logSpawn(entityType, coordinates);
    }

    @Override
    public void onDeath(Class<? extends Creature> creatureType, Coordinates coordinates) {
        renderer.logDeath(creatureType, coordinates);
    }

    @Override
    public void onMoveEnd(SimulationMap simulationMap) {
        renderer.viewMap(simulationMap);
    }

    @Override
    public void onMoveStart() {
        renderer.clearScreen();
    }
}
