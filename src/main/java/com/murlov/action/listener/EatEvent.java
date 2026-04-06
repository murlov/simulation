package com.murlov.action.listener;

import com.murlov.entity.Creature;
import com.murlov.entity.Entity;
import com.murlov.simulation.Coordinates;

public record EatEvent(Class<? extends Creature> creatureType,
                       Coordinates from,
                       Class<? extends Entity> victimType,
                       Coordinates to

) implements Event {
}
