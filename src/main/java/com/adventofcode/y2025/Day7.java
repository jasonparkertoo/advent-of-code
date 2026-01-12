package com.adventofcode.y2025;

import com.adventofcode.input.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Advent of Code 2025 – Day 7 solution.
 *
 * <p>The puzzle deals with a grid of cells that can contain either a
 * splitter (&#96;^&#96;) or a normal cell. A beam starts at the position marked
 * by &#96;S&#96; and moves downwards one row per step. When it encounters a
 * splitter, the beam splits into two beams travelling to the left and right
 * neighbouring columns. All other cells simply propagate the existing beam(s)
 * straight down.
 *
 * <p>This class provides two main calculations:
 * <ul>
 *   <li>{@link #countTimelines()} – counts the total number of distinct
 *       timelines (i.e. beams) that reach the bottom row.</li>
 *   <li>{@link #countBeamSplits()} – counts how many times a beam splits
 *       during its traversal.</li>
 * </ul>
 */
record Day7(Data d) {
    /** Symbol used to identify the start position in the input grid. */
    private static final String START_CHARACTER = "S";

    /** Symbol that represents a splitter which splits a beam into two. */
    private static final String SPLITTER_CHARACTER = "^";

    /**
     * Propagates the current beam counts to the next row.
     *
     * @param current mapping of column index → number of beams in that column
     * @param row list of cell symbols for the target row
     * @return a new map containing the updated beam counts for the next row
     */
    Map<Integer, Long> propagateCounts(final Map<Integer, Long> current, final List<String> row) {
        Map<Integer, Long> result = new HashMap<>();
        for (Map.Entry<Integer, Long> entry : current.entrySet()) {
            int col = entry.getKey();
            long val = entry.getValue();
            if (col < 0 || col >= row.size()) continue;
            String cell = row.get(col);
            if (cell.equals(SPLITTER_CHARACTER)) {
                for (int c : new int[] { col - 1, col + 1 }) {
                    if (c >= 0 && c < row.size()) {
                        result.merge(c, val, Long::sum);
                    }
                }
            } else {
                result.merge(col, val, Long::sum);
            }
        }
        return result;
    }

    /** Internal record representing the result of {@link #propagateSplits}. */
    private record Splits(Map<Integer, Integer> next, Integer counts) {}

    /**
     * Propagates beams for a row that may contain splitters.
     *
     * @param current mapping of column index → number of beams in that column
     * @param row list of cell symbols for the target row
     * @return {@link Splits} containing the new beam distribution and the
     *         number of splits that occurred in this row
     */
    Splits propagateSplits(Map<Integer, Integer> current, List<String> row) {
        int[] splitCounter = { 0 };
        Map<Integer, Integer> next = new HashMap<>();
        int size = row.size();
        for (Map.Entry<Integer, Integer> entry : current.entrySet()) {
            int col = entry.getKey();
            if (col < 0 || col >= size) continue;
            int val = entry.getValue();
            String cell = row.get(col);
            if (cell.equals(SPLITTER_CHARACTER)) {
                splitCounter[0]++;
                for (int c : new int[] { col - 1, col + 1 }) {
                    if (c >= 0 && c < size) {
                        next.merge(c, val, Integer::sum);
                    }
                }
            } else {
                next.merge(col, val, Integer::sum);
            }
        }
        return new Splits(next, splitCounter[0]);
    }

    /**
     * Counts how many distinct timelines (beams) reach the bottom of the grid.
     * The start position is taken from the input and a single beam originates
     * there. Subsequent rows are processed until no beams remain or the last
     * row has been reached.
     *
     * @return total number of beams that reach the final row
     */
    long countTimelines() {
        var grid = this.d.asGrid();
        var start = findStart(grid).orElseThrow();
        Map<Integer, Long> current = new HashMap<>() {
            {
                put(start.column(), 1L);
            }
        };

        for (var rowIndex = start.row() + 1; rowIndex < grid.size() && !current.isEmpty(); rowIndex++) {
            current = propagateCounts(current, grid.get(rowIndex));
        }

        return current.values().stream().mapToLong(Long::longValue).sum();
    }

    /** Simple record representing a coordinate in the grid. */
    private record Position(int row, int column) {}

    /**
     * Finds the start position marked by {@value #START_CHARACTER}.
     *
     * @param grid 2‑D list of cell symbols
     * @return an {@link Optional} containing the first matching position or
     *         empty if none is found
     */
    Optional<Position> findStart(List<List<String>> grid) {
        return IntStream.range(0, grid.size())
            .filter(row -> grid.get(row).contains(START_CHARACTER))
            .mapToObj(row -> {
                int col = grid.get(row).indexOf(START_CHARACTER);
                return new Position(row, col);
            })
            .findFirst();
    }

    /**
     * Counts how many times beams split during traversal of the grid.
     * A new beam is spawned for each splitter encountered. The method
     * aggregates all splits across all rows.
     *
     * @return total number of split events that occurred while propagating beams
     */
    int countBeamSplits() {
        List<List<String>> grid = this.d.asGrid();
        Position startPosition = this.findStart(grid).orElseThrow();

        Map<Integer, Integer> current = new HashMap<>() {
            {
                put(startPosition.column(), 1);
            }
        };

        int[] total = { 0 };
        Map<Integer, Integer> curr = new HashMap<>(current);
        IntStream.range(startPosition.row() + 1, grid.size()).forEach(row -> {
            if (!curr.isEmpty()) {
                Splits splits = propagateSplits(curr, grid.get(row));
                total[0] += splits.counts();
                curr.clear();
                curr.putAll(splits.next());
            }
        });

        return total[0];
    }
}
