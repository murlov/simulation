package com.murlov.view;

import com.murlov.action.listener.DeathEvent;
import com.murlov.action.listener.EatEvent;
import com.murlov.entity.*;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public class ConsoleRenderer implements Renderer {

    @Override
    public void printMap(SimulationMap simulationMap) {
        for (int y = 0; y < simulationMap.getSize().height(); y++) {
            for (int x = 0; x < simulationMap.getSize().width(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (simulationMap.getEntities().containsKey(coordinates)) {
                    Entity entity = simulationMap.getEntity(coordinates);
                    System.out.print(getIcon(entity.getClass()));
                } else {
                    System.out.print("\uD83D\uDFE9");
                }
            }
            System.out.println();
        }

        System.out.println("\n\n");
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

    @Override
    public void printEat(EatEvent event) {
        System.out.println(event.eater().getClass().getSimpleName() + " " + event.from() + " has eaten " + event.target().getClass().getSimpleName() + " " + event.to() + "\n");
    }


    @Override
    public void printDeath(DeathEvent event) {
        System.out.println(event.creature().getClass().getSimpleName() + " " + event.coordinates() + " has died" + "\n");
    }

    @Override
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
