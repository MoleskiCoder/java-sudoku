package com.github.moleskicoder.sudoku;

public class SudokuGrid extends Grid<Integer> {

    public static final int UNASSIGNED = 0;

    private static final int WIDTH = 9;
    private static final int HEIGHT = 9;

    public SudokuGrid(final Integer[] initial) {
        super(WIDTH, HEIGHT, initial);
    }

    @Override
    public final String toString() {
        final StringBuilder output = new StringBuilder();
        output.append('\n');
        final int height = this.getHeight();
        for (int y = 0; y < height; ++y) {
            final int width = this.getWidth();
            for (int x = 0; x < width; ++x) {
                final int number = this.get(x, y);
                output.append(' ');
                if (number == UNASSIGNED) {
                    output.append('-');
                } else {
                    output.append(number);
                }
                output.append(' ');
                if ((x + 1) % 3 == 0 && x + 1 < width) {
                    output.append('|');
                }
            }
            if ((y + 1) % 3 == 0 && y + 1 < width) {
                output.append("\n --------+---------+--------");
            }
            output.append('\n');
        }
        return output.toString();
    }
}
