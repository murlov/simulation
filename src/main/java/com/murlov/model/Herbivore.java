package com.murlov.model;

import com.murlov.action.PathFinder;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public abstract class Herbivore extends Creature {

    public Herbivore(String icon) {
        super(icon);
    }
    public Herbivore(int health, String icon) {
        super(health, icon);
    }

    public EntityGroup getGroup() {
        return EntityGroup.HERBIVORE;
    }

    public boolean makeMove(SimulationMap simulationMap, Coordinates oldCoordinates, PathFinder pathFinder) {
        Coordinates newCoordinates = pathFinder.execute(simulationMap, this);
        Creature creature = (Creature) simulationMap.getEntities().get(oldCoordinates);

        if (newCoordinates != null){
            if (hasGrassNearby(newCoordinates, simulationMap)) {
                eatGrass(creature, oldCoordinates, newCoordinates, simulationMap);
                return true;
            } else {
                simulationMap.setEntity(newCoordinates, this);
                simulationMap.getEntities().remove(oldCoordinates);
                notifyMove(creature.getType(), oldCoordinates, newCoordinates);
                oldCoordinates = newCoordinates;
                newCoordinates = pathFinder.execute(simulationMap, this);
                if (hasGrassNearby(newCoordinates, simulationMap)) {
                    eatGrass(creature, oldCoordinates, newCoordinates, simulationMap);
                } else {
                    satietyDecrement();
                }
                return true;
            }
        }
        return false;
    }

    private boolean hasGrassNearby(Coordinates newCoordinates, SimulationMap simulationMap) {
        if (newCoordinates != null){
            Entity targetEntity = simulationMap.getEntities().get(newCoordinates);
            if (targetEntity != null && targetEntity.getGroup() == EntityGroup.GRASS) {
                return true;
            }
        }
        return false;
    }

    private void eatGrass(Creature herbivore, Coordinates oldCoordinates, Coordinates newCoordinates, SimulationMap simulationMap){
        Entity targetEntity = simulationMap.getEntities().get(newCoordinates);
        simulationMap.getEntities().remove(newCoordinates);
        simulationMap.countInGroupDecrement(EntityGroup.GRASS);
        notifyEat(herbivore.getType(), oldCoordinates, targetEntity.getType(), newCoordinates);
        satietyIncrement();
    }
}
