package com.murlov.simulation;

import com.murlov.util.RandomProvider;

import java.util.Random;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates () {
        this.x = 0;
        this.y = 0;
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;
        return (x == that.x) && (y == that.y);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public static Coordinates getRandom (Size size) {
        Random random = RandomProvider.getInstance();
        int x = random.nextInt(size.getWidth());
        int y = random.nextInt(size.getLength());
        return new Coordinates(x, y);
    }
}
