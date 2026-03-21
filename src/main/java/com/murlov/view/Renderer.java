package com.murlov.view;

import com.murlov.entity.Creature;
import com.murlov.entity.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public interface Renderer {

    void printMap(SimulationMap map);

    void printMove(Class<? extends Creature> creatureType, Coordinates from, Coordinates to);

    void printAttack(Class<? extends Creature> attackerType, Coordinates from, Class<? extends Creature> victimType, Coordinates to);

    void printEat(Class<? extends Creature> creatureType, Coordinates from, Class<? extends Entity> victimType, Coordinates to);

    void printSpawn(Class<? extends Entity> entityType, Coordinates coordinates);

    void printDeath(Class<? extends Creature> creatureType, Coordinates coordinates);

    void clearScreen();
}
