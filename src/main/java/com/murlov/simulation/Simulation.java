package com.murlov.simulation;

import com.murlov.view.Renderer;

public class Simulation {
    SimulationSettings settings = SimulationSettings.getInstance();
    Map map = new Map();

    public void startSimulation() {
        map.setRandomEntitiesPositions();
        Renderer renderer = new Renderer();
        renderer.viewMap(map.entities);
    }
}
