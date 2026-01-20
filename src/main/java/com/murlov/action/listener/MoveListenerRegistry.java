package com.murlov.action.listener;

import com.murlov.model.Creature;

public class MoveListenerRegistry {

    private final MoveEventListener listener;

    public MoveListenerRegistry(MoveEventListener listener) {
        this.listener = listener;
    }

    public void attachListener(Creature creature) {
        creature.setMoveEventListener(listener);
    }
}
