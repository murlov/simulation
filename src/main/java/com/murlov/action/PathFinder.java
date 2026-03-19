package com.murlov.action;

import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

import java.util.List;

public interface PathFinder {
    List<Coordinates> execute(SimulationMap simulationMap, Coordinates start, Class<? extends Entity> target);
}
