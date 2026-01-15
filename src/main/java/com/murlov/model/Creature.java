package com.murlov.model;

import com.murlov.action.MoveEventListener;
import com.murlov.action.PathFinder;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

public abstract class Creature extends Entity {
    private int health;
    private int satiety;
    private MoveEventListener listener;
    public boolean isDead;
    private final static int DEAFAULT_HEALTH = 10;
    private final static int DEFAULT_SATIETY = 10;
    private final static int DAMAGE_FROM_HUNGER = 1;

    public Creature() {
        this.health = DEAFAULT_HEALTH;
    }

    public Creature(int health) {
        this.health = health;
        satiety = DEFAULT_SATIETY;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return 0;
    }

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

    public void notifyMoveEnd(SimulationMap simulationMap) {
        if (listener != null) {
            listener.onMoveEnd(simulationMap);
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
    }

    private void die() {
        isDead = true;
    }

    public void satietyIncrement() {
        satiety = Math.min(10, ++satiety);
    }

    public void satietyDecrement() {
        if (satiety <= 0) {
            takeDamage();
        }
        satiety--;
    }

    public boolean makeMove(SimulationMap simulationMap, Coordinates oldCoordinates, PathFinder pathFinder) {
        Coordinates newCoordinates = pathFinder.execute(simulationMap, this);
        Creature creature = (Creature) simulationMap.getEntities().get(oldCoordinates);

        if (newCoordinates != null){
            if (hasResourceNearby(newCoordinates, simulationMap)) {
                consumeResource(creature, oldCoordinates, newCoordinates, simulationMap);
                return true;
            } else {
                simulationMap.setEntity(newCoordinates, this);
                simulationMap.getEntities().remove(oldCoordinates);
                notifyMove(creature.getClass(), oldCoordinates, newCoordinates);
                oldCoordinates = newCoordinates;
                newCoordinates = pathFinder.execute(simulationMap, this);
                if (hasResourceNearby(newCoordinates, simulationMap)) {
                    consumeResource(creature, oldCoordinates, newCoordinates, simulationMap);
                } else {
                    satietyDecrement();
                }
                return true;
            }
        }
        return false;
    }

    private boolean hasResourceNearby(Coordinates newCoordinates, SimulationMap simulationMap) {
        if (newCoordinates != null){
            Entity targetEntity = simulationMap.getEntities().get(newCoordinates);
            Class <? extends Entity> targetEntityType = switch (this.getClass().getSimpleName()) {
                case "Wolf" -> Rabbit.class;
                case "Rabbit" -> Grass.class;
                default -> throw new IllegalStateException("Unexpected entityType: " + this.getClass().getSimpleName());
            };
            return targetEntity != null && targetEntity.getClass() == targetEntityType;
        }
        return false;
    }

    private void consumeResource(Creature creature, Coordinates oldCoordinates, Coordinates newCoordinates, SimulationMap simulationMap) {
        if (creature.getClass() == Rabbit.class) {
            Entity targetEntity = simulationMap.getEntities().get(newCoordinates);
            simulationMap.getEntities().remove(newCoordinates);
            simulationMap.countForEntityTypeDecrement(Grass.class);
            notifyEat(creature.getClass(), oldCoordinates, targetEntity.getClass(), newCoordinates);
            satietyIncrement();
        } else if (creature.getClass() == Wolf.class) {
            Creature herbivore = (Creature) simulationMap.getEntities().get(newCoordinates);
            herbivore.takeDamage(getDamage());
            notifyAttack(creature.getClass(), oldCoordinates, herbivore.getClass(), newCoordinates);
            if (herbivore.getHealth() == 0) {
                notifyEat(creature.getClass(), oldCoordinates, herbivore.getClass(), newCoordinates);
                simulationMap.getEntities().remove(newCoordinates);
                simulationMap.countForEntityTypeDecrement(Rabbit.class);
            }
            satietyIncrement();
        }
    }
}
