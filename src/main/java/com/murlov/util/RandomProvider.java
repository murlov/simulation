package com.murlov.util;

import java.util.Random;

public class RandomProvider {
    private static final Random instance = new Random(1);

    public static Random getInstance() {
        return instance;
    }
}