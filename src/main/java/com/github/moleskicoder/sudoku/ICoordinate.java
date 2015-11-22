package com.github.moleskicoder.sudoku;

public interface ICoordinate {

    void set(int xCoordinate, int yCoordinate);

    int getX();
    int getY();

    void setX(int xCoordinate);
    void setY(int yCoordinate);
}
