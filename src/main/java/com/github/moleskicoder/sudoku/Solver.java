package com.github.moleskicoder.sudoku;

/**
 * From: https://see.stanford.edu/materials/icspacs106b/H19-RecBacktrackExamples.pdf
 *
 * A straightforward port from the original C++ to Java
 *
 */
public final class Solver implements ISolver {

    private final IGrid<Integer> grid;
    private final int width;
    private final int height;

    public Solver(final IGrid<Integer> start) {
        this.grid = start;
        this.width = this.grid.getWidth();
        this.height = this.grid.getHeight();
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
        for (int number = 1; number < 10; ++number) { // consider digits 1 to 9
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
        for (int y = 0; y < this.height; ++y) {
            coordinate.setY(y);
            for (int x = 0; x < this.width; ++x) {
                coordinate.setX(x);
                if (this.grid.get(coordinate) == SudokuGrid.UNASSIGNED) {
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
        final int x = coordinate.getX();
        final int y = coordinate.getY();
        return !this.isUsedInRow(y, number)
            && !this.isUsedInColumn(x, number)
            && !this.isUsedInBox(x - x % 3, y - y % 3, number);
    }

    /*
     * Function: isUsedInRow
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * in the specified row matches the given number.
     */
    private boolean isUsedInRow(final int y, final int number) {
        for (int x = 0; x < this.width; ++x) {
            if (this.grid.get(x, y) == number) {
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
    private boolean isUsedInColumn(final int x, final int number) {
        for (int y = 0; y < this.height; ++y) {
            if (this.grid.get(x, y) == number) {
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
    private boolean isUsedInBox(final int boxStartX, final int boxStartY, final int number) {
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                if (this.grid.get(x + boxStartX, y + boxStartY) == number) {
                    return true;
                }
            }
        }
        return false;
    }
}