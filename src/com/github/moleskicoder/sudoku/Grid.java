package com.github.moleskicoder.sudoku;

public class Grid<T> implements IGrid<T> {

    private final int width;
    private final int height;
    private final T[] values;

    public Grid(final int gridWidth, final int gridHeight, final T[] initial) {
        this.width = gridWidth;
        this.height = gridHeight;
        this.values = initial;
        final int size = initial.length;
        if (this.width * this.height != size) {
            throw new IllegalArgumentException("initial array is the wrong size.");
        }
    }

    @Override
    public final int getHeight() {
        return this.height;
    }

    @Override
    public final int getWidth() {
        return this.width;
    }

    @Override
    public final void set(final ICoordinate coordinate, final T value) {
        this.values[this.getOffset(coordinate)] = value;
    }

    @Override
    public final void set(final int x, final int y, final T value) {
        this.values[this.getOffset(x, y)] = value;
    }

    @Override
    public final T get(final ICoordinate coordinate) {
        return this.values[this.getOffset(coordinate)];
    }

    @Override
    public final T get(final int x, final int y) {
        return this.values[this.getOffset(x, y)];

    }

    @Override
    public final String toString() {
        final StringBuilder output = new StringBuilder();
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                final T number = this.get(x, y);
                if (number == null) {
                    output.append("  ");
                } else {
                    output.append(number.toString());
                    output.append(' ');
                }
            }
            output.append('\n');
        }
        return output.toString();
    }

    private int getOffset(final ICoordinate coordinate) {
        return this.getOffset(coordinate.getX(), coordinate.getY());
    }

    private int getOffset(final int x, final int y) {
        return x + y * this.width;
    }
}
