package com.murlov.action;

import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public class EntitiesMoveAction {
    public static boolean execute(Map map) {
        Coordinates newCoordinates = new Coordinates();
        Coordinates oldCoordinates = new Coordinates();
        Entity entity = null;
        for (java.util.Map.Entry<Coordinates, Entity> entry : map.getEntities().entrySet()) {
            if (entry.getValue() instanceof Creature) {
                oldCoordinates = entry.getKey();
                newCoordinates = PathFindAction.execute(map, entry.getValue());
                entity = entry.getValue();
                break;
            }
        }
        if (entity != null && newCoordinates != null) {
            map.setEntity(newCoordinates, entity);
            map.getEntities().remove(oldCoordinates);
            return true;
        }
        return false;
    }
}
