package com.murlov.action;

import com.murlov.simulation.Map;

public interface Action {

    void execute(Map map);

    default void execute(Map map, MoveListenerRegistry listenerRegistry) {
    }
}
