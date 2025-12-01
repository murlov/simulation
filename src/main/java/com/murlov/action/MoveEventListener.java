package com.murlov.action;


import com.murlov.model.EntityType;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

public interface MoveEventListener {
    void onMove(EntityType creatureType, Coordinates from, Coordinates to);
    void onAttack(EntityType attackerType, EntityType victimType, Coordinates to);
    void onEat(EntityType creatureType, EntityType victimType, Coordinates to);
    void onMoveEnd(Map map);
}
