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

    @Override
    public void set(final int xCoordinate, final int yCoordinate) {
        this.x = xCoordinate;
        this.y = yCoordinate;
    }

    @Override
    public int getX() {
        if (this.x == -1) {
            throw new IllegalStateException("X is uninitialised");
        }
        return this.x;
    }

    @Override
    public void setX(final int xCoordinate) {
        this.x = xCoordinate;
    }

    @Override
    public int getY() {
        if (this.y == -1) {
            throw new IllegalStateException("Y is uninitialised");
        }
        return this.y;
    }

    @Override
    public void setY(final int yCoordinate) {
        this.y = yCoordinate;
    }
}
