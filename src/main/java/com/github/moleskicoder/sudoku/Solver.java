package com.github.moleskicoder.sudoku;

import java.util.Set;

/**
 * From: https://see.stanford.edu/materials/icspacs106b/H19-RecBacktrackExamples.pdf
 *
 * A straightforward port from the original C++ to Java
 *
 */
public final class Solver implements ISolver {

    private final ISudokuGrid grid;
    private final int width;
    private final int height;

    public Solver(final ISudokuGrid start) {
        this.grid = start;
        this.width = this.grid.getWidth();
        this.height = this.grid.getHeight();
    }

    @Override
    public boolean solve() {
        this.grid.eliminate();
        return this.solve(0);
    }

    /*
     * Function: solve
     * ---------------------
     * Takes a partially filled-in grid and attempts to assign values to all
     * unassigned locations in such a way to meet the requirements for sudoku
     * solution (non-duplication across rows, columns, and boxes). The function
     * operates via recursive backtracking: it finds an unassigned location with
     * the grid and then considers all digits from 1 to 9 in a loop. If a digit
     * is found that has no existing conflicts, tentatively assign it and recur
     * to attempt to fill in rest of grid. If this was successful, the puzzle is
     * solved. If not, unmake that decision and try again. If all digits have
     * been examined and none worked out, return false to backtrack to previous
     * decision point.
     */
    private boolean solve(final int index) {

        final int offset = this.grid.getOffset(index);

        if (offset == -1) {
            return true; // success!
        }

        final Set<Integer> numbers = this.grid.getPossibilities(offset);

        final int x = offset % SudokuGrid.DIMENSION;
        final int y = offset / SudokuGrid.DIMENSION;

        for (final int number : numbers) {
            if (this.isAvailable(x, y, number)) { // if looks promising,
                this.grid.set(offset, number); // make tentative assignment
                if (this.solve(index + 1)) {
                    return true; // recur, if success, yay!
                }
            }
        }
        this.grid.set(offset, SudokuGrid.UNASSIGNED); // failure, unmake & try again
        return false; // this triggers backtracking
    }

    /*
     * Function: isAvailable
     * ---------------------
     * Returns a boolean which indicates whether it will be legal to assign
     * number to the given row,column location. As assignment is legal if it that
     * number is not already used in the row, column, or box.
     */
    private boolean isAvailable(final int x, final int y, final int number) {
        return !this.isUsedInRow(y, number)
            && !this.isUsedInColumn(x, number)
            && !this.isUsedInBox(x - x % SudokuGrid.BOX_DIMENSION, y - y % SudokuGrid.BOX_DIMENSION, number);
    }

    /*
     * Function: isUsedInRow
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * in the specified row matches the given number.
     */
    private boolean isUsedInRow(final int y, final int number) {
        int offset = y * SudokuGrid.DIMENSION;
        for (int x = 0; x < this.width; ++x) {
            if (this.grid.get(offset) == number) {
                return true;
            }
            offset++;
        }
        return false;
    }

    /*
     * Function: isUsedInColumn
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * in the specified column matches the given number.
     */
    private boolean isUsedInColumn(final int x, final int number) {
        int offset = x;
        for (int y = 0; y < this.height; ++y) {
            if (this.grid.get(offset) == number) {
                return true;
            }
            offset += SudokuGrid.DIMENSION;
        }
        return false;
    }

    /*
     * Function: isUsedInBox
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * within the specified 3x3 box matches the given number.
     */
    private boolean isUsedInBox(final int boxStartX, final int boxStartY, final int number) {
        for (int yOffset = 0; yOffset < SudokuGrid.BOX_DIMENSION; ++yOffset) {
            final int y = yOffset + boxStartY;
            int offset = boxStartX + y * SudokuGrid.DIMENSION;
            for (int xOffset = 0; xOffset < SudokuGrid.BOX_DIMENSION; ++xOffset) {
                if (this.grid.get(offset) == number) {
                    return true;
                }
                offset++;
            }
        }
        return false;
    }
}