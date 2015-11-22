package com.github.moleskicoder.sudoku;

/**
 * From: https://see.stanford.edu/materials/icspacs106b/H19-RecBacktrackExamples.pdf
 *
 * A straightforward port from the original C++ to Java
 *
 */
public final class Solver implements ISolver {

    private final IGrid<Integer> grid;

    public Solver(final IGrid<Integer> start) {
        this.grid = start;
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
    @Override
    public boolean solve() {

        final ICoordinate coordinate = new Coordinate();
        if (!this.findUnassignedLocation(coordinate)) {
            return true; // success!
        }
        for (int number = 1; number <= 9; number++) { // consider digits 1 to 9
            if (this.isAvailable(coordinate, number)) { // if looks promising,
                this.grid.set(coordinate, number); // make tentative assignment
                if (this.solve()) {
                    return true; // recur, if success, yay!
                }
                this.grid.set(coordinate, SudokuGrid.UNASSIGNED); // failure, unmake & try again
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
    private boolean findUnassignedLocation(final ICoordinate coordinate) {
        final int numberOfRows = this.grid.getHeight();
        for (int row = 0; row < numberOfRows; ++row) {
            final Integer numberOfColumns = this.grid.getWidth();
            for (int column = 0; column < numberOfColumns; ++column) {
                if (this.grid.get(column, row) == SudokuGrid.UNASSIGNED) {
                    coordinate.set(column, row);
                    return true;
                }
            }
        }
        return false;
    }
/*
 * Function: isAvailable
 * ---------------------
 * Returns a boolean which indicates whether it will be legal to assign
 * number to the given row,column location. As assignment is legal if it that
 * number is not already used in the row, column, or box.
 */
    private boolean isAvailable(final ICoordinate coordinate, final int number) {
        final int column = coordinate.getX();
        final int row = coordinate.getY();
        return !this.isUsedInRow(row, number)
            && !this.isUsedInColumn(column, number)
            && !this.isUsedInBox(row - row % 3, column - column % 3, number);
    }

    /*
     * Function: isUsedInRow
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * in the specified row matches the given number.
     */
    private boolean isUsedInRow(final int row, final int number) {
        final Integer numberOfColumns = this.grid.getWidth();
        for (int column = 0; column < numberOfColumns; column++) {
            if (this.grid.get(column, row) == number) {
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
    private boolean isUsedInColumn(final int column, final int number) {
        final int numberOfRows = this.grid.getHeight();
        for (int row = 0; row < numberOfRows; row++) {
            if (this.grid.get(column, row) == number) {
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
    private boolean isUsedInBox(final int boxStartRow, final int boxStartColumn, final int number) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                if (this.grid.get(column + boxStartColumn, row + boxStartRow) == number) {
                    return true;
                }
            }
        }
        return false;
    }
}