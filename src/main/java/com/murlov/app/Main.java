package com.murlov.app;

import com.murlov.factory.DefaultSimulationSettingsFactory;
import com.murlov.factory.InputSimulationSettingsFactory;
import com.murlov.settings.SimulationSettings;
import com.murlov.factory.SimulationSettingsFactory;
import com.murlov.simulation.Simulation;
import com.murlov.view.Renderer;

import java.util.Scanner;

public class Main {
    private final static int DEFAULT_SETTINGS = 1;
    private final static int INPUT_SETTINGS = 2;

    public static void main(String[] args) {
        Renderer renderer = new Renderer();
        int settingsMode = getSettingsMode(renderer);
        SimulationSettingsFactory settingsFactory = getSettingsFactory(settingsMode);
        SimulationSettings settings = settingsFactory.get();

        Simulation simulation = new Simulation();
        simulation.start();
    }

    private static int getSettingsMode(Renderer renderer) {
        Scanner scanner = new Scanner(System.in);

        renderer.settingsChoiceMessage();
        while (true) {
            renderer.settingsMenuMessage();
            String input = scanner.nextLine();
            if (isInteger(input)) {
                int value = Integer.parseInt(input);
                if (value == DEFAULT_SETTINGS) {
                    return DEFAULT_SETTINGS;
                } else if (value == INPUT_SETTINGS) {
                    return INPUT_SETTINGS;
                } else {
                    renderer.optionNoFoundMessage();
                }
            } else {
                renderer.invalidInputMessage();
            }
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static SimulationSettingsFactory getSettingsFactory(int settingsMode) {
        return switch (settingsMode) {
            case DEFAULT_SETTINGS -> new DefaultSimulationSettingsFactory();
            case INPUT_SETTINGS -> new InputSimulationSettingsFactory();
            default -> throw new IllegalArgumentException();
        };
    }
}