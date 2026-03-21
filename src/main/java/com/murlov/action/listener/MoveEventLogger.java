package com.murlov.action.listener;

import com.murlov.entity.Creature;
import com.murlov.entity.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;
import com.murlov.view.Renderer;

public record MoveEventLogger(Renderer renderer) implements MoveEventListener {

    @Override
    public void onMove(Class<? extends Creature> creatureType, Coordinates from, Coordinates to) {
        renderer.printMove(creatureType, from, to);
    }

    @Override
    public void onAttack(Class<? extends Creature> attackerType, Coordinates from, Class<? extends Creature> victimType, Coordinates to) {
        renderer.printAttack(attackerType, from, victimType, to);
    }

    @Override
    public void onEat(Class<? extends Creature> creatureType, Coordinates from, Class<? extends Entity> victimType, Coordinates to) {
        renderer.printEat(creatureType, from, victimType, to);
    }

    @Override
    public void onSpawn(Class<? extends Entity> entityType, Coordinates coordinates) {
        renderer.printSpawn(entityType, coordinates);
    }

    @Override
    public void onDeath(Class<? extends Creature> creatureType, Coordinates coordinates) {
        renderer.printDeath(creatureType, coordinates);
    }

    @Override
    public void onMoveEnd(SimulationMap simulationMap) {
        renderer.printMap(simulationMap);
    }

    @Override
    public void onMoveStart() {
        renderer.clearScreen();
    }
}
