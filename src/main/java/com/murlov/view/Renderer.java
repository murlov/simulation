package com.murlov.view;

import com.murlov.model.Entity;
import com.murlov.model.EntityType;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public class Renderer {

    public void message(String message) {
        System.out.println(message);
    }

    public void Map(Map map) {
        SimulationSettings settings = SimulationSettings.getInstance();
        for (int y = 0; y < settings.getSizeOfMap().length(); y++) {
            for (int x = 0; x < settings.getSizeOfMap().width(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (map.getEntities().containsKey(coordinates)) {
                    Entity entity = map.getEntities().get(coordinates);
                    System.out.print(entity.getIcon());
                } else {
                    System.out.print("\uD83D\uDFEB");
                }
            }
            System.out.println();
        }
    }

    public void suggestContinue() {
        System.out.println("Для выполнения хода нажмите Enter. Для завершения симуляции — введите 0.\n");
    }

    public void inputMessage(String title, int min, int max) {
        System.out.printf("%s (%d-%d): ", title, min, max);
    }

    public void noResourcesMessage() {
        System.out.println("Ресурсы закончились, симуляция завершена");
    }

    public void tryAgainMessage() {
        System.out.println("Ошибка, попробуйте ещё раз.");
    }

    public void inputMessage() {
        System.out.println("Введите данные");
    }

    public void settingsChoiceMessage() {
        System.out.println("Хотите использовать настройки по-умолчанию или хотите задать свои?");
    }

    public void settingsMenuMessage() {
        System.out.println("""
                    1. Использовать настройки по-умолчанию (Размер карты — 10x10; Процент заполнения — 40; Минимальное количество хищников, травоядных и травы — 10)
                    2. Задать свои настройки
                    """);
    }

    public void optionNoFoundMessage() {
        System.out.println("Такой пункт отсутствует");
    }

    public void invalidInputMessage() {
        System.out.println("Некорректный ввод");
    }

    public void entitiesCannotMoveMessage() {
        System.out.println("Непредвиденная ошибка. Существа не могут двигаться.");
    }

    public void newLine() {
        System.out.println();
    }

    public void logMove(EntityType creatureType, Coordinates from, Coordinates to) {
        System.out.println(creatureType + " moved from " + from + " to " + to);
    }

    public void logAttack(EntityType attackerType, EntityType victimType, Coordinates position) {
        System.out.println(attackerType + " attacked " + victimType + " on " + position);
    }

    public void logEat(EntityType creatureType, EntityType victimType, Coordinates position) {
        System.out.println(creatureType + " ate " + victimType + " on " + position);
    }
}
