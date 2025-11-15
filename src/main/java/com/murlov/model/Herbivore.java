package com.murlov.model;

import com.murlov.action.PathFindAction;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

import java.nio.file.Path;

public abstract class Herbivore extends Creature {

    public Herbivore(String icon) {
        super(icon);
    }
    public Herbivore(Integer speed, Integer health, String icon) {
        super(speed, health, icon);
    }

    public EntityGroup getGroup() {
        return EntityGroup.HERBIVORE;
    }

    public boolean makeMove(Map map, Coordinates oldCoordinates){
        Coordinates newCoordinates = PathFindAction.execute(map, this);

        if (newCoordinates != null){
            Entity targetEntity = map.getEntities().get(newCoordinates);
            if (targetEntity != null && targetEntity.getGroup() == EntityGroup.GRASS) {
                eatGrass(newCoordinates, map);
                return true;
            } else {
                map.setEntity(newCoordinates, this);
                map.getEntities().remove(oldCoordinates);
                newCoordinates = PathFindAction.execute(map, this);
                if (hasGrassNearby(newCoordinates, map)) {
                    eatGrass(newCoordinates, map);
                }
                return true;
            }
        }
        return false;
    }

    private boolean hasGrassNearby(Coordinates newCoordinates, Map map) {
        if (newCoordinates != null){
            Entity targetEntity = map.getEntities().get(newCoordinates);
            if (targetEntity != null && targetEntity.getGroup() == EntityGroup.GRASS) {
                return true;
            }
        }
        return false;
    }

    private void eatGrass(Coordinates newCoordinates, Map map){
        map.getEntities().remove(newCoordinates);
    }
}
