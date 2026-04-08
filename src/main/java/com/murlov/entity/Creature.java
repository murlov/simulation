package com.murlov.entity;

import com.murlov.action.PathFinder;
import com.murlov.action.listener.DeathEvent;
import com.murlov.action.listener.EatEvent;
import com.murlov.action.listener.EventBus;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

import java.util.List;

import static java.lang.Math.min;

public abstract class Creature extends Entity {
    private static final int DAMAGE_FROM_HUNGER = 1;
    private static final int NEIGHBOR_PATH_LENGTH = 2;
    private static final int LAST_INDEX_OFFSET = 1;
    private static final int STOP_BEFORE_TARGET_OFFSET = 2;
    private int health;
    private int satiety;
    private final int speed;
    private Coordinates coordinates;
    private boolean isDead;
    private final int maxSatiety;
    private final Class<? extends Entity> food;

    public Creature(int health, int speed, int satiety, Class<? extends Entity> food) {
        this.health = health;
        this.satiety = satiety;
        this.speed = speed;
        this.food = food;
        maxSatiety = satiety;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    private void takeDamageFromHunger(){
        health = Math.max(0, health - DAMAGE_FROM_HUNGER);
        if (health == 0) {
            die();
        }
    }

    private void takeDamageFromAttack(int damage) {
        health = Math.max(0, health - damage);
        if (health == 0) {
            die();
        }
    }

    private void die() {
        isDead = true;
    }

    private void incrementSatiety() {
        satiety++;
        if (satiety > maxSatiety) {
            satiety = maxSatiety;
        }
    }

    private void decrementSatiety() {
        if (satiety <= 0) {
            takeDamageFromHunger();
        }
        satiety--;
    }

    public boolean makeMove(SimulationMap simulationMap, PathFinder pathFinder, EventBus eventBus) {
        List<Coordinates> path = pathFinder.find(simulationMap, getCoordinates(), food);
        if (path.isEmpty()) {
            return false;
        }
        Coordinates newCoordinates;


        if (path.size() == NEIGHBOR_PATH_LENGTH && hasResourceNearby(getCoordinates(), simulationMap)) {
            newCoordinates = path.get(NEIGHBOR_PATH_LENGTH - LAST_INDEX_OFFSET);
            consumeResource(getCoordinates(), newCoordinates, simulationMap, eventBus);
        } else {
            newCoordinates = path.get(min(speed, path.size() - STOP_BEFORE_TARGET_OFFSET));
            simulationMap.moveCreature(this, newCoordinates);
            decrementSatiety();
            if (isDead) {
                eventBus.publish(new DeathEvent(this, getCoordinates()));
                simulationMap.removeEntity(getCoordinates());
            } else if (hasResourceNearby(getCoordinates(), simulationMap)) {
                path = pathFinder.find(simulationMap, getCoordinates(), food);
                if (path.isEmpty()) {
                    return false;
                }
                if (path.size() == NEIGHBOR_PATH_LENGTH) {
                    newCoordinates = path.get(NEIGHBOR_PATH_LENGTH - LAST_INDEX_OFFSET);
                    consumeResource(getCoordinates(), newCoordinates, simulationMap, eventBus);
                }
            }
        }
        return true;
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

    private void consumeResource(Coordinates oldCoordinates, Coordinates newCoordinates, SimulationMap simulationMap, EventBus eventBus) {
        if (getClass() == Rabbit.class) {
            Entity targetEntity = simulationMap.getEntity(newCoordinates);
            eventBus.publish(new EatEvent(this, oldCoordinates, targetEntity, newCoordinates));
            simulationMap.removeEntity(newCoordinates);
            incrementSatiety();
        } else if (getClass() == Wolf.class) {
            Creature herbivore = (Creature) simulationMap.getEntity(newCoordinates);
            herbivore.takeDamageFromAttack(((Predator) this).damage);
            if (herbivore.health == 0) {
                eventBus.publish(new EatEvent(this, oldCoordinates, herbivore, newCoordinates));
                simulationMap.removeEntity(newCoordinates);
            }
            incrementSatiety();
        }
    }
}
