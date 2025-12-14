package com.murlov.action;

import com.murlov.model.EntityType;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;
import com.murlov.view.Renderer;

public record MoveEventLogger(Renderer renderer) implements MoveEventListener {

    @Override
    public void onMove(EntityType creatureType, Coordinates from, Coordinates to) {
        renderer.logMove(creatureType, from, to);
        renderer.newLine();
    }

    @Override
    public void onAttack(EntityType attackerType, Coordinates from, EntityType victimType, Coordinates to) {
        renderer.logAttack(attackerType, from, victimType, to);
        renderer.newLine();
    }

    @Override
    public void onEat(EntityType creatureType, Coordinates from, EntityType victimType, Coordinates to) {
        renderer.logEat(creatureType, from, victimType, to);
        renderer.newLine();
    }

    @Override
    public void onDeath(EntityType creatureType, Coordinates coordinates) {
        renderer.logDeath(creatureType, coordinates);
        renderer.newLine();
    }

    @Override
    public void onMoveEnd(Map map) {
        renderer.Map(map);
    }

    @Override
    public void onMoveStart() {
        renderer.clearScreen();
    }
}
