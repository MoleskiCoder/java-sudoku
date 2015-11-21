package com.github.moleskicoder.sudoku;

/**
 * From: https://see.stanford.edu/materials/icspacs106b/H19-RecBacktrackExamples.pdf
 *
 * A straightforward port from the original C++ to Java
 *
 */
public class Solver {

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

        final long start = System.currentTimeMillis();
        final boolean solved = solveSudoku(puzzle);
        final long finish = System.currentTimeMillis();

        if (solved) {
            System.out.print(puzzle.toString());

            final long elapsed = finish - start;

            System.out.format("\n\nTime taken %d.%03d seconds\n", elapsed / 1000, elapsed % 1000);
        } else {
            System.out.println("No solution exists");
        }
    }

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
        final ICoordinate coordinate = new Coordinate();
        if (!findUnassignedLocation(grid, coordinate)) {
            return true; // success!
        }
        for (int number = 1; number <= 9; number++) { // consider digits 1 to 9
            if (isConflicted(grid, coordinate, number)) { // if looks promising,
                grid.set(coordinate, number); // make tentative assignment
                if (solveSudoku(grid)) {
                    return true; // recur, if success, yay!
                }
                grid.set(coordinate, SudokuGrid.UNASSIGNED); // failure, unmake & try again
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
    public static boolean findUnassignedLocation(final IGrid<Integer> grid, final ICoordinate coordinate) {
        final int numberOfRows = grid.getHeight();
        for (int row = 0; row < numberOfRows; ++row) {
            final Integer numberOfColumns = grid.getWidth();
            for (int column = 0; column < numberOfColumns; ++column) {
                if (grid.get(column, row) == SudokuGrid.UNASSIGNED) {
                    coordinate.set(column, row);
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
    public static boolean isConflicted(final IGrid<Integer> grid, final ICoordinate coordinate, final int number) {
        final int column = coordinate.getX();
        final int row = coordinate.getY();
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
            if (grid.get(column, row) == number) {
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
            if (grid.get(column, row) == number) {
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
                if (grid.get(column + boxStartColumn, row + boxStartRow) == number) {
                    return true;
                }
            }
        }
        return false;
    }
}