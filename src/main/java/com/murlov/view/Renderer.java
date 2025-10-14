package com.murlov.view;

import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationSettings;

public class Renderer {
    public void viewMap (java.util.Map<Coordinates, Entity> entities) {
        for (int y = 0; y < SimulationSettings.getSizeOfMap().getLength(); y++) {
            for (int x = 0; x < SimulationSettings.getSizeOfMap().getWidth(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (entities.containsKey(coordinates)) {
                    Entity entity = entities.get(coordinates);
                    System.out.print(entity.getIcon());
                } else {
                    System.out.print("..");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
