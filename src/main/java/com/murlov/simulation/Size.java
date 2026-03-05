package com.murlov.simulation;

public record Size(int width, int height) {
    public Size() {
        this(0, 0);
    }

    public int getArea() {
        return this.width * this.height;
    }
}
