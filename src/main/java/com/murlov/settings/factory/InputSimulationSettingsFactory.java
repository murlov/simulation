package com.murlov.settings.factory;

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

    private static final int MIN_VALUE_FOR_FILL_PERCENTAGE = 5;
    private static final int MAX_VALUE_FOR_FILL_PERCENTAGE = 100;
    private static final int MAX_VALUE_FOR_MAP_WIDTH = 50;
    private static final int MAX_VALUE_FOR_MAP_HEIGHT = 50;
    private static final int MIN_VALUE_FOR_MIN_NUMBERS_FOR_ENTITY_TYPES = 1;
    private static final int MIN_VALUE_FOR_HERBIVORE_HEALTH = 1;
    private static final int MAX_VALUE_FOR_HERBIVORE_HEALTH = 10;
    private static final int MIN_VALUE_FOR_HERBIVORE_SPEED = 1;
    private static final int MAX_VALUE_FOR_HERBIVORE_SPEED = 5;
    private static final int MIN_VALUE_FOR_HERBIVORE_SATIETY = 1;
    private static final int MAX_VALUE_FOR_HERBIVORE_SATIETY = 10;
    private static final int MIN_VALUE_FOR_PREDATOR_HEALTH = 1;
    private static final int MAX_VALUE_FOR_PREDATOR_HEALTH = 10;
    private static final int MIN_VALUE_FOR_PREDATOR_SPEED = 1;
    private static final int MAX_VALUE_FOR_PREDATOR_SPEED = 5;
    private static final int MIN_VALUE_FOR_PREDATOR_SATIETY = 1;
    private static final int MAX_VALUE_FOR_PREDATOR_SATIETY = 10;
    private static final int MIN_VALUE_FOR_PREDATOR_DAMAGE = 1;
    private static final int MAX_VALUE_FOR_PREDATOR_DAMAGE = 10;


    @Override
    public SimulationSettings get() {
        SimulationSettings settings = SimulationSettings.getInstance();
        String failMessage = "Некорректный ввод";

        System.out.println("Введите данные");
        int percent = input("Процент заполнения (При значении 25 — 25% карты заполнится объектами)",
                failMessage, MIN_VALUE_FOR_FILL_PERCENTAGE, MAX_VALUE_FOR_FILL_PERCENTAGE);
        settings.setFillPercentage(percent);

        double minArea = settings.getNumberOfEntityTypes()/(percent / 100.0);
        int minSide = (int) Math.ceil(Math.sqrt(minArea));
        int x = input("Ширина карты", failMessage, minSide, MAX_VALUE_FOR_MAP_WIDTH);
        int y = input("Длина карты", failMessage, minSide, MAX_VALUE_FOR_MAP_HEIGHT);
        settings.setSizeOfMap(x, y);

        Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes = new HashMap<>(settings.getNumberOfEntityTypes());
        List<Class<? extends Entity>> entityTypes = List.of(Wolf.class, Rabbit.class, Grass.class);

        System.out.println("Минимальное количество для каждой группы (Произойдет респаун, если указаных сущностей будет меньше заданного значения):");
        for (Class<? extends Entity> type: entityTypes) {
            int value = input(type.getSimpleName(), failMessage, MIN_VALUE_FOR_MIN_NUMBERS_FOR_ENTITY_TYPES, settings.getNumberOfEntitiesPerEntityType());
            minNumbersForEntityTypes.put(type, value);
        }
        settings.setMinNumbersForEntityTypes(minNumbersForEntityTypes);

        System.out.println("Показатели для травоядных:");
        int health = input("Здоровье:", failMessage, MIN_VALUE_FOR_HERBIVORE_HEALTH, MAX_VALUE_FOR_HERBIVORE_HEALTH);
        settings.setHerbivoreHealth(health);
        int speed = input("Скорость передвижения", failMessage, MIN_VALUE_FOR_HERBIVORE_SPEED, MAX_VALUE_FOR_HERBIVORE_SPEED);
        settings.setHerbivoreSpeed(speed);
        int satiety = input("Сытость", failMessage, MIN_VALUE_FOR_HERBIVORE_SATIETY, MAX_VALUE_FOR_HERBIVORE_SATIETY);
        settings.setHerbivoreSatiety(satiety);

        System.out.println("Показатели для хищников:");
        health = input("Здоровье:", failMessage, MIN_VALUE_FOR_PREDATOR_HEALTH, MAX_VALUE_FOR_PREDATOR_HEALTH);
        settings.setPredatorHealth(health);
        speed = input("Скорость передвижения", failMessage, MIN_VALUE_FOR_PREDATOR_SPEED, MAX_VALUE_FOR_PREDATOR_SPEED);
        settings.setPredatorSpeed(speed);
        satiety = input("Сытость", failMessage, MIN_VALUE_FOR_PREDATOR_SATIETY, MAX_VALUE_FOR_PREDATOR_SATIETY);
        settings.setPredatorSatiety(satiety);
        int damage = input("Сила атаки", failMessage, MIN_VALUE_FOR_PREDATOR_DAMAGE, MAX_VALUE_FOR_PREDATOR_DAMAGE);
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
