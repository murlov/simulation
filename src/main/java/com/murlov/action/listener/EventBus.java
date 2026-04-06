package com.murlov.action.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

    public <T extends Event> void subscribe(Class<T> eventType, EventListener<T> eventListener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(eventListener);
    }

    public <T extends Event> void publish(T event) {
        List<EventListener<?>> requiredListeners = listeners.get(event.getClass());
        if (requiredListeners == null) {
            throw new RuntimeException("No listeners for publish event");
        }

        for (EventListener<?> listener : requiredListeners) {
            ((EventListener <T>) listener).onEvent(event);
        }
    }
}
