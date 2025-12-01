package com.murlov.action;

import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;
import com.murlov.view.Renderer;

import java.util.ArrayList;
import java.util.List;

public class EntitiesMoveAction implements Action {

    @Override
    public boolean execute(Map map) {
        boolean hasMoved = false;
        List<Coordinates> keys = new ArrayList<>(map.getEntities().keySet());

        for (Coordinates coordinates : keys) {
            Entity entity = map.getEntities().get(coordinates);
            if (entity instanceof Creature creature) {
                if (creature.makeMove(map, coordinates)) {
                    hasMoved = true;
                }
            }
        }
        if (!hasMoved) {
            Renderer renderer = new Renderer();
            renderer.entitiesCannotMoveMessage();
            return false;
        }
        return true;
    }
}
