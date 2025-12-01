package com.murlov.simulation;

public record Size(int width, int length) {
    public Size() {
        this(0, 0);
    }

    public int getArea() {
        return this.width * this.length;
    }
}
