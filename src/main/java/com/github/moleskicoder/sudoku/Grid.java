package com.github.moleskicoder.sudoku;

public class Grid<T> implements IGrid<T> {

    private final int width;
    private final int height;
    private final T[] values;

    public Grid(final int gridWidth, final int gridHeight, final T[] initial) {
        this.width = gridWidth;
        this.height = gridHeight;
        this.values = initial.clone();
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
        this.set(this.getOffset(coordinate), value);
    }

    @Override
    public final void set(final int x, final int y, final T value) {
        this.set(this.getOffset(x, y), value);
    }

    @Override
    public final void set(final int offset, final T value) {
        this.values[offset] = value;
    }

    @Override
    public final T get(final ICoordinate coordinate) {
        return this.get(this.getOffset(coordinate));
    }

    @Override
    public final T get(final int x, final int y) {
        return this.get(this.getOffset(x, y));
    }

    @Override
    public final T get(final int offset) {
        return this.values[offset];
    }

    private int getOffset(final ICoordinate coordinate) {
        return this.getOffset(coordinate.getX(), coordinate.getY());
    }

    private int getOffset(final int x, final int y) {
        return x + y * this.width;
    }
}
