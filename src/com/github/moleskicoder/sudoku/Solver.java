package com.github.moleskicoder.sudoku;

/**
 * From: https://see.stanford.edu/materials/icspacs106b/H19-RecBacktrackExamples.pdf
 *
 * A straightforward port from the original C++ to Java
 *
 */
public class Solver {

    /*
     * Function: SolveSudoku
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
    public static boolean SolveSudoku(Grid<Integer> grid) {
        Integer row = null, col = null;
        if (!FindUnassignedLocation(grid, row, col))
            return true; // success!
        for (int num = 1; num <= 9; num++) { // consider digits 1 to 9
            if (NoConflicts(grid, row, col, num)) { // if looks promising,
                grid.set(row, col, num); // make tentative assignment
                if (SolveSudoku(grid))
                    return true; // recur, if success, yay!
                grid.set(row, col, null); // failure, unmake & try again
            }
        }
        return false; // this triggers backtracking
    }

    /*
     * Function: FindUnassignedLocation
     * --------------------------------
     * Searches the grid to find an entry that is still unassigned. If found,
     * the reference parameters row, col will be set the location that is
     * unassigned, and true is returned. If no unassigned entries remain, false
     * is returned.
     */
    public static boolean FindUnassignedLocation(Grid<Integer> grid, Integer row, Integer col) {
        for (row = 0; row < grid.numRows(); row++)
            for (col = 0; col < grid.numCols(); col++)
                if (grid.get(row, col) == null)
                    return true;
        return false;
    }
/*
 * Function: NoConflicts
 * ---------------------
 * Returns a boolean which indicates whether it will be legal to assign
 * num to the given row,col location. As assignment is legal if it that
 * number is not already used in the row, col, or box.
 */
    public static boolean NoConflicts(Grid<Integer> grid, Integer row, Integer col, Integer num) {
        return !UsedInRow(grid, row, num) && !UsedInCol(grid, col, num) && !UsedInBox(grid, row - row % 3, col - col % 3, num);
    }

    /*
     * Function: UsedInRow
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * in the specified row matches the given number.
     */
    public static boolean UsedInRow(Grid<Integer> grid, Integer row, Integer num) {
        for (int col = 0; col < grid.numCols(); col++)
            if (grid.get(row, col) == num)
                return true;
        return false;
    }

    /*
     * Function: UsedInCol
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * in the specified column matches the given number.
     */
    static boolean UsedInCol(Grid<Integer> grid, int col, int num) {
        for (int row = 0; row < grid.numRows(); row++)
            if (grid.get(row, col) == num)
                return true;
        return false;
    }

    /*
     * Function: UsedInBox
     * -------------------
     * Returns a boolean which indicates whether any assigned entry
     * within the specified 3x3 box matches the given number.
     */
    static boolean UsedInBox(Grid<Integer> grid, int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (grid.get(row + boxStartRow, col + boxStartCol) == num)
                    return true;
        return false;
    }
}