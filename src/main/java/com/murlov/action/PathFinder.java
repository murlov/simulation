package com.murlov.action;

import com.murlov.model.Creature;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public interface PathFinder {
    Coordinates execute(Map map, Creature creature);
}
