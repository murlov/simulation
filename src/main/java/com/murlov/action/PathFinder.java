package com.murlov.action;

import com.murlov.model.Creature;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

import java.util.List;

public interface PathFinder {
    List<Coordinates> execute(SimulationMap simulationMap, Creature creature);
}
