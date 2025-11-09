package com.murlov.view;

import com.murlov.model.Entity;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public class Renderer {
    public static void viewMap(Map map) {
        SimulationSettings settings = SimulationSettings.getInstance();
        for (int y = 0; y < settings.getSizeOfMap().getLength(); y++) {
            for (int x = 0; x < settings.getSizeOfMap().getWidth(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (map.getEntities().containsKey(coordinates)) {
                    Entity entity = map.getEntities().get(coordinates);
                    System.out.print(entity.getIcon());
                } else {
                    System.out.print("..");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void suggestContinue() {
        System.out.println("Для выполнения хода нажмите Enter. Для завершения симуляции — введите 0.\n");
    }

    public static void inputMessage(String title, int min, int max) {
        System.out.printf("%s (%d-%d): ", title, min, max);
    }

    public static void noResourcesMessage() {
        System.out.println("Ресурсы закончились, симуляция завершена");
    }

    public static void tryAgainMessage() {
        System.out.println("Ошибка, попробуйте ещё раз.");
    }

    public static void inputMessage() {
        System.out.println("Введите данные");
    }

    public static void settingsChoiceMessage() {
        System.out.println("Хотите использовать настройки по-умолчанию или хотите задать свои?");
    }

    public static void settingsMenuMessage() {
        System.out.println("""
                    1. Использовать настройки по-умолчанию (Размер карты — 10x10; Процент заполнения — 10)
                    2. Задать свои настройки
                    """);
    }

    public static void optionNoFoundMessage() {
        System.out.println("Такой пункт отсутствует");
    }

    public static void invalidInputMessage() {
        System.out.println("Некорректный ввод");
    }

    public static void message(String message) {
        System.out.println(message);
    }
}
