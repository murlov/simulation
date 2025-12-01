package com.murlov.action;

import com.murlov.model.Creature;

public record MoveListenerRegistry(MoveEventListener listener) {

    public void attachListener(Creature creature) {
        creature.setMoveEventListener(listener);
    }
}
