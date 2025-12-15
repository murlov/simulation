package com.murlov.action;

import com.murlov.model.Creature;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public interface PathFinder {
    Coordinates execute(SimulationMap simulationMap, Creature creature);
}
