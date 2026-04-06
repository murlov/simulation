package com.murlov.action.listener;

import com.murlov.view.Renderer;

public record DeathEventLogger(Renderer renderer) implements EventListener<DeathEvent>{

    @Override
    public void onEvent(DeathEvent event) {
        renderer.printDeath(event);
    }
}
