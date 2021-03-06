package com.github.moleskicoder.sudoku;

public interface IGrid<T> {

    int getHeight();
    int getWidth();

    void set(int x, int y, T value);
    void set(int offset, T value);
    T get(int x, int y);
    T get(int offset);
}
