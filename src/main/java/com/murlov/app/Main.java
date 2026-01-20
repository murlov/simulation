package com.murlov.app;

import com.murlov.settings.factory.SimulationSettingsFactoryProvider;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Simulation;
import com.murlov.view.ConsoleRenderer;
import com.murlov.view.Renderer;

public class Main {
    public static void main(String[] args) {
        Renderer renderer = new ConsoleRenderer();
        SimulationSettingsFactoryProvider simulationSettingsFactoryProvider = new SimulationSettingsFactoryProvider();
        SimulationSettings settings = simulationSettingsFactoryProvider.getFactory().get();

        Simulation simulation = new Simulation(settings, renderer);
        simulation.start();
    }
}