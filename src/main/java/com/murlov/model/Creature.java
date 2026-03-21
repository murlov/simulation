package com.murlov.model;

import com.murlov.action.listener.MoveEventListener;
import com.murlov.action.PathFinder;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

import java.util.List;

import static java.lang.Math.min;

public abstract class Creature extends Entity {
    private int health;
    private int satiety;
    private final int speed;
    public boolean isDead;
    private final int maxSatiety;
    private static final int DAMAGE_FROM_HUNGER = 1;
    private static final int NEIGHBOR_PATH_LENGTH = 2;
    private static final int LAST_INDEX_OFFSET = 1;
    private static final int STOP_BEFORE_TARGET_OFFSET = 2;
    private MoveEventListener listener;

    public Creature(int health, int speed, int satiety) {
        this.health = health;
        this.satiety = satiety;
        this.speed = speed;
        maxSatiety = satiety;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return 0;
    }

    public abstract Class<? extends Entity> getTarget();

    public void setMoveEventListener(MoveEventListener listener) {
        this.listener = listener;
    }

    public void notifyMove(Class<? extends Creature> creatureType, Coordinates from, Coordinates to) {
        if (listener != null) {
            listener.onMove(creatureType, from, to);
        }
    }

    public void notifyAttack(Class<? extends Creature> attackerType, Coordinates from, Class<? extends Creature> victimType, Coordinates to) {
        if (listener != null) {
            listener.onAttack(attackerType, from, victimType, to);
        }
    }

    public void notifyEat(Class<? extends Creature> creatureType, Coordinates from, Class<? extends Entity> victimType, Coordinates to) {
        if (listener != null) {
            listener.onEat(creatureType, from, victimType, to);
        }
    }

    public void notifyDeath(Class<? extends Creature> creatureType, Coordinates coordinates) {
        if (listener != null) {
            listener.onDeath(creatureType, coordinates);
        }
    }

    public void notifyMoveStart() {
        if (listener != null) {
            listener.onMoveStart();
        }
    }

    public void takeDamage (){
        health = Math.max(0, health - DAMAGE_FROM_HUNGER);
        if (health == 0) {
            die();
        }
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
        if (health == 0) {
            die();
        }
    }

    private void die() {
        isDead = true;
    }

    public void incrementSatiety() {
        satiety = min(maxSatiety, ++satiety);
    }

    public void decrementSatiety() {
        if (satiety <= 0) {
            takeDamage();
        }
        satiety--;
    }

    public boolean makeMove(SimulationMap simulationMap, Coordinates currentCoordinates, PathFinder pathFinder) {
        List<Coordinates> path = pathFinder.find(simulationMap, getCoordinates(), getTarget());
        if (path.isEmpty()) {
            return false;
        }
        Coordinates newCoordinates;
        Creature creature = (Creature) simulationMap.getEntity(currentCoordinates);


        if (path.size() == NEIGHBOR_PATH_LENGTH && hasResourceNearby(currentCoordinates, simulationMap)) {
            newCoordinates = path.get(NEIGHBOR_PATH_LENGTH - LAST_INDEX_OFFSET);
            consumeResource(creature, currentCoordinates, newCoordinates, simulationMap);
            return true;
        } else {
            newCoordinates = path.get(min(speed, path.size() - STOP_BEFORE_TARGET_OFFSET));
            simulationMap.moveEntity(this, newCoordinates);
            decrementSatiety();

            notifyMove(creature.getClass(), currentCoordinates, newCoordinates);
            currentCoordinates = newCoordinates;
            if (hasResourceNearby(currentCoordinates, simulationMap)) {
                path = pathFinder.find(simulationMap, currentCoordinates, getTarget());
                if (path.isEmpty()) {
                    return false;
                }
                if (path.size() == NEIGHBOR_PATH_LENGTH) {
                    newCoordinates = path.get(NEIGHBOR_PATH_LENGTH - LAST_INDEX_OFFSET);
                    consumeResource(creature, currentCoordinates, newCoordinates, simulationMap);
                }
            }
            return true;
        }
    }

    private boolean hasResourceNearby(Coordinates coordinates, SimulationMap simulationMap) {
        Class<? extends Entity> targetEntityType = switch (this.getClass().getSimpleName()) {
            case "Wolf" -> Rabbit.class;
            case "Rabbit" -> Grass.class;
            default -> throw new IllegalStateException("Unexpected entityType: " + this.getClass().getSimpleName());
        };
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                Coordinates targetCoordinates = new Coordinates(coordinates.x() + x,  coordinates.y() + y);
                if (simulationMap.getEntities().containsKey(targetCoordinates) && simulationMap.getEntity(targetCoordinates).getClass() == targetEntityType) {
                    return true;
                }
            }
        }
        return false;
    }

    private void consumeResource(Creature creature, Coordinates oldCoordinates, Coordinates newCoordinates, SimulationMap simulationMap) {
        if (creature.getClass() == Rabbit.class) {
            Entity targetEntity = simulationMap.getEntity(newCoordinates);
            simulationMap.removeEntity(targetEntity);
            notifyEat(creature.getClass(), oldCoordinates, targetEntity.getClass(), newCoordinates);
            incrementSatiety();
        } else if (creature.getClass() == Wolf.class) {
            Creature herbivore = (Creature) simulationMap.getEntity(newCoordinates);
            herbivore.takeDamage(getDamage());
            notifyAttack(creature.getClass(), oldCoordinates, herbivore.getClass(), newCoordinates);
            if (herbivore.getHealth() == 0) {
                notifyEat(creature.getClass(), oldCoordinates, herbivore.getClass(), newCoordinates);
                simulationMap.removeEntity(herbivore);
            }
            incrementSatiety();
        }
    }
}
