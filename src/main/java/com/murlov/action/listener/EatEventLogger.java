package com.murlov.action.listener;

import com.murlov.view.Renderer;

public record EatEventLogger(Renderer renderer) implements EventListener<EatEvent> {

    @Override
    public void onEvent(EatEvent event) {
        renderer.printEat(event);
    }
}
