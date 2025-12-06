package com.murlov.model;

import com.murlov.action.PathFinder;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public abstract class Predator extends Creature {
    int damage;

    public Predator(String icon) {
        super(icon);
        this.damage = 1;
    }

    public Predator(Integer speed, Integer health, Integer damage, String icon) {
        super(speed, health, icon);
        this.damage = damage;
    }

    public EntityGroup getGroup() {
        return EntityGroup.PREDATOR;
    }

    public int getDamage() {
        return damage;
    }

    public boolean makeMove(Map map, Coordinates oldCoordinates, PathFinder pathFinder) {
        Coordinates newCoordinates = pathFinder.execute(map, this);
        Creature creature = (Creature) map.getEntities().get(oldCoordinates);

        if (newCoordinates != null){
            Entity targetEntity = map.getEntities().get(newCoordinates);
            if (hasHerbivoreNearby(newCoordinates, map)) {
                attack(creature, oldCoordinates, newCoordinates, map);
                notifyAttack(creature.getType(), oldCoordinates, targetEntity.getType(), newCoordinates);
                notifyMoveEnd(map);
                return true;
            } else {
                map.setEntity(newCoordinates, this);
                map.getEntities().remove(oldCoordinates);
                notifyMove(creature.getType(), oldCoordinates, newCoordinates);
                oldCoordinates = newCoordinates;
                newCoordinates = pathFinder.execute(map, this);
                if (hasHerbivoreNearby(newCoordinates, map)) {
                    attack(creature, oldCoordinates, newCoordinates, map);
                }
                notifyMoveEnd(map);
                return true;
            }
        }
        return false;
    }

    private boolean hasHerbivoreNearby(Coordinates newCoordinates, Map map) {
        if (newCoordinates != null){
            Entity targetEntity = map.getEntities().get(newCoordinates);
            if (targetEntity != null && targetEntity.getGroup() == EntityGroup.HERBIVORE) {
                return true;
            }
        }
        return false;
    }
    private void attack(Creature attacker, Coordinates from, Coordinates to, Map map){
        Creature herbivore = (Creature) map.getEntities().get(to);
        herbivore.takeDamage(this.getDamage());
        if (herbivore.getHealth() == 0) {
            map.getEntities().remove(to);
            map.countInGroupDecrement(EntityGroup.HERBIVORE);
            notifyAttack(attacker.getType(), to, herbivore.getType(), to);
            notifyEat(attacker.getType(), from, herbivore.getType(), to);
        }
    }
}
