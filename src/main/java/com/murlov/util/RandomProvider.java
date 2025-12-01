package com.murlov.util;

import java.util.Random;

public class RandomProvider {
    private static final Random instance = new Random();

    public static Random getInstance() {
        return instance;
    }
}