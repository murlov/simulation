package com.murlov.view;

import com.murlov.model.Entity;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;

public class Renderer {
    public void viewMap (java.util.Map<Coordinates, Entity> entities) {
        SimulationSettings settings = SimulationSettings.getInstance();
        for (int y = 0; y < settings.getSizeOfMap().getLength(); y++) {
            for (int x = 0; x < settings.getSizeOfMap().getWidth(); x++) {
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
