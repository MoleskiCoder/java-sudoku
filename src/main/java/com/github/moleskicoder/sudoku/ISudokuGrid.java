package com.github.moleskicoder.sudoku;

import java.util.Set;

public interface ISudokuGrid extends IGrid<Integer> {
    Set<Integer> getPossibilities(int offset);
    int getOffset(int index);
    int getOffsetCount();
    void eliminate();
}
