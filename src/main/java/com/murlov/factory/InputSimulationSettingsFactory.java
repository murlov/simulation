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
        int percent = input("Процент заполнения (к примеру, при вводе 25 — 25% карты заполнится объектами):",
                failMessage, 5, 100);
        settings.setFillPercentage(percent);

        double minArea = settings.getNumberOfEntityTypes()/(percent / 100.0);
        int minSide = (int) Math.ceil(Math.sqrt(minArea));
        int x = input("Ширина карты:", failMessage, minSide, 50);
        int y = input("Длина карты:", failMessage, minSide, 50);
        settings.setSizeOfMap(x, y);

        Map<Class<? extends Entity>, Integer> minNumbersForEntityTypes = new HashMap<>(settings.getNumberOfEntityTypes());
        List<Class<? extends Entity>> entityTypes = List.of(Wolf.class, Rabbit.class, Grass.class);

        System.out.println("Минимальное количество для каждой группы (если количество существ в группе будет ниже указанного на момент окончания " +
                "хода всех существ — произойдёт респаун):");
        for (Class<? extends Entity> type: entityTypes) {
            int value = input(type.getSimpleName(), failMessage, 1, settings.getNumberOfEntitiesPerEntityType());
            minNumbersForEntityTypes.put(type, value);
        }
        settings.setMinNumbersForEntityTypes(minNumbersForEntityTypes);

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
