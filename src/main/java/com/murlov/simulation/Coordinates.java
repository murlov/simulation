package com.murlov.simulation;

public record Coordinates(int x, int y) {
    public Coordinates() {
        this(0, 0);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
