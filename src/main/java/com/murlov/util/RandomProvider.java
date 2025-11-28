package com.murlov.util;

import java.util.Random;

public class RandomProvider {
//    private static final long DEFAULT_SEED = 20;
//    private static Random instance = new Random(DEFAULT_SEED);
    private static Random instance = new Random();

    public static Random getInstance() {
        return instance;
    }

    public static void setSeed(long seed) {
        instance = new Random(seed);
    }
}