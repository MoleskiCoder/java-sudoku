package com.github.moleskicoder.sudoku;

public final class Game {

    public static void main(final String[] args) {

        // http://www.telegraph.co.uk/news/science/science-news/9359579/Worlds-hardest-sudoku-can-you-crack-it.html
        final Integer[] data = {
                8, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 3, 6, 0, 0, 0, 0, 0,
                0, 7, 0, 0, 9, 0, 2, 0, 0,
                0, 5, 0, 0, 0, 7, 0, 0, 0,
                0, 0, 0, 0, 4, 5, 7, 0, 0,
                0, 0, 0, 1, 0, 0, 0, 3, 0,
                0, 0, 1, 0, 0, 0, 0, 6, 8,
                0, 0, 8, 5, 0, 0, 0, 1, 0,
                0, 9, 0, 0, 0, 0, 4, 0, 0
        };

        final ISudokuGrid puzzle = new SudokuGrid(data);
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
