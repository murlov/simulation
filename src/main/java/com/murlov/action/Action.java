package com.murlov.action;

import com.murlov.simulation.Map;

public interface Action {

    boolean execute(Map map);

    default boolean execute(Map map, Runnable onMoveCallback) {
        boolean result = execute(map);
        if (result && onMoveCallback != null) {
            onMoveCallback.run();
        }
        return result;
    };
}
