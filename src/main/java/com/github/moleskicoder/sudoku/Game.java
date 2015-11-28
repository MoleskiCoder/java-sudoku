package com.github.moleskicoder.sudoku;

public final class Game {

    public static void main(final String[] args) {

        final Integer[] data = {
                3, 0, 6, 5, 0, 8, 4, 0, 0,
                5, 2, 0, 0, 0, 0, 0, 0, 0,
                0, 8, 7, 0, 0, 0, 0, 3, 1,
                0, 0, 3, 0, 1, 0, 0, 8, 0,
                9, 0, 0, 8, 6, 3, 0, 0, 5,
                0, 5, 0, 0, 9, 0, 6, 0, 0,
                1, 3, 0, 0, 0, 0, 2, 5, 0,
                0, 0, 0, 0, 0, 0, 0, 7, 4,
                0, 0, 5, 2, 0, 6, 3, 0, 0
        };

        final IGrid<Integer> puzzle = new SudokuGrid(data);
        final ISolver solver = new Solver(puzzle);

        final long start = System.currentTimeMillis();
        final boolean solved = solver.solve();
        final long finish = System.currentTimeMillis();

        if (solved) {
            System.out.print(puzzle);

            final long elapsed = finish - start;
            final double seconds = (double)elapsed / 1000.0 + (double)(elapsed % 1000L) / 1000.0;

            System.out.format("\n\nTime taken %f seconds\n", seconds);
        } else {
            System.out.println("No solution exists");
        }
    }
}
