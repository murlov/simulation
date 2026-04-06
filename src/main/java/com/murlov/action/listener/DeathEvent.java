package com.murlov.action.listener;

import com.murlov.entity.Creature;
import com.murlov.simulation.Coordinates;

public record DeathEvent(Creature creature,
                         Coordinates coordinates
) implements Event {
}
