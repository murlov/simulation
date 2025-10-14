package com.murlov.simulation;

import java.util.Random;

public class Coordinates {
    public final int x;
    public final int y;

    public Coordinates () {
        this.x = 0;
        this.y = 0;
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
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
        Random rand = new Random();
        int x = rand.nextInt(size.getWidth());
        int y = rand.nextInt(size.getLength());
        return new Coordinates(x, y);
    }


}
