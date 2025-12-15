package com.murlov.model;

import com.murlov.action.PathFinder;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public abstract class Predator extends Creature {
    int damage;

    public Predator(String icon) {
        super(icon);
        this.damage = 1;
    }

    public Predator(int health, int damage, String icon) {
        super(health, icon);
        this.damage = damage;
    }

    public EntityGroup getGroup() {
        return EntityGroup.PREDATOR;
    }

    public int getDamage() {
        return damage;
    }

    public boolean makeMove(SimulationMap simulationMap, Coordinates oldCoordinates, PathFinder pathFinder) {
        Coordinates newCoordinates = pathFinder.execute(simulationMap, this);
        Creature creature = (Creature) simulationMap.getEntities().get(oldCoordinates);

        if (newCoordinates != null){
            if (hasHerbivoreNearby(newCoordinates, simulationMap)) {
                attack(creature, oldCoordinates, newCoordinates, simulationMap);
                return true;
            } else {
                simulationMap.setEntity(newCoordinates, this);
                simulationMap.getEntities().remove(oldCoordinates);
                notifyMove(creature.getType(), oldCoordinates, newCoordinates);
                oldCoordinates = newCoordinates;
                newCoordinates = pathFinder.execute(simulationMap, this);
                if (hasHerbivoreNearby(newCoordinates, simulationMap)) {
                    attack(creature, oldCoordinates, newCoordinates, simulationMap);
                } else {
                    satietyDecrement();
                }
                return true;
            }
        }
        return false;
    }

    private boolean hasHerbivoreNearby(Coordinates newCoordinates, SimulationMap simulationMap) {
        if (newCoordinates != null){
            Entity targetEntity = simulationMap.getEntities().get(newCoordinates);
            if (targetEntity != null && targetEntity.getGroup() == EntityGroup.HERBIVORE) {
                return true;
            }
        }
        return false;
    }
    private void attack(Creature attacker, Coordinates from, Coordinates to, SimulationMap simulationMap){
        Creature herbivore = (Creature) simulationMap.getEntities().get(to);
        herbivore.takeDamage(this.getDamage());
        notifyAttack(attacker.getType(), from, herbivore.getType(), to);
        if (herbivore.getHealth() == 0) {
            notifyEat(attacker.getType(), from, herbivore.getType(), to);
            simulationMap.getEntities().remove(to);
            simulationMap.countInGroupDecrement(EntityGroup.HERBIVORE);
        }
        satietyIncrement();
    }
}
