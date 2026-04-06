package com.murlov.action.listener;

import com.murlov.entity.Creature;
import com.murlov.entity.Entity;
import com.murlov.simulation.Coordinates;

public record EatEvent(Creature eater,
                       Coordinates from,
                       Entity target,
                       Coordinates to

) implements Event {
}
