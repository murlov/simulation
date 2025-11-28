package com.murlov.model;

import com.murlov.util.RandomProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum EntityType {
    GRASS(EntityGroup.GRASS),
    TREE(EntityGroup.STATIC),
    ROCK(EntityGroup.STATIC),
    ZEBRA(EntityGroup.HERBIVORE),
    ELEPHANT(EntityGroup.HERBIVORE),
    GOAT(EntityGroup.HERBIVORE),
    WOLF(EntityGroup.PREDATOR),
    TIGER(EntityGroup.PREDATOR),
    LEOPARD(EntityGroup.PREDATOR);

    private final EntityGroup group;


    EntityType(EntityGroup group) {
        this.group = group;
    }

    public EntityGroup getGroup() {
        return group;
    }

    public static EntityType getRandom(EntityGroup group) {
        List<EntityType> types = new ArrayList<EntityType>();
        for (EntityType type : EntityType.values()) {
            if (type.getGroup() == group) {
                types.add(type);
            }
        }
        Random random = RandomProvider.getInstance();
        return types.get(random.nextInt(types.size()));
    }
}
