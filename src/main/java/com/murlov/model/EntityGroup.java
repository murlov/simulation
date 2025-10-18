package com.murlov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum EntityGroup {
    HERBIVORE,
    PREDATOR,
    STATIC,
    GRASS;

    public static EntityGroup getRandom() {
        List<EntityGroup> groups = new ArrayList<EntityGroup>();
        Random rand = new Random();
        int size = EntityGroup.values().length;
        int randomIndex = rand.nextInt(size);
        return EntityGroup.values()[randomIndex];
    }
}
