package com.murlov.simulation;

import com.murlov.action.InitEntitiesAction;
import com.murlov.settings.SimulationSettings;
import com.murlov.view.Renderer;

public class Simulation {

    private final Map map;
    private final Renderer renderer;
    private final SimulationSettings settings;

    public Simulation() {
        settings = SimulationSettings.getInstance();
        map = new Map();
        renderer = new Renderer();
    }

    public void startSimulation() {
        nextTurn();
    }

    private void nextTurn() {
        InitEntitiesAction.execute(map);
        renderer.viewMap(map);
    }

    public void stopSimulation() {
    }
}
