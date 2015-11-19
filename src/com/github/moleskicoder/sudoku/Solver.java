package com.github.moleskicoder.sudoku;

/**
 * From: https://see.stanford.edu/materials/icspacs106b/H19-RecBacktrackExamples.pdf
 *
 * A straightforward port from the original C++ to Java
 *
 */
public class Solver {

    /*
     * Function: solveSudoku
     * ---------------------
     * Takes a partially filled-in grid and attempts to assign values to all
     * unassigned locations in such a way to meet the requirements for com.github.moleskicoder.sudoku.Solver
     * solution (non-duplication across rows, columns, and boxes). The function
     * operates via recursive backtracking: it finds an unassigned location with
     * the grid and then considers all digits from 1 to 9 in a loop. If a digit
     * is found that has no existing conflicts, tentatively assign it and recur
     * to attempt to fill in rest of grid. If this was successful, the puzzle is
     * solved. If not, unmake that decision and try again. If all digits have
     * been examined and none worked out, return false to backtrack to previous
     * decision point.
     */
    public static boolean solveSudoku(final IGrid<Integer> grid) {
        final Integer row = null;
        final Integer column = null;
        if (!findUnassignedLocation(grid, row, column)) {
            return true; // success!
        }
        for (int num = 1; num <= 9; num++) { // consider digits 1 to 9
            if (isConflicted(grid, row, column, num)) { // if looks promising,
                grid.set(row, column, num); // make tentative assignment
                if (solveSudoku(grid)) {
                    return true; // recur, if success, yay!
                }
                grid.set(row, column, null); // failure, unmake & try again
            }
        }
        return false; // this triggers backtracking
    }

    /*
     * Function: findUnassignedLocation
     * --------------------------------
     * Searches the grid to find an entry that is still unassigned. If found,
     * the reference parameters row, column will be set the location that is
     * unassigned, and true is returned. If no unassigned entries remain, false
     * is returned.
     */
    public static boolean findUnassignedLocation(final IGrid<Integer> grid, Integer row, Integer column) {
        final int numberOfRows = grid.getHeight();
        for (row = 0; row < numberOfRows; ++row) {
            final Integer numberOfColumns = grid.getWidth();
            for (column = 0; column < numberOfColumns; ++column) {
                if (grid.get(row, column) == null) {
                    return true;
                }
            }
        }
        return false;
    }
/*
 * Function: isConflicted
 * ---------------------
 * Returns a boolean which indicates whether it will be legal to assign
 * number to the given row,column location. As assignment is legal if it that
 * number is not already used in the row, column, or box.
 */
    public static boolean isConflicted(final IGrid<Integer> grid, final int row, final int column, final int number) {
        return !isUsedInRow(grid, row, number)
            && !isUsedInColumn(grid, column, number)
            && !isUsedInBox(grid, row - row % 3, column - column % 3, number);
    }

    /*
     * Function: isUsedInRow
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * in the specified row matches the given number.
     */
    public static boolean isUsedInRow(final IGrid<Integer> grid, final int row, final int number) {
        final Integer numberOfColumns = grid.getWidth();
        for (int column = 0; column < numberOfColumns; column++) {
            if (grid.get(row, column) == number) {
                return true;
            }
        }
        return false;
    }

    /*
     * Function: isUsedInColumn
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * in the specified column matches the given number.
     */
    static boolean isUsedInColumn(final IGrid<Integer> grid, final int column, final int number) {
        final int numberOfRows = grid.getHeight();
        for (int row = 0; row < numberOfRows; row++) {
            if (grid.get(row, column) == number) {
                return true;
            }
        }
        return false;
    }

    /*
     * Function: isUsedInBox
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * within the specified 3x3 box matches the given number.
     */
    static boolean isUsedInBox(final IGrid<Integer> grid, final int boxStartRow, final int boxStartColumn, final int number) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                if (grid.get(row + boxStartRow, column + boxStartColumn) == number) {
                    return true;
                }
            }
        }
        return false;
    }
}