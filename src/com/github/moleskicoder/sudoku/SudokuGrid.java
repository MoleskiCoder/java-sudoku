package com.github.moleskicoder.sudoku;

public class SudokuGrid extends Grid<Integer> {

    public static final int UNASSIGNED = 0;

    private static final int WIDTH = 9;
    private static final int HEIGHT = 9;

    public SudokuGrid(final Integer[] initial) {
        super(WIDTH, HEIGHT, initial);
    }
}
