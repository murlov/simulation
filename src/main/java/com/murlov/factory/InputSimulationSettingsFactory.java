package com.murlov.factory;

import com.murlov.settings.SimulationSettings;
import com.murlov.view.Renderer;

import java.util.Scanner;

public class InputSimulationSettingsFactory implements SimulationSettingsFactory {

    @Override
    public SimulationSettings get() {
        Renderer renderer = new Renderer();
        SimulationSettings settings = SimulationSettings.getInstance();
        renderer.inputMessage();
        String failMessage = "Некорректный ввод";
        settings.setFillPercentage(input(renderer, "Процент заполнения. При вводе 25 — 25% карты заполнится объектами.", failMessage, 5, 100));
        int x = input(renderer, "Ширина карты", failMessage, 6, 15);
        int y = input(renderer, "Длина карты", failMessage, 6, 15);
        settings.setSizeOfMap(x, y);
        return settings;
    }

    private int input(Renderer renderer, String title, String failMessage, int min, int max) {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            renderer.inputMessage(title, min, max);
            String input = scanner.nextLine();
            if (isInteger(input)) {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            }
            renderer.message(failMessage);
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
