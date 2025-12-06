package com.murlov.model;

import com.murlov.action.PathFinder;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

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

    public boolean makeMove(Map map, Coordinates oldCoordinates, PathFinder pathFinder) {
        Coordinates newCoordinates = pathFinder.execute(map, this);
        Creature creature = (Creature) map.getEntities().get(oldCoordinates);

        if (newCoordinates != null){
            Entity targetEntity = map.getEntities().get(newCoordinates);
            if (hasGrassNearby(newCoordinates, map)) {
                eatGrass(newCoordinates, map);
                notifyEat(creature.getType(), oldCoordinates, targetEntity.getType(), newCoordinates);
                notifyMoveEnd(map);
                return true;
            } else {
                map.setEntity(newCoordinates, this);
                map.getEntities().remove(oldCoordinates);
                notifyMove(creature.getType(), oldCoordinates, newCoordinates);
                oldCoordinates = newCoordinates;
                newCoordinates = pathFinder.execute(map, this);
                if (hasGrassNearby(newCoordinates, map)) {
                    targetEntity = map.getEntities().get(newCoordinates);
                    eatGrass(newCoordinates, map);
                    notifyEat(creature.getType(), oldCoordinates, targetEntity.getType(), newCoordinates);
                }
                notifyMoveEnd(map);
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
        map.countInGroupDecrement(EntityGroup.GRASS);
    }
}
