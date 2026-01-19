package com.murlov.factory;

import com.murlov.model.Entity;
import com.murlov.model.Grass;
import com.murlov.model.Rabbit;
import com.murlov.model.Wolf;
import com.murlov.settings.SimulationSettings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputSimulationSettingsFactory implements SimulationSettingsFactory {

    @Override
    public SimulationSettings get() {
        SimulationSettings settings = SimulationSettings.getInstance();
        String failMessage = "Некорректный ввод";

        System.out.println("Введите данные");
        int percent = input("Процент заполнения (При значении 25 — 25% карты заполнится объектами)",
                failMessage, 5, 100);
        settings.setFillPercentage(percent);

        double minArea = settings.getNumberOfEntityTypes()/(percent / 100.0);
        int minSide = (int) Math.ceil(Math.sqrt(minArea));
        int x = input("Ширина карты", failMessage, minSide, 50);
        int y = input("Длина карты", failMessage, minSide, 50);
        settings.setSizeOfMap(x, y);

        Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes = new HashMap<>(settings.getNumberOfEntityTypes());
        List<Class<? extends Entity>> entityTypes = List.of(Wolf.class, Rabbit.class, Grass.class);

        System.out.println("Минимальное количество для каждой группы (Произойдет респаун, если указаных сущностей будет меньше заданного значения):");
        for (Class<? extends Entity> type: entityTypes) {
            int value = input(type.getSimpleName(), failMessage, 1, settings.getNumberOfEntitiesPerEntityType());
            minNumbersForEntityTypes.put(type, value);
        }
        settings.setMinNumbersForEntityTypes(minNumbersForEntityTypes);

        System.out.println("Показатели для травоядных:");
        int health = input("Здоровье:", failMessage, 1, 10);
        settings.setHerbivoreHealth(health);
        int speed = input("Скорость передвижения", failMessage, 1, 5);
        settings.setHerbivoreSpeed(speed);
        int satiety = input("Сытость", failMessage, 1, 10);
        settings.setHerbivoreSatiety(satiety);

        System.out.println("Показатели для хищников:");
        health = input("Здоровье:", failMessage, 1, 10);
        settings.setPredatorHealth(health);
        speed = input("Скорость передвижения", failMessage, 1, 5);
        settings.setPredatorSpeed(speed);
        satiety = input("Сытость", failMessage, 1, 10);
        settings.setPredatorSatiety(satiety);
        int damage = input("Сила атаки", failMessage, 1, 10);
        settings.setPredatorDamage(damage);

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
            System.out.println(failMessage + "\n");
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
