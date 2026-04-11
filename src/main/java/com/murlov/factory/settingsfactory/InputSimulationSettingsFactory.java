package com.murlov.factory.settingsfactory;

import com.murlov.entity.Entity;
import com.murlov.entity.Grass;
import com.murlov.entity.Rabbit;
import com.murlov.entity.Wolf;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Size;

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
        SimulationSettings.Parameters parameters = new SimulationSettings.Parameters();
        String failMessage = "Некорректный ввод";

        System.out.println("Введите данные");
        double fillPercentage = (double) input("Процент заполнения (При значении 25 — 25% карты заполнится объектами)",
                failMessage, MIN_VALUE_FOR_FILL_PERCENTAGE, MAX_VALUE_FOR_FILL_PERCENTAGE) / 100.0;

        double minArea = SimulationSettings.Parameters.NUMBER_OF_ENTITY_TYPES/(fillPercentage);
        int minSide = (int) Math.ceil(Math.sqrt(minArea));
        int x = input("Ширина карты", failMessage, minSide, MAX_VALUE_FOR_MAP_WIDTH);
        int y = input("Длина карты", failMessage, minSide, MAX_VALUE_FOR_MAP_HEIGHT);
        Size mapSize = new Size(x, y);
        parameters.mapSize = mapSize;

        parameters.numberOfEntitiesPerEntityType = (int) (mapSize.getArea()*fillPercentage) /
                SimulationSettings.Parameters.NUMBER_OF_ENTITY_TYPES;
        parameters.numberOfRemainingEntities = (int) (mapSize.getArea()*fillPercentage) -
                parameters.numberOfEntitiesPerEntityType * SimulationSettings.Parameters.NUMBER_OF_ENTITY_TYPES;

        Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes =
                new HashMap<>(SimulationSettings.Parameters.NUMBER_OF_ENTITY_TYPES);
        List<Class<? extends Entity>> entityTypes = List.of(Wolf.class, Rabbit.class, Grass.class);

        System.out.println("Минимальное количество для каждой группы (Произойдет респаун, если указаных сущностей" +
                " будет меньше заданного значения):");
        for (Class<? extends Entity> type: entityTypes) {
            int value = input(type.getSimpleName(), failMessage, MIN_VALUE_FOR_MIN_NUMBERS_FOR_ENTITY_TYPES,
                    parameters.numberOfEntitiesPerEntityType);
            minNumbersForEntityTypes.put(type, value);
        }
        parameters.minNumbersForEntityTypes = minNumbersForEntityTypes;

        System.out.println("Показатели для травоядных:");
        int health = input("Здоровье:", failMessage, MIN_VALUE_FOR_HERBIVORE_HEALTH, MAX_VALUE_FOR_HERBIVORE_HEALTH);
        parameters.herbivoreHealth = health;
        int speed = input("Скорость передвижения", failMessage, MIN_VALUE_FOR_HERBIVORE_SPEED, MAX_VALUE_FOR_HERBIVORE_SPEED);
        parameters.herbivoreSpeed = speed;
        int satiety = input("Сытость", failMessage, MIN_VALUE_FOR_HERBIVORE_SATIETY, MAX_VALUE_FOR_HERBIVORE_SATIETY);
        parameters.herbivoreSatiety = satiety;

        System.out.println("Показатели для хищников:");
        health = input("Здоровье:", failMessage, MIN_VALUE_FOR_PREDATOR_HEALTH, MAX_VALUE_FOR_PREDATOR_HEALTH);
        parameters.predatorHealth = health;
        speed = input("Скорость передвижения", failMessage, MIN_VALUE_FOR_PREDATOR_SPEED, MAX_VALUE_FOR_PREDATOR_SPEED);
        parameters.predatorSpeed = speed;
        satiety = input("Сытость", failMessage, MIN_VALUE_FOR_PREDATOR_SATIETY, MAX_VALUE_FOR_PREDATOR_SATIETY);
        parameters.predatorSatiety = satiety;
        int damage = input("Сила атаки", failMessage, MIN_VALUE_FOR_PREDATOR_DAMAGE, MAX_VALUE_FOR_PREDATOR_DAMAGE);
        parameters.predatorDamage = damage;

        return new SimulationSettings(parameters);
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
