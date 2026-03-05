package com.murlov.simulation;

import com.murlov.util.RandomProvider;

import java.util.Random;

public record Coordinates(int x, int y) {
    public Coordinates() {
        this(0, 0);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public static Coordinates getRandom(Size size) {
        Random random = RandomProvider.getInstance();
        int x = random.nextInt(size.width());
        int y = random.nextInt(size.height());
        return new Coordinates(x, y);
    }
}
