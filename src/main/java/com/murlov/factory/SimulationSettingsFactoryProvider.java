package com.murlov.factory;

import java.util.Scanner;

public class SimulationSettingsFactoryProvider {

    private final static int DEFAULT_SETTINGS = 1;
    private final static int INPUT_SETTINGS = 2;

    public SimulationSettingsFactory getFactory() {
        int settingsMode = getSettingsMode();
        return getSettingsFactory(settingsMode);
    }

    private static int getSettingsMode() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Хотите использовать настройки по-умолчанию или хотите задать свои?");
        while (true) {
            System.out.println("""
                    1. Использовать настройки по-умолчанию:
                        - Размер карты — 10x10
                        - Процент заполнения — 40 (40% карты заполнится объектами)
                        - Минимальное количество хищников, травоядных и травы — 8 (Произойдет респаун, если указаных сущностей будет меньше заданного значения)
                        - Травоядные:
                            - Здоровье — 10
                            - Скорость передвижения  — 1
                            - Сытость — 10
                        - Хищники:
                            - Здоровье — 10
                            - Скорость передвижения  — 1
                            - Сытость — 10
                            - Сила атаки — 1
                            
                    2. Задать свои настройки
                    """);

            String input = scanner.nextLine();
            if (isInteger(input)) {
                int value = Integer.parseInt(input);
                if (value == DEFAULT_SETTINGS) {
                    return DEFAULT_SETTINGS;
                } else if (value == INPUT_SETTINGS) {
                    return INPUT_SETTINGS;
                } else {
                    System.out.println("Такой пункт отсутствует");
                }
            } else {
                System.out.println("Некорректный ввод\n\n");
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
