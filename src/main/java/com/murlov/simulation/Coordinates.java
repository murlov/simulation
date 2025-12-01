package com.murlov.simulation;

import com.murlov.util.RandomProvider;

import java.util.Random;

public record Coordinates(int X, int Y) {
    public Coordinates() {
        this(0, 0);
    }

    @Override
    public String toString() {
        return "(" + X + "," + Y + ")";
    }

    public static Coordinates getRandom(Size size) {
        Random random = RandomProvider.getInstance();
        int X = random.nextInt(size.width());
        int Y = random.nextInt(size.length());
        return new Coordinates(X, Y);
    }
}
