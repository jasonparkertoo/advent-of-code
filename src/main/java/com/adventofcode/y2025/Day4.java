package com.adventofcode.y2025;

import com.adventofcode.input.Data;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

record Day4(Data data) {
    private static final Function<List<String>, List<List<String>>> dataTransformer = in -> {
        return in
            .stream()
            .map(str -> Arrays.asList(str.split("")))
            .toList();
    };

    private static final int[][] DIRECTIONS = {
        { -1, -1 },
        { -1, 0 },
        { -1, 1 },
        { 0, -1 },
        { 0, 1 },
        { 1, -1 },
        { 1, 0 },
        { 1, 1 },
    };

    private static final String PAPER_ROLL = "@";

    private int count(int row, int col, List<List<String>> grid) {
        return (int) Arrays.stream(DIRECTIONS)
            .map(d -> new int[] { row + d[0], col + d[1] })
            .filter(
                p ->
                    p[0] >= 0 &&
                    p[0] < grid.size() &&
                    p[1] >= 0 &&
                    p[1] < grid.get(p[0]).size() &&
                    PAPER_ROLL.equals(grid.get(p[0]).get(p[1]))
            )
            .count();
    }

    int countAccessible(int maxPaperRolls) {
        final List<List<String>> transformedData = data.transform(dataTransformer);

        return IntStream.range(0, transformedData.size())
            .map(row ->
                (int) IntStream.range(0, transformedData.get(row).size())
                    .filter(col -> PAPER_ROLL.equals(transformedData.get(row).get(col)))
                    .filter(col -> this.count(row, col, transformedData) <= maxPaperRolls)
                    .count()
            )
            .sum();
    }
}
