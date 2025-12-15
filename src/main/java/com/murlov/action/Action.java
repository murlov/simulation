package com.murlov.action;

import com.murlov.simulation.SimulationMap;

public interface Action {

    void execute(SimulationMap simulationMap);

    default void execute(SimulationMap simulationMap, MoveListenerRegistry listenerRegistry) {
    }
}
