package com.murlov.action;


import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public interface MoveEventListener {
    void onMove(Class<? extends Creature> creatureType, Coordinates from, Coordinates to);
    void onAttack(Class<? extends Creature> attackerType, Coordinates from, Class<? extends Creature> victimType, Coordinates to);
    void onEat(Class<? extends Creature> creatureType, Coordinates from, Class<? extends Entity> victimType, Coordinates to);
    void onSpawn(Class<? extends Entity> entityType, Coordinates coordinates);
    void onDeath(Class<? extends Creature> creatureType, Coordinates coordinates);
    void onMoveEnd(SimulationMap simulationMap);
    void onMoveStart();
}
