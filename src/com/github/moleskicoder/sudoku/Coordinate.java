package com.github.moleskicoder.sudoku;

public final class Coordinate implements ICoordinate {

    private int x;
    private int y;

    public Coordinate() {
        this.x = -1;
        this.y = -1;
    }

    public Coordinate(final int xCoordinate, final int yCoordinate) {
        this.set(xCoordinate, yCoordinate);
    }

    public void set(final int xCoordinate, final int yCoordinate) {
        this.x = xCoordinate;
        this.y = yCoordinate;
    }

    public int getX() {
        if (this.x == -1) {
            throw new IllegalStateException("X in uninitialised");
        }
        return this.x;
    }

    public void setX(final int xCoordinate) {
        this.x = xCoordinate;
    }

    public int getY() {
        if (this.y == -1) {
            throw new IllegalStateException("X in uninitialised");
        }
        return this.y;
    }

    public void setY(final int yCoordinate) {
        this.y = yCoordinate;
    }
}
