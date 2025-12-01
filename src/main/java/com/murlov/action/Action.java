package com.murlov.action;

import com.murlov.simulation.Map;

public interface Action {

    boolean execute(Map map);

    default boolean execute(Map map, MoveListenerRegistry listenerRegistry) {
        return execute(map);
    }
}
