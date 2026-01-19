package com.murlov.view;

import com.murlov.model.*;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public class ConsoleRenderer implements Renderer {

    @Override
    public void viewMap(SimulationMap simulationMap) {
        for (int y = 0; y < simulationMap.getSize().length(); y++) {
            for (int x = 0; x < simulationMap.getSize().width(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (simulationMap.getEntities().containsKey(coordinates)) {
                    Entity entity = simulationMap.getEntities().get(coordinates);
                    System.out.print(getIcon(entity.getClass()));
                } else {
                    System.out.print("\uD83D\uDFE9");
                }
            }
            System.out.println();
        }

        System.out.println("\n\n");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String getIcon(Class<? extends Entity> entityType) {
        return switch (entityType.getSimpleName()) {
            case "Grass" -> "\uD83C\uDF31";
            case "Rabbit" -> "\uD83D\uDC07";
            case "Rock" -> "\uD83E\uDEA8";
            case "Tree" -> "\uD83C\uDF33";
            case "Wolf" -> "\uD83D\uDC3A";
            default -> throw new RuntimeException("Invalid EntityType");
        };
    }

    public void logMove(Class<? extends Creature> creatureType, Coordinates from, Coordinates to) {
        System.out.println(creatureType.getSimpleName() + " has moved from " + from + " to " + to + "\n");
    }

    public void logAttack(Class<? extends Creature> attackerType, Coordinates from, Class<? extends Creature> victimType, Coordinates to) {
        System.out.println(attackerType.getSimpleName() + " " + from + " has attacked " + victimType.getSimpleName() + " on " + to + "\n");
    }

    public void logEat(Class<? extends Creature> creatureType, Coordinates from, Class<? extends Entity> victimType, Coordinates to) {
        System.out.println(creatureType.getSimpleName() + " " + from + " has eaten " + victimType.getSimpleName() + " on " + to + "\n");
    }

    @Override
    public void logSpawn(Class<? extends Entity> entityType, Coordinates coordinates) {
        System.out.println(entityType.getSimpleName() + " has spawned on " + coordinates + "\n");
    }

    public void logDeath(Class<? extends Creature> creatureType, Coordinates coordinates) {
        System.out.println(creatureType.getSimpleName() + " has died on " + coordinates + "\n");
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
