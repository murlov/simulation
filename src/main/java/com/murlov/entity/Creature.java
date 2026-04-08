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
    private static final int NEIGHBOR_INDEX = 1;
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

    public void makeMove(SimulationMap simulationMap, PathFinder pathFinder, EventBus eventBus) {
        List<Coordinates> path = pathFinder.find(simulationMap, getCoordinates(), food);
        if (path.isEmpty()) {
            throw new RuntimeException("Creature cannot find food");
        }
        Coordinates newCoordinates;


        if (hasResourceNearby(getCoordinates(), simulationMap)) {
            newCoordinates = path.get(NEIGHBOR_INDEX);
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
                newCoordinates = path.get(NEIGHBOR_INDEX);
                consumeResource(getCoordinates(), newCoordinates, simulationMap, eventBus);
            }
        }
    }

    private boolean hasResourceNearby(Coordinates coordinates, SimulationMap simulationMap) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                Coordinates targetCoordinates = new Coordinates(coordinates.x() + x,  coordinates.y() + y);
                boolean isFoodAtTargetCoordinates = simulationMap.getEntities().containsKey(targetCoordinates) &&
                        simulationMap.getEntity(targetCoordinates).getClass() == food;
                if (isFoodAtTargetCoordinates) {
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
