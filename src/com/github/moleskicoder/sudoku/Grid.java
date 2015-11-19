package com.github.moleskicoder.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Grid<T> implements IGrid<T> {

    private final int width;
    private final int height;
    private final List<T> values;

    public Grid(final int gridWidth, final int gridHeight) {
        this.width = gridWidth;
        this.height = gridHeight;
        this.values = new ArrayList<T>(this.height * this.width);
    }

    public Grid(final int gridWidth, final int gridHeight, final T[] initial) {
        this.width = gridWidth;
        this.height = gridHeight;
        this.values = new ArrayList<T>(this.height * this.width);
        final int size = initial.length;
        if (this.width * this.height != size) {
            throw new IllegalArgumentException("initial array is the wrong size.");
        }
        for (int i = 0; i < size; ++i) {
            this.values.set(i, initial[i]);
        }
    }

    @Override
    public final Integer getHeight() {
        return this.height;
    }

    @Override
    public final Integer getWidth() {
        return this.width;
    }

    @Override
    public final void set(final int x, final int y, final T value) {
        this.values.set(this.getOffset(x, y), value);
    }

    @Override
    public final T get(final int x, final int y) {
        return this.values.get(this.getOffset(x, y));
    }

    private int getOffset(final int x, final int y) {
        return x + y * this.width;
    }
}
