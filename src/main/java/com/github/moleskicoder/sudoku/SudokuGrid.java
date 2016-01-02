package com.github.moleskicoder.sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class SudokuGrid extends Grid<Integer> implements ISudokuGrid {

    public static final int UNASSIGNED = 0;

    public static final int DIMENSION = 9;
    public static final int BOX_DIMENSION = 3;

    private static final int CELL_COUNT = DIMENSION * DIMENSION;
    private static final int WIDTH = DIMENSION;
    private static final int HEIGHT = DIMENSION;

    private final List<Set<Integer>> possibles = new ArrayList<>();
    private final List<Integer> offsets = new ArrayList<>();

    public SudokuGrid(final Integer[] initial) {

        super(WIDTH, HEIGHT, initial);

        final Set<Integer> numbers = new HashSet<>();
        for (int i = 1; i < DIMENSION + 1; ++i) {
            numbers.add(i);
        }

        for (int offset = 0; offset < CELL_COUNT; ++offset) {
            if (this.get(offset) == UNASSIGNED) {
                this.possibles.add(new HashSet<Integer>(numbers));
            } else {
                this.possibles.add(new HashSet<Integer>());
            }
        }
    }

    @Override
    public Set<Integer> getPossibilities(final int offset) {
        return this.possibles.get(offset);
    }

    @Override
    public int getOffset(final int index) {
        if (index + 1 > this.getOffsetCount()) {
            return -1;
        }
        return this.offsets.get(index);
    }

    @Override
    public int getOffsetCount() {
        return this.offsets.size();
    }

    @Override
    public void eliminate() {
        do {
            this.eliminateAssigned();
            this.eliminateDangling();
        } while (this.transferSingularPossibilities());

        for (int i = 0; i < CELL_COUNT; ++i) {
            final Set<Integer> possible = this.possibles.get(i);
            if (possible.size() > 1) {
                this.offsets.add(i);
            }
        }
    }

    private void eliminateDangling() {
        this.eliminateRowDangling();
        this.eliminateColumnDangling();
        this.eliminateBoxDangling();
    }

    private void eliminateRowDangling() {
        for (int y = 0; y < HEIGHT; ++y) {
            int offset = y * DIMENSION;
            final Map<Integer, List<Integer>> counters = new HashMap<>();
            for (int x = 0; x < WIDTH; ++x) {
                this.adjustPossibleCounters(counters, offset);
                offset++;
            }
            this.transferCountedEliminations(counters);
        }
    }

    private void eliminateColumnDangling() {
        for (int x = 0; x < WIDTH; ++x) {
            int offset = x;
            final Map<Integer, List<Integer>> counters = new HashMap<>();
            for (int y = 0; y < HEIGHT; ++y) {
                this.adjustPossibleCounters(counters, offset);
                offset += DIMENSION;
            }
            this.transferCountedEliminations(counters);
        }
    }

    private void eliminateBoxDangling() {
        for (int y = 0; y < HEIGHT; y += BOX_DIMENSION) {
            for (int x = 0; x < WIDTH; x += BOX_DIMENSION) {
                final Map<Integer, List<Integer>> counters = new HashMap<>();

                final int boxStartX = x - x % BOX_DIMENSION;
                final int boxStartY = y - y % BOX_DIMENSION;

                for (int yOffset = 0; yOffset < BOX_DIMENSION; ++yOffset) {
                    final int boxY = yOffset + boxStartY;
                    int offset = boxStartX + boxY * DIMENSION;
                    for (int xOffset = 0; xOffset < BOX_DIMENSION; ++xOffset) {
                        this.adjustPossibleCounters(counters, offset);
                        offset++;
                    }
                }
                this.transferCountedEliminations(counters);
            }
        }
    }

    private void transferCountedEliminations(final Map<Integer, List<Integer>> counters) {
        for (final Map.Entry<Integer, List<Integer>> counter : counters.entrySet()) {
            final List<Integer> cells = counter.getValue();
            if (cells.size() == 1) {
                final int number = counter.getKey();
                final int cell = cells.get(0);
                final Set<Integer> possible = this.possibles.get(cell);
                possible.clear();
                possible.add(number);
            }
        }
    }

    private void adjustPossibleCounters(final Map<Integer, List<Integer>> counters, final int offset) {
        for (final int possible : this.possibles.get(offset)) {
            List<Integer> counter = counters.get(possible);
            if (counter == null) {
                counter = new ArrayList<>();
                counters.put(possible, counter);
            }
            counter.add(offset);
        }
    }

    private void eliminateAssigned() {
        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                final int number = this.get(x, y);
                if (number != UNASSIGNED) {
                    this.clearRowPossibles(y, number);
                    this.clearColumnPossibles(x, number);
                    this.clearBoxPossibilities(x - x % BOX_DIMENSION, y - y % BOX_DIMENSION, number);
                }
            }
        }
    }

    private boolean transferSingularPossibilities() {
        boolean transfer = false;
        for (int offset = 0; offset < CELL_COUNT; ++offset) {
            final Set<Integer> possible = this.possibles.get(offset);
            if (possible.size() == 1) {
                final Iterator<Integer> possibleIterator = possible.iterator();
                final int singular = possibleIterator.next();
                this.set(offset, singular);
                possible.clear();
                transfer = true;
            }
        }
        return transfer;
    }

    private void clearRowPossibles(final int y, final int number) {
        int offset = y * DIMENSION;
        for (int x = 0; x < WIDTH; ++x) {
            final Set<Integer> possible = this.possibles.get(offset);
            possible.remove(number);
            offset++;
        }
    }

    private void clearColumnPossibles(final int x, final int number) {
        int offset = x;
        for (int y = 0; y < HEIGHT; ++y) {
            final Set<Integer> possible = this.possibles.get(offset);
            possible.remove(number);
            offset += DIMENSION;
        }
    }

    private void clearBoxPossibilities(final int boxStartX, final int boxStartY, final int number) {
        for (int yOffset = 0; yOffset < BOX_DIMENSION; ++yOffset) {
            final int y = yOffset + boxStartY;
            int offset = boxStartX + y * DIMENSION;
            for (int xOffset = 0; xOffset < BOX_DIMENSION; ++xOffset) {
                final Set<Integer> possible = this.possibles.get(offset);
                possible.remove(number);
                offset++;
            }
        }
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
                    output.append(Integer.toString(number, DIMENSION + 1));
                }
                output.append(' ');
                if ((x + 1) % SudokuGrid.BOX_DIMENSION == 0 && x + 1 < width) {
                    output.append('|');
                }
            }
            if ((y + 1) % SudokuGrid.BOX_DIMENSION == 0 && y + 1 < width) {
                output.append("\n --------+---------+--------");
            }
            output.append('\n');
        }
        return output.toString();
    }
}
