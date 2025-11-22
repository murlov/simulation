package com.murlov.factory;

import com.murlov.model.EntityGroup;
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
        settings.setFillPercentage(input(renderer, "Процент заполнения (к примеру, при вводе 25 — 25% карты заполнится объектами):", failMessage, 5, 100));
        int x = input(renderer, "Ширина карты:", failMessage, 6, 15);
        int y = input(renderer, "Длина карты:", failMessage, 6, 15);
        settings.setSizeOfMap(x, y);
        int[] counts = inputArray(renderer, "Минимальное количество хищников, травоядных и травы. Три числа, через пробел (если количество опустится ниже минимального, " +
                "будет происходить респаун соответствующей группы):", failMessage, 1, settings.getPerGroup(), 3);
        settings.setMinCountInGroup(EntityGroup.PREDATOR, counts[0]);
        settings.setMinCountInGroup(EntityGroup.HERBIVORE, counts[1]);
        settings.setMinCountInGroup(EntityGroup.GRASS, counts[2]);
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

    private int[] inputArray(Renderer renderer, String title, String failMessage, int min, int max, int count) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            renderer.inputMessage(title, min, max);
            String input = scanner.nextLine();

            String[] parts = input.split("\\s+");
            if (parts.length != count) {
                renderer.message(failMessage);
                continue;
            }

            int[] array = new int[parts.length];
            boolean arrayIsCorrect = true;

            for (int i = 0; i < parts.length; i++) {
                if (isInteger(parts[i])) {
                    int value = Integer.parseInt(parts[i]);
                    if (value >= min && value <= max) {
                        array[i] = value;
                    } else {
                        arrayIsCorrect = false;
                        break;
                    }
                } else {
                    arrayIsCorrect = false;
                    break;
                }
            }
            if (arrayIsCorrect) {
                return array;
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
