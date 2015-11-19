package com.github.moleskicoder.sudoku;

public interface IGrid<T> {

    Integer getHeight();
    Integer getWidth();

    void set(final int x, final int y, final T value);
    T get(final int x, final int y);
}
