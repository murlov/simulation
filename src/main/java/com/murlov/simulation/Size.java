package com.murlov.simulation;

public record Size(int width, int height) {
    public Size() {
        this(0, 0);
    }

    public Size(Size size) {
        this(size.width, size.height);
    }

    public int getArea() {
        return this.width * this.height;
    }
}
