package com.murlov.factory;

import com.murlov.settings.SimulationSettings;

import java.util.Scanner;

public class InputSimulationSettingsFactory implements SimulationSettingsFactory {

    @Override
    public SimulationSettings get() {
        SimulationSettings settings = SimulationSettings.getInstance();

        System.out.print("Введите данные");
        String failMessage = "Некорректный ввод";
        settings.setDensity(input("Плотность заполнения. Например, 5 = каждая группа займёт 1/5 карты", failMessage, 5, 100));
        int x = input("Ширина карты", failMessage, 6, 15);
        int y = input("Длина карты", failMessage, 6, 15);
        settings.setSizeOfMap(x, y);
        return settings;
    }

    private int input(String title, String failMessage, int min, int max) {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.printf("%s (%d-%d): ", title, min, max);
            String input = scanner.nextLine();
            if (isInteger(input)) {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            }
            System.out.println(failMessage);
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
