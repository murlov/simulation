package com.murlov.view;

import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public interface Renderer {

    void viewMap(SimulationMap map);

    void logMove(Class<? extends Creature> creatureType, Coordinates from, Coordinates to);

    void logAttack(Class<? extends Creature> attackerType, Coordinates from, Class<? extends Creature> victimType, Coordinates to);

    void logEat(Class<? extends Creature> creatureType, Coordinates from, Class<? extends Entity> victimType, Coordinates to);

    void logSpawn(Class<? extends Entity> entityType, Coordinates coordinates);

    void logDeath(Class<? extends Creature> creatureType, Coordinates coordinates);

    void clearScreen();
}
