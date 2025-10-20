package com.murlov.view;

import com.murlov.model.Entity;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public class Renderer {
    public void viewMap (Map map) {
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
}
