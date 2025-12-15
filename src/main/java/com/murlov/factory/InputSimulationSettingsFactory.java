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
        String failMessage = "Некорректный ввод";

        renderer.inputMessage();
        int percent = input(renderer, "Процент заполнения (к примеру, при вводе 25 — 25% карты заполнится объектами):", failMessage, 5, 100);
        settings.setFillPercentage(percent);

        double minArea = settings.getNumberOfGroups()/(percent / 100.0);
        int minSide = (int) Math.ceil(Math.sqrt(minArea));
        int x = input(renderer, "Ширина карты:", failMessage, minSide, 50);
        int y = input(renderer, "Длина карты:", failMessage, minSide, 50);
        settings.setSizeOfMap(x, y);

        java.util.Map<EntityGroup, Integer> minNumbersInGroups = new java.util.HashMap<>(settings.getNumberOfGroups());
        renderer.inputMinNumbersInGroups();
        for (EntityGroup group: EntityGroup.values()) {
            if (!group.equals(EntityGroup.STATIC)) {
                int value = input(renderer, String.valueOf(group), failMessage, 1, settings.getNumberOfEntitiesPerGroup());
                minNumbersInGroups.put(group, value);
            }
        }
        settings.setMinNumbersInGroups(minNumbersInGroups);

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
            renderer.newLine();
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
