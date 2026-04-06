package com.murlov.action.listener;

import com.murlov.entity.Creature;
import com.murlov.simulation.Coordinates;

public record DeathEvent(Class<? extends Creature> creatureType,
                         Coordinates coordinates
) implements Event {
}
