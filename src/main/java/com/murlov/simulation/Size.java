package com.murlov.simulation;

public class Size {
    public final int width;
    public final int length;

    public Size() {
        width = 0;
        length = 0;
    }

    public Size(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public int getArea() {
        return this.width*this.length;
    }

    public int getWidth() {
        return this.width;
    }

    public int getLength() {
        return this.length;
    }
}
