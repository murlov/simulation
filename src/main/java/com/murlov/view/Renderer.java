package com.murlov.view;

import com.murlov.action.listener.DeathEvent;
import com.murlov.action.listener.EatEvent;
import com.murlov.simulation.SimulationMap;

public interface Renderer {

    void printMap(SimulationMap map);

    void printEat(EatEvent eatEvent);

    void printDeath(DeathEvent event);

    void clearScreen();
}
