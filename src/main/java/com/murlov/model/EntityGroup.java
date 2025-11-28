package com.murlov.model;

import com.murlov.util.RandomProvider;

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
        Random random = RandomProvider.getInstance();
        int size = EntityGroup.values().length;
        int randomIndex = random.nextInt(size);
        return EntityGroup.values()[randomIndex];
    }
}
