package com.github.moleskicoder.sudoku;

import java.util.ArrayList;
import java.util.List;

public final class Grid<T> {

    private final int width;
    private final int height;
    private final List<T> values;

    public Grid(final int gridWidth, final int gridHeight) {
        this.width = gridWidth;
        this.height = gridHeight;
        this.values = new ArrayList<T>(this.height * this.width);
    }

    public Integer numRows() {
        return this.height;
    }

    public Integer numCols() {
        return this.width;
    }

    public void set(final int x, final int y, final T value) {
        this.values.set(this.getOffset(x, y), value);
    }

    public T get(final int x, final int y) {
        return this.values.get(this.getOffset(x, y));
    }

    private int getOffset(final int x, final int y) {
        return x + y * this.width;
    }
}
