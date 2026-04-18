package com.murlov.factory.settingsfactory;

import com.murlov.util.PropertiesProvider;

import java.util.Scanner;

public class SimulationSettingsFactoryProvider {

    private static final int DEFAULT_SETTINGS = 1;
    private static final int INPUT_SETTINGS = 2;
    private static final PropertiesProvider propertiesProvider = new PropertiesProvider();

    public SimulationSettingsFactory getFactory() {
        int settingsMode = getSettingsMode();
        return getSettingsFactory(settingsMode);
    }

    private static int getSettingsMode() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Как поступить с настройками?");
        while (true) {
            System.out.printf("""
                    1. Использовать настройки по-умолчанию:
                        - Размер карты — %sx%s
                        - Процент заполнения — %s (%s%% карты заполнится объектами)
                        - Минимальное количество сущностей (Произойдет респаун, если указаных сущностей будет меньше заданного значения):
                            - Хищников - %s
                            - Травоядных - %s
                            - Травы - %s
                        - Травоядные:
                            - Здоровье — %s
                            - Скорость передвижения  — %s
                            - Сытость — %s
                        - Хищники:
                            - Здоровье — %s
                            - Скорость передвижения  — %s
                            - Сытость — %s
                            - Сила атаки — %s
                            
                    2. Задать свои настройки
                    """,
                    propertiesProvider.getString("map.width"),
                    propertiesProvider.getString("map.height"),
                    propertiesProvider.getString("fillPercentage"),
                    propertiesProvider.getString("fillPercent"),
                    propertiesProvider.getString("wolfsNumber"),
                    propertiesProvider.getString("rabbitsNumber"),
                    propertiesProvider.getString("grassNumber"),
                    propertiesProvider.getString("herbivore.health"),
                    propertiesProvider.getString("herbivore.speed"),
                    propertiesProvider.getString("herbivore.satiety"),
                    propertiesProvider.getString("predator.health"),
                    propertiesProvider.getString("predator.speed"),
                    propertiesProvider.getString("predator.satiety"),
                    propertiesProvider.getString("predator.damage")
                    );

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

    public static void main(String[] args) {
        System.out.printf("""
                There are
                %d apples
                """,
                5);
    }
}
