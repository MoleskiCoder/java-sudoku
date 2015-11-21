package com.github.moleskicoder.sudoku;

public interface IGrid<T> {

    int getHeight();
    int getWidth();

    void set(ICoordinate coordinate, T value);
    void set(int x, int y, T value);
    T get(ICoordinate coordinate);
    T get(int x, int y);
}
