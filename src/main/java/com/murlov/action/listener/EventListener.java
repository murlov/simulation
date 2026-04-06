package com.murlov.action.listener;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
